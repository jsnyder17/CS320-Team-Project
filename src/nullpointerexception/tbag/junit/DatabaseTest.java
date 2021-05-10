package nullpointerexception.tbag.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.actors.Player;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.persist.DerbyDatabase;
import nullpointerexception.tbag.rooms.Room;

class DatabaseTest {
	DerbyDatabase db = new DerbyDatabase();
	
	
	
	@Test
	public void testOutPut() {
		System.out.println("creating tables");
		db.setUsername("ian");
		db.dropTables();
		db.createTables();
		System.out.println("creating tables");
		db.loadInitialData();
		System.out.println("loading data");
		
		ArrayList<String> inPut = new ArrayList<String>();
		inPut.add("move");
		inPut.add("go");
		
		db.addOutput(inPut);
		ArrayList<String> outPut = new ArrayList<String>();
		outPut.add("move");
		outPut.add("go");
		ArrayList<String> test = db.getOutput();
		assertEquals(test, outPut);
	}
	
	@Test
	public void testGetRooms() {
		System.out.println("creating tables");
		db.setUsername("ian");
		db.dropTables();
		db.createTables();
		System.out.println("creating tables");
		db.loadInitialData();
		System.out.println("loading data");
		
		ArrayList<Room> rooms = db.getRooms(); 
		assertEquals(3, rooms.get(2).getRoomId());
		assertEquals(false, rooms.get(0).getNorthDoor().getIsUnlocked());
	}
	
	@Test
	public void testMovePlayer() {
		System.out.println("creating tables");
		db.setUsername("ian");
		db.dropTables();
		db.createTables();
		System.out.println("creating tables");
		db.loadInitialData();
		System.out.println("loading data");
		
		db.movePlayer(3);
		assertEquals(3, db.getPlayer().getCurrentRoom());
	}
	
	@Test
	public void testUpdatePlayer() {
		
		System.out.println("creating tables");
		db.setUsername("ian");
		db.dropTables();
		db.createTables();
		System.out.println("creating tables");
		db.loadInitialData();
		System.out.println("loading data");
		
		Player player = db.getPlayer();
		player.setHealth(50);
		player.setEquippedWeaponIndex(0);
		
		db.updatePlayer(player);
		
		assertEquals(50, db.getPlayer().getHealth());
		assertEquals(0, db.getPlayer().getEquippedWeaponIndex());
	}
	
	@Test
	public void testGetNpc() {
		
		System.out.println("creating tables");
		db.setUsername("ian");
		db.dropTables();
		db.createTables();
		System.out.println("creating tables");
		db.loadInitialData();
		System.out.println("loading data");
		
		Npc player = db.getIndividualNPC("wild_ape");
		
		
		assertEquals("wild_ape", player.getName());
	
	}
	
	
	@Test
	public void testUpdateNpc() {
		
		System.out.println("creating tables");
		db.setUsername("ian");
		db.dropTables();
		db.createTables();
		System.out.println("creating tables");
		db.loadInitialData();
		System.out.println("loading data");
		
		Npc player = db.getIndividualNPC("wild_ape");
		player.setHealth(10);
		player.setHostile(true);
		
		db.updateNPC(player);
		assertEquals(10, db.getIndividualNPC("wild_ape").getHealth());
		assertEquals(0, db.getIndividualNPC("wild_ape").getEquippedWeaponIndex());
	}
	
	@Test
	public void testMoveItem() {
		
		System.out.println("creating tables");
		db.setUsername("ian");
		db.dropTables();
		db.createTables();
		System.out.println("creating tables");
		db.loadInitialData();
		System.out.println("loading data");
		
		ArrayList<Item> first = db.getAllItemsFromLacation(2);
		db.moveItem(2, 2);
		ArrayList<Item> second = db.getAllItemsFromLacation(2);
		
		boolean result = true;
		
		if(first == second) {
			result = false;
		}
		
		assertEquals(true, result);
		
	}
	

}
