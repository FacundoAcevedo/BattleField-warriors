import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.FieldCellType;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorManager;
import ia.battle.core.actions.Action;
import ia.battle.core.actions.Attack;
import ia.battle.core.actions.Move;
import ia.exceptions.RuleException;

public class Cthulhu extends Warrior {
	private FieldCell position;
	private Warrior targetWarrior;
	private int attention;
	
	public Cthulhu(String name, int health, int defense, int strength, int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
		// vamos a buscar al enemigo <-
		// si esta en un rango disparable, lo hago
		// si el hunter esta cerca cambio a escape?
Action action;
		
		int closerDistance = Integer.MAX_VALUE, distance;

			try {
				for (Warrior warrior : BattleField.getInstance().getWarriors()) {
					// recorre todos los warriors ( el mismo es uno) y evita al hunter
					if (warrior != this && warrior.getPosition() != BattleField.getInstance().getHunterData().getFieldCell()) {
						distance = computeDistance(this.position, warrior.getPosition());
						// busco el warrior mas cercano y lo setea como objetivo
						
							targetWarrior = warrior;
					
					}
				}


			} catch (RuleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
		
			// lo voy a buscar
			List<FieldCell> adj = BattleField.getInstance().getAdjacentCells(this.position);
			FieldCell origen = this.position;
			FieldCell destino = targetWarrior.getPosition();
			AStar estrellita = new AStar();
			ArrayList<Node> ruta = estrellita.findPath(origen, destino);
			
			Node nodoNext = ruta.get(0);
	
			
			action = new CthulhuMove(nodoNext.getFieldCell());
		

		return action;
	}
		
	

	@Override
	public void wasAttacked(int damage, FieldCell source) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enemyKilled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void worldChanged(FieldCell oldCell, FieldCell newCell) {
		// TODO Auto-generated method stub
		
	}

	private int computeDistance(FieldCell source, FieldCell target) {
		int distance = 0;

		distance = Math.abs(target.getX() - source.getX());
		distance += Math.abs(target.getY() - source.getY());

		return distance;
	}
	

}

class CthulhuMove extends Move {

	private FieldCell nextMove;
	
	CthulhuMove (FieldCell cell) {
		nextMove = cell;
	}

	@Override
	public ArrayList<FieldCell> move() {
		ArrayList<FieldCell> cells = new ArrayList<FieldCell>();
		cells.add(nextMove);
		return cells;
	}
}
