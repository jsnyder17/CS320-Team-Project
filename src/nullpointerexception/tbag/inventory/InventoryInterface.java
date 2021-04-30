package nullpointerexception.tbag.inventory;

import nullpointerexception.tbag.inventory.Inventory;
import nullpointerexception.tbag.items.Item;

public interface InventoryInterface {
	public Inventory getInventory();
	public void addItem(Item item);
	public void removeItem(Item item);
}
