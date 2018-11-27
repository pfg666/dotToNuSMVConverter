package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formatter {
	List<Pair<String, String>> rules = new ArrayList<>();
	
 	/**
	 * formats the given template with arguments
	 * @param template the template to format in
	 * @param formatrules String pairs (so an even number of varargs should be supplied) of which
	 * occurrences of the first argument, preceded with an @-sign, in the template are replaced by
	 * the second argument.
	 * @return the template with the formatrules applied
	 */
	public String format(String template, String... formatRules) {
		for (String s : formatRules) {
			if (s == null) {
				throw new RuntimeException("Cannot format null-strings:\n" + Arrays.toString(formatRules));
			}
		}
		addRules(formatRules);
		for (Pair<String, String> rule : this.rules) {
			template = template.replaceAll(Pattern.quote("@" + rule.el1) + "(?![A-Za-z])", Matcher.quoteReplacement(rule.el2));
		}
		return template;
	}
	
	public void addRule(String arg, String replacement) {
		if (arg == null || replacement == null) {
			throw new RuntimeException("Cannot replace '" + arg + "' by '" + replacement + "', null found");
		}
		this.rules.add(new Pair<String, String>(arg, replacement));
	}
	
	public void addRules(String... formatRules) {
		if (formatRules.length % 2 != 0) {
			throw new RuntimeException("an even number of varargs should be supplied to formatRules");
		}
		for (int i = 0; i < formatRules.length; i += 2) {
			addRule(formatRules[i], formatRules[i+1]);
		}
	}
}
