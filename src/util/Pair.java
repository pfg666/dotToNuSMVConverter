package util;

/**
 * Pair of two objects
 * 
 * @param <S>
 *            The type of the first element
 * @param <T>
 *            The type of the second element
 */
public class Pair<S, T> {

	public S el1;
	public T el2;

	public Pair() {
	}

	public <U extends S, V extends T> Pair(U el1, V el2) {
		this.el1 = el1;
		this.el2 = el2;
	}

	@Override
	public String toString() {
		return "<" + el1 + ", " + el2 + ">";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Pair)) {
			return false;
		}
		Pair<?, ?> p = (Pair<?, ?>) o;
		return p.el1.equals(this.el1) && p.el2.equals(this.el2);
	}
	
	@Override
	public int hashCode() {
		return 12*(el1 == null ? 34 : el1.hashCode()) + 56*(el2 == null ? 78 : el2.hashCode());
	}
}
