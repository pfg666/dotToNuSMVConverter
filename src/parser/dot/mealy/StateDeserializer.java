package parser.dot.mealy;

import data.State;
import parser.Deserializer;

public class StateDeserializer implements Deserializer<State>{
	@Override
	public State deserialize(String label) {
		return new State(label);
	}
}
