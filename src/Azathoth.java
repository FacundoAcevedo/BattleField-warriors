import java.util.ArrayList;

import ia.battle.core.BattleField;
import ia.battle.core.FieldCell;
import ia.battle.core.Warrior;
import ia.battle.core.actions.Action;
import ia.battle.core.actions.Attack;
import ia.battle.core.actions.Move;
import ia.battle.core.actions.Skip;
import ia.exceptions.RuleException;

public  class Azathoth extends Warrior {
	
	private FieldCell lastPos;
	private boolean yaAtaque;  

	public Azathoth(String name, int health, int defense, int strength, int speed, int range) throws RuleException {
		super(name, health, defense, strength, speed, range);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action playTurn(long tick, int actionNumber) {
		Action action = new Skip();
		//si es la primera accion guardo la poss
		if(actionNumber == 0){
			lastPos = this.getPosition();
		}
		if(BattleField.getInstance().getEnemyData().getInRange()){
			//si ataque y la accion es la 3ra me muevo uno para atras
			if(yaAtaque && actionNumber > 1){
				AStarSingleton estrellita = AStarSingleton.getInstance();
				ArrayList<FieldCell> path = estrellita.findPath(this.getPosition(), lastPos);
				ArrayList<FieldCell> miCamino = new ArrayList<>();
				for (FieldCell n : path) {
					miCamino.add(n);
				}
				yaAtaque = false;
				action = new Move() {
					public ArrayList<FieldCell> move() {
						return miCamino;
					}
				};
				return action;
			}
			//ataco 
			else{
				yaAtaque = true;
				return new Attack(BattleField.getInstance().getEnemyData().getFieldCell());
			}
		}
		// lo voy a buscar
		else {
			AStarSingleton estrellita = AStarSingleton.getInstance();
			ArrayList<FieldCell> ruta = estrellita.findPath(this.getPosition(), BattleField.getInstance().getEnemyData().getFieldCell());
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
