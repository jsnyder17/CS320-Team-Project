package nullpointerexception.tbag.junit;

import nullpointerexception.tbag.actors.Player;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
private Player model;
	
	@Before
	public void setup() {
		model = new Player();
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
	
	@Test
	public void testSetVaccineCount() {
		int VaccineCount = 1;
		
		model.setVaccineUseCount(VaccineCount);
		
		assertEquals(VaccineCount, model.getVaccineUseCount());
	}
	@Test
	public void testSetPlayerId() {
		int playerId = 1;
		
		model.setPlayerId(playerId);
		
		assertEquals(playerId, model.getPlayerId());
	}
	
}
