package data;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Model {
	public final List<Transition> transitions; 
	public final List<Symbol> inputAlphabet;
	public final Map<String, State> stateMap;
	public final List<Symbol> outputAlphabet;
	public final State init;
	
	public Model(Map<String, State> stateMap, List<Transition> transitions, State init) {
		super();
		this.transitions = transitions;
		inputAlphabet = transitions
				.stream().map(t -> t.getInput())
				.distinct().collect(Collectors.toList());
		outputAlphabet = transitions
				.stream().map(t -> t.getOutput())
				.distinct().collect(Collectors.toList());
		
		this.init = init;
		this.stateMap = stateMap;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	public List<Symbol> getInputAlphabet() {
		return inputAlphabet;
	}

	public Map<String, State> getStateMap() {
		return stateMap;
	}

	public List<Symbol> getOutputAlphabet() {
		return outputAlphabet;
	}
	
	public String toString() {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		pw.println("Input Alphabet: " + inputAlphabet);
		pw.println("Output Alphabet: " + outputAlphabet);
		for (Transition transition : transitions) {
			pw.println(transition);
		}
		pw.close();
		return sw.toString();
	}
	
}