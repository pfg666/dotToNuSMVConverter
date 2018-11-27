package parser;

public interface Deserializer<T> {
	/**
	 * Generates an object from the string, returning the object or null, if the string could not be processed.  
	 */
	public T deserialize(String label);
}
