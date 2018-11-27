package generator;

import java.io.File;

import com.sun.istack.internal.Nullable;

import data.Model;

public interface ModelGenerator {
	/**
	 * Generates code for the model in a format which can be processed by a certain tool.   
	 */
	public void generate(Model model, @Nullable File specification, File modelFile) throws Exception;
	
	public default void generate(Model model, File modelFile) throws Exception {
		generate(model, null, modelFile);
	}
	
}
