package generator.nusmv;

import data.Transition;
import generator.TransitionCodeGenerator;

public class NextStateCodeGenerator implements TransitionCodeGenerator {
	public String getPrefix() {
		return "next(state) := case";
	}
	
	public String generateCode(Transition transition) {
		return "state = " + NuSMVGenerator.nusmvFriendly(transition.getStart()) + 
				" & inp = " + NuSMVGenerator.nusmvFriendly(transition.getInput()) 
		+ ": " + NuSMVGenerator.nusmvFriendly(transition.getEnd()) + ";";
	}
	
	public String getPostfix() {
		return "esac;";
	}
};
