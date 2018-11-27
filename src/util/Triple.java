package util;

public class Triple <S,T,U> {

	public S el1;
	public T el2;
	public U el3;
	
	public Triple() {
	}

	public <X extends S, Y extends T, Z extends U> Triple(X el1, Y el2, Z el3) {
		this.el1 = el1;
		this.el2 = el2;
		this.el3 = el3;
	}

	@Override
	public String toString() {
		return "<" + el1 + ", " + el2 + "," + el3 + ">";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Triple)) {
			return false;
		}
		Triple<?, ?, ?> p = (Triple<?, ?, ?>) o;
		return ((p.el1 == null && this.el1 == null) || p.el1.equals(this.el1))
				&& ((p.el2 == null && this.el2 == null) || p.el2.equals(this.el2))
				&& ((p.el3 == null && this.el3 == null) || p.el3.equals(this.el3));
	}
	
	@Override
	public int hashCode() {
		return 12*(el1 == null ? 34 : el1.hashCode())
			+ 56*(el2 == null ? 78 : el2.hashCode())
			+ 90*(el3 == null ? 13 : el3.hashCode());
	}
}
