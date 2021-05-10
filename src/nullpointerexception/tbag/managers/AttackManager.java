package nullpointerexception.tbag.managers;

import java.util.ArrayList;

import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.Weapon;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class AttackManager extends Manager {
	private CheckStatusManager csm;
	
	public AttackManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		super(commandParams, gm, db);
		
		csm = new CheckStatusManager(gm, db);
		
		if (!gm.getRooms().get(gm.getCurrentRoomIndex()).getIsDark()) {
			executeBattle();
		}
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
					boolean found = false;
					manageUnhostileConditions(npcs.get(i));
					
					if (npcs.get(i).getHostile()) {
						output.add(npcs.get(i).getName() + ": '" + npcs.get(i).getRandomCombatDialogue() + "'");
						
						for (Item item : npcs.get(i).getInventory().getItems()) {
							if (item.getType() == 4) {
								Weapon wm = (Weapon)item;
								
								if (wm.getEquipped()) {
									if (npcs.get(i).getName().equals("dr_scientist")) {
										damage = wm.getDamage()/(gm.getPlayer().getVaccineUseCount()+1);
									}
									else {
										damage = wm.getDamage();
									}
									
									weaponName = wm.getName();
									
									found = true;
									
									break;
								}
							}
						}
						if (!found) {
							damage = 1;
							weaponName = "fist";
						}
							
						
						output.add("!!!! " + npcs.get(i).getName() + " attacked with " + weaponName + " for " + damage + " damage! !!!!");
						
						gm.getPlayer().setHealth(gm.getPlayer().getHealth() - damage);
						
						db.updatePlayer(gm.getPlayer());
						
						csm.checkHealthStatus();
					}
				}
			}
		}
		else {
			if (commandParams.size() > 1) {
				boolean found = false;
				Npc npc = gm.getRooms().get(gm.getCurrentRoomIndex()).getNpc(commandParams.get(1));
				
				if (npc != null) {
					int damage = 0;
					String weaponName = "";
					
					// Determine damage
					for (Item item : gm.getPlayer().getInventory().getItems()) {
						if (item.getType() == 4) {
							Weapon wm = (Weapon)item;
							
							if (wm.getEquipped()) {
								damage = wm.getDamage();
								weaponName = wm.getName();
								
								found = true;
								
								break;
							}
						}
					}
					if (!found) {
						damage = 1;
						weaponName = "fist";
					}
					
					npc.setHealth(npc.getHealth() -  damage);
					
					db.updateNPC(npc);
					
					output.add("You dealt " + damage + " damage to " + npc.getName() + "! ");
					output.add("They have " + npc.getHealth() + " hp left. ");
					
					if (npc.getHealth() > 0) {
						// Turn NPC hostile if not already 
						if (!npc.getHostile()) {
							System.out.print("The npc is being set to hostile: ");
							npc.setHostile(true);
							System.out.println("Npc hostility = " + npc.getHostileString());
							db.updateNPC(npc);
							
							Npc gregory = db.getIndividualNPC("robot_assistant");
							System.out.println("The database sees this npc as: " + gregory.getHostileString());
							System.out.println("The database see this npc's health as: " + gregory.getHealth());
						}
					}
					else {
						output.add("You have defeated '" + npc.getName() + "!' ");
						
						// Drop npc items if their inventory is not empty 
						if (!npc.getInventory().getIsEmpty()) {
							for (Item item : npc.getInventory().getItems()) {
								if (item.getType() == 4) {
									Weapon weapon = (Weapon)item;
									
									weapon.setEquipped(false);
								}
								gm.getRooms().get(gm.getCurrentRoomIndex()).addItem(item);
								db.moveItem(item.getItemId(), gm.getRooms().get(gm.getCurrentRoomIndex()).getInventoryId());
							}
							output.add("It looks like they've dropped some things. ");
						}
						
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
	
	public void manageUnhostileConditions(Npc npc) {
		// Strange man 
		if (npc.getName().equals("strange_man")) {
			if (npc.getInventory().getItem("fluffles'_the_bear") != null) {
				npc.setHostile(false);
				db.updateNPC(npc);
			}
		}
	}
	
	public ArrayList<String> getOutput() {
		return output;
	}
}
