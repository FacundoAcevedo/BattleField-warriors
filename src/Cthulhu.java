import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.FieldCellType;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorManager;
import ia.battle.core.actions.Action;
import ia.battle.core.actions.Attack;
import ia.battle.core.actions.Move;
import ia.battle.core.actions.Skip;
import ia.exceptions.RuleException;

public class Cthulhu extends Warrior {
	private FieldCell position;
	private Warrior targetWarrior;
	private int attention;

	public Cthulhu(String name, int health, int defense, int strength, int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
		// vamos a buscar al enemigo <-
		// si esta en un rango disparable, lo hago
		// si el hunter esta cerca cambio a escape?
		Action action = new Skip();
		
		
		int closerDistance = Integer.MAX_VALUE, distance;
		
		try {
			for (Warrior warrior : BattleField.getInstance().getWarriors()) {
				// recorre todos los warriors ( el mismo es uno) y evita al
				// hunter
				
				
				if (warrior != this
						&& warrior.getPosition() != BattleField.getInstance().getHunterData().getFieldCell()) {
							
					targetWarrior = warrior;
					LogFile.log("Encontre un enemigo: "+targetWarrior.getName());
					LogFile.log(targetWarrior.getName() + " - posicion: "+ targetWarrior.getPosition().toString());

					// lo voy a buscar
					FieldCell origen = this.getPosition();
					FieldCell destino = targetWarrior.getPosition();
					
					LogFile.log(this.getName() + "parado en: "+ origen);
					LogFile.log(targetWarrior.getName()+ " parado en:" + destino);
					AStar estrellita = new AStar();
					LogFile.log("PREVIO A LA ESTRELLITA");
					ArrayList<Node> ruta = estrellita.findPath(origen, destino);
					//NUNCA LLEGA AC�
					LogFile.log("PREVIO AL MOVE");
					//ESTA ES LA ACCION PARA REALIZAR EL MOVIMIENTO
					action = new Move(){
						@Override
						public ArrayList<FieldCell> move() {
							LogFile.log("ENTRE AL MOVE");
							ArrayList<FieldCell> miCamino = new ArrayList<>();
							for(Node n : ruta) {
								FieldCell f = n.getFieldCell();
								miCamino.add(f);
							}
							
							return miCamino;
						}
					};
					
					
					/*
					Node nodoNext = ruta.get(1);
					
					LogFile.log("Me muevo a: " + nodoNext.getY() + nodoNext.getX());



					action = new CthulhuMove(nodoNext.getFieldCell());
					*/
					
					

				}
			}
			
			return action;

		} catch (RuleException e) {
			// TODO Auto-generated catch block
			LogFile.log("ERROR: " +e.getMessage());
		}

		return action;

	}

	@Override
	public void wasAttacked(int damage, FieldCell source) {
		// TODO Auto-generated method stub
		LogFile.log(this.getName() + " - Fuí atacado.");

	}

	@Override
	public void enemyKilled() {
		// TODO Auto-generated method stub
		LogFile.log(this.getName() + "Maté a un enemigo.");


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

	CthulhuMove(FieldCell cell) {
		nextMove = cell;
	}

	@Override
	public ArrayList<FieldCell> move() {
		ArrayList<FieldCell> cells = new ArrayList<FieldCell>();
		cells.add(nextMove);
		return cells;
	}
}
