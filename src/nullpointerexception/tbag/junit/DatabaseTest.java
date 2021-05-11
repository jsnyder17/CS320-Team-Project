package nullpointerexception.tbag.junit;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.actors.Player;
import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.items.FinalRoomPuzzle;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.LightSource;
import nullpointerexception.tbag.items.Orb;
import nullpointerexception.tbag.items.Weapon;
import nullpointerexception.tbag.persist.DerbyDatabase;
import nullpointerexception.tbag.rooms.Room;

class DatabaseTest {
	private DerbyDatabase db = null;
	
	@Before
	public void setup() {
		initializeDatabase();
	}
	
	@Test
	public void testOutPut() {
		initializeDatabase();
		
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
		initializeDatabase();
		
		ArrayList<Room> rooms = db.getRooms(); 
		assertEquals(3, rooms.get(2).getRoomId());
		assertEquals(false, rooms.get(0).getNorthDoor().getIsUnlocked());
	}
	
	@Test
	public void testMovePlayer() {
		initializeDatabase();
		
		db.movePlayer(3);
		assertEquals(3, db.getPlayer().getCurrentRoom());
	}
	
	@Test
	public void testUpdatePlayer() {
		initializeDatabase();
		
		Player player = db.getPlayer();
		player.setHealth(50);
		player.setEquippedWeaponIndex(0);
		
		db.updatePlayer(player);
		
		assertEquals(50, db.getPlayer().getHealth());
		assertEquals(0, db.getPlayer().getEquippedWeaponIndex());
	}
	
	@Test
	public void testGetNpc() {
		initializeDatabase();
		
		Npc player = db.getIndividualNPC("wild_ape");
		
		
		assertEquals("wild_ape", player.getName());
	
	}
	
	
	@Test
	public void testUpdateNpc() {
		initializeDatabase();
		
		Npc player = db.getIndividualNPC("wild_ape");
		player.setHealth(10);
		player.setHostile(true);
		
		db.updateNPC(player);
		assertEquals(10, db.getIndividualNPC("wild_ape").getHealth());
		assertEquals(0, db.getIndividualNPC("wild_ape").getEquippedWeaponIndex());
	}
	
	@Test
	public void testMoveItem() {
		initializeDatabase();
		
		ArrayList<Item> first = db.getAllItemsFromLacation(2);
		db.moveItem(2, 2);
		ArrayList<Item> second = db.getAllItemsFromLacation(2);
		
		boolean result = true;
		
		if(first == second) {
			result = false;
		}
		
		assertEquals(true, result);
	}
	
	@Test
	public void testGetNpcNormalDialogue() {
		initializeDatabase();
		
		ArrayList<String> dialogue = db.getNpcNormalDialogue("dying_rick_astley");
		ArrayList<String> dialogue2 = db.getNpcNormalDialogue("robot_assistant");
		
		assertEquals(true, !dialogue.isEmpty());
		assertEquals(true, !dialogue2.isEmpty());
	}
	
	@Test
	public void testGetNpcCombatDialogue() {
		initializeDatabase();
		
		ArrayList<String> dialogue = db.getNpcCombatDialogue("strange_man");
		ArrayList<String> dialogue2 = db.getNpcCombatDialogue("robot_assistant");
		ArrayList<String> dialogue3 = db.getNpcCombatDialogue("wild_ape");
		ArrayList<String> dialogue4 = db.getNpcCombatDialogue("dr_scientist");
		
		assertEquals(true, !dialogue.isEmpty());
		assertEquals(true, !dialogue2.isEmpty());
		assertEquals(true, !dialogue3.isEmpty());
		assertEquals(true, !dialogue4.isEmpty());
	}
	
	@Test
	public void getPlayerItems() {
		initializeDatabase();
		
		ArrayList<Item> items = db.getAllItemsFromLacation(db.getPlayer().getInventoryIndex());
		
		assertEquals(true, items.isEmpty());
	}
	
	@Test
	public void doUpdates() {
		initializeDatabase();
		
		Item item = db.getItembyId(2);
		LightSource ls = (LightSource)db.getItembyId(10);
		Clothing c = (Clothing)db.getItembyId(7);
		Weapon w = (Weapon)db.getItembyId(1);
		Orb o = (Orb)db.getItembyId(11);
		FinalRoomPuzzle frp = (FinalRoomPuzzle)db.getItembyId(16);
		
		Npc npc = db.getIndividualNPC("strange_man");
		
		item.setUsed(true);
		db.updateBaseItem(item);
		
		ls.setUsed(true);
		db.updateLsItem(ls);
		
		c.setUsed(true);
		db.updateClothingItem(c);
		
		w.setUsed(true);
		db.updateWeaponItem(w);
		
		o.setUsed(true);
		db.updateOrbItem(o);
		
		frp.setUsed(true);
		db.updateFrpItem(frp);
		
		npc.setHostile(false);
		db.updateNPC(npc);
		
		assertEquals(true, db.getItembyId(2).getUsed());
		assertEquals(true, db.getItembyId(10).getUsed());
		assertEquals(true, db.getItembyId(7).getUsed());
		assertEquals(true, db.getItembyId(1).getUsed());
		assertEquals(true, db.getItembyId(11).getUsed());
		assertEquals(true, db.getItembyId(16).getUsed());
		assertEquals(false, db.getIndividualNPC("strange_man").getHostile());
	}
	
	private void initializeDatabase() {
		db = new DerbyDatabase();
		db.setUsername("JUnitTests");

		File file = new File("C:/Users/" + System.getProperty("user.name") + "/Documents/JUnitTests.db/");

		if (file.isDirectory()) {
			System.out.println("Dropping tables ... ");
			db.dropTables();
		}
		
		System.out.println("Creating tables ... " );
		db.createTables();
		
		System.out.println("Loading initial data from CSVs ... ");
		db.loadInitialData();
	}
}