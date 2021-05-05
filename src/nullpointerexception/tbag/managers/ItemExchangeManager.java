package nullpointerexception.tbag.managers;

import java.util.ArrayList;

import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.items.FinalRoomPuzzle;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.LightSource;
import nullpointerexception.tbag.items.Orb;
import nullpointerexception.tbag.items.Weapon;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;
import nullpointerexception.tbag.rooms.Room;

public class ItemExchangeManager extends Manager {
	public ItemExchangeManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		super(commandParams, gm, db);
		
		System.out.println("ITEM EXCHANGE CALLED. ");
		
		executeCommand();
	}
	private void executeCommand() {
		if (commandParams.get(0).equals("take") || commandParams.get(0).equals("pick-up")) {
			if (commandParams.size() > 1) {
				if (!commandParams.get(1).equals("all")) {
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
				else {
					Room room = gm.getRooms().get(gm.getCurrentRoomIndex());
					int index = room.getInventory().getItems().size() - 1;
					
					while (index > -1) {
						Item item = room.getInventory().getItems().get(index);
						
						if (item.getType() != 5) {
		
							gm.getPlayer().addItem(item);
							db.moveItem(item.getItemId(), gm.getPlayer().getInventoryIndex());
							
							room.removeItem(item);
							
							output.add("Added '" + item.getName() + "' to inventory. ");
						}
						else {
							output.add("You try and struggle but can't seem to get it to budge. ");
						}
						
						index -= 1;
					}
				}
			}
			else {
				output.add(commandParams.get(0) + " what? ");
			}
		}
		
		
		
		
		else if (commandParams.get(0).equals("drop")) {
			boolean canDrop = false;
			if (commandParams.size() > 1) {
				if (!commandParams.get(1).equals("all")) {
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
								
								db.moveItem(item.getItemId(), gm.getRooms().get(gm.getCurrentRoomIndex()).getInventoryId());

								output.add("Removed '" + item.getName() + "' from inventory. ");

								canDrop = false;
							}
						}
						else {
							output.add("I don't think you have that item. ");
						}
					}
				}
				else {
					int index = gm.getPlayer().getInventory().getItems().size() - 1;

					while (index > -1) {
						Item item = gm.getPlayer().getInventory().getItems().get(index);

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

							db.moveItem(item.getItemId(), gm.getRooms().get(gm.getCurrentRoomIndex()).getInventoryId());

							output.add("Removed '" + item.getName() + "' from inventory. ");

							canDrop = false;
						}
						
						index -= 1;
					}
				}
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
						updateEquippedWeaponIndex();
						
						//db.moveItem(item.getItemId(), npc.getInventoryIndex());
						
						db.moveItem(item.getItemId(), npc.getInventoryIndex());
						
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
		
		
		
		else if (commandParams.get(0).equals("insert")) {
			if (commandParams.size() > 2) {
				Item item = gm.getPlayer().getInventory().getItem(commandParams.get(1));
				
				if (item != null) {
					if (item.getType() == 6) {
						Orb orb = (Orb)item;
						
						Item frpItem = gm.getRooms().get(gm.getCurrentRoomIndex()).getInventory().getItem(commandParams.get(2));
						if (frpItem != null) {
							if (frpItem.getType() == 5) {
								FinalRoomPuzzle frp = (FinalRoomPuzzle)frpItem;
								
								frp.addItem(orb);
								gm.getPlayer().removeItem(orb);
								
								db.moveItem(orb.getItemId(), frp.getInventoryId());
								
								output.add("The orb fits perfectly into the spherical slots on the device and locks into place. (" + frp.getInventory().getItems().size() + ") orbs in the machine. ");
							}
							else {
								output.add("You can't seem to find a way to insert this. ");
							}
						}
						else {
							output.add("You can't insert something into something that isn't here. ");
						}
					}
					else {
						output.add("It doesn't seem like this is something that should be inserted somewhere. ");
					}
				}
				else {
					output.add("I don't think you have that item. ");
				}
			}
			else {
				output.add("Insert what into what? ");
			}
		}
	}
	
	private void updateEquippedWeaponIndex() {
		for (Item item : gm.getPlayer().getInventory().getItems()) {
			if (item.getType() == 4) {
				Weapon wm = (Weapon)item;
				
				if (wm.getEquipped()) {
					gm.getPlayer().setEquippedWeaponIndex(gm.getPlayer().getInventory().findItem(item));
					db.updatePlayer(gm.getPlayer());
					db.updateWeaponItem(wm);
				}
			}
		}
	}
	
	public ArrayList<String> getOutput() {
		return output;
	}
}

