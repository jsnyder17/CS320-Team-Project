package nullpointerexception.tbag.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import nullpointerexception.tbag.inventory.Inventory;
import nullpointerexception.tbag.items.Item;


public class InventoryTest{
	private Inventory model;
	
	@Before
	public void setup() {
		model = new Inventory();
	}
	
	@Test
	public void testAddItem() {
		Item item = new Item();
		
		model.addItem(item);
		
		assertTrue(item.equals(model.getItem(item.getName())));
	}
	@Test
	public void testRemoveItem() {
		Item item = new Item();
		int itemCount1 = 0;
		int itemCount2 = 0;
		
		model.addItem(item);
		itemCount1 = model.getItemCount();
		
		model.removeItem(item);
		itemCount2 = model.getItemCount();
		
		assertEquals((itemCount2 + 1), itemCount1);
	}
}
