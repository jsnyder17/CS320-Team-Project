package nullpointerexception.tbag.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import nullpointerexception.tbag.items.Item;

public class ItemTest {
	private Item item;
	
	@Before
	public void setup() {
		item = new Item();
	}
	
	@Test
	public void testSetName() {
		item.setName("test");
		
		assertTrue(item.getName().equals("test"));
	}
	
	@Test
	public void testSetDescription() {
		ArrayList<String> test = new ArrayList<String>();
		
		test.add("test");
		item.setDescription(test);
		
		assertTrue(item.getDescription().get(0).equals("test"));
	}
	
	@Test
	public void testSetUsed() {
		item.setUsed(false);
		
		assertTrue(!item.getUsed());
	}
	
	@Test
	public void testSetType() {
		item.setType(0);
		
		assertTrue(item.getType() == 0);
	}	
}
