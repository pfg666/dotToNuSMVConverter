package data;

public class Symbol {
	private String name;
	
	public Symbol(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Symbol && ((Symbol) o).name.equals(this.name);
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}
	
}
