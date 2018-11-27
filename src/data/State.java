package data;

public class State {
	private String name;
	
	public State(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof State && ((State) o).name.equals(this.name);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
}