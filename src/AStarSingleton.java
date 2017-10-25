
import java.util.ArrayList;
import java.util.Collections;

import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.FieldCellType;

public class AStarSingleton {

	//private ArrayList<Node> nodes;
	private ArrayList<Node> closedNodes, openNodes;
	private Node origin, destination;

    //Singleton instancia
	private static AStarSingleton instance = null;
	// constructores
	private AStarSingleton() {
		// Exists only to defeat instantiation.
	}

	public static AStarSingleton getInstance() {
		if(instance == null) {
			instance = new AStarSingleton();
		}
		return instance;
	}


	public ArrayList<FieldCell> findPath(FieldCell desde, FieldCell hasta) {
		//ArrayList<Node> nodes = new ArrayList<Node>();
		closedNodes = new ArrayList<Node>();
		openNodes = new ArrayList<Node>();

		
		origin = new Node(desde);
		destination = new Node(hasta);

		Node currentNode = origin;
		while (!currentNode.equals(destination)) {
			processNode(currentNode);
			currentNode = getMinFValueNode();
		}

		return retrievePath();
	}
	

	private ArrayList<FieldCell> retrievePath() {
		ArrayList<FieldCell> path = new ArrayList<FieldCell>();
		Node node = destination;

		while (!node.equals(origin)) {
			path.add(node.getCelda());
			node = node.getParent();
		}

		Collections.reverse(path);

		return path;
	}

	private void processNode(Node node) {

		// FieldCells adyacentes
		ArrayList<FieldCell> adj = (ArrayList<FieldCell>) BattleField.getInstance().getAdjacentCells(node.getCelda());
		//transformamos a nodos para poder laburarlos
		ArrayList<Node> nodosAdyacentes= new ArrayList<Node>();
		for (FieldCell fc : adj) {
			nodosAdyacentes.add(new Node(fc));
		}
		
		

		openNodes.remove(node);
		closedNodes.add(node);

		for (Node n : nodosAdyacentes) {
			boolean bloqueado = n.getCelda().getFieldCellType().equals(FieldCellType.BLOCKED);

			if (closedNodes.contains(n) || bloqueado)
				continue;

			//Compute the Manhattan distance from node 'n' to destination
			int h = Math.abs(destination.getX() - n.getX());
			h += Math.abs(destination.getY() - n.getY());

			//Compute the distance from origin to node 'n' 
			int g = node.getG();
			if (node.getX() == n.getX() || node.getY() == n.getY())
				g += 10;
			else
				g += 14;

			if (!openNodes.contains(n)) {
				if (n.equals(destination)) {
					destination.setParent(node);
					destination.setH(h);
					destination.setG(g);
				} else {
					n.setParent(node);
					n.setH(h);
					n.setG(g);
				}


				openNodes.add(n);
			} else {

				if (h + g < n.getF()) {

					n.setParent(node);
					n.setH(h);
					n.setG(g);
				}
			}
		}
	}

	private Node getMinFValueNode() {
		Node node = openNodes.get(0);

		for (Node n : openNodes)
			if (node.getF() > n.getF())
				node = n;

		return node;
	}

	
}
