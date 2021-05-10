package nullpointerexception.tbag.managers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;
import nullpointerexception.tbag.rooms.Room;

public class GenericManager extends Manager {
	public GenericManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		super(commandParams, gm, db);
		
		System.out.println("GenericManager default constructor called. ");
		System.out.println("gm.currentRoomIndex = " + gm.getCurrentRoomIndex());
		executeCommand();
	}
	
	private void executeCommand() {
		if (commandParams.get(0).equals("examine")) {
			examine();
		}
		else if (commandParams.get(0).equals("inventory")) {
			displayInventory();
		}
		else {
			clearScreen();
		}
	}
	
	private void examine() {
		if (commandParams.size() < 3) {
			if (commandParams.size() == 1) {
				Room room = gm.getRooms().get(gm.getPlayer().getCurrentRoom() - 1);
				output.add(room.getDescription());
			}
			else {
				Item item = gm.getPlayer().getInventory().getItem(commandParams.get(1));
				
				if (item != null) {
					output.add(item.getStringDescription());
				}
				else {
					output.add("I don't think you have that item. ");
				}
			}
		}
		else {
			output.add("What? ");
		}
	}
	
	private void displayInventory() {
		String inventoryStr = "";
		
		inventoryStr += "====== INVENTORY ======\n";
		
		for (int i = 0; i < gm.getPlayer().getInventory().getItemCount(); i++) {
			inventoryStr += (gm.getPlayer().getInventory().getItems().get(i).getName() + "\n");
		}
		inventoryStr += "=======================\nTotal: (" + gm.getPlayer().getInventory().getItemCount() + ") items\n";
		
		output.add(inventoryStr);
	}
	
	private void clearScreen() {
		
	}
}
