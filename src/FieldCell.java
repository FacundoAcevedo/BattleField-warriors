public class FieldCell {
	private int g;
	private int h;
	private FieldCell parent;

	private int x, y;
	
	public FieldCell(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getF() {
		return g + h;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public FieldCell getParent() {
		return parent;
	}

	public void setParent(FieldCell parent) {
		this.parent = parent;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Object obj) {
		FieldCell other = (FieldCell)obj;
		
		return this.x == other.getX() && this.y == other.getY();
	}
	
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
