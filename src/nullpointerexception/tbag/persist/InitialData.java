package nullpointerexception.tbag.persist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.actors.Player;
import nullpointerexception.tbag.items.FinalRoomPuzzle;
import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.rooms.Door;
import nullpointerexception.tbag.rooms.Room;
import nullpointerexception.tbag.items.LightSource;
import nullpointerexception.tbag.items.Orb;
import nullpointerexception.tbag.items.Weapon;
import nullpointerexception.tbag.other.Pair;

public class InitialData {

	/*
	// reads initial Author data from CSV file and returns a List of Authors
	public static List<Author> getAuthors() throws IOException {
		List<Author> authorList = new ArrayList<Author>();
		ReadCSV readAuthors = new ReadCSV("authors.csv");
		try {
			// auto-generated primary key for authors table
			Integer authorId = 1;
			while (true) {
				List<String> tuple = readAuthors.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Author author = new Author();

				// read author ID from CSV file, but don't use it
				// it's there for reference purposes, just make sure that it is correct
				// when setting up the BookAuthors CSV file				
				Integer.parseInt(i.next());
				// auto-generate author ID, instead
				author.setAuthorId(authorId++);				
				author.setLastname(i.next());
				author.setFirstname(i.next());
				authorList.add(author);
			}
			//System.out.println("authorList loaded from CSV file");
			return authorList;
		} finally {
			readAuthors.close();
		}
	}
	*/
	
	public static List<Item> getItems() throws IOException {
		List<Item> itemList = new ArrayList<Item>();
		ReadCSV readItems = new ReadCSV("itemsList.csv");
		
		try {
			Integer itemId = 1;
			
			while (true) {
				List<String> tuple = readItems.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Item item = new Item();
				
				//Integer.parseInt(i.next());
				item.setItemId(itemId++);
				item.setType(Integer.parseInt(i.next()));
				itemList.add(item);
			}
			//System.out.println("itemList loaded from CSV file. ");
			return itemList;
		}
		finally {
			readItems.close();
		}
	}
	
	public static List<Pair<Integer, String>> getNpcCombatDialog() throws IOException {
		List<Pair<Integer, String>> dialogList = new ArrayList<Pair<Integer, String>>();
		ReadCSV readDialog = new ReadCSV("npcCombatDialogue.csv");
		
		try {
			Integer dialogId = 1;
			
			while (true) {
				List<String> tuple = readDialog.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				dialogId = Integer.parseInt(i.next());
				String dialog = i.next();
				
				dialogList.add(new Pair(dialogId, dialog));
			}
			
			return dialogList;
		}
		finally {
			readDialog.close();
		}
	}
	
	public static List<Pair<Integer, String>> getNpcNormalDialog() throws IOException {
		List<Pair<Integer, String>> dialogList = new ArrayList<Pair<Integer, String>>();
		ReadCSV readDialog = new ReadCSV("npcNormalDialog.csv");
		
		try {
			Integer dialogId = 1;
			
			while (true) {
				List<String> tuple = readDialog.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				
				dialogId = Integer.parseInt(i.next());
				String dialog = i.next();
				
				dialogList.add(new Pair(dialogId, dialog));
			}
			
			return dialogList;
		}
		finally {
			readDialog.close();
		}
	}
	
	public static List<Player> getPlayer() throws IOException {
        List<Player> playerList = new ArrayList<Player>();
        ReadCSV readPlayer = new ReadCSV("playerList.csv");
        
        try {
            Integer playerId = 1;
            
            while (true) {
                List<String> tuple = readPlayer.next();
                if (tuple == null) {
                    break;
                }
                Iterator<String> i = tuple.iterator();
                Player player = new Player();
                
                //Integer.parseInt(i.next());
                player.setName(i.next());
                player.setRoomNumber(Integer.parseInt(i.next()));
                player.setHealth(Integer.parseInt(i.next()));
                player.setVaccineUseCount(Integer.parseInt(i.next()));
                player.setEquippedWeaponIndex(Integer.parseInt(i.next()));
                player.setInventoryIndex(Integer.parseInt(i.next()));
                
                playerList.add(player);
            }
            //System.out.println("player list loaded from CSV file. ");
            return playerList;
        }
        finally {
            readPlayer.close();
        }
    }
	
