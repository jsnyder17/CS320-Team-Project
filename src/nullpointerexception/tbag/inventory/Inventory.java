package nullpointerexception.tbag.inventory;

import java.util.ArrayList;

import nullpointerexception.tbag.items.Item;

public class Inventory {
	private ArrayList<Item> items;
	private int itemCount;
	private boolean isEmpty;
	
	public Inventory() {
		items = new ArrayList<Item>();
		itemCount = 0;
		
		checkEmpty();
		
		//System.out.println("InventoryModel default constructor called. ");
	}
	public Inventory(ArrayList<Item> items, int itemCount) {
		for (int i = 0; i < items.size(); i++) {
			this.items.add(items.get(i));
		}
		
		this.itemCount = itemCount;
		
		checkEmpty();
		
		//System.out.println("InventoryModel secondary constructor called. ");
	}
	public Item getItemFromIndex (int index) {
		Item itemAtIndex = null;
		if (index < items.size()) {
			itemAtIndex = items.get(index);
		}
		return itemAtIndex;
	}
	public Item getItem(String itemName) {	// Will return null if no item is found 
		// Find the item in the inventory
		Item searchedItem = null;
		int itemIndex = findItem(itemName);
		
		if (itemIndex != -1) {
			searchedItem = items.get(itemIndex);
		}
		
		return searchedItem;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	public int getItemCount() {
		return itemCount;
	}
	public boolean getIsEmpty() {
		return isEmpty;
	}
	
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}
	
	public void addItem(Item item) {
		items.add(item);
		itemCount += 1;
		
		checkEmpty();
		
		//System.out.println("Added '" + items.get(items.size() - 1).getName() + "' to inventory. ");
	}
	public boolean removeItem(Item item) {
		boolean success = false;
		
		if (itemCount > 0) {
			int itemIndex = findItem(item.getName());
			if (itemIndex != -1) {
				items.remove(itemIndex);
				itemCount -= 1;
				
				checkEmpty();
				
				success = true;
				
				//System.out.println("Removed '" + item.getName() + "' from inventory. ");
			}
		}
		
		return success;
	}
	
	public int findItem(String itemName) {
		boolean found = false;
		int foundIndex = -1;
		int currentIndex = 0;
		
		while (!found && currentIndex < items.size()) {
			if (items.get(currentIndex).getName().equals(itemName)) {
				foundIndex = currentIndex;
				//System.out.println("Found matching item at: " + foundIndex + ". ");
				found = true;
			}
			else {
				currentIndex += 1;
			}
		}
		
		return foundIndex;
	}
	public int findItem(Item item) {
		boolean found = false;
		int foundIndex = -1;
		int currentIndex = 0;
		
		while (!found && currentIndex < items.size()) {
			if (items.get(currentIndex).getName().equals(item.getName())) {
				foundIndex = currentIndex;
				//System.out.println("Found matching item at: " + foundIndex + ". ");
				found = true;
			}
			else {
				currentIndex += 1;
			}
		}
		
		return foundIndex;
	}
	
	private void checkEmpty() {
		if (itemCount == 0) {
			isEmpty = true;
		}
		else {
			isEmpty = false;
		}
		
		//System.out.println("isEmpty: " + isEmpty);
	}
}
