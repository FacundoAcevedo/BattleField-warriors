
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ia.battle.core.BattleField;
import ia.battle.core.ConfigurationManager;
import ia.battle.core.FieldCell;
import ia.battle.core.FieldCellType;

public class AStar {

	// Arra de nodos
	private ArrayList<Node> nodes;
	// Array de nodos [ abiertos y cerrados ]
	private ArrayList<Node> closedNodes, openNodes;

	private Node origin, destination;

	public ArrayList<Node> findPath(FieldCell origen, FieldCell destino) {
		// Enuentra la ruta entre dos puntos
		// Devuelve La lista de nodos por la que pasa

		// defino un array de nodos ( Tipo nodo)
		nodes = new ArrayList<Node>();
		closedNodes = new ArrayList<Node>();
		openNodes = new ArrayList<Node>();
		LogFile.log("== AStar ==");
		// Obtengo todos los field Cells segun el alto y ancho del mapa
		// alias de x
		int ancho = ConfigurationManager.getInstance().getMapWidth();
		// alias de Y
		int alto = ConfigurationManager.getInstance().getMapHeight();

		//
		// A node is added for each passable cell in the map
		// Filtro los puntos del mapa caminables y los recreo en forma de nodo (grafo)
		LogFile.log("AStar: Obtengo los nodos caminables");

		for (int i = 0; i < ancho; i++) // me muevo por el eje X
			for (int j = 0; j < alto; j++) { // me muevo por el eje Y
				// Obtengo el field cell que estoy recorriendo
				FieldCell fieldCellActual = BattleField.getInstance().getFieldCell(i, j);
				if (fieldCellActual.getFieldCellType() == FieldCellType.NORMAL)
					// si el nodo esta caminable/sin pared (?) creo el
					// nodo para procesarlo luego
					nodes.add(new Node(fieldCellActual));
			}

		// obtengo de la lista recien creada, el nodo de origen
		origin = nodes.get(nodes.indexOf(new Node(origen)));
		LogFile.log("ORIGEN: " + origin.toString());

		// lo mismo pero el destino
		destination = nodes.get(nodes.indexOf(new Node(destino)));
		LogFile.log("DESTINO: " + destination.toString());

		// vamoas a iterar sobre currentNode
		Node currentNode = origin;


		while (!currentNode.equals(destination)) {
			processNode(currentNode);
			currentNode = getMinFValueNode();
			LogFile.log("Nodo procesado: " + currentNode.toString());

		}

		// PARA MI EL QUILOMBO ESTA ACA ADENTRO
		ArrayList<Node> nodes = retrievePath();

		// CREE ESTO PARA VER SI LOGUEA; PERO ES COMO QUE NUNCA LLEGA AC�
		if (nodes.isEmpty())
			LogFile.log("NODOS DEL PATH: VACIO");
		else
			LogFile.log("NODOS DEL PATH: NO VACIO");

		for (Node n : nodes) {
			LogFile.log("PATH A SEGUIR: " + n.getFieldCell().toString());
		}

		return retrievePath();
	}

	private ArrayList<Node> retrievePath() {
		ArrayList<Node> path = new ArrayList<Node>();
		Node node = destination;

		while (!node.equals(origin)) {
			path.add(node);
			node = node.getParent();
		}

		Collections.reverse(path);
		return path;
	}

	private void processNode(Node node) {
		// Obtengo los nodos adyacentes al nodo recibido

		// obtengo los adyacentes
		ArrayList<Node> adj = getAdjacentNodes(node);

		// saco de la lista de nodos abiertos el nodo en el que estoy parado
		openNodes.remove(node);
		// y loa grego aca
		closedNodes.add(node);

		for (Node n : adj) {
			// por cada nodo adyacente -> n

			if (closedNodes.contains(n))
				// si n es un nodo cerrado no salgo
				continue;

			// aca calculo H y G
			// Compute the Manhattan distance from node 'n' to destination
			int h = Math.abs(destination.getX() - n.getX());
			h += Math.abs(destination.getY() - n.getY());

			// Compute the distance from origin to node 'n'
			float g = node.getG();
			if (node.getX() == n.getX() || node.getY() == n.getY())
				// Calculamos el costo de moverse en vertical/horizontal o en diagonal
				// TODO: de donde sacamos estos datos
				g += 10;
			else
				g += 14;

			if (!openNodes.contains(n)) {

				n.setParent(node);
				n.setH(h);
				n.setG(g);

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

	private ArrayList<Node> getAdjacentNodes(Node node) {
		ArrayList<Node> adjNodes = new ArrayList<Node>();

		List<FieldCell> adjFieldCells = BattleField.getInstance().getAdjacentCells(node.getFieldCell());

		for (FieldCell n : adjFieldCells) {
			// recorro la lista de field cells adjacentes y las "convierto" a nodos y los
			// agrego a la lista
			adjNodes.add(new Node(n));

		}

		return adjNodes;
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * MazeGenerator mg = new MazeGenerator((40 - 1) / 4, (40 - 1) / 2);
	 * 
	 * AStar a = new AStar(mg.getMaze());
	 * 
	 * System.out.println("The maze to resolve:"); a.printMap();
	 * 
	 * ArrayList<Node> bestPath = a.findPath(1, 1, 35, 37);
	 * 
	 * a.mergePath(bestPath);
	 * 
	 * System.out.println(); System.out.println("The best path:"); a.printMap(); }
	 */
}
