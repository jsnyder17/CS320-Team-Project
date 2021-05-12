package nullpointerexception.tbag.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import nullpointerexception.tbag.rooms.Door;

public class DoorTest {
	private Door door;
	
	@Before
	public void setup() {
		door = new Door();
	}
	
	@Test
	public void testSetDescription() {
		door.setDescription("test");
		
		assertTrue(door.getDescription().equals("test"));
	}
	
	@Test
	public void testSetUnlocked() {
		door.setIsUnlocked(true);
		
		assertTrue(door.getIsUnlocked());
	}	
	
	@Test
	public void testSetIsOneWay() {
		door.setIsOneWay(true);
		
		assertTrue(door.getIsOneWay());
	}
	
	@Test
	public void testSetUsed() {
		door.setUsed(true);
		
		assertTrue(door.getUsed());
	}
	
	@Test
	public void testSetDoorKey() {
		door.setDoorKey("key");
		
		assertTrue(door.getDoorKey().equals("key"));
	}
	
	@Test
	public void testSetDirectionString() {
		door.setDirectionString("test");
		
		assertTrue(door.getDirectionString().equals("test"));
	}
}
