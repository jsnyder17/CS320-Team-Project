package nullpointerexception.tbag.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import nullpointerexception.tbag.actors.Actor;



public class ActorTest {
	private Actor model;
	
	@Before
	public void setup() {
		model = new Actor();
	}
	
	@Test
	public void testSetName() {
		String name = "Test name";
		
		model.setName(name);
		
		assertEquals(name, model.getName());
	}
	@Test
	public void testSetRoomNumber() {
		int roomNumber = 1;
		
		model.setRoomNumber(roomNumber);
		
		assertEquals(roomNumber, model.getCurrentRoom());
	}
	@Test
	public void testSetHealth() {
		int health = 1;
		
		model.setHealth(health);
		
		assertEquals(health, model.getHealth());
	}
}