	public static List<Weapon> getWeapon() throws IOException {
        List<Weapon> itemList = new ArrayList<Weapon>();
        ReadCSV readItems = new ReadCSV("weaponItems.csv");
        
        try {
            Integer itemId = 1;
            
            while (true) {
                List<String> tuple = readItems.next();
                if (tuple == null) {
                    break;
                }
                Iterator<String> i = tuple.iterator();
                Weapon item = new Weapon();
                
                //Integer.parseInt(i.next());
                
                item.setItemId(Integer.parseInt(i.next()));
                item.setName(i.next());
                item.setUsed(Boolean.parseBoolean(i.next()));
                item.setDamage(Integer.parseInt(i.next()));
                item.setEquipped(Boolean.parseBoolean(i.next()));
                
                itemList.add(item);
            }
            //System.out.println("weapon items loaded from CSV file. ");
            return itemList;
        }
        finally {
            readItems.close();
        }
    }
	
	public static List<Orb> getOrb() throws IOException {
        List<Orb> itemList = new ArrayList<Orb>();
        ReadCSV readItems = new ReadCSV("orbItems.csv");
        
        try {
            Integer itemId = 1;
            
            while (true) {
                List<String> tuple = readItems.next();
                if (tuple == null) {
                    break;
                }
                Iterator<String> i = tuple.iterator();
                Orb item = new Orb();
                
                //Integer.parseInt(i.next());
                item.setItemId(Integer.parseInt(i.next()));
                item.setName(i.next());
                item.setUsed(Boolean.parseBoolean(i.next()));
                
                itemList.add(item);
            }
            //System.out.println("orbList loaded from CSV file. ");
            return itemList;
        }
        finally {
            readItems.close();
        }
    }
	
