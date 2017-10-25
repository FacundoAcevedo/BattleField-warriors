import java.util.ArrayList;

import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorData;
import ia.battle.core.WarriorManager;
import ia.battle.core.actions.Action;
import ia.battle.core.actions.Move;
import ia.battle.core.actions.Skip;
import ia.exceptions.RuleException;

public class Cthulhu extends Warrior {

	public Cthulhu(String name, int health, int defense, int strength, int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
		// TODO Auto-generated method stub
		Action action = new Skip();

		int closerDistance = Integer.MAX_VALUE, distance;

		try {
			for (Warrior warrior : BattleField.getInstance().getWarriors()) {
				// recorre todos los warriors ( el mismo es uno) y evita al
				// hunter

				if (warrior != this
						&& warrior.getPosition() != BattleField.getInstance().getHunterData().getFieldCell()) {

					Warrior targetWarrior = warrior;
					// lo voy a buscar
					FieldCell origen = this.getPosition();
					FieldCell destino = targetWarrior.getPosition();

					AStarSingleton estrellita = AStarSingleton.getInstance();
					ArrayList<FieldCell> ruta = estrellita.findPath(origen, destino);
					// ESTA ES LA ACCION PARA REALIZAR EL MOVIMIENTO
					action = new Move() {
						@Override
						public ArrayList<FieldCell> move() {
							ArrayList<FieldCell> miCamino = new ArrayList<>();
							for (FieldCell n : ruta) {
								
								miCamino.add(n);
							}

							return miCamino;
						}
					};

				}
			}

			return action;

		} catch (RuleException e) {
			// TODO Auto-generated catch block
		}

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
