import ia.battle.core.ConfigurationManager;
import ia.battle.core.Warrior;
import ia.battle.core.WarriorManager;
import ia.exceptions.RuleException;

public class Lovecraft extends WarriorManager {
	private int warriorsEnviados = 0;
	
	@Override
	public String getName() {
		return "H.P Lovecraft";
	}

	@Override
	public Warrior getNextWarrior() throws RuleException {
		int maxPoints = ConfigurationManager.getInstance().getMaxPointsPerWarrior();
		Warrior w;
		
		if(warriorsEnviados % 2 == 0)
				w = new Cthulhu("El tulaso", 20, 20, 20, 20, 20);
		else
				w = new Azathoth("El thothulo", 20, 20, 20, 20, 20);
		
		
		warriorsEnviados++;
		
		return w;
	}

}

