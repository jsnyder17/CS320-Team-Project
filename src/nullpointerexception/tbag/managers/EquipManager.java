package nullpointerexception.tbag.managers;

import java.util.ArrayList;

import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.Weapon;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class EquipManager extends Manager {
	public EquipManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		super(commandParams, gm, db);
		
		executeCommand();
	}
	
	private void executeCommand() {
		if (commandParams.get(0).equals("equip")) {
			if (commandParams.size() == 2) {
				Item item = gm.getPlayer().getInventory().getItem(commandParams.get(1));
				if (item != null) {
					if (gm.getPlayer().getEquippedWeaponIndex() != -1) {
						output.add("You already have a weapon equipped you goober... ");
					}
					else if (item.getType() == 4) {
						Weapon wm = (Weapon)item;
						
						wm.setEquipped(true);
						db.updateWeaponItem(wm);
						
						gm.getPlayer().setEquippedWeaponIndex(gm.getPlayer().getInventory().findItem(wm));
						db.updatePlayer(gm.getPlayer());
						
						output.add("You equipped the '" + wm.getName() + ".' It has " + wm.getDamage() + " damage. ");
					}
					else {
						output.add("You really want to use that as a weapon? ");
					}
				}
				else {
					output.add("I don't think you have that item. ");
				}
			}
			else {
				output.add("Equip what? ");
			}
		}
		else if (commandParams.get(0).equals("unequip")) {
			if (commandParams.size() == 2) {
				Item item = gm.getPlayer().getInventory().getItem(commandParams.get(1));
				
				if (item != null) {
					if (item.getType() == 4) {
						
						Weapon wm = (Weapon)item;
						
						if (gm.getPlayer().getEquippedWeaponIndex() != -1) {
							wm.setEquipped(false);
							db.updateWeaponItem(wm);
							
							gm.getPlayer().setEquippedWeaponIndex(-1);
							db.updatePlayer(gm.getPlayer());
							
							output.add("You put away the '" + wm.getName() + "'");
						}
						else {
							output.add("You don't have anything equipped right? ");
						}
					}
					else {
						output.add("That's not a weapon. ");
					}
				}
				else {
					output.add("I don't think you have that item. ");
				}
			}
		}
	}
}
