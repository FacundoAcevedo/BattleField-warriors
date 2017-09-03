import ia.battle.core.ConfigurationManager;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorManager;
import ia.exceptions.RuleException;

public class Lovecraft extends WarriorManager {
	private int warriorsEnviados;
	
	@Override
	public String getName() {
		return "H.P Lovecraft";
	}

	@Override
	public Warrior getNextWarrior() throws RuleException {
		int maxPoints = ConfigurationManager.getInstance().getMaxPointsPerWarrior();
		Warrior w;
		
		
		w = new Cthulhu("El Barbaro", 20, 20, 20, 20, 20);
		
		
		warriorsEnviados++;
		
		return w;
	}

}

