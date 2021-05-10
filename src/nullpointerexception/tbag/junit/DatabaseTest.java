package nullpointerexception.tbag.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import nullpointerexception.tbag.actors.Player;
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
	public void testGetIndividualNpc() {
		
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
	
	

}
