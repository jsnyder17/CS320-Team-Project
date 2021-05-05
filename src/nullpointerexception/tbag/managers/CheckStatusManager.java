package nullpointerexception.tbag.managers;

import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class CheckStatusManager extends Manager {
	public CheckStatusManager(GameManagerModel gm, DerbyDatabase db) {
		super(gm, db);
	}
	
	public void checkMaskStatus() {
		Clothing mask = (Clothing)gm.getPlayer().getInventory().getItem("mask");
		
		if (mask != null) {
			if (!mask.getWearing()) {
				// check vaccine status
				output.add("You can almost taste the dirtiness of the air as you enter. ");
				
				// Decrement player health if vaccine has not been used
				if (gm.getPlayer().getVaccineUseCount() < 4) {
					// Decrement player health
					gm.getPlayer().decrementHealth();
					db.updatePlayer(gm.getPlayer());
					checkHealthStatus();
				}
			}
			else {
				output.add("You feel protected by your mask as you enter. ");
			}
		}
		else {
			// Decrement player health if vaccine has not been used
			if (gm.getPlayer().getVaccineUseCount() < 4) {
				// Decrement player health
				gm.getPlayer().decrementHealth();
				db.updatePlayer(gm.getPlayer());
				
				output.add("You can almost taste the dirtiness of the air as you enter. ");
				
				checkHealthStatus();
			}
		}
	}
	
	public boolean checkHealthStatus() {
		boolean dead = false;
		
		if (gm.getPlayer().getHealth() >= 0) {
			dead = true;
		}
		
		return dead;
	}
}
