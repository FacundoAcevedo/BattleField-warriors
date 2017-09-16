import ia.battle.core.FieldCell;

public class Node {
	private float g;
	private int h;
	private FieldCell fieldCell;
	private Node parent;

	private int x, y;
	
	public Node( FieldCell fieldCell) {
	
		this.setFieldCell(fieldCell);
		this.g = fieldCell.getCost();
		this.x = fieldCell.getX();
		this.y = fieldCell.getY();
	}
	
	public float getF() {
		return g + h;
	}

	public float getG() {
		return g;
	}

	public void setG(float g) {
		this.g = g;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Object obj) {
		Node other = (Node)obj;
		
		return this.x == other.getX() && this.y == other.getY();
	}
	
	public String toString() {
		return "[" + x + ", " + y + "]";
	}

	public FieldCell getFieldCell() {
		return fieldCell;
	}

	public void setFieldCell(FieldCell fieldCell) {
		this.fieldCell = fieldCell;
	}

}
