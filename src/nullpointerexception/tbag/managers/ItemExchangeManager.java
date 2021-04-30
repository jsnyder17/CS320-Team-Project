package nullpointerexception.tbag.managers;

import java.util.ArrayList;

import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.LightSource;
import nullpointerexception.tbag.items.Weapon;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class ItemExchangeManager extends Manager {
	public ItemExchangeManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		super(commandParams, gm, db);
		
		System.out.println("ITEM EXCHANGE CALLED. ");
		
		executeCommand();
	}
	private void executeCommand() {
		if (commandParams.get(0).equals("take") || commandParams.get(1).equals("pick-up")) {
			for (int i = 1; i < commandParams.size(); i++) {
				Item item = gm.getRooms().get(gm.getCurrentRoomIndex()).getInventory().getItem(commandParams.get(i));
				
				if (item != null) {
					if (item.getType() != 5) {
						gm.getPlayer().addItem(item);
						db.moveItem(item.getItemId(), gm.getPlayer().getInventoryIndex());
						
						gm.getRooms().get(gm.getCurrentRoomIndex()).removeItem(item);
						
						output.add("Added '" + item.getName() + "' to inventory. ");
					}
					else {
						output.add("You try and struggle but can't seem to get it to budge. ");
					}
				}
				else {
					output.add("I don't think you have that item. ");
				}
			}
		}
		
		
		
		
		else if (commandParams.get(0).equals("drop")) {
			boolean canDrop = false;
			
			for (int i = 1; i < commandParams.size(); i++) {
				Item item = gm.getPlayer().getInventory().getItem(commandParams.get(i));
				
				if (item != null) {
					if (item.getType() == 0 || item.getType() == 1 || item.getType() == 6) {
						canDrop = true;
					}
					else {
						if (item.getType() == 2) {
							// Player must turn off light before dropping 
							LightSource lsm = (LightSource)item;
							
							if (!lsm.getLit()) {
								canDrop = true;
							}
							else {
								output.add("It's never a good idea to leave the lights on. ");
							}
						}
						else if (item.getType() == 3) {
							// If player is wearing item, they must not be wearing it in order to drop it 
							Clothing cm = (Clothing)item;
							
							if (!cm.getWearing()) {
								canDrop = true;
							}
							else {
								output.add("I think you need to take that off first. ");
							}
						}
						else if (item.getType() == 4) {
							Weapon wm = (Weapon)item;
							
							if (!wm.getEquipped()) {
								canDrop = true;
							}
							else {
								output.add("You should never drop your weapon while it is readied. ");
							}
						}
					}
					
					if (canDrop) {
						gm.getPlayer().removeItem(item);
						gm.getRooms().get(gm.getCurrentRoomIndex()).addItem(item);
						
						db.moveItem(gm.getRooms().get(gm.getCurrentRoomIndex()).getInventoryId(), item.getItemId());
						
						output.add("Removed '" + item.getName() + "' from inventory. ");
					}
				}
				else {
					output.add("I don't think you have that item. ");
				}
				
				canDrop = false;
			}
		}
		
		
		
		
		else if (commandParams.get(0).equals("give")) {
			if (commandParams.size() > 2) {
				Item item = gm.getPlayer().getInventory().getItem(commandParams.get(1));
				
				if (item != null) {
					Npc npc = gm.getRooms().get(gm.getCurrentRoomIndex()).getNpc(commandParams.get(2));
					
					if (npc != null) {
						npc.addItem(item);
						gm.getPlayer().removeItem(item);
						
						db.moveItem(npc.getInventoryIndex(), item.getItemId());
						
						output.add("You gave the '" + item.getName() + "' to " + npc.getName() + ". ");
					}
					else {
						output.add("That person isn't there right now. ");
					}
				}
				else {
					output.add("I don't think you have that item. ");
				}
			}
			else {
				output.add("Give what to who? ");
			}
		}
	}
	
	public ArrayList<String> getOutput() {
		return output;
	}
}
