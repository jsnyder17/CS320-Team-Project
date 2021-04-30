package nullpointerexception.tbag.managers;

import java.util.ArrayList;
import java.util.Random;

import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class MovementManager {
	private ArrayList<String> output;
	public MovementManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		output = new ArrayList<String>();
		
		executeCommand(commandParams, gm, db);
	}
	private void executeCommand(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		if (commandParams.size() == 2) {
			boolean canMove = false;
			Random rand = new Random();
			ArrayList<Npc> npcs = gm.getRooms().get(gm.getCurrentRoomIndex()).getNpcs();
			
			// If the room contains hostile npcs, then have them prevent the player from traversing to the next room 
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
			
			if (canMove) {
				move(commandParams.get(1), gm, db);
			}
		}
		else {
			output.add("Move where? ");
		}
	}
	private void move(String direction, GameManagerModel gm, DerbyDatabase db) {
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
	}
	
	public ArrayList<String> getOutput() {
		return output;
	}
}
