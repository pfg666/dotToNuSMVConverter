package generator.nusmv;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.Symbol;

public class SpecReader {
	private Set<Symbol> specInputAlphabet;
	private Set<Symbol> specOutputAlphabet;
	private File specFile;
	public static final Pattern COND_MATCH = Pattern.compile("[a-zA-Z0-9_\\+]+\\s*\\!?\\=\\s*[a-zA-Z0-9_\\+]+");
	
	public static List<String> getVarValuations(String varName, String spec) {
		Matcher matcher = COND_MATCH.matcher(spec);
		List<String> valuations = new ArrayList<String>();
		while (matcher.find()) {
			String predString = matcher.group();
			String[] pred = predString.split("\\!?=");
			pred = Arrays.stream(pred).map(s -> s.trim()).toArray(String []::new);
			if (pred[0].equals(varName))
				valuations.add(pred[1]);
			else
				valuations.add(pred[0]);
		}
		
		return valuations;
	}

	public SpecReader(File specFile, String inpVar, String outVar) throws IOException{
		specInputAlphabet = new LinkedHashSet<>();
		specOutputAlphabet = new LinkedHashSet<>();
		this.specFile = specFile;
		Scanner scanner = new Scanner(specFile);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			Matcher matcher = COND_MATCH.matcher(line);
			while (matcher.find()) {
				String predString = matcher.group();
				String[] pred = predString.split("\\!?=");
				if (predString.contains(inpVar))
					if (pred[0].equals(inpVar))
						specInputAlphabet.add(new Symbol(pred[1]));
					else
						specInputAlphabet.add(new Symbol(pred[0]));
				else if (predString.contains(outVar))
					if (pred[0].equals(outVar))
						specOutputAlphabet.add(new Symbol(pred[1]));
					else
						specOutputAlphabet.add(new Symbol(pred[0]));
			}
		}
		scanner.close();
	}
	
	public List<Symbol> getSpecInputAlphabet() {
		return new ArrayList<>(specInputAlphabet);
	}

	public List<Symbol> getSpecOutputAlphabet() {
		return new ArrayList<>(specOutputAlphabet);
	}


	public File getSpecFile() {
		return specFile;
	}
	
	public static void main(String [] args) throws Exception{
		for (String spec : args) {
			SpecReader specReader = new SpecReader(new File(spec), "inp", "out");
			System.out.println("input alphabet: " + specReader.getSpecInputAlphabet());
			System.out.println("output alphabet: " + specReader.getSpecOutputAlphabet());
		}
	}
}
