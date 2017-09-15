import ia.battle.core.FieldCell;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorManager;
import ia.battle.core.actions.Action;
import ia.exceptions.RuleException;

public class Cthulhu extends Warrior {

	public Cthulhu(String name, int health, int defense, int strength, int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
		// vamos a buscar al enemigo <-
		// si esta en un rango disparable, lo hago
		// si el hunter esta cerca cambio a escape?
		
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
