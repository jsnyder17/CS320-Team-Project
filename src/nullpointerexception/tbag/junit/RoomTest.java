package nullpointerexception.tbag.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import nullpointerexception.tbag.actors.Actor;
import nullpointerexception.tbag.inventory.Inventory;
import nullpointerexception.tbag.rooms.Door;
import nullpointerexception.tbag.rooms.Room;



public class RoomTest {
	private Room model;
	
	@Before
	public void setup() {
		model = new Room();
	}
	
	@Test
	public void testRoomId() {
		model.setRoomId(69);
		assertEquals(69, model.getRoomId());
	}
	@Test
	public void testRoomDescription() {
		model.setRoomDescription("test");
		assertEquals("test", model.getRoomDescription());
	}
	@Test
	public void testPrevDiscovered() {
		model.setPrevDiscovered(true);
		assertEquals(true, model.getPrevDiscovered());
	}
	@Test
	public void testPrevDString() {
		model.setPrevDiscovered(true);
		assertEquals("true", model.getPrevDiscoveredString());
	}
	@Test
	public void testSecretDiscovered() {
		model.setSecretDiscovered(true);
		assertEquals(true, model.getSecretDiscovered());
	}
	@Test
	public void testSecretDString() {
		model.setSecretDiscovered(true);
		assertEquals("true", model.getSecretDiscoveredString());
	}
	@Test
	public void testDark() {
		model.setIsDark(true);
		assertEquals(true, model.getIsDark());
	}
	@Test
	public void testDarkString() {
		model.setIsDark(true);
		assertEquals("true", model.getIsDarkString());
	}
	@Test
	public void testRoomNumber() {
		model.setRoomNumber(2);
		assertEquals(2, model.getRoomNumber());
	}
	@Test
	public void testGetDoorLocations() {
		ArrayList<Door> doors = new ArrayList<Door>();
		doors = model.getDoorLocations();
		assertEquals(doors, model.getDoorLocations());
	}
	@Test
	public void getNorthDoor() {
		Door door = new Door();
		model.setNorthDoor(door);
		assertEquals(door, model.getNorthDoor());
	}
	@Test
	public void getSouthDoor() {
		Door door = new Door();
		model.setSouthDoor(door);
		assertEquals(door, model.getSouthDoor());
	}
	@Test
	public void getEastDoor() {
		Door door = new Door();
		model.setEastDoor(door);
		assertEquals(door, model.getEastDoor());
	}
	@Test
	public void getWestDoor() {
		Door door = new Door();
		model.setWestDoor(door);
		assertEquals(door, model.getWestDoor());
	}
	@Test
	public void getDownDoor() {
		Door door = new Door();
		model.setDownDoor(door);
		assertEquals(door, model.getDownDoor());
	}
	@Test
	public void getUpDoor() {
		Door door = new Door();
		model.setUpDoor(door);
		assertEquals(door, model.getUpDoor());
	}
	@Test
	public void getDoorLocationsUp() {
		int location = 1;
		model.setUpDoorId(location);
		assertEquals(location, model.getUpDoorId());
	}
	@Test
	public void getDoorLocationsDown() {
		int location = 1;
		model.setDownDoorId(location);
		assertEquals(location, model.getDownDoorId());
	}
	@Test
	public void getDoorLocationsEast() {
		int location = 1;
		model.setEastDoorId(location);
		assertEquals(location, model.getEastDoorId());
	}
	@Test
	public void getDoorLocationsWest() {
		int location = 1;
		model.setWestDoorId(location);
		assertEquals(location, model.getWestDoorId());
	}
	@Test
	public void getDoorLocationsNorth() {
		int location = 1;
		model.setNorthDoorId(location);
		assertEquals(location, model.getNorthDoorId());
	}
	@Test
	public void getDoorLocationsSouth() {
		int location = 1;
		model.setSouthDoorId(location);
		assertEquals(location, model.getSouthDoorId());
	}
	@Test
	public void testInventoryId() {
		int id = 1;
		model.setInventoryId(id);
		assertEquals(id, model.getInventoryId());
	}
	@Test
	public void testInventory() {
		Inventory inventory = model.getInventory();
		assertEquals(inventory, model.getInventory());
	}
	@Test
	public void testSetName() {
		String name = "Test name";
		
		model.setName(name);
		
		assertEquals(name, model.getName());
	}
}
