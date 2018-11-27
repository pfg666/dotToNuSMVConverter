package data;

public class Transition {
	private State start;
	private State end;
	private Symbol input;
	private Symbol output;

	public Transition(State start, State end, Symbol input, Symbol output) {
		super();
		this.start = start;
		this.end = end;
		this.input = input;
		this.output = output;
	}

	public State getStart() {
		return start;
	}

	public State getEnd() {
		return end;
	}

	public Symbol getInput() {
		return input;
	}

	public Symbol getOutput() {
		return output;
	}
	
	public String toString() {
		return String.format("{}[ start={}; end={}; input={} output={} ]", 
				getClass().getSimpleName(), getStart(), getEnd(), getInput(), getOutput());
	}

}
