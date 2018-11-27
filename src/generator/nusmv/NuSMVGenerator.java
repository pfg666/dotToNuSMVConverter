package generator.nusmv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import data.Model;
import data.State;
import data.Symbol;
import data.Transition;
import generator.ModelGenerator;

public class NuSMVGenerator implements ModelGenerator {
	static final String INP_VAR = "inp";
	static final String OUT_VAR = "out";
	
	static String nusmvFriendly(Object element) {
		String output = element.toString();
		output = output.replace("<br />", "/").replace("|", " ");
		output = output.replace("<", "\"").replace(">]", "]");
		output =  output.replaceAll("[\\(\\)]", " ")
				.replaceAll("\\s+&\\s+", "#")
				.replaceAll("[\\s\\(\\)\\.]+", "_")
				.replaceAll("/", "_")
				.replaceAll("_+", "_");
		output = output.replaceAll("\\+", "_");
		output = output.replaceAll("\\,", "_");
		output = output.replaceAll("[\\[\\]]", "");
		if (output.endsWith("_"))
			output = output.substring(0, output.length()-1);
		if (output.startsWith("_"))
			output = output.substring(1, output.length());
		return output;
	}
	
	private OutputCodeGenerator outputGenerator;
	private NextStateCodeGenerator nextStateGenerator;
	private SpecReader specReader;

	public NuSMVGenerator() {
		outputGenerator = new OutputCodeGenerator();
		nextStateGenerator = new NextStateCodeGenerator();
	}
	
	public void generate(Model model, File specification, File modelFile) throws IOException, ParseException {
		List<Symbol> outputAlphabet = model.getOutputAlphabet();
		if (specification != null) {
			specReader = new SpecReader(specification, INP_VAR, OUT_VAR);
			// ugly hack needed for purposeful warning message
			List<Symbol> nusmvInputSymbols = model.getInputAlphabet()
					.stream()
					.map(NuSMVGenerator::nusmvFriendly)
					.map(Symbol::new)
					.collect(Collectors.toList());
			
			if (!nusmvInputSymbols.containsAll(specReader.getSpecInputAlphabet()) ) {
				List<Symbol> unincludedInputs = specReader.getSpecInputAlphabet()
				.stream()
				.filter(inp -> !nusmvInputSymbols.contains(inp))
				.collect(Collectors.toList());
				System.err.println("WARNING: Automaton does not include all inputs found in specification. Unincluded inputs: "
						+ "\n " + unincludedInputs
						);
			}
			
			outputAlphabet = Stream.concat(outputAlphabet.stream(), 
					specReader.getSpecOutputAlphabet().stream()
					).distinct().collect(Collectors.toList());
		}
		
		generateNuSMVModel(model.stateMap, model.init, model.transitions, model.inputAlphabet, outputAlphabet, modelFile);
	}
	
	private void generateNuSMVModel(Map<String, State> stateMap, State init, List<Transition> transitions, List<Symbol> inputAlphabet, List<Symbol> outputAlphabet, 
			File modelFile) throws IOException, ParseException {
		PrintWriter writer = new PrintWriter(new FileWriter(modelFile));
		
		writer.append("MODULE main\r\nVAR state : {");
		boolean firstState = true; // just not to put a comma before the first state
		for (String state : stateMap.keySet()) {
			if (!firstState) {
				writer.append(",");
			}
			firstState = false;
			writer.append(state);
		}
		
		writer.append("};\r\n"+ INP_VAR +" : " + createAlphaString(inputAlphabet)  
		+ ";\r\n"+ OUT_VAR+ " : " + createAlphaString(outputAlphabet));
		
		writer.append(";\r\nASSIGN\r\ninit(state) := " + init.toString() + ";\r\n");
		
		nextStateGenerator.generateTransitionList(transitions, writer);
		outputGenerator.generateTransitionList(transitions, writer);
		
		if (specReader != null) {
			Scanner scanner = new Scanner(specReader.getSpecFile());
			while (scanner.hasNextLine()) {
				writer.println(scanner.nextLine());
			}
			scanner.close();
		}
		
		writer.close();
		
	}
	
	static String createAlphaString(List<Symbol> alpha) {
		String alphaStr = alpha
				.stream()
				.map(s -> nusmvFriendly(s))
				.collect(Collectors.toList())
				.toString();
		alphaStr= "{" + alphaStr.substring(1, alphaStr.length()-1) + "}";
		return alphaStr;
	}
}
