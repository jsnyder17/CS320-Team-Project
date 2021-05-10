package nullpointerexception.tbag.managers;

import java.util.ArrayList;
import java.util.Random;

import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.LightSource;
import nullpointerexception.tbag.persist.DerbyDatabase;
import nullpointerexception.tbag.rooms.Room;

public class MovementManager extends Manager {
	private CheckStatusManager csm;
	
	public MovementManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		super(commandParams, gm, db);
		
		csm = new CheckStatusManager(gm, db);
		
		executeCommand();
	}
	private void executeCommand() {
		if (commandParams.size() == 2) {
			boolean canMove = false;
			Random rand = new Random();
			Room room = gm.getRooms().get(gm.getCurrentRoomIndex());
			ArrayList<Npc> npcs = room.getNpcs();
			
			// If the room contains hostile npcs, then have them prevent the player from traversing to the next room 
			if (!room.getIsDark()) {
				if (npcs.size() > 0) {
					ArrayList<Npc> hostiles = new ArrayList<Npc>();
					
					for (int i = 0; i < npcs.size(); i++) {
						if (npcs.get(i).getHostile()) {
							hostiles.add(npcs.get(i));
							System.out.println("Found hostile npc. ");
						}
					}
					
					if (hostiles.size() > 0) {
						int result = rand.nextInt(100);
						
						if (result > 50) {
							canMove = true;
						}
						else {
							output.add("'" + hostiles.get(rand.nextInt(hostiles.size())).getName() + "' blocks your path! ");
						}
					}
					else {
						canMove = true;
					}
				}
				else {
					canMove = true;
				}
			}
			else {	// Player has 50% chance of moving in the dark 
				int result = rand.nextInt(100);
				
				if (result > 50) {
					canMove = true;
				}
				else {
					output.add("You try to move in the darkness but trip and fall. ");
				}
			}
			
			if (canMove) {
				move(commandParams.get(1), gm, db);
			}
		}
		else {
			output.add("Move where? ");
		}
	}
	private void move(String direction, GameManagerModel gm, DerbyDatabase db) {
		boolean moved = false;
		int currentRoom = gm.getPlayer().getCurrentRoom();
		
		if (direction.equals("north")) {
			if (gm.getRooms().get(gm.getCurrentRoomIndex()).getNorthDoor()!= null) {
				if (gm.getRooms().get(gm.getCurrentRoomIndex()).getNorthDoor().getIsUnlocked()) {
					gm.getRooms().get(gm.getCurrentRoomIndex()).getNorthDoor().setUsed(true);
					db.updateDoor(gm.getRooms().get(gm.getCurrentRoomIndex()).getNorthDoor());
					
					gm.getPlayer().setRoomNumber(gm.getRooms().get(gm.getCurrentRoomIndex()).getNorthDoor().getNextRoom(currentRoom));
					db.updatePlayer(gm.getPlayer());
					
					gm.setCurrentRoomIndex(gm.getPlayer().getCurrentRoom() - 1);
					output.add("You moved north. ");
					
					gm.getRooms().get(gm.getCurrentRoomIndex()).setPrevDiscovered(true);
					db.updateRoom(gm.getRooms().get(gm.getCurrentRoomIndex()));
					
					gm.getRooms().get(gm.getCurrentRoomIndex()).getSouthDoor().setUsed(true);
					db.updateDoor(gm.getRooms().get(gm.getCurrentRoomIndex()).getSouthDoor());
					
					moved = true;
					
				}
				else {
					output.add("It's locked. ");
				}
			}
			else {
				output.add("There's nothing in that direction ... ");
			}
		}
		else if (direction.equals("south")) {
			if (gm.getRooms().get(gm.getCurrentRoomIndex()).getSouthDoor() != null) {
				if (gm.getRooms().get(gm.getCurrentRoomIndex()).getSouthDoor().getIsUnlocked()) {
					gm.getRooms().get(gm.getCurrentRoomIndex()).getSouthDoor().setUsed(true);
					db.updateDoor(gm.getRooms().get(gm.getCurrentRoomIndex()).getSouthDoor());
					
					gm.getPlayer().setRoomNumber(gm.getRooms().get(gm.getCurrentRoomIndex()).getSouthDoor().getNextRoom(currentRoom));
					db.updatePlayer(gm.getPlayer());
					
					gm.setCurrentRoomIndex(gm.getPlayer().getCurrentRoom() - 1);
					output.add("You moved south. ");
					
					gm.getRooms().get(gm.getCurrentRoomIndex()).setPrevDiscovered(true);
					db.updateRoom(gm.getRooms().get(gm.getCurrentRoomIndex()));
					
					gm.getRooms().get(gm.getCurrentRoomIndex()).getNorthDoor().setUsed(true);
					db.updateDoor(gm.getRooms().get(gm.getCurrentRoomIndex()).getNorthDoor());
					
					moved = true;
				}
				else {
					output.add("It's locked. ");
				}
			}
			else {
				output.add("There's nothing in that direction ... ");
			}
		}
		else if (direction.equals("east")) {
			if (gm.getRooms().get(gm.getCurrentRoomIndex()).getEastDoor() != null) {
				if (gm.getRooms().get(gm.getCurrentRoomIndex()).getEastDoor().getIsUnlocked()) {
					gm.getRooms().get(gm.getCurrentRoomIndex()).getEastDoor().setUsed(true);
					db.updateDoor(gm.getRooms().get(gm.getCurrentRoomIndex()).getEastDoor());
					
					gm.getPlayer().setRoomNumber(gm.getRooms().get(gm.getCurrentRoomIndex()).getEastDoor().getNextRoom(currentRoom));
					db.updatePlayer(gm.getPlayer());
					
					gm.setCurrentRoomIndex(gm.getPlayer().getCurrentRoom() - 1);
					output.add("You moved east. ");
					
					gm.getRooms().get(gm.getCurrentRoomIndex()).setPrevDiscovered(true);
					db.updateRoom(gm.getRooms().get(gm.getCurrentRoomIndex()));
					
					gm.getRooms().get(gm.getCurrentRoomIndex()).getWestDoor().setUsed(true);
					db.updateDoor(gm.getRooms().get(gm.getCurrentRoomIndex()).getWestDoor());
					
					moved = true;
				}
				else {
					output.add("It's locked. ");
				}
			}
			else {
				output.add("There's nothing in that direction ... ");
			}
		}
		else if (direction.equals("west")) {
			if (gm.getRooms().get(gm.getCurrentRoomIndex()).getWestDoor() != null) {
				if (gm.getRooms().get(gm.getCurrentRoomIndex()).getWestDoor().getIsUnlocked()) {
					gm.getRooms().get(gm.getCurrentRoomIndex()).getWestDoor().setUsed(true);
					db.updateDoor(gm.getRooms().get(gm.getCurrentRoomIndex()).getWestDoor());
					
					gm.getPlayer().setRoomNumber(gm.getRooms().get(gm.getCurrentRoomIndex()).getWestDoor().getNextRoom(currentRoom));
					db.updatePlayer(gm.getPlayer());
					
					gm.setCurrentRoomIndex(gm.getPlayer().getCurrentRoom() - 1);
					output.add("You moved west. ");
					
					gm.getRooms().get(gm.getCurrentRoomIndex()).setPrevDiscovered(true);
					db.updateRoom(gm.getRooms().get(gm.getCurrentRoomIndex()));
					
					gm.getRooms().get(gm.getCurrentRoomIndex()).getEastDoor().setUsed(true);
					db.updateDoor(gm.getRooms().get(gm.getCurrentRoomIndex()).getEastDoor());
					
					moved = true;
				}
				else {
					output.add("It's locked. ");
				}
			}
			else {
				output.add("There's nothing in that direction ... ");
			}
		}
		else if (direction.equals("up")) {
			if (gm.getRooms().get(gm.getCurrentRoomIndex()).getUpDoor() != null) {
				if (gm.getRooms().get(gm.getCurrentRoomIndex()).getUpDoor().getIsUnlocked()) {
					gm.getPlayer().setRoomNumber(gm.getRooms().get(gm.getCurrentRoomIndex()).getUpDoor().getNextRoom(currentRoom));
					db.updatePlayer(gm.getPlayer());
					
					//gm.setCurrentRoomIndex(gm.getPlayer().getCurrentRoom() - 1);
					output.add("You moved up. ");
					
					gm.getRooms().get(gm.getCurrentRoomIndex()).setPrevDiscovered(true);
					db.updateRoom(gm.getRooms().get(gm.getCurrentRoomIndex()));
					
					moved = true;
				}
				else {
					output.add("It's locked. ");
				}
			}
			else {
				output.add("There's nothing in that direction ... ");
			}
		}
		else if (direction.equals("down")) {
			if (gm.getRooms().get(gm.getCurrentRoomIndex()).getDownDoor() != null) {
				if (gm.getRooms().get(gm.getCurrentRoomIndex()).getDownDoor().getIsUnlocked()) {
					gm.getPlayer().setRoomNumber(gm.getRooms().get(gm.getCurrentRoomIndex()).getDownDoor().getNextRoom(currentRoom));
					db.updatePlayer(gm.getPlayer());
					
					//gm.setCurrentRoomIndex(gm.getPlayer().getCurrentRoom() - 1);
					output.add("You moved down. ");
					
					gm.getRooms().get(gm.getCurrentRoomIndex()).setPrevDiscovered(true);
					db.updateRoom(gm.getRooms().get(gm.getCurrentRoomIndex()));
					
					moved = true;
				}
				else {
					output.add("It's locked. ");
				}
			}
			else {
				output.add("There's nothing in that direction ... ");
			}
		}
		else {
			output.add("I don't know what direction '" + direction + "' is ... ");
		}
		
		// If moved, check the player's mask status 
		if (moved) {
			checkLight();
			
			csm.checkMaskStatus();
			
			Room room = gm.getRooms().get(gm.getPlayer().getCurrentRoom() - 1);
			output.add(room.getDescription());
		}
	}
	private void checkLight() {
		Room room = gm.getRooms().get(gm.getCurrentRoomIndex());
		
		// Check player's inventory for lit item
		for (Item item : gm.getPlayer().getInventory().getItems()) {
			if (item.getType() == 2) {
				LightSource ls = (LightSource)item;
				
				room.setIsDark(!ls.getLit());
				db.updateRoom(room);
				
				break;
			}
		}
		
		// Check room's inventory for lit item 
		for (Item item : room.getInventory().getItems()) {
			if (item.getType() == 2) {
				LightSource ls = (LightSource)item;
				
				room.setIsDark(!ls.getLit());
				db.updateRoom(room);
				
				break;
			}
		}
	}
	
	public ArrayList<String> getOutput() {
		return output;
	}
}
