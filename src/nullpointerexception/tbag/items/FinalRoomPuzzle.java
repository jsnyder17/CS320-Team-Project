package nullpointerexception.tbag.items;

import nullpointerexception.tbag.inventory.Inventory;
import nullpointerexception.tbag.inventory.InventoryInterface;

public class FinalRoomPuzzle extends Item implements InventoryInterface {
	private Inventory inventory;
	int inventoryId;
	
	public FinalRoomPuzzle() {
		super();
		
		inventory = new Inventory();
		inventoryId = 0;
	}
	
	public void setInventoryId(int inventoryId) {
		this.inventoryId = inventoryId;
	}
	
	public int getInventoryId() {
		return inventoryId;
	}
	
	public boolean insert(Orb orb) {
		boolean success = false;
		
		if (orb.getName().equals("red_orb") || orb.getName().equals("green_orb") || orb.getName().equals("blue_orb")) {
			if (inventory.getItems().size() < 3) {
				addItem(orb);
				success = true;
			}
		}
		
		return success;
	}
	
	public boolean checkOrder() {
		boolean goodOrder = false;
		
		if (inventory.getItems().size() > 2) {
			if (inventory.getItems().get(0).getName().equals("red_orb") && inventory.getItems().get(1).getName().equals("green_orb") && inventory.getItems().get(2).getName().equals("blue_orb")) {
				goodOrder = true;
			}
		}
		
		return goodOrder;
	}
	
	public String fileOutput() {
		String outputStr = "";
		
		outputStr = (super.getName() + "," + super.getUsed() + "," + super.getType());
		
		return outputStr;
	}
	
	public String toString() {
		return (super.getItemId() + "      " + super.getName() + "      " + super.getUsed() + this.inventoryId);
	}
	@Override
	public Inventory getInventory() {
		return inventory;
	}
	@Override
	public void addItem(Item item) {
		inventory.addItem(item);
		
	}
	@Override
	public void removeItem(Item item) {
		inventory.removeItem(item);
	}
}
