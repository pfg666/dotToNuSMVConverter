package main;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import data.Model;
import generator.nusmv.NuSMVGenerator;
import parser.dot.mealy.DotModelParser;

public class DotToNuSMVTransformer {

	private File specFile;

	private DotModelParser modelParser;

	private NuSMVGenerator modelGenerator;

	public void addSpecFile(String specFile, String inpVar, String outVar) throws IOException {
		this.specFile = new File(specFile);
	}

	public DotToNuSMVTransformer() {
		modelParser = new DotModelParser();
		modelGenerator = new NuSMVGenerator();
	}

	public void generateNuSMVModel(String inputDotFile, String outputNuSMVFile) throws IOException, ParseException {
		Model model = generateModelFromDotFile(inputDotFile);
		modelGenerator.generate(model, specFile, new File(outputNuSMVFile));
	}

	public Model generateModelFromDotFile(String inputDotFile) throws IOException, ParseException {
		File modelFile = new File(inputDotFile);
		Model model = modelParser.parse(modelFile);
		return model;
	}

	/**
	 * 
	 * Translates Mealy machines to NuSMV models.
	 *
	 * Arguments are paths to .dot or .smv files: .dot files contain Mealy
	 * machines in .dot format from which a NuSMV model is generated in .smv
	 * format. .smv files contain NuSMV specifications which should be appended
	 * to the resulting NuSMV models. This allows for the specification to be
	 * directly executed/checked in NuSMV.
	 *
	 * If no .smv file is provided, no specification is appended. Otherwise,
	 * each .smv specification is appended to NuSMV models obtained from
	 * follow-up .dot files until a new .smv specification is read.
	 *
	 */
	public static void main(String[] args) throws IOException, ParseException {
		DotToNuSMVTransformer transformer = new DotToNuSMVTransformer();
		for (String filePath : args) {
			if (!new File(filePath).exists()) {
				System.err.println(filePath + " does not exist");
			} else if (filePath.endsWith(".dot")) {
				String nuSMVFile = filePath.replace(".dot", ".smv");
				transformer.generateNuSMVModel(filePath, nuSMVFile);
			} else if (filePath.endsWith(".smv")) {
				transformer.addSpecFile(filePath, "inp", "out");
			} else {
				System.err.println(filePath + " does not have a .dot or .smv file extension");
			}
		}
	}

}