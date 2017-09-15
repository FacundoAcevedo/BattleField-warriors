
import java.util.ArrayList;
import java.util.List;

import java.util.Collections;

public class AStar {

    // Array de mapa
	private int[][] map;
	//Arra de nodos
	private List<FieldCell> nodes;
	// Array de nodos [ abiertos y cerrados ]
	private ArrayList<FieldCell> closedNodes, openNodes;
	
	private FieldCell origin, destination;

	public AStar(int[][] map) {
		// Matriz del mapa (enteros)
		// abiertos y cerrados?
		// setter
		
		this.map = map;
	}

	public ArrayList<FieldCell> findPath(int x1, int y1, int x2, int y2) {
		// Enuentra la ruta entre dos puntos
		// Devuelve La lista de nodos por la que pasa
		
		// defino un array de nodos ( Tipo nodo)
		nodes = new ArrayList<FieldCell>();
		closedNodes = new ArrayList<FieldCell>();
		openNodes = new ArrayList<FieldCell>();

		// A node is added for each passable cell in the map
		// Filtro los puntos del mapa caminables y los recreo en forma de nodo (grafo)
		for (int i = 0; i < map.length; i++)
			for (int j = 0; j < map[i].length; j++) {
				// se va moviendo por la primer columna uando i=0 y asi sucesivamente 
				if (map[i][j] == 0)
					// si el nodo esta caminable/sin pared (?) creo el 
					// nodo para procesarlo luego
					nodes.add(new FieldCell(i, j));
			}
			
		// obtengo el nodo (previamente creado ↑ ) a partir de la coordenada pasa 
		// en la firma de la funcion, osea el punto de partida del camino
		origin = nodes.get(nodes.indexOf(new FieldCell(x1, y1)));
		
		
		//lo mismo pero el destino
		destination = nodes.get(nodes.indexOf(new FieldCell(x2, y2)));

		FieldCell currentNode = origin;
		while (!currentNode.equals(destination)) {
			processNode(currentNode);
			currentNode = getMinFValueNode();
		}

		return retrievePath();
	}

	private ArrayList<FieldCell> retrievePath() {
		ArrayList<FieldCell> path = new ArrayList<FieldCell>();
		FieldCell node = destination;

		while (!node.equals(origin)) {
			path.add(node);
			node = node.getParent();
		}

		Collections.reverse(path);

		return path;
	}

	private void processNode(FieldCell node) {
		// Obtengo los nodos adyacentes al nodo recibido

		// obtengo los adyacentes
		ArrayList<FieldCell> adj = getAdjacentNodes(node);

		//saco de la lista de nodos abiertos el nodo en el que estoy parado
		openNodes.remove(node);
		// y lo agrego aca
		closedNodes.add(node);

		for (FieldCell n : adj) {
			// por cada nodo adyacente  -> n

			if (closedNodes.contains(n))
				//si n es un nodo cerrado no salgo
				continue;

			// aca calculo H y G
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

	private FieldCell getMinFValueNode() {
		FieldCell node = openNodes.get(0);

		for (FieldCell n : openNodes)
			if (node.getF() > n.getF())
				node = n;

		return node;
	}

	private ArrayList<FieldCell> getAdjacentNodes(FieldCell node) {
		ArrayList<FieldCell> adjCells = new ArrayList<FieldCell>();

		int x = node.getX();
		int y = node.getY();

		if (nodes.indexOf(new FieldCell(x + 1, y)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new FieldCell(x + 1, y))));

		if (nodes.indexOf(new FieldCell(x, y + 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new FieldCell(x, y + 1))));

		if (nodes.indexOf(new FieldCell(x - 1, y)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new FieldCell(x - 1, y))));

		if (nodes.indexOf(new FieldCell(x, y - 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new FieldCell(x, y - 1))));

		if (nodes.indexOf(new FieldCell(x - 1, y - 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new FieldCell(x - 1, y - 1))));

		if (nodes.indexOf(new FieldCell(x + 1, y + 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new FieldCell(x + 1, y + 1))));

		if (nodes.indexOf(new FieldCell(x - 1, y + 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new FieldCell(x - 1, y + 1))));

		if (nodes.indexOf(new FieldCell(x + 1, y - 1)) >= 0)
			adjCells.add(nodes.get(nodes.indexOf(new FieldCell(x + 1, y - 1))));

		return adjCells;
	}

	private void mergePath(ArrayList<FieldCell> path) {
		for(FieldCell node : path)
			map[node.getX()][node.getY()] = 2;
		
	}
	
	public void printMap() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++)
				switch (map[i][j]) {
				case 0:
					System.out.print("   ");
					break;
					
				case 1:
					System.out.print("XXX");
					break;
					
				case 2:
					System.out.print(" o ");
					break;
				}
				
			System.out.println();
		}
	}

//	public static void main(String[] args) {
//		
//		MazeGenerator mg = new MazeGenerator((40 - 1) / 4, (40 - 1) / 2);
//		
//		AStar a = new AStar(mg.getMaze());
//		
//		System.out.println("The maze to resolve:");
//		a.printMap();
//
//		ArrayList<Node> bestPath = a.findPath(1, 1, 35, 37);
//		
//		a.mergePath(bestPath);
//		
//		System.out.println();
//		System.out.println("The best path:");
//		a.printMap();
//	}
}
