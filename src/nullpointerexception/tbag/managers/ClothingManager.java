package nullpointerexception.tbag.managers;

import java.util.ArrayList;

import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class ClothingManager extends Manager {
	public ClothingManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		super(commandParams, gm, db);
		
		System.out.println("ClothingManager constructor called. ");
		
		executeCommand();
	}
	
	private void executeCommand() {
		if (commandParams.get(0).equals("put-on")) {
			if (commandParams.size() > 1) {
				Item item = gm.getPlayer().getInventory().getItem(commandParams.get(1));
				
				if (item != null) {
					if (item.getType() == 3) {
						Clothing clothing = (Clothing)item;
						
						if (!clothing.getWearing()) {
							clothing.setWearing(true);
							db.updateClothingItem(clothing);
							output.add("You put on the '" + clothing.getName() + ".'");
						}
						else {
							output.add("You're already wearing that. ");
						}
					}
					else {
						output.add("That's not clothing. ");
					}
				}
				else {
					output.add("I don't think you have that item. ");
				}
			}
			else {
				output.add("Put on what? ");
			}
		}
		
		
		
		else if (commandParams.get(0).equals("take-off")) {
			if (commandParams.size() > 1) {
				Item item = gm.getPlayer().getInventory().getItem(commandParams.get(1));
				
				if (item != null) {
					if (item.getType() == 3) {
						Clothing clothing = (Clothing)item;
						
						if (clothing.getWearing()) {
							clothing.setWearing(false);
							db.updateClothingItem(clothing);
							output.add("You took off '" + clothing.getName() + ".' ");
						}
						else {
							output.add("You're not wearing that. ");
						}
					}
					else {
						output.add("That's not clothing. ");
					}
				}
			}
			else {
				output.add("Take off what? ");
			}
		}
	}
}
