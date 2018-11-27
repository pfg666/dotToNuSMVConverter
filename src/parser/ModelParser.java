package parser;

import java.io.File;

import data.Model;

public interface ModelParser {
	/**
	 * Generates a model from a File. 
	 */
	public Model parse(File modelFile);
}
