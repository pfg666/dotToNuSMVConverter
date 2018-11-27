package parser.dot.mealy;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.State;
import data.Transition;

public class DotTransitionParser {
	
	public static final String PATTERN_TRANSITION = "(s[0-9]+) -> (s[0-9]+)\\s*\\[label=\"([^\\s]*)\\s?/\\s?([^\\s]*)\"\\];?";
	
	private Pattern transitionPattern;
	private Map<String, State> states;
	private SymbolDeserializer symbolDeserializer;

	public DotTransitionParser(Map<String, State> states) {
		this.states = states;
		transitionPattern = Pattern.compile(PATTERN_TRANSITION, Pattern.MULTILINE);
		symbolDeserializer = new SymbolDeserializer();
	}
	
	
	public List<Transition> parse(File file) throws FileNotFoundException {
		List<Transition> transitions = new ArrayList<Transition>();
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			
			// quick check
			if (!line.contains("->")) {
				continue;
			}
			
			line = stripLine(line);
			
			Matcher m = transitionPattern.matcher(line);
			while (m.find()) {
				String startString = m.group(1);
				String endString = m.group(2);
				String input = m.group(3);
				String output = m.group(4);
				
				State start = states.get(startString);
				State end = states.get(endString);
				if (start == null) {
					System.err.println("States: " + states.values());
					scanner.close();
					throw new RuntimeException("Could not find begin state '" + startString + "' which appears in transitions");
				}
				if (end == null) {
					System.err.println("States: " + states.values());
					scanner.close();
					throw new RuntimeException("Could not find end state '" + endString + "' which appears in transitions");
				}	
				
				transitions.add(new Transition(
						start,
						end,
						symbolDeserializer.deserialize(input),
						symbolDeserializer.deserialize(output)
						));
			}
		}
		
		scanner.close();
		
		return transitions;
	}
	
	// this is needed for processing XML in old dot format, which contain XML tags
	private String stripLine(String line) {
		String stripped = line
                .replaceAll(
                        "<<table border=\"0\" cellpadding=\"1\" cellspacing=\"0\"><tr><td>",
                        "\"");
        stripped = stripped.replaceAll("</td><td>", " ");
        stripped = stripped.replaceAll("</td></tr></table>>", "\"");
		return  stripped;
	}
}
