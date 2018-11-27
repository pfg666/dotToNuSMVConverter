package parser.dot.mealy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import data.Model;
import data.State;
import data.Transition;
import util.Pair;

public class DotModelParser {
	
	public Model parse(File modelFile) throws FileNotFoundException {
		DotStateParser stateParser = new DotStateParser();
		Pair<State, Map<String, State>> result = stateParser.parse(modelFile);
		State initialState = result.el1;
		Map<String, State> stateMap = result.el2;
		DotTransitionParser transitionParser = new DotTransitionParser(stateMap);
		List<Transition> transitions = transitionParser.parse(modelFile);
		
		return new Model(stateMap, transitions, initialState);
	}
}
