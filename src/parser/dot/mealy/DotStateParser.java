package parser.dot.mealy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.State;
import util.Pair;

public class DotStateParser {
	public static final String PATTERN_SINGLE_STATE = "(s[0-9]+)", 
	PATTERN_INITIAL_STATE = "__start0 -> "+PATTERN_SINGLE_STATE;
	
	
	private Pattern statePattern;
	private Pattern initialStatePattern;

	private StateDeserializer stateSerializer;
	
	public DotStateParser() {
		statePattern = Pattern.compile(PATTERN_SINGLE_STATE, Pattern.DOTALL);
		initialStatePattern = Pattern.compile(PATTERN_INITIAL_STATE);
		stateSerializer = new StateDeserializer();
	}
	
	public Pair<State, Map<String, State>> parse(File file) throws FileNotFoundException {
		Map<String, State> stateMap = new TreeMap<String, State>(
				(s1, s2) -> Integer.valueOf(s1.substring(1)).compareTo(Integer.valueOf(s2.substring(1)))
				);
		State initialState = null;
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			Matcher stateMatcher = statePattern.matcher(line);
			if (stateMatcher.find()) {
				String stateLabel = stateMatcher.group(1);
				if (!stateMap.containsKey(stateLabel)) {
					State state = stateSerializer.deserialize(stateLabel);
					stateMap.put(stateLabel, state);
				}
			}
			
			Matcher initialStateMatcher = initialStatePattern.matcher(line);
			if (initialStateMatcher.find()) {
				String initialStateString = initialStateMatcher.group(1);
				State state = stateSerializer.deserialize(initialStateString);
				if (initialState == null) {
					initialState = state;
				} else {
					if (!state.equals(initialState)) {
						scanner.close();
						throw new RuntimeException(String.format("Multiple initial states found, namely {} and {}", initialState, state));	
					}
				}
			}
		}
		
		if (initialState == null && !stateMap.isEmpty()) {
			initialState = stateMap.values().iterator().next();
		}
		
		scanner.close();
		
		return new Pair<>(initialState, stateMap);
	}
}
