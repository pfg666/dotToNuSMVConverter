package generator;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import data.State;
import data.Transition;
import util.Pair;

/**
 * Generates code for transitions: "generateCode" should be called once for every transition. E.g. if you have the following mealy machine:
 * s0 --- a/x ---> s1
 * s0 --- b/y ---> s1
 * s1 --- a/x ---> s1
 * s1 --- b/x ---> s0
 * Then this code is called four times.
 * 
 * E.g. if you want to generate code that calculates the new state, you can implement this as follows:
 * getPrefix() -> "next(state) := case"       (hardcoded)
 * getPostfix() -> "esac;"       (hardcoded)
 * generateCode(i,start,o,dest) -> "state = <start> & input = <i>: <dest>;"       (substituting the right values for <start>,<i>,<dest>)
 * 
 * This will generate the following code:
 * 
 * next(state) := case
 * state = s0 & input = a: s1;
 * state = s0 & input = b: s1;
 * state = s1 & input = a: s1;
 * state = s1 & input = b: s0;
 * esac;
 * 
 * (For calculating the next output, do it similarly, but now use the output instead of the next state)
 * 
 * @author ramon
 *
 * @param <S> the type of symbols you use (e.g. packets, system calls, etc), which you may need to extract the relevant properties of that symbol.
 */
public interface TransitionCodeGenerator {
	public String generateCode(Transition transition);
	
	public default String getPrefix() {
		return "";
	}
	
	public default String getPostfix() {
		return "";
	}
	
	/**
	 * Main usage of the generator, by supplying it with transitions
	 * generate code for each transition, in an order depending on the inputs: actions first, other transitions second
	 * @param transitions the list of transitions, for each element of which code is generated
	 * @param generator determines what code is generated per transition
	 * @return
	 */
	public default void generateTransitionList(List<Transition> transitions, PrintWriter pw) {
		pw.println(getPrefix());
		for (Transition transition : transitions) {
			String transitionCode = generateCode(transition);
			pw.println(transitionCode);
		}
		pw.println(getPostfix());
	}
}
