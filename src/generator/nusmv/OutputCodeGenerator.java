package generator.nusmv;

import data.Transition;
import generator.TransitionCodeGenerator;

public class OutputCodeGenerator implements TransitionCodeGenerator {
	public String getPrefix() {
		return "out := case";
	}

	public String generateCode(Transition transition) {
		return "state = " + NuSMVGenerator.nusmvFriendly(transition.getStart()) + " & inp = "
				+ NuSMVGenerator.nusmvFriendly(transition.getInput()) + ": "
				+ NuSMVGenerator.nusmvFriendly(transition.getOutput()) + ";";
	}

	public String getPostfix() {
		return "esac;";
	}

};