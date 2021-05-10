package nullpointerexception.tbag.managers;

import java.util.ArrayList;

import nullpointerexception.tbag.items.LightSource;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;
import nullpointerexception.tbag.rooms.Room;

public class LightSourceManager extends Manager {
	public LightSourceManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		super(commandParams, gm, db);
		
		executeCommand();
	}
	
	private void executeCommand() {
		if (commandParams.size() > 1) {
			LightSource ls = (LightSource)gm.getPlayer().getInventory().getItem(commandParams.get(1));
			
			if (ls != null) {
				if (commandParams.get(0).equals("turn-on")) {
					if (!ls.getLit()) {
						ls.setLit(true);
						db.updateLsItem(ls);
						
						Room room = gm.getRooms().get(gm.getCurrentRoomIndex());
						
						room.setIsDark(false);
						db.updateRoom(room);
						
						output.add("You turned on the '" + ls.getName() + "'");
					}
					else {
						output.add("It's already on. ");
					}
				}
				else {
					if (ls.getLit()) {
						ls.setLit(false);
						db.updateLsItem(ls);
						
						output.add("You turned off the '" + ls.getName() + "'");
						
						Room room = gm.getRooms().get(gm.getCurrentRoomIndex());
						
						room.setIsDark(true);
						db.updateRoom(room);
					}
					else {
						output.add("It's already off. ");
					}
				}
			}
			else {
				output.add("I don't think you have that item. ");
			}
		}
		else {
			output.add("Turn on or off what? ");
		}
	}
}
