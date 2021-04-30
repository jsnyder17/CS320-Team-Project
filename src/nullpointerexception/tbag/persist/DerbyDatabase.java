package nullpointerexception.tbag.persist;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.actors.Player;
import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.items.FinalRoomPuzzle;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.LightSource;
import nullpointerexception.tbag.items.Orb;
import nullpointerexception.tbag.items.Weapon;
import nullpointerexception.tbag.other.Pair;
import nullpointerexception.tbag.persist.DerbyDatabase.Transaction;
import nullpointerexception.tbag.rooms.Door;
import nullpointerexception.tbag.rooms.Room;

public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}

	public interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;

	// The main method creates the database tables and loads the initial data.
	public static void main(String[] args) throws IOException {
		System.out.println("Creating tables...");
		DerbyDatabase db = new DerbyDatabase();
		db.dropTables();
		db.createTables();

		System.out.println("Loading initial data...");
		db.loadInitialData();

		System.out.println("TBAG DB successfully initialized!");
		
		ArrayList<Npc> npcs = db.getNpcs(-1);
		for (Npc npc : npcs) {
			System.out.println(npc.toString());
		}
	}

	public void createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@SuppressWarnings("resource")
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				// dropTables();

				try {
					stmt = conn.prepareStatement("create table itemsList(item_id int primary key generated always as identity (start with 1, increment by 1), item_type int not null)");

					stmt.executeUpdate();

					// System.out.println("itemsList table created. ");

					stmt = conn.prepareStatement("create table itemsMap(item_id int constraint itemsMap_item_id references itemsList, item_location int)");

					stmt.executeUpdate();

					// System.out.println("itemsMap table created. ");

					stmt = conn.prepareStatement("create table baseItems(item_id int constraint baseItems_item_id references itemsList, item_name varchar(40), isUsed boolean)");

					stmt.executeUpdate();

					// System.out.println("baseItems table created. ");

					stmt = conn.prepareStatement("create table keyItems(item_id int constraint keyItems_item_id references itemsList, item_name varchar(40), isUsed boolean)");

					stmt.executeUpdate();

					// System.out.println("keyItems table created. ");

					stmt = conn.prepareStatement("create table lsItems(item_id int constraint lsItems_item_id references itemsList, item_name varchar(40), isUsed boolean, isLit boolean)");

					stmt.executeUpdate();

					// System.out.println("lsItems table created. ");

					stmt = conn.prepareStatement("create table clothingItems(item_id int constraint clothingItems_item_id references itemsList, item_name varchar(40), isUsed boolean, isWearing boolean)");

					stmt.executeUpdate();

					// System.out.println("clothingItems table created. ");

					stmt = conn.prepareStatement("create table weaponItems(item_id int constraint weaponItems_item_id references itemsList, item_name varchar(40), isUsed boolean, damage int, isEquipped boolean)");

					stmt.executeUpdate();

					// System.out.println("weaponItems table created. ");

					stmt = conn.prepareStatement("create table frpItems(item_id int constraint frpItems_item_id references itemsList, item_name varchar(40), isUsed boolean)");

					stmt.executeUpdate();

					// System.out.println("frpItems table created. ");

					stmt = conn.prepareStatement("create table orbItems(item_id int constraint orbItems_item_id references itemsList, item_name varchar(40), isUsed boolean)");

					stmt.executeUpdate();

					// System.out.println("orbItems table created. ");

					stmt = conn.prepareStatement("create table playerList(actor_id int primary key generated always as identity (start with 1, increment by 1), actor_name varchar(40), current_room int, health int, vaccineUseCount int, equippedWeaponIndex int, inventory_index int)");

					stmt.executeUpdate();

					// System.out.println("playerList table created. ");

					stmt = conn.prepareStatement("create table npcList(npc_id int primary key generated always as identity (start with 1, increment by 1), actor_name varchar(40), current_room int, health int, equippedWeaponIndex int, isHostile boolean, inventory_index int)");

					stmt.executeUpdate();

					// System.out.println("npcList table created. ");

					stmt = conn.prepareStatement("create table doorsList(door_id int primary key generated always as identity (start with 1, increment by 1), is_unlocked boolean, door_desc varchar(255), isOneWay boolean, isUsed boolean, doorKeyName varchar(255), roomCoord1 int, roomCoord2 int)");

					stmt.executeUpdate();

					// System.out.println("doorsList table created. ");

					stmt = conn.prepareStatement("create table roomsList(room_id int primary key generated always as identity (start with 1, increment by 1), room_name varchar(40), room_desc long varchar,  prevDiscovered boolean, secretDiscovered boolean, isDark boolean, inventory int, door_north int, door_south int, door_east int, door_west int, door_up int, door_down int)");

					stmt.executeUpdate();

					// System.out.println("roomsList table created. ");

					stmt = conn.prepareStatement("create table outputList(output_data long varchar)");

					stmt.executeUpdate();

					// System.out.println("output table created. ");

					stmt = conn.prepareStatement("create table userInfo(user_id int primary key generated always as identity (start with 1, increment by 1), user_name varchar(40), password varchar(40))");

					stmt.executeUpdate();

					// System.out.println("userInfo table created. ");

					stmt = conn.prepareStatement("create table npcCombatDialogList(npc_id int constraint npcCombatDialogList_npc_id references npcList, dialogue varchar(6500))");

					stmt.executeUpdate();

					// System.out.println("npcCombatDialogList table created. ");

					stmt = conn.prepareStatement("create table npcNormalDialogList(npc_id int constraint npcNormalDialogList_npc_id references npcList, dialogue varchar(6500))");

					stmt.executeUpdate();
				} finally {
					DBUtil.closeQuietly(stmt);
				}

				return true;
			}
		});
	}

	public void dropTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				ArrayList<String> tables = new ArrayList<String>();

				tables.add("drop table baseItems");
				tables.add("drop table keyItems");
				tables.add("drop table lsItems");
				tables.add("drop table clothingItems");
				tables.add("drop table weaponItems");
				tables.add("drop table frpItems");
				tables.add("drop table orbItems");
				tables.add("drop table itemsMap");
				tables.add("drop table playerList");
				tables.add("drop table roomsList");
				tables.add("drop table itemsList");
				tables.add("drop table doorsList");
				tables.add("drop table outputList");
				tables.add("drop table userInfo");
				tables.add("drop table npcCombatDialogList");
				tables.add("drop table npcNormalDialogList");
				tables.add("drop table npcList");

				try {

					for (int i = 0; i < tables.size(); i++) {
						stmt = conn.prepareStatement(tables.get(i));

						stmt.executeUpdate();
					}

					// System.out.println("Dropped all tables. ");
				} finally {
					DBUtil.closeQuietly(stmt);
				}

				return true;
			}
		});
	}

	public Boolean addOutput(ArrayList<String> output) {
		return executeTransaction(new Transaction<Boolean>() {
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					for (String s : output) {
						stmt = conn.prepareStatement("INSERT INTO outputList(output_data) values(?)");
						stmt.setString(1, s);

						stmt.executeUpdate();
					}
				} finally {
					DBUtil.closeQuietly(stmt);
				}

				return false;
			}
		});
	}

	public ArrayList<String> getOutput() {
		return executeTransaction(new Transaction<ArrayList<String>>() {
			public ArrayList<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement("SELECT * FROM outputList");

					resultSet = stmt.executeQuery();

					ArrayList<String> outputList = new ArrayList<String>();

					while (resultSet.next()) {
						outputList.add(resultSet.getString(1));
					}

					return outputList;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public ArrayList<Item> getBaseItem(String sourceName, String itemName) {
		//
		return null;
	}

	public ArrayList<Item> getKey(String sourceName, String itemName) {
		return executeTransaction(new Transaction<ArrayList<Item>>() {
			public ArrayList<Item> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {

					if (sourceName == "-1" && itemName == "all") {
						stmt = conn.prepareStatement("SELECT * FROM keyItems");
					} else {
						stmt = conn.prepareStatement("select keyItems.* " + "from keyItems, itemsMap "
								+ "where keyItems.item_id = keyItems.item_id " + "and keyItems.item_name = ? "
								+ "and itemsMap.item_location = ?");

						stmt.setString(1, itemName);
						stmt.setString(2, sourceName);
					}

					ArrayList<Item> itemList = new ArrayList<Item>();

					resultSet = stmt.executeQuery();

					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Item item = new Item();
						int index = 1;

						item.setItemId(resultSet.getInt(index++));
						item.setName(resultSet.getString(index++));
						item.setUsed(resultSet.getBoolean(index++));

						itemList.add(item);
					}

					if (!found) {
						// System.out.println("No keys were found in the database. ");
					}

					return itemList;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public ArrayList<LightSource> getLightSource(String sourceName, String itemName) {
		return executeTransaction(new Transaction<ArrayList<LightSource>>() {
			public ArrayList<LightSource> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {

					if (sourceName == "-1" && itemName == "all") {
						stmt = conn.prepareStatement("SELECT * FROM lsItems");
					} else {
						stmt = conn.prepareStatement("select lsItems.* " + "from lsItems, itemsMap "
								+ "where lsItems.item_id = itemsMap.item_id " + "and lsItems.item_name = ? "
								+ "and itemsMap.item_location = ?");

						stmt.setString(1, itemName);
						stmt.setString(2, sourceName);
					}

					ArrayList<LightSource> itemList = new ArrayList<LightSource>();

					resultSet = stmt.executeQuery();

					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						LightSource item = new LightSource();
						int index = 1;

						item.setItemId(resultSet.getInt(index++));
						item.setName(resultSet.getString(index++));
						item.setUsed(resultSet.getBoolean(index++));
						item.setLit(resultSet.getBoolean(index++));

						itemList.add(item);
					}

					if (!found) {
						// System.out.println("No clothing was found in the database. ");
					}

					return itemList;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public ArrayList<Clothing> getClothing(String sourceName, String itemName) {
		return executeTransaction(new Transaction<ArrayList<Clothing>>() {
			public ArrayList<Clothing> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {

					if (sourceName == "-1" && itemName == "all") {
						stmt = conn.prepareStatement("SELECT * FROM clothingItems");
						// stmt = conn.prepareStatement("select clothingItems.item_id "
						// + "from clothingItems");
					} else {
						stmt = conn.prepareStatement("select clothingItems.* " + "from clothingItems, itemsMap "
								+ "where clothingItems.item_id = itemsMap.item_id " + "and clothingItems.item_name = ? "
								+ "and itemsMap.item_location = ?");

						stmt.setString(1, itemName);
						stmt.setString(2, sourceName);
					}

					ArrayList<Clothing> itemList = new ArrayList<Clothing>();

					resultSet = stmt.executeQuery();

					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Clothing item = new Clothing();
						int index = 1;

						// System.out.println("Item id = " + resultSet.getInt(index));
						item.setItemId(resultSet.getInt(index++));
						item.setName(resultSet.getString(index++));
						item.setUsed(resultSet.getBoolean(index++));
						item.setWearing(resultSet.getBoolean(index++));

						itemList.add(item);
					}

					if (!found) {
						// System.out.println("No clothing was found in the database. ");
					}

					return itemList;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public List<Pair<Integer, Integer>> testitemsmap() {
		return executeTransaction(new Transaction<List<Pair<Integer, Integer>>>() {
			public List<Pair<Integer, Integer>> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				List<Pair<Integer, Integer>> list = new ArrayList<Pair<Integer, Integer>>();
				stmt = conn.prepareStatement("select * from itemsMap");
				ResultSet resultSet = null;
				resultSet = stmt.executeQuery();
				while (resultSet.next()) {
					int id = resultSet.getInt("item_id");
					int location = resultSet.getInt("item_location");

					list.add(new Pair(id, location));
				}
				return list;
			}
		});

	}

	@Override
	public ArrayList<FinalRoomPuzzle> getFinalRoomPuzzle(String source, String itemName) {
		return executeTransaction(new Transaction<ArrayList<FinalRoomPuzzle>>() {
			@Override
			public ArrayList<FinalRoomPuzzle> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				if (source.equals("all")) {
					stmt = conn.prepareStatement("SELECT * FROM frpItems");
				} else if (!source.equals(null) && !itemName.equals(null)) {
					stmt = conn.prepareStatement(
							"SELECT frpItems.* FROM frpItems, itemsMap WHERE frpItems.item_id=itemsMap.item_id AND frpItems.item_name=? AND itemsMap.item_location=?");

					stmt.setString(1, itemName);
					stmt.setString(2, source);
				}

				resultSet = stmt.executeQuery();

				ArrayList<FinalRoomPuzzle> frpList = new ArrayList<FinalRoomPuzzle>();

				Boolean found = false;

				while (resultSet.next()) {
					found = true;

					FinalRoomPuzzle frp = new FinalRoomPuzzle();
					loadFrp(frp, resultSet, 1);

					frpList.add(frp);
				}

				return frpList;
			}
		});
	}

	@Override
	public boolean addBaseItem(String sourceName, Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addLightSource(String sourceName, LightSource lsm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addClothing(String sourceName, Clothing cm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addWeapon(String destinationName, Weapon wm) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addFinalRoomPuzzle(String destinationName, FinalRoomPuzzle fr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addOrb(String destinationName, Orb om) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeBaseItem(String sourceName, String itemName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeLightSource(String sourceName, String itemName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeClothing(String sourceName, String itemName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeWeapon(String sourceName, String itemName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeFinalRoomPuzzle(String sourceName, String itemName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeOrb(String sourceName, String itemName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Npc> getNpcs(int roomNumber) {
		return executeTransaction(new Transaction<ArrayList<Npc>>() {
			@Override
			public ArrayList<Npc> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					if (roomNumber == -1) {
						stmt = conn.prepareStatement("SELECT * FROM npcList");
					} else {
						stmt = conn.prepareStatement("SELECT * FROM npcList WHERE npcList.room_number=?");
						stmt.setInt(1, roomNumber);
					}

					resultSet = stmt.executeQuery();

					ArrayList<Npc> npcList = new ArrayList<Npc>();

					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Npc npc = new Npc();
						loadNpc(npc, resultSet, 1);
						
						System.out.println("Loaded: " + npc.toString());
						
						npcList.add(npc);
					}

					return npcList;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public ArrayList<Item> getItems(String source) {
		return executeTransaction(new Transaction<ArrayList<Item>>() {
			public ArrayList<Item> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement("SELECT * FROM itemsList");

					ArrayList<Item> itemList = new ArrayList<Item>();

					resultSet = stmt.executeQuery();

					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Item item = new Item();
						loadItem(item, resultSet, 1);

						itemList.add(item);
					}
					if (!found) {
						// System.out.println("No items were found in the database. ");
					}

					return itemList;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public ArrayList<Room> getRooms() {
		return executeTransaction(new Transaction<ArrayList<Room>>() {
			@Override
			public ArrayList<Room> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement("SELECT * FROM roomsList");

					resultSet = stmt.executeQuery();

					Boolean found = false;

					ArrayList<Room> roomList = new ArrayList<Room>();

					while (resultSet.next()) {
						found = true;

						Room room = new Room();
						loadRoom(room, resultSet, 1);

						roomList.add(room);
					}

					return roomList;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public void loadRoom(Room room, ResultSet resultSet, int index) throws SQLException {
		// set other fields of the room

		room.setRoomNumber(resultSet.getInt(index++));
		room.setRoomId(room.getRoomNumber());
		room.setName(resultSet.getString(index++));

		String desc = resultSet.getString(index++);

		// System.out.println("Read: " + desc);

		room.setRoomDescription(desc);
		room.setPrevDiscovered(resultSet.getBoolean(index++));
		room.setSecretDiscovered(resultSet.getBoolean(index++));
		room.setIsDark(resultSet.getBoolean(index++));
		room.setInventoryId(resultSet.getInt(index++));

		// add the doors
		ArrayList<Door> doorsList = new ArrayList<Door>();
		doorsList = getDoors();

		int doorNCoord = resultSet.getInt(index++);
		int doorSCoord = resultSet.getInt(index++);
		int doorECoord = resultSet.getInt(index++);
		int doorWCoord = resultSet.getInt(index++);
		int doorUCoord = resultSet.getInt(index++);
		int doorDCoord = resultSet.getInt(index++);

		room.addNorthDoor(null);
		room.addSouthDoor(null);
		room.addEastDoor(null);
		room.addWestDoor(null);
		room.addUpDoor(null);
		room.addDownDoor(null);

		for (Door door : doorsList) {
			if (doorNCoord == door.getDoorId()) {
				room.setNorthDoor(door);
			} else if (doorSCoord == door.getDoorId()) {
				room.setSouthDoor(door);
			} else if (doorECoord == door.getDoorId()) {
				room.setEastDoor(door);
			} else if (doorWCoord == door.getDoorId()) {
				room.setWestDoor(door);
			} else if (doorUCoord == door.getDoorId()) {
				room.setUpDoor(door);
			} else if (doorDCoord == door.getDoorId()) {
				room.setDownDoor(door);
			}
		}
	}

	@Override
	public Player getPlayer() {
		return executeTransaction(new Transaction<Player>() {
			@Override
			public Player execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement("select * from playerList");

					Player player = new Player();

					boolean found = false;

					resultSet = stmt.executeQuery();

					while (resultSet.next()) {
						player.setPlayerId(resultSet.getInt("actor_id"));
						player.setName(resultSet.getString("actor_name"));
						player.setRoomNumber(resultSet.getInt("current_room"));
						player.setHealth(resultSet.getInt("health"));
						player.setEquippedWeaponIndex(resultSet.getInt("equippedWeaponIndex"));
						player.setVaccineUseCount(resultSet.getInt("vaccineUseCount"));

						found = true;
					}
					if (!found) {
						// System.out.println("No player found. ");
					}
					return player;
				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(resultSet);
				}
			}
		});
	}

	@Override
	public boolean movePlayer(final int moveToo) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					stmt = conn.prepareStatement("UPDATE playerList SET current_room=?");
					stmt.setString(1, String.valueOf(moveToo));
					stmt.executeUpdate();
					return true;
				}

				finally {
					DBUtil.closeQuietly(stmt);
				}

			}

		});
	}

	@Override
	public boolean setPlayerHealth(final int health) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					stmt = conn.prepareStatement("UPDATE playerList SET health=?");
					stmt.setString(1, String.valueOf(health));
					stmt.executeUpdate();

					return true;
				}

				finally {
					DBUtil.closeQuietly(stmt);
				}

			}

		});
	}

	@Override
	public boolean setPlayerEquippedWeaponIndex(final int weaponIndex) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					stmt = conn.prepareStatement("UPDATE playerList SET equippedWeaponIndex=?");
					stmt.setString(1, String.valueOf(weaponIndex));
					stmt.executeUpdate();

					return true;
				}

				finally {
					DBUtil.closeQuietly(stmt);
				}

			}

		});

	}

	public boolean unlockDoor(final int roomNumber) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				PreparedStatement stmt2 = null;

				try {

					stmt = conn.prepareStatement(
							"select door_north, door_south, door_east, door_west, door_up, door_down from roomsList where room_id=?");
					stmt.setInt(1, roomNumber);
					resultSet = stmt.executeQuery();
					while (resultSet.next()) {
						if (!resultSet.getString("door_north").equals("null")) {
							stmt2 = conn.prepareStatement("update doorsList set is_Unlocked=? where door_id=?");
							stmt2.setBoolean(1, true);
							stmt2.setInt(2, resultSet.getInt("door_north"));
							stmt2.executeUpdate();
						}
						if (!resultSet.getString("door_south").equals("null")) {
							stmt2 = conn.prepareStatement("update doorsList set is_Unlocked=? where door_id=?");
							stmt2.setBoolean(1, true);
							stmt2.setInt(2, resultSet.getInt("door_south"));
							stmt2.executeUpdate();
						}
						if (!resultSet.getString("door_east").equals("null")) {
							stmt2 = conn.prepareStatement("update doorsList set is_Unlocked=? where door_id=?");
							stmt2.setBoolean(1, true);
							stmt2.setInt(2, resultSet.getInt("door_east"));
							stmt2.executeUpdate();
						}
						if (!resultSet.getString("door_west").equals("null")) {
							stmt2 = conn.prepareStatement("update doorsList set is_Unlocked=? where door_id=?");
							stmt2.setBoolean(1, true);
							stmt2.setInt(2, resultSet.getInt("door_west"));
							stmt2.executeUpdate();
						}
						if (!resultSet.getString("door_up").equals("null")) {
							stmt2 = conn.prepareStatement("update doorsList set is_Unlocked=? where door_id=?");
							stmt2.setBoolean(1, true);
							stmt2.setInt(2, resultSet.getInt("door_up"));
							stmt2.executeUpdate();
						}
						if (!resultSet.getString("door_down").equals("null")) {
							stmt2 = conn.prepareStatement("update doorsList set is_Unlocked=? where door_id=?");
							stmt2.setBoolean(1, true);
							stmt2.setInt(2, resultSet.getInt("door_down"));
							stmt2.executeUpdate();
						}
					}

					return true;
				}

				finally {
					DBUtil.closeQuietly(stmt);
				}

			}

		});
	}

	@Override
	public int getItemType(final int item_id) {
		return executeTransaction(new Transaction<Integer>() {
			@Override
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet result = null;

				try {
					stmt = conn.prepareStatement("select item_type from itemsList where item_id=?");
					stmt.setInt(1, item_id);

					result = stmt.executeQuery();

					int type = 0;

					while (result.next()) {
						type = result.getInt("item_type");
					}
					return type;
				} finally {
					DBUtil.closeQuietly(result);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public Npc getIndividualNPC(final String name) {
		return executeTransaction(new Transaction<Npc>() {
			@Override
			public Npc execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;
				try {
					stmt = conn.prepareStatement("select * from npcList where actor_name=?");
					stmt.setString(1, name);
					resultSet = stmt.executeQuery();

					Npc npc = new Npc();

					while (resultSet.next()) {

						loadNpc(npc, resultSet, 1);

					}
					return npc;

				} finally {
					DBUtil.closeQuietly(resultSet);
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public boolean setNpcHostile(String name, boolean isHostile) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update npcList set isHostile=? where actor_name=?");
					stmt.setBoolean(1, isHostile);
					stmt.setString(2, name);
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public boolean setNpcEquippedWeaponIndex(String name, int weaponIndex) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update npcList set equippedWeaponIndex=? where actor_name=?");
					stmt.setInt(1, weaponIndex);
					stmt.setString(2, name);
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public boolean moveItem(final int item_id, final int newLocation) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update itemsMap set item_location=? where item_id=?");
					stmt.setInt(1, newLocation);
					stmt.setInt(2, item_id);
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean setPrevDiscovered(final int room_id, final boolean isDiscovered) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update roomsList set prevDiscovered=? where room_id=?");
					stmt.setBoolean(1, isDiscovered);
					stmt.setInt(2, room_id);
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public boolean setNpcHealth(final String name, final int health) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update npcList set health=? where actor_name=?");
					stmt.setInt(1, health);
					stmt.setString(2, name);
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public boolean setItemIsUsed(String name, boolean isUsed) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update itemsList set isUsed=? where item_name=?");
					stmt.setBoolean(1, isUsed);
					stmt.setString(2, name);
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public Item getItem(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Item> getAllItemsFromLacation(int location) {
		return executeTransaction(new Transaction<ArrayList<Item>>() {
			@Override
			public ArrayList<Item> execute(Connection conn) throws SQLException {
				PreparedStatement stmt3 = null;
				ResultSet result3 = null;
				PreparedStatement stmt = null;
				ResultSet result = null;
				PreparedStatement stmt2 = null;
				ResultSet result2 = null;
				ArrayList<Item> items = new ArrayList<Item>();
				List<Integer> ids = new ArrayList<Integer>();
				try {
					stmt3 = conn.prepareStatement("select item_id from itemsMap where item_location=?");
					stmt3.setInt(1, location);
					result3 = stmt3.executeQuery();
					while (result3.next()) {
						ids.add(result3.getInt("item_id"));
					}
					for (int item_id : ids) {
						// item_id = result3.getInt("item_id");
						stmt = conn.prepareStatement("select item_type from itemsList where item_id=?");
						stmt.setInt(1, item_id);
						result = stmt.executeQuery();
						int type = 0;
						while (result.next()) {
							type = result.getInt("item_type");
						}
						// base items
						if (type == 0) {
							stmt2 = conn.prepareStatement("select * from baseItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							Item item = new Item();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(0);
								item.setUsed(result2.getBoolean("isUsed"));

							}
							items.add(item);

						}
						// keys
						else if (type == 1) {
							stmt2 = conn.prepareStatement("select * from keyItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							Item item = new Item();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(1);
								item.setUsed(result2.getBoolean("isUsed"));

							}
							items.add(item);

						}
						// Light Source
						else if (type == 2) {
							stmt2 = conn.prepareStatement("select * from lsItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							LightSource item = new LightSource();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(2);
								item.setUsed(result2.getBoolean("isUsed"));
								item.setLit(result2.getBoolean("isLit"));

							}
							items.add(item);

						}
						// Clothing
						else if (type == 3) {
							stmt2 = conn.prepareStatement("select * from clothingItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							Clothing item = new Clothing();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(3);
								item.setUsed(result2.getBoolean("isUsed"));
								item.setWearing(result2.getBoolean("isWearing"));

							}
							items.add(item);

						}
						// Weapon
						else if (type == 4) {
							stmt2 = conn.prepareStatement("select * from weaponItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							Weapon item = new Weapon();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(4);
								item.setUsed(result2.getBoolean("isUsed"));
								item.setDamage(result2.getInt("damage"));
								item.setEquipped(result2.getBoolean("isEquipped"));

							}
							items.add(item);

						}
						// FinalRoomPuzzle
						else if (type == 5) {
							stmt2 = conn.prepareStatement("select * from frpItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							FinalRoomPuzzle item = new FinalRoomPuzzle();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(5);
								item.setUsed(result2.getBoolean("isUsed"));

							}
							items.add(item);

						}
						// orb
						else if (type == 6) {
							stmt2 = conn.prepareStatement("select * from orbItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							Orb item = new Orb();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(6);
								item.setUsed(result2.getBoolean("isUsed"));

							}
							items.add(item);

						}
					}
					return items;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
				}
			}
		});
	}

	public boolean updateRoom(Room room) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement(
							"update roomsList set prevDiscovered=?, secretDiscovered=?, isDark=? where room_id=?");

					stmt.setBoolean(1, room.getPrevDiscovered());
					stmt.setBoolean(2, room.getSecretDiscovered());
					stmt.setBoolean(3, room.getIsDark());
					stmt.setInt(4, room.getRoomId());
					stmt.executeUpdate();

					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean removeNpc(Npc n) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("delete from npcList where npc_id=?");

					stmt.setInt(1, n.getActorId());
					stmt.executeUpdate();

					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean updateDoor(Door door) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update doorsList set isUsed=?, is_Unlocked=? where door_id=?");
					stmt.setBoolean(1, door.getUsed());
					stmt.setBoolean(2, door.getIsUnlocked());
					stmt.setInt(3, door.getDoorId());
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public Item getItembyId(final int id) {
		return executeTransaction(new Transaction<Item>() {
			@Override
			public Item execute(Connection conn) throws SQLException {
				PreparedStatement stmt3 = null;
				ResultSet result3 = null;
				PreparedStatement stmt = null;
				ResultSet result = null;
				PreparedStatement stmt2 = null;
				ResultSet result2 = null;
				ArrayList<Item> items = new ArrayList<Item>();
				List<Integer> ids = new ArrayList<Integer>();
				try {
					// stmt3 = conn.prepareStatement("select item_id from itemsMap where
					// item_location=?");
					ids.add(id);
					for (int item_id : ids) {
						// item_id = result3.getInt("item_id");
						stmt = conn.prepareStatement("select item_type from itemsList where item_id=?");
						stmt.setInt(1, item_id);
						result = stmt.executeQuery();
						int type = 0;
						while (result.next()) {
							type = result.getInt("item_type");
						}
						// base items
						if (type == 0) {
							stmt2 = conn.prepareStatement("select * from baseItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							Item item = new Item();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(0);
								item.setUsed(result2.getBoolean("isUsed"));

							}
							return item;

						}
						// keys
						else if (type == 1) {
							stmt2 = conn.prepareStatement("select * from keyItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							Item item = new Item();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(1);
								item.setUsed(result2.getBoolean("isUsed"));

							}
							return item;

						}
						// Light Source
						else if (type == 2) {
							stmt2 = conn.prepareStatement("select * from lsItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							LightSource item = new LightSource();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(2);
								item.setUsed(result2.getBoolean("isUsed"));
								item.setLit(result2.getBoolean("isLit"));

							}
							return item;

						}
						// Clothing
						else if (type == 3) {
							stmt2 = conn.prepareStatement("select * from clothingItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							Clothing item = new Clothing();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(3);
								item.setUsed(result2.getBoolean("isUsed"));
								item.setWearing(result2.getBoolean("isWearing"));

							}
							return item;

						}
						// Weapon
						else if (type == 4) {
							stmt2 = conn.prepareStatement("select * from weaponItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							Weapon item = new Weapon();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(4);
								item.setUsed(result2.getBoolean("isUsed"));
								item.setDamage(result2.getInt("damage"));
								item.setEquipped(result2.getBoolean("isEquipped"));

							}
							return item;

						}
						// FinalRoomPuzzle
						else if (type == 5) {
							stmt2 = conn.prepareStatement("select * from frpItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							FinalRoomPuzzle item = new FinalRoomPuzzle();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(5);
								item.setUsed(result2.getBoolean("isUsed"));

							}
							return item;

						}
						// orb
						else if (type == 6) {
							stmt2 = conn.prepareStatement("select * from orbItems where item_id=?");
							stmt2.setInt(1, item_id);
							result2 = stmt2.executeQuery();

							Orb item = new Orb();

							while (result2.next()) {
								item.setItemId(result2.getInt("item_id"));
								item.setName(result2.getString("item_name"));
								item.setType(6);
								item.setUsed(result2.getBoolean("isUsed"));

							}
							return item;

						}
					}
					Item item = new Item();
					return item;

				} finally {
					DBUtil.closeQuietly(stmt);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
				}
			}
		});
	}

	@Override
	public boolean setWeaponisEquipped(String name, boolean isEquipped) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update weaponItems set isEquipped=? where item_name=?");
					stmt.setBoolean(1, isEquipped);
					stmt.setString(2, name);
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public boolean setClothingIsWearing(String name, boolean isWearing) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update clothingItems set isUsed=? where item_name=?");
					stmt.setBoolean(1, isWearing);
					stmt.setString(2, name);
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public boolean setLsIsLit(String name, boolean isLit) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update lsItems set isLit=? where item_name=?");
					stmt.setBoolean(1, isLit);
					stmt.setString(2, name);
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean doesHaveItem(final int item_id, final int inventory_id) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				stmt = conn.prepareStatement("select item_location from itemsMap where item_id=?");
				stmt.setInt(1, item_id);
				resultSet = stmt.executeQuery();
				int location = 0;
				while (resultSet.next()) {
					location = resultSet.getInt("item_location");
				}
				if (location == inventory_id) {
					return true;
				} else {
					return false;
				}
			}
		});
	}

	@Override
	public boolean clearTables() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> getNpcCombatDialogue(String npcName) {
		return executeTransaction(new Transaction<ArrayList<String>>() {
			@Override
			public ArrayList<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement("select npcCombatDialogList.dialogue "
							+ "from npcCombatDialogList, npcList " + "where npcList.actor_name = ? and "
							+ "npcList.npc_id = npcCombatDialogList.npc_id");
					stmt.setString(1, npcName);

					resultSet = stmt.executeQuery();

					Boolean found = false;

					ArrayList<String> dialogList = new ArrayList<String>();

					while (resultSet.next()) {
						found = true;

						String dialog = new String();

						dialog = resultSet.getString(1);

						dialogList.add(dialog);
					}

					return dialogList;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public ArrayList<String> getNpcNormalDialogue(String npcName) {
		return executeTransaction(new Transaction<ArrayList<String>>() {
			@Override
			public ArrayList<String> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement("select npcNormalDialogList.dialogue "
							+ "from npcNormalDialogList, npcList " + "where npcList.actor_name = ? and "
							+ "npcList.npc_id = npcNormalDialogList.npc_id");
					stmt.setString(1, npcName);

					resultSet = stmt.executeQuery();

					Boolean found = false;

					ArrayList<String> dialogList = new ArrayList<String>();

					while (resultSet.next()) {
						found = true;

						String dialog = new String();

						dialog = resultSet.getString(1);

						dialogList.add(dialog);
					}

					return dialogList;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public ArrayList<String> CombatDialogue(String npcName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Orb> getOrb(String sourceName, String itemName) {
		return executeTransaction(new Transaction<ArrayList<Orb>>() {
			public ArrayList<Orb> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {

					if (sourceName == "-1" && itemName == "all") {
						stmt = conn.prepareStatement("SELECT * FROM orbItems");
						// stmt = conn.prepareStatement("select clothingItems.item_id "
						// + "from clothingItems");
					} else {
						stmt = conn.prepareStatement("select orbItems.* " + "from orbItems, itemsMap "
								+ "where orbItems.item_id = itemsMap.item_id " + "and orbItems.item_name = ? "
								+ "and itemsMap.item_location = ?");

						stmt.setString(1, itemName);
						stmt.setString(2, sourceName);
					}

					ArrayList<Orb> itemList = new ArrayList<Orb>();

					resultSet = stmt.executeQuery();

					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Orb item = new Orb();
						int index = 1;

						// System.out.println("Item id = " + resultSet.getInt(index));
						item.setItemId(resultSet.getInt(index++));
						item.setName(resultSet.getString(index++));
						item.setUsed(resultSet.getBoolean(index++));

						itemList.add(item);
					}

					if (!found) {
						// System.out.println("No orb was found in the database. ");
					}

					return itemList;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public ArrayList<Weapon> getWeapon(String sourceName, String itemName) {
		return executeTransaction(new Transaction<ArrayList<Weapon>>() {
			public ArrayList<Weapon> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {

					if (sourceName == "-1" && itemName == "all") {
						stmt = conn.prepareStatement("SELECT * FROM weaponItems");
						// stmt = conn.prepareStatement("select clothingItems.item_id "
						// + "from clothingItems");
					} else {
						stmt = conn.prepareStatement("select weaponItems.* " + "from weaponItems, itemsMap "
								+ "where weaponItems.item_id = itemsMap.item_id " + "and weaponItems.item_name = ? "
								+ "and itemsMap.item_location = ?");

						stmt.setString(1, itemName);
						stmt.setString(2, sourceName);
					}

					ArrayList<Weapon> itemList = new ArrayList<Weapon>();

					resultSet = stmt.executeQuery();

					Boolean found = false;

					while (resultSet.next()) {
						found = true;

						Weapon item = new Weapon();
						int index = 1;

						// System.out.println("Item id = " + resultSet.getInt(index));
						item.setItemId(resultSet.getInt(index++));
						item.setName(resultSet.getString(index++));
						item.setUsed(resultSet.getBoolean(index++));
						item.setDamage(resultSet.getInt(index++));
						item.setEquipped(resultSet.getBoolean(index++));

						itemList.add(item);
					}

					if (!found) {
						// System.out.println("No weapon was found in the database. ");
					}

					return itemList;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public ArrayList<Door> getDoors() {
		return executeTransaction(new Transaction<ArrayList<Door>>() {
			@Override
			public ArrayList<Door> execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				ResultSet resultSet = null;

				try {
					stmt = conn.prepareStatement("SELECT * FROM doorsList");

					resultSet = stmt.executeQuery();

					Boolean found = false;

					ArrayList<Door> doorList = new ArrayList<Door>();

					while (resultSet.next()) {
						found = true;

						Door door = new Door();
						loadDoor(door, resultSet, 1);

						doorList.add(door);
					}

					return doorList;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	@Override
	public Integer getItemId(String itemName) {
		return executeTransaction(new Transaction<Integer>() {
			public Integer execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;

				try {
					return 0;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean updateLsItem(LightSource ls) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update lsItems set isUsed=?, isLit=? where item_id=?");
					stmt.setBoolean(1, ls.getUsed());
					stmt.setBoolean(2, ls.getLit());
					stmt.setInt(3, ls.getItemId());
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean updateBaseItem(Item item) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update baseItems set isUsed=? where item_id=?");
					stmt.setBoolean(1, item.getUsed());
					stmt.setInt(2, item.getItemId());
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean updateKeyItem(Item item) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update keyItems set isUsed=? where item_id=?");
					stmt.setBoolean(1, item.getUsed());
					stmt.setInt(2, item.getItemId());
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean updateClothingItem(Clothing item) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update clothingItems set isUsed=?, isWearing=? where item_id=?");
					stmt.setBoolean(1, item.getUsed());
					stmt.setBoolean(2, item.getWearing());
					stmt.setInt(3, item.getItemId());
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean updateWeaponItem(Weapon item) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement(
							"update weaponItems set isUsed=?, damage=?, isEquipped=? where item_id=?");
					stmt.setBoolean(1, item.getUsed());
					stmt.setInt(2, item.getDamage());
					stmt.setBoolean(3, item.getEquipped());
					stmt.setInt(4, item.getItemId());
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean updateFrpItem(FinalRoomPuzzle item) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update frpItems set isUsed=? where item_id=?");
					stmt.setBoolean(1, item.getUsed());
					stmt.setInt(2, item.getItemId());
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean updateOrbItem(Orb item) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement("update orbItems set isUsed=? where item_id=?");
					stmt.setBoolean(1, item.getUsed());
					stmt.setInt(2, item.getItemId());
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean updatePlayer(Player player) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement(
							"update playerList set current_room=?, health=?, vaccineUseCount=?, equippedWeaponIndex=?, actor_name=? where actor_id=?");

					stmt.setInt(1, player.getCurrentRoom());
					System.out.println("Set current room to " + player.getCurrentRoom());

					stmt.setInt(2, player.getHealth());
					stmt.setInt(3, player.getVaccineUseCount());
					stmt.setInt(4, player.getEquippedWeaponIndex());
					stmt.setString(5, player.getName());
					stmt.setInt(6, player.getPlayerId());
					stmt.executeUpdate();
					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public boolean updateNPC(Npc npc) {
		return executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt = null;
				try {
					stmt = conn.prepareStatement(
							"update npcList set actor_name=?, current_room=?, health=?, isHostile=? where npc_id=?");

					stmt.setString(1, npc.getName());
					stmt.setInt(2, npc.getCurrentRoom());
					stmt.setInt(3, npc.getHealth());
					stmt.setBoolean(4, npc.getHostile());
					stmt.setInt(5, npc.getActorId());
					stmt.executeUpdate();

					return true;
				} finally {
					DBUtil.closeQuietly(stmt);
				}
			}
		});
	}

	public void loadItem(Item item, ResultSet resultSet, int index) throws SQLException {
		item.setItemId(resultSet.getInt(index++));
		item.setType(resultSet.getInt(index++));
	}

	public void loadFrp(FinalRoomPuzzle frp, ResultSet resultSet, int index) throws SQLException {
		int id = resultSet.getInt(index++);

		// System.out.println("Read ID of " + id);

		frp.setItemId(id);
		frp.setName(resultSet.getString(index++));
		frp.setUsed(resultSet.getBoolean(index++));
	}

	public void loadDoor(Door door, ResultSet resultSet, int index) throws SQLException {
		door.setDoorId(resultSet.getInt(index++));
		door.setIsUnlocked(resultSet.getBoolean(index++));
		door.setDescription(resultSet.getString(index++));
		door.setIsOneWay(resultSet.getBoolean(index++));
		door.setUsed(resultSet.getBoolean(index++));
		door.setDoorKey(resultSet.getString(index++));
		door.addRoomCoords(resultSet.getInt(index++));
		door.addRoomCoords(resultSet.getInt(index++));
	}

	public void loadNpc(Npc npc, ResultSet resultSet, int index) throws SQLException {
		npc.setNpcId(resultSet.getInt(index++));
		npc.setName(resultSet.getString(index++));
		npc.setRoomNumber(resultSet.getInt(index++));
		npc.setHealth(resultSet.getInt(index++));
		npc.setEquippedWeaponIndex(resultSet.getInt(index++));
		
		boolean hostile = resultSet.getBoolean(index++);
		System.out.println("This NPC is hostile : " + hostile);
		npc.setHostile(hostile);
		
		npc.setInventoryIndex(resultSet.getInt(index++));

		npc.setCombatDialogue(getNpcCombatDialogue(npc.getName()));
		npc.setNormalDialogue(getNpcNormalDialogue(npc.getName()));
	}

	@Override
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Item> itemList;
				List<Door> doorList;
				List<Npc> npcList;
				List<FinalRoomPuzzle> frpList;
				List<Integer> itemLocations;
				List<Clothing> clothingList;
				List<Item> keysList;
				List<LightSource> lightSourceList;
				List<Player> playerList;
				List<Weapon> weaponList;
				List<Room> roomList;
				List<Pair<Integer, String>> npcCombatDialogList;
				List<Pair<Integer, String>> npcNormalDialogList;
				List<Item> baseItems;
				List<Orb> orbList;

				try {
					itemList = InitialData.getItems();
					doorList = InitialData.getDoors();
					npcList = InitialData.getNpcs();
					frpList = InitialData.getFrps();
					itemLocations = InitialData.getItemsMap();
					clothingList = InitialData.getClothing();
					keysList = InitialData.getKeys();
					lightSourceList = InitialData.getlightSource();
					playerList = InitialData.getPlayer();
					weaponList = InitialData.getWeapon();
					orbList = InitialData.getOrb();
					roomList = InitialData.getRooms();
					npcCombatDialogList = InitialData.getNpcCombatDialog();
					npcNormalDialogList = InitialData.getNpcNormalDialog();
					baseItems = InitialData.getBaseItems();

				} catch (IOException e) {
					throw new SQLException("Couldn't read yo mama.", e);
				}

				PreparedStatement insertItem = null;
				PreparedStatement insertDoor = null;
				PreparedStatement insertNpc = null;
				PreparedStatement insertFrp = null;
				PreparedStatement insertLocation = null;
				PreparedStatement insertClothing = null;
				PreparedStatement insertKeys = null;
				PreparedStatement insertLights = null;
				PreparedStatement insertPlayer = null;
				PreparedStatement insertWeapon = null;
				PreparedStatement insertOrbItems = null;
				PreparedStatement insertRooms = null;
				PreparedStatement insertCombatDialog = null;
				PreparedStatement insertNormalDialog = null;
				PreparedStatement insertBaseItems = null;

				try {
					insertItem = conn.prepareStatement("insert into itemsList(item_type) values(?)");
					for (Item item : itemList) {
						insertItem.setInt(1, item.getType());
						insertItem.addBatch();
					}
					insertItem.executeBatch();

					//// System.out.println("itemsList table populated. ");

					insertDoor = conn.prepareStatement(
							"insert into doorsList(is_unlocked, door_desc, isOneWay, isUsed, doorKeyName, roomCoord1, roomCoord2) values(?,?,?,?,?,?,?)");
					for (Door door : doorList) {
						insertDoor.setBoolean(1, door.getIsUnlocked());
						insertDoor.setString(2, door.getDescription());
						insertDoor.setBoolean(3, door.getIsOneWay());
						insertDoor.setBoolean(4, door.getUsed());
						insertDoor.setString(5, door.getDoorKey());
						insertDoor.setInt(6, door.getRoomCoords().get(0));
						insertDoor.setInt(7, door.getRoomCoords().get(1));
						insertDoor.addBatch();
					}
					insertDoor.executeBatch();

					insertBaseItems = conn
							.prepareStatement("insert into baseItems(item_id, item_name, isUsed) values(?,?,?)");
					for (Item base : baseItems) {
						insertBaseItems.setInt(1, base.getItemId());
						insertBaseItems.setString(2, base.getName());
						insertBaseItems.setBoolean(3, base.getUsed());
						insertBaseItems.addBatch();
					}
					insertBaseItems.executeBatch();

					insertNpc = conn.prepareStatement(
							"insert into npcList(actor_name, current_room, health, equippedWeaponIndex, isHostile, inventory_index) values(?,?,?,?,?,?)");
					for (Npc npc : npcList) {
						insertNpc.setString(1, npc.getName());
						insertNpc.setInt(2, npc.getCurrentRoom());
						insertNpc.setInt(3, npc.getHealth());
						insertNpc.setInt(4, npc.getEquippedWeaponIndex());
						
						boolean hostile = npc.getHostile();
						
						System.out.println("Adding hosilte npc: " + hostile);
						
						insertNpc.setBoolean(5, npc.getHostile());
						insertNpc.setInt(6, npc.getInventoryIndex());
						insertNpc.addBatch();
					}
					insertNpc.executeBatch();
					
					/*
					ArrayList<Npc> npcs = getNpcs(-1);
					for (Npc npc : npcs) {
						System.out.println(npc.toString());
					}
					*/

					//// System.out.println("npcList table populated. ");

					insertFrp = conn.prepareStatement("insert into frpItems(item_id, item_name, isUsed) values(?,?,?)");
					for (FinalRoomPuzzle frp : frpList) {
						insertFrp.setInt(1, frp.getItemId());
						insertFrp.setString(2, frp.getName());
						insertFrp.setBoolean(3, frp.getUsed());
						insertFrp.addBatch();
					}
					insertFrp.executeBatch();

					//// System.out.println("frpList table populated. ");

					insertLocation = conn.prepareStatement("insert into itemsMap(item_id, item_location) values(?,?)");
					int i = 0;
					int x = 1;
					for (int location : itemLocations) {
						insertLocation.setInt(1, x);
						insertLocation.setInt(2, itemLocations.get(i));
						insertLocation.addBatch();
						i++;
						x++;
					}
					insertLocation.executeBatch();
					//// System.out.println("itemsMap table populated. ");

					insertClothing = conn.prepareStatement(
							"insert into clothingItems(item_id, item_name, isUsed, isWearing) values(?,?,?,?)");
					for (Clothing item : clothingList) {
						insertClothing.setInt(1, item.getItemId());
						insertClothing.setString(2, item.getName());
						insertClothing.setBoolean(3, item.getUsed());
						insertClothing.setBoolean(4, item.getWearing());
						insertClothing.addBatch();
					}
					insertClothing.executeBatch();

					//// System.out.println("clothingList table populated. ");

					insertKeys = conn
							.prepareStatement("insert into keyItems(item_id, item_name, isUsed) values(?,?,?)");
					for (Item item : keysList) {
						insertKeys.setInt(1, item.getItemId());
						insertKeys.setString(2, item.getName());
						insertKeys.setBoolean(3, item.getUsed());
						insertKeys.addBatch();
					}
					insertKeys.executeBatch();

					//// System.out.println("keysList table populated. ");

					insertWeapon = conn.prepareStatement(
							"insert into weaponItems(item_id, item_name, isUsed, damage, isEquipped) values(?,?,?,?,?)");
					for (Weapon item : weaponList) {
						// System.out.println("Adding " + item.toString() + "... ");
						// System.out.println("Item id: " + item.getItemId());
						insertWeapon.setInt(1, item.getItemId());
						insertWeapon.setString(2, item.getName());
						insertWeapon.setBoolean(3, item.getUsed());
						insertWeapon.setInt(4, item.getDamage());
						insertWeapon.setBoolean(5, item.getEquipped());
						insertWeapon.addBatch();
					}
					insertWeapon.executeBatch();

					// System.out.println("weaponList table populated. ");

					insertLights = conn
							.prepareStatement("insert into lsItems(item_id, item_name, isUsed, isLit) values(?,?,?,?)");
					for (LightSource item : lightSourceList) {
						insertLights.setInt(1, item.getItemId());
						insertLights.setString(2, item.getName());
						insertLights.setBoolean(3, item.getUsed());
						insertLights.setBoolean(4, item.getLit());
						insertLights.addBatch();
					}
					insertLights.executeBatch();

					//// System.out.println("lightsList table populated. ");

					insertPlayer = conn.prepareStatement(
							"insert into playerList(actor_name, current_room, health, vaccineUseCount, equippedWeaponIndex, inventory_index) values(?,?,?,?,?,?)");
					for (Player p : playerList) {
						insertPlayer.setString(1, p.getName());
						insertPlayer.setInt(2, p.getCurrentRoom());
						insertPlayer.setInt(3, p.getHealth());
						insertPlayer.setInt(4, p.getVaccineUseCount());
						insertPlayer.setInt(5, p.getEquippedWeaponIndex());
						insertPlayer.setInt(6, p.getInventoryIndex());
						insertPlayer.addBatch();
					}
					insertPlayer.executeBatch();

					//// System.out.println("playerList table populated. ");

					insertRooms = conn.prepareStatement(
							"insert into roomsList(room_name, room_desc, prevDiscovered, secretDiscovered, isDark, inventory, door_north, door_south, door_east, door_west, door_up, door_down) values(?,?,?,?,?,?,?,?,?,?,?,?)");
					for (Room room : roomList) {
						// insertRooms.setInt(1, room.getRoomNumber());
						insertRooms.setString(1, room.getName());

						String desc = room.getRoomDescription();
						System.out.println("Writing: " + room.getRoomDescription()
								+ "----------------------------------------------------------------");

						insertRooms.setString(2, desc);
						insertRooms.setBoolean(3, room.getPrevDiscovered());
						insertRooms.setBoolean(4, room.getSecretDiscovered());
						insertRooms.setBoolean(5, room.getIsDark());
						insertRooms.setInt(6, room.getInventoryId());
						insertRooms.setInt(7, room.getNorthDoorId());
						insertRooms.setInt(8, room.getSouthDoorId());
						insertRooms.setInt(9, room.getEastDoorId());
						insertRooms.setInt(10, room.getWestDoorId());
						insertRooms.setInt(11, room.getUpDoorId());
						insertRooms.setInt(12, room.getDownDoorId());
						insertRooms.addBatch();
					}
					insertRooms.executeBatch();
					//// System.out.println("RoomList table populated. ");

					insertCombatDialog = conn
							.prepareStatement("insert into npcCombatDialogList(npc_id, dialogue) values(?,?)");
					for (Pair p : npcCombatDialogList) {
						insertCombatDialog.setInt(1, (int) p.getLeft());
						insertCombatDialog.setString(2, (String) p.getRight());
						insertCombatDialog.addBatch();
					}
					insertCombatDialog.executeBatch();
					//// System.out.println("npCombatDialogList table populated. ");

					insertNormalDialog = conn
							.prepareStatement("insert into npcNormalDialogList(npc_id, dialogue) values(?,?)");
					for (Pair p : npcNormalDialogList) {
						insertNormalDialog.setInt(1, (int) p.getLeft());
						insertNormalDialog.setString(2, (String) p.getRight());
						insertNormalDialog.addBatch();
					}
					insertNormalDialog.executeBatch();
					//// System.out.println("npcNormalDialogList table populated. ");

					insertOrbItems = conn
							.prepareStatement("insert into orbItems(item_id, item_name, isUsed) values(?,?,?)");
					for (Orb orb : orbList) {
						// System.out.println("Adding orb: " + orb.toString() + " ... ");
						insertOrbItems.setInt(1, orb.getItemId());
						insertOrbItems.setString(2, orb.getName());
						insertOrbItems.setBoolean(3, orb.getUsed());
						insertOrbItems.addBatch();
					}
					insertOrbItems.executeBatch();
					// System.out.println("orbItems inserted into orbItems");

				} finally {
					DBUtil.closeQuietly(insertItem);
					DBUtil.closeQuietly(insertDoor);
					DBUtil.closeQuietly(insertNpc);
					DBUtil.closeQuietly(insertFrp);
					DBUtil.closeQuietly(insertClothing);
					DBUtil.closeQuietly(insertKeys);
					DBUtil.closeQuietly(insertLights);
					DBUtil.closeQuietly(insertPlayer);
					DBUtil.closeQuietly(insertWeapon);
					DBUtil.closeQuietly(insertOrbItems);
					DBUtil.closeQuietly(insertCombatDialog);
					DBUtil.closeQuietly(insertNormalDialog);
				}

				return true;
			}
		});

	}

	// wrapper SQL transaction function that calls actual transaction function
	// (which has retries)
	public <ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}

	// SQL transaction function which retries the transaction MAX_ATTEMPTS times
	// before failing
	public <ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();

		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;

			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}

			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}

			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	// TODO: Here is where you name and specify the location of your Derby SQL
	// database
	// TODO: Change it here and in SQLDemo.java under
	// CS320_LibraryExample_Lab06->edu.ycp.cs320.sqldemo
	// TODO: DO NOT PUT THE DB IN THE SAME FOLDER AS YOUR PROJECT - that will cause
	// conflicts later w/Git
	private Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:C:/Users/jawsh/d.db;create=true");

		// Set autocommit() to false to allow the execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);

		return conn;
	}

	public boolean testQueries() {
		// test getPlayer
		Player player = getPlayer();
		// System.out.println(player.getName() + " got player");

		// test movePlayer
		movePlayer(2);
		// System.out.println("Player moved");

		// test setPlayerHealth
		setPlayerHealth(90);
		// System.out.println("Changed Player Health");

		// test setPlayerEquippedWeaponIndex
		setPlayerEquippedWeaponIndex(5);
		// System.out.println("Changed Player weapon index");

		player = getPlayer();
		// System.out.println(player.getCurrentRoom() + " " + player.getHealth() + " "+
		// player.getEquippedWeaponIndex());

		// unlock door
		ArrayList<Door> doors = getDoors();
		// System.out.println(doors.get(0).getIsUnlocked());
		unlockDoor(1);
		doors = getDoors();
		// System.out.println(doors.get(0).getIsUnlocked());

		// test getIndividualNPC
		Npc npc = getIndividualNPC("wild_ape");
		// System.out.println(npc.getName());

		// test setNpcHealth
		setNpcHealth("wild_ape", 69);
		npc = getIndividualNPC("wild_ape");
		// System.out.println(npc.getHealth());

		// moveItem
		Item map = getItem("map");
		moveItem(9, 10);
		boolean test = doesHaveItem(9, 10);
		// System.out.println(test);

		// test GgetAllItemsFromLocation
		ArrayList<Item> items = getAllItemsFromLacation(1);
		// System.out.println(items);
		return true;
	}

	@Override
	public ArrayList<Door> getDoors(String roomName) {
		// TODO Auto-generated method stub
		return null;
	}

}