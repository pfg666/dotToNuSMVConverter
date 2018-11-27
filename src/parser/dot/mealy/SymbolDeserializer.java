package parser.dot.mealy;

import data.Symbol;
import parser.Deserializer;

public class SymbolDeserializer implements Deserializer<Symbol> {
	@Override
	public Symbol deserialize(String label) {
		return new Symbol(label);
	}
}