	public static List <Room> getRooms() throws IOException {
		List<Room> roomList  = new ArrayList<Room>();
		ReadCSV readRooms = new ReadCSV("roomList.csv");

		try {
			int count = 1;
			while (true) {
				List<String> tuple = readRooms.next();
				if(tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Room room = new Room();

				room.setRoomNumber(count);
				room.setName(i.next());
				String description = i.next();
				
				System.out.println("read: " + description);
				
				room.setRoomDescription(description);
				room.setPrevDiscovered(Boolean.parseBoolean(i.next()));
				room.setSecretDiscovered(Boolean.parseBoolean(i.next()));
				room.setIsDark(Boolean.parseBoolean(i.next()));
				room.setInventoryId(Integer.parseInt(i.next()));
				room.setNorthDoorId(Integer.parseInt(i.next()));
				room.setSouthDoorId(Integer.parseInt(i.next()));
				room.setEastDoorId(Integer.parseInt(i.next()));
				room.setWestDoorId(Integer.parseInt(i.next()));
				room.setUpDoorId(Integer.parseInt(i.next()));
				room.setDownDoorId(Integer.parseInt(i.next()));
				
				roomList.add(room);
				
				count += 1;

			}
			//System.out.println("RoomsLoaded loaded from roomItems. ");
			return roomList;
		}
		finally {
			readRooms.close();
		}
	}
	
	public static List<FinalRoomPuzzle> getFrps() throws IOException {
		List<FinalRoomPuzzle> frpList = new ArrayList<FinalRoomPuzzle>();
		ReadCSV readFrps = new ReadCSV("frpItems.csv");
		
		try {
			Integer itemId = 1;
			
			while (true) {
				List<String> tuple = readFrps.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				FinalRoomPuzzle frp = new FinalRoomPuzzle();
				
				frp.setItemId(Integer.parseInt(i.next()));
				frp.setName(i.next());
				frp.setUsed(Boolean.parseBoolean(i.next()));
				frpList.add(frp);
			}
			//System.out.println("frpList loaded from CSV file. ");
			return frpList;
		}
		finally {
			readFrps.close();
		}
	}
	
	public static List<Door> getDoors() throws IOException {
		List<Door> doorList = new ArrayList<Door>();
		ReadCSV readDoors = new ReadCSV("doorList.csv");
		
		try {
			Integer doorId = 1;
			
			while (true) {
				List<String> tuple = readDoors.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Door door = new Door();
				
				door.setDoorId(doorId++);
				door.setIsUnlocked(Boolean.parseBoolean(i.next()));
				door.setDescription(i.next());
				door.setIsOneWay(Boolean.parseBoolean(i.next()));
				door.setUsed(Boolean.parseBoolean(i.next()));
				door.setDoorKey(i.next());
				door.addRoomCoords(Integer.parseInt(i.next()));
				door.addRoomCoords(Integer.parseInt(i.next()));
				
				doorList.add(door);
			}
			
			//System.out.println("doorList loaded from CSV file. ");
			return doorList;
		}
		finally {
			readDoors.close();
		}
	}
	
	public static List<Integer> getItemsMap() throws IOException {
        List<Integer> locations = new ArrayList<Integer>();
        ReadCSV readItems = new ReadCSV("itemsMap.csv");
        
        try {
            
            Integer itemId = 1;
            
            while (true) {
                List<String> tuple = readItems.next();
                
                if(tuple == null) {
                    break;
                }
                Iterator<String> i = tuple.iterator();
                int location;
                i.next();
                location = Integer.parseInt(i.next());
                locations.add(location);
            }
            //System.out.println(locations);
            return locations;

        }
        finally {
            readItems.close();
        }
    }
	
	public static List<Npc> getNpcs() throws IOException {
		List<Npc> npcList = new ArrayList<Npc>();
		ReadCSV readNpcs = new ReadCSV("npcList.csv");
		
		try {
			Integer npcId = 1;
			
			while (true) {
				List<String> tuple = readNpcs.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Npc npc = new Npc();
				
				npc.setNpcId(npcId++);
				npc.setName(i.next());
				npc.setRoomNumber(Integer.parseInt(i.next()));
				npc.setHealth(Integer.parseInt(i.next()));
				npc.setEquippedWeaponIndex(Integer.parseInt(i.next()));
				npc.setHostile(Boolean.parseBoolean(i.next()));
				npc.setInventoryIndex(Integer.parseInt(i.next()));
				
				npcList.add(npc);
				
				System.out.println("====" + npc.getHostile() + "====");
			}
			//System.out.println("npcList loaded from CSV file. ");
			return npcList;
		}
		finally {
			readNpcs.close();
		}
	}
	public static List <Clothing> getClothing() throws IOException {
		List<Clothing> itemList = new ArrayList<Clothing>();
		ReadCSV readItems = new ReadCSV("clothingItems.csv");
		
		try {
			while (true) {
				List<String> tuple = readItems.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Clothing item = new Clothing();
				
				item.setItemId(Integer.parseInt(i.next()));
				item.setName(i.next());
				item.setUsed(Boolean.parseBoolean(i.next()));
				item.setWearing(Boolean.parseBoolean(i.next()));
				itemList.add(item);
				
			}
			//System.out.println("clothing loaded from clothingList. ");
			return itemList;
		}
		finally {
			readItems.close();
		}
	}
	
	public static List <Item> getKeys() throws IOException {
		List<Item> itemList = new ArrayList<Item>();
		ReadCSV readItems = new ReadCSV("keyItems.csv");
		
		try {
			while (true) {
				List<String> tuple = readItems.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Item item = new Item();
				
				item.setItemId(Integer.parseInt(i.next()));
				item.setName(i.next());
				item.setUsed(Boolean.parseBoolean(i.next()));
				
				itemList.add(item);
				
			}
			//System.out.println("keys loaded from keyItems. ");
			return itemList;
		}
		finally {
			readItems.close();
		}
	}
	
	public static List <LightSource> getlightSource() throws IOException {
		List<LightSource> itemList  = new ArrayList<LightSource>();
		ReadCSV readItems = new ReadCSV("lsItems.csv");
		
		try {
			while (true) {
				List<String> tuple = readItems.next();
				if(tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				LightSource item = new LightSource();
				
				item.setItemId(Integer.parseInt(i.next()));
				item.setName(i.next());
				item.setUsed(Boolean.parseBoolean(i.next()));
				item.setLit(Boolean.parseBoolean(i.next()));
				
				itemList.add(item);
				
			}
			//System.out.println("light sources loaded from lsItems. ");
			return itemList;
		}
		finally {
			readItems.close();
		}
	}
	
	public static List<Item> getBaseItems() throws IOException {
		List<Item> items = new ArrayList<Item>();
		ReadCSV readItems = new ReadCSV("baseItems.csv");
		try {
			while(true) {
				List<String> tuple = readItems.next();
				if(tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Item item = new Item();
				item.setItemId(Integer.parseInt(i.next()));
				item.setName(i.next());
				item.setUsed(Boolean.parseBoolean(i.next()));
				
				items.add(item);
				
			}
			//System.out.println("base items loaded ");
			return items;
		}
		finally {
			readItems.close();
		}
	}
}