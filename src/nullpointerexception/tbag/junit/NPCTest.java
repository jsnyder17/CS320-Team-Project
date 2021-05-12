package nullpointerexception.tbag.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import nullpointerexception.tbag.actors.Actor;
import nullpointerexception.tbag.actors.Npc;



public class NPCTest {
	private Npc model;
	
	@Before
	public void setup() {
		model = new Npc();
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
	public void testNpcId() {
		int id = 1;
		
		model.setActorId(id);
		
		assertEquals(id, model.getActorId());
	}
	@Test
	public void testNpcHostile() {
		boolean hostile = true;
		
		model.setHostile(hostile);
		
		assertEquals(hostile, model.getHostile());
	}
	@Test
	public void testNormalDialog() {
		ArrayList<String> dialog = new ArrayList<String>();
		
		dialog.add("Test");
		
		model.setNormalDialogue(dialog);
		
		assertEquals("Test", model.getRandomNormalDialogue());
	}
	@Test
	public void testCombatDialog() {
		ArrayList<String> dialog = new ArrayList<String>();
		
		dialog.add("Test");
		
		model.setCombatDialogue(dialog);
		
		assertEquals("Test", model.getRandomCombatDialogue());
	}
}
