package nullpointerexception.tbag.persist;

import java.sql.ResultSet;
import java.util.ArrayList;

import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.LightSource;
import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.actors.Player;
import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.items.Weapon;
import nullpointerexception.tbag.rooms.Door;
import nullpointerexception.tbag.rooms.Room;
import nullpointerexception.tbag.items.FinalRoomPuzzle;
import nullpointerexception.tbag.items.Orb;

public interface IDatabase {
	public ArrayList<LightSource> getLightSource(final String sourceName, final String itemName);
	public ArrayList<Clothing> getClothing(final String sourceName, final String itemName);
	public ArrayList<Weapon> getWeapon(final String sourceName, final String itemName);
	public ArrayList<FinalRoomPuzzle> getFinalRoomPuzzle(final String source, final String itemName);
	public ArrayList<Orb> getOrb(final String source, final String itemName);
	public ArrayList<Door> getDoors();
	public ArrayList<Npc> getNpcs(final int roomNumber);
	public ArrayList<Item> getItems(final String source);
	public ArrayList<Room> getRooms();//returns arrayList of all the rooms with their doors placed in them
	public Player getPlayer();
	public boolean movePlayer(final int moveToo);
	public boolean setPlayerHealth(final int health);
	public boolean setPlayerEquippedWeaponIndex(final int weaponIndex);
	public int getItemType(final int item_id);
	public Npc getIndividualNPC(final String name);
	public boolean setNpcHostile(final String name, final boolean isHostile);
	public boolean setNpcEquippedWeaponIndex(final String name, final int weaponIndex);
	public boolean moveItem(final int item_id, final int newLocation);
	public boolean setNpcHealth(final String name, final int health);
	public boolean setItemIsUsed(final String name,final boolean isUsed);
	public ArrayList<Item> getAllItemsFromLacation(final int location);
	public boolean setWeaponisEquipped(final String name, final boolean isEquipped);
	public boolean setClothingIsWearing(final String name, final boolean isWearing);
	public boolean setLsIsLit(final String name, final boolean isLit);
	public boolean doesHaveItem(final int item_id, final int inventory_id);
	public Integer getItemId(String itemName);
	public boolean unlockDoor(final int roomNumber);
	public Item getItembyId(final int id);
	public boolean updateLsItem(LightSource ls);
	public boolean updateBaseItem(Item item);
	public boolean updateKeyItem(Item item);
	public boolean updateClothingItem(Clothing item);
	public boolean updateWeaponItem(Weapon item);
	public boolean updateFrpItem(FinalRoomPuzzle item);
	public boolean updateOrbItem(Orb item);
	public boolean updatePlayer(Player player);
	public boolean updateNPC(Npc npc);
	public boolean updateRoom(Room room);
	public boolean removeNpc(Npc n);
	public boolean updateDoor(Door door);
	public ArrayList<String> getNpcCombatDialogue(final String npcName);
	public ArrayList<String> getNpcNormalDialogue(final String npcName);
	public void loadInitialData();

}
