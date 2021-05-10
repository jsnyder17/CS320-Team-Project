package nullpointerexception.tbag.managers;

import java.util.ArrayList;

import nullpointerexception.tbag.rooms.Room;
import nullpointerexception.tbag.rooms.Door;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class UseManager extends Manager {	
	public UseManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		super(commandParams, gm, db);
		
		executeCommand();
	}
	
	private void executeCommand() {
		if (commandParams.size() == 2) {
			// Check if item exists
			Item item = gm.getPlayer().getInventory().getItem(commandParams.get(1));
			
			if (item != null) {
				// Determine action depending on item type
				if (item.getType() != 0) {
					if (item.getType() == 1) {	// Key 
						unlockDoor(item);
					}
					else if (item.getType() == 2 || item.getType() == 3 || item.getType() == 4) {
						output.add("How exactly do you use that? ");
					}
				}
				else { 
					if (item.getName().compareTo("map") == 0) {
						System.out.println("Opening map ... ");
						displayMap();
					}
					else if (item.getName().compareTo("letter") == 0) {
						displayLetter();
					}
					else if (item.getName().equals("vaccine")) {	// Vaccine
						if (!item.getUsed()) {
							item.setUsed(true);
							gm.getPlayer().setVaccineUseCount(gm.getPlayer().getVaccineUseCount() + 1);
							output.add("You used the vaccine. ");
							
							db.updatePlayer(gm.getPlayer());
						}
						else {
							output.add("This dose of the vaccine has already been used. ");
						}
					}
					else if (item.getName().equals("red_orb") || item.getName().equals("green_orb") || item.getName().equals("blue_orb")) {
						output.add("How do you use this? ");
					}
					else {
						output.add("I don't think you have that item. ");
					}
				}
			}
			else {
				output.add("I don't think you have that item. ");
			}
		}
		else {
			output.add("Use what? ");
		}
	}
	
	private void displayLetter() {
		String bookString = "";
		
		bookString += "==========================================================================================\n\n";
		bookString += "To anyone reading this, \n\n";
		bookString += "It's all a lie. They've had them since day one and kept them from us.\n\n ";
		bookString += "Please, if you find this letter, you must leave with at least one vaccine \n";
		bookString += "in hand. The world needs you. I don't think I have much longer, so it's \n";
		bookString += "up to you. I wrote down everything you need to know below. Good luck.\n\n";
		bookString += "'move <direction>' (north, south, east, west, up, or down) - Move in specified direction. \n";
		bookString += "'examine' - Get a description of your surroundings. \n";
		bookString += "'inventory' - View a list of your items. \n";
		bookString += "'use <item name>' - Use an item in your inventory. \n";
		bookString += "'pickup <item name 1, item name 2, ...>' OR 'pickup all' - Add an item you see to your inventory. \n";
		bookString += "'take <item name 1, item name 2, ...>' OR 'take all' - Add an item you see to your inventory. \n";
		bookString += "'drop <item name 1, item name 2, ...>' OR 'drop all' - Drop the specified items. \n";
		bookString += "'insert <item name> <object name>' - Insert the specified item into an object in the room. \n";
		bookString += "'give <item name> <entity name>' - Give the specified item to an entity in the room. \n";
		bookString += "'put-on <item name>' - Wear an item in your inventory. \n";
		bookString += "'take-off <item name>' - Take off an item you're wearing. \n";
		bookString += "'turn-on <item name>' - Turn on a light source that you have. \n";
		bookString += "'turn-off <item name>' - Turn off a light source that you have. \n";
		bookString += "'equip <item name>' - Equip a weapon in your inventory. \n";
		bookString += "'unequip <item name>' - Unequip your currently equipped weapon. \n";
		bookString += "'attack <entity name> - Attack an entity with your currently equipped weapon. \n";
		bookString += "'talk <entity name> - Have a friendly chat with another in the room. \n";
		bookString += "'clear' - Clear your mind to relieve stress. \n";
		bookString += "'reset' - Gain the power of God and restart everything. \n\n";
		bookString += "-- Rick Astley \n\n";
		bookString += "P.S. \n";
		bookString += "If you end up not making it out, we're never gonna give you up here in \n";
		bookString += "the afterlife, so it won't be too bad. \n";
		bookString += "==========================================================================================\n\n";
		
		output.add(bookString);
	}
	
	private void unlockDoor(Item item) {
		boolean found = false;
		int index = 0;
		
		// Unlock appropriate door
		while (index < 6 && !found) {
			if ((gm.getRooms().get(gm.getCurrentRoomIndex()).getDoorLocations().get(index) != null) && (gm.getRooms().get(gm.getCurrentRoomIndex()).getDoorLocations().get(index).getDoorKey().compareTo(item.getName()) == 0)) {
				System.out.println("Found door. ");
				gm.getRooms().get(gm.getCurrentRoomIndex()).getDoorLocations().get(index).unlock();
				
				db.updateDoor(gm.getRooms().get(gm.getCurrentRoomIndex()).getDoorLocations().get(index));
				
				output.add("You unlocked the door with the '" + item.getName() + ". ");
				System.out.println("Finished unlocking door. ");
				System.out.println("isUnlocked: " + gm.getRooms().get(gm.getCurrentRoomIndex()).getDoorLocations().get(index).getIsUnlocked());
				
				found = true;
			}
			else {
				index += 1;
			}
		}
		if (!found) {
			output.add("You cannot unlock any doors in this room with that key. ");
		}
	}
	
	private void displayMap() {
		String[] mapRows = new String[7];
		String mapDisplay = "";
		
		mapRows[0] = formatEastWest(new Room[] {gm.getRooms().get(1), gm.getRooms().get(2)}, new int[] {3});
		mapRows[1] = formatNorthSouth(new Room[] {gm.getRooms().get(1), gm.getRooms().get(2)}, new int[] {3});
		mapRows[2] = formatEastWest(new Room[] {gm.getRooms().get(0), gm.getRooms().get(4), gm.getRooms().get(3)}, new int[] {1, 1});
		mapRows[3] = formatNorthSouth(new Room[] {gm.getRooms().get(0), gm.getRooms().get(4), gm.getRooms().get(3)}, new int[] {1, 1});
		mapRows[4] = "  " + (formatEastWest(new Room[] {gm.getRooms().get(5), gm.getRooms().get(6), gm.getRooms().get(7)}, new int[] {1, 1}));
		mapRows[5] = "  " + (formatNorthSouth(new Room[] {gm.getRooms().get(5), gm.getRooms().get(6), gm.getRooms().get(7)}, new int[] {1, 1}));
		mapRows[6] = "      " + (formatEastWest(new Room[] {gm.getRooms().get(8), gm.getRooms().get(9)}, new int[] {1}));
		
		for (int i = 0; i < mapRows.length; i++) {
			mapDisplay += (mapRows[i] + "\n");
		}
		
		output.add(mapDisplay);
	}
	
	
	private String formatEastWest(Room[] rooms, int[] distances) { //int[] offsets{distance between room1 and room2, distance between room2 and room3, etc ...}
		String formattedRowString = "";
		String roomString = "";
		String pathString = "";
		String[] rowString = new String[rooms.length];
		int distanceIndex = 0;
		
		for (int i = 0; i < rooms.length; i++) {
			if (rooms[i].getPrevDiscovered()) {
				roomString = "X";

				// Evaluate paths
				if (rooms[i].getEastDoor() != null) {
					if (rooms[i].getEastDoor().getUsed()) {
						pathString = "-";
					}
					else {
						pathString = " ";
					}
				}
				else {
					pathString = " ";
				}
				
				
			}
			else {
				roomString = " ";
				pathString = " ";
			}
			
			rowString[i] = roomString;
			
			if (distanceIndex < distances.length) { 
				for (int j = 0; j < distances[distanceIndex]; j++) {
					rowString[i] += pathString;
				}
				
				distanceIndex += 1;
			}
		}
		
		
		for (int i = 0; i < rowString.length; i++) {
			formattedRowString += rowString[i];
		}
		
		return formattedRowString;

	}
	private String formatNorthSouth(Room[] rooms, int[] distances) {
		String formattedRowString = "";
		String pathString = "";
		String[] rowString = new String[rooms.length];
		int distanceIndex = 0;
		
		for (int i = 0; i < rooms.length; i++) {
			if (rooms[i].getSouthDoor() != null) {
				if (rooms[i].getSouthDoor().getUsed()) {
					pathString = "|";
				}
				else {
					pathString = " ";
				}
			}
			else {
				pathString = " ";
			}
			
			rowString[i] = pathString;
			
			if (distanceIndex < distances.length) { 
				for (int j = 0; j < distances[distanceIndex]; j++) {
					rowString[i] += " ";
				}
				
				distanceIndex += 1;
			}
		}
		
		
		for (int i = 0; i < rowString.length; i++) {
			formattedRowString += rowString[i];
		}
		
		return formattedRowString;
	}
}
