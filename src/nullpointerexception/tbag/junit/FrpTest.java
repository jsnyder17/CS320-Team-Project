package nullpointerexception.tbag.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import nullpointerexception.tbag.items.FinalRoomPuzzle;
import nullpointerexception.tbag.items.Orb;

public class FrpTest {
	private FinalRoomPuzzle frp;
	
	@Before
	public void setup() {
		frp = new FinalRoomPuzzle();
	}
	
	@Test
	public void testInsertItem() {
		Orb orb = new Orb();
		
		frp.addItem(orb);
		
		assertTrue(frp.getInventory().getItems().size() > 0);
	}
	
	@Test
	public void testSetName() {
		frp.setName("test");
		
		assertTrue(frp.getName().equals("test"));
	}
	
	@Test
	public void testSetDescription() {
		ArrayList<String> test = new ArrayList<String>();
		
		test.add("test");
		frp.setDescription(test);
		
		assertTrue(frp.getDescription().get(0).equals("test"));
	}
	
	@Test
	public void testSetUsed() {
		frp.setUsed(false);
		
		assertTrue(!frp.getUsed());
	}
	
	@Test
	public void testSetType() {
		frp.setType(0);
		
		assertTrue(frp.getType() == 0);
	}	
}
