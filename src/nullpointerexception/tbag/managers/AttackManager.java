package nullpointerexception.tbag.managers;

import java.util.ArrayList;

import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.items.Weapon;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class AttackManager extends Manager {
	public AttackManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		super(commandParams, gm, db);
		
		executeBattle();
	}
	
	private void executeBattle() {
		if (commandParams == null) {	// Npc attacks 
			ArrayList<Npc> npcs = gm.getRooms().get(gm.getPlayer().getCurrentRoom() - 1).getNpcs();
			
			// Have hostile NPCs attack the player 
			if (npcs.size() > 0) {
				System.out.println("There are NPCs in this room. " + npcs.size());
				int damage = 0;
				String weaponName = "";
				
				for (int i = 0; i < npcs.size(); i++) {
					if (npcs.get(i).getHostile()) {
						output.add(npcs.get(i).getName() + ": '" + npcs.get(i).getRandomCombatDialogue() + "'");
						
						if (npcs.get(i).getEquippedWeaponIndex() != -1) {
							System.out.println("My equipped weapon index is at: " + npcs.get(i).getEquippedWeaponIndex());
							Weapon wm = (Weapon)npcs.get(i).getInventory().getItems().get(npcs.get(i).getEquippedWeaponIndex() - 1);
							
							damage = wm.getDamage();
							weaponName = wm.getName();
						}
						else {
							damage = 1;
							weaponName = "fist";
						}
						
						output.add(npcs.get(i).getName() + " attacked with " + weaponName + " for " + damage + " damage! ");
						
						gm.getPlayer().setHealth(gm.getPlayer().getHealth() - damage);
						
						db.updatePlayer(gm.getPlayer());
					}
				}
			}
		}
		else {
			if (commandParams.size() > 1) {
				Npc npc = gm.getRooms().get(gm.getCurrentRoomIndex()).getNpc(commandParams.get(1));
				
				if (npc != null) {
					int damage = 0;
					String weaponName = "";
					
					// Determine damage
					if (gm.getPlayer().getEquippedWeaponIndex() != -1) {
						Weapon wm = (Weapon)gm.getPlayer().getInventory().getItems().get(gm.getPlayer().getEquippedWeaponIndex()); 
						
						damage = wm.getDamage();
						weaponName = wm.getName();
					}
					else {
						damage = 1;
						weaponName = "fist";
					}
					
					npc.setHealth(npc.getHealth() -  damage);
					
					db.updateNPC(npc);
					
					output.add("You dealt " + damage + " damage to " + npc.getName() + "! ");
					
					if (npc.getHealth() > 0) {
						// Turn NPC hostile if not already 
						if (!npc.getHostile()) {
							npc.setHostile(true);
							db.updateNPC(npc);
						}
					}
					else {
						output.add("You have defeated '" + npc.getName() + "!' ");
						
						// Drop npc items if their inventory is not empty 
						if (!npc.getInventory().getIsEmpty()) {
							for (int i = 0; i < npc.getInventory().getItems().size(); i++) {
								gm.getRooms().get(gm.getCurrentRoomIndex()).addItem(npc.getInventory().getItems().get(i));
								db.moveItem(gm.getRooms().get(gm.getCurrentRoomIndex()).getInventoryId(), npc.getInventory().getItems().get(i).getItemId());
							}
						}
						
						output.add("It looks like they've dropped some things. ");
						
						gm.getRooms().get(gm.getCurrentRoomIndex()).getNpcs().remove(npc);
						db.removeNpc(npc);
						
					}
				}
				else {
					output.add("I don't think that person is here right now. ");
				}
			}
			else {
				output.add("Attack who? ");
			}
		}
	}
	
	public ArrayList<String> getOutput() {
		return output;
	}
}
