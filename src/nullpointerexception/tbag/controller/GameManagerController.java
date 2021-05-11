package nullpointerexception.tbag.controller;

import java.util.ArrayList;
import java.util.List;

import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.actors.Player;
import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.items.FinalRoomPuzzle;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.Orb;
import nullpointerexception.tbag.items.Weapon;
import nullpointerexception.tbag.managers.AttackManager;
import nullpointerexception.tbag.managers.CheckEndGameManager;
import nullpointerexception.tbag.managers.CheckStatusManager;
import nullpointerexception.tbag.managers.ClothingManager;
import nullpointerexception.tbag.managers.CommandManager;
import nullpointerexception.tbag.managers.EndGameManager;
import nullpointerexception.tbag.managers.EquipManager;
import nullpointerexception.tbag.managers.GenericManager;
import nullpointerexception.tbag.managers.ItemExchangeManager;
import nullpointerexception.tbag.managers.LightSourceManager;
import nullpointerexception.tbag.managers.MovementManager;
import nullpointerexception.tbag.managers.ResetManager;
import nullpointerexception.tbag.managers.TalkManager;
import nullpointerexception.tbag.managers.UseManager;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.other.Pair;
import nullpointerexception.tbag.persist.DatabaseProvider;
import nullpointerexception.tbag.persist.DerbyDatabase;
import nullpointerexception.tbag.persist.IDatabase;
import nullpointerexception.tbag.rooms.Room;

public class GameManagerController {
	private String command;
	private GameManagerModel gm;
	private DerbyDatabase db;
	private ArrayList<String> outputList;
	
	public GameManagerController(String username, GameManagerModel gm, DerbyDatabase db) {
		this.gm = gm;
		this.db = db;
		
		outputList = new ArrayList<String>();
		
		//db.createTables();
		//db.loadInitialData();
		
        
        System.out.println("LOADING DATA TO GAME MANAGER CONTROLLER =============================================");
		
		// Player
        Player player = db.getPlayer();
        
        player.setName(username);
        
        ArrayList<Room> rooms = db.getRooms();
        ArrayList<Npc> npcs = db.getNpcs(-1);
        ArrayList<Item> items;
        
        List<Pair<Integer, Integer>> itemsMap = db.testitemsmap();
        
        for (Npc npc : npcs) {
        	for (Pair p : itemsMap) {
        		if (npc.getInventoryIndex() == (int)p.getRight()) {
        			Item item = db.getItembyId((int)p.getLeft());
        			npc.addItem(item);
        		}
        	}
        }
        
        for (Room room : rooms) {
        	for (Pair p : itemsMap) {
        		if (room.getInventoryId() == (int)p.getRight()) {
        			Item item = db.getItembyId((int)p.getLeft());
        			
        			/*
        			if (item.getType() == 5) {
        				FinalRoomPuzzle frp = (FinalRoomPuzzle)item;
        				System.out.println("Adding " + frp.toString() + " ... ");
        			}
        			*/
        			
        			room.addItem(item);
        		}
        	}
        }
        
        for (Pair p : itemsMap) {
        	if (player.getInventoryIndex() == (int)p.getRight()) {
        		Item item = db.getItembyId((int)p.getLeft());
        		player.addItem(item);
        	}
        }
        
        // Add orbs to frp item
        FinalRoomPuzzle frp = (FinalRoomPuzzle)rooms.get(9).getInventory().getItem("orb_container");
        
        System.out.println(frp.toString());
        
        for (Pair p : itemsMap) {
        	if (frp.getInventoryId() == (int)p.getRight()) {
        		Item item = db.getItembyId((int)p.getLeft());
        		frp.addItem(item);
        	}
        }
        
        // Add npcs to rooms
        for (Room room : rooms) {
            for (Npc npc : npcs) {
                if (room.getRoomNumber() == npc.getCurrentRoom()) {
                    room.addNpc(npc);
                }
            }
        }

        player.calcStatString();
        
        gm.setPlayer(player);
        gm.setRooms(rooms);
        gm.setCurrentRoomIndex(player.getCurrentRoom() - 1);
        
        System.out.println("Room #" + gm.getPlayer().getCurrentRoom());
        
        displayGameState();
	}
	
	public void setCommand(String command) {
		System.out.println("Setting command to '" + command + "' ... ");
		this.command = command;
	}
	
	public void doGame() {
		boolean endGame = false;
		boolean resetting = false;
		
		ArrayList<String> commandParams = new ArrayList<String>();
		
		outputList.add("> " + command);
		
		CheckStatusManager csm = new CheckStatusManager(gm, db);
		
		// Command entry 
		CommandManager cm = new CommandManager();
		cm.setCommand(command);
		commandParams = cm.getCommandParams();
		
		System.out.println("Verb entered: '"+ commandParams.get(0) + "' ");
		
		// Possible commands 
		if (commandParams.get(0).equals("move")) {
			MovementManager mm = new MovementManager(commandParams, gm, db);
			
			addoutputList(mm.getOutput());
			
			if (mm.getMoved()) {
				csm.checkMaskStatus();
			}
		}
		else if (commandParams.get(0).equals("take") || commandParams.get(0).equals("pick-up") || commandParams.get(0).equals("drop") || commandParams.get(0).equals("give") || commandParams.get(0).equals("insert")) {
			ItemExchangeManager iem = new ItemExchangeManager(commandParams, gm, db);
			
			addoutputList(iem.getOutput());
			
			// See if the game has been completed, since this will only happen if there is an iem instance 
			if (commandParams.get(0).equals("insert")) {
				CheckEndGameManager cegm = new CheckEndGameManager(gm, db);
				
				gm.setCompletedEnding(cegm.determineWinCondition());
			}
		}
		else if (commandParams.get(0).equals("attack")) {
			AttackManager am = new AttackManager(commandParams, gm, db);
			
			addoutputList(am.getOutput());
		}
		else if (commandParams.get(0).equals("use")) {
			UseManager um = new UseManager(commandParams, gm, db);
			
			addoutputList(um.getOutput());
		}
		else if (commandParams.get(0).equals("take-off") || commandParams.get(0).equals("put-on")) {
			ClothingManager clm = new ClothingManager(commandParams, gm, db);
			
			addoutputList(clm.getOutput());
		}
		else if (commandParams.get(0).equals("turn-on") || commandParams.get(0).equals("turn-off")) {
			LightSourceManager lsm = new LightSourceManager(commandParams, gm, db);
			
			addoutputList(lsm.getOutput());
		}
		else if (commandParams.get(0).equals("examine") || commandParams.get(0).equals("inventory") || commandParams.get(0).equals("clear")) {
			GenericManager gem = new GenericManager(commandParams, gm, db);
			
			addoutputList(gem.getOutput());
		}
		else if (commandParams.get(0).equals("equip") || commandParams.get(0).equals("unequip")) {
			EquipManager em = new EquipManager(commandParams, gm, db);
			
			addoutputList(em.getOutput());
		}
		else if (commandParams.get(0).equals("talk")) {
			TalkManager tm = new TalkManager(commandParams, gm, db);
			
			addoutputList(tm.getOutput());
		}
		else if (commandParams.get(0).equals("reset")) {
			System.out.println("Resetting ... ");
			ResetManager rm = new ResetManager(commandParams, gm, db);
			
			addoutputList(rm.getOutput());
			
			resetting = true;
		}
		
		// Npc attacks
	    if (!resetting) {
			AttackManager am = new AttackManager(null, gm, db);
		    addoutputList(am.getOutput());
		    
		    gm.setDeathEnding(csm.checkHealthStatus());
	    }
		
		processoutputList();
	}
	
	private void processoutputList() {
		db.addOutput(outputList);
		gm.setOutput(db.getOutput());
	}
	
	private void addoutputList(ArrayList<String> outputList) {
		for (String s : outputList) {
			this.outputList.add(s);
		}
	}
	
	private void displayGameState() {
        for (Room room : gm.getRooms()) {
        	System.out.println(room.toString());
        	
        	System.out.println("\nMy npcs are ... ");
        	for (Npc npc : room.getNpcs()) {
        		System.out.println(npc.toString());
        	}
        	
        	System.out.println("\n\n");
        	//System.out.println(room.getInventory().getItems().size() + " items in this inventory. ");
        }
        System.out.println("\n\n\n\n\n\n");
        
        System.out.println(gm.getPlayer().toString() + "Equipped weapon index: " + gm.getPlayer().getEquippedWeaponIndex());
        System.out.println("====== player INVENTORY ======");
        for (int i = 0; i < gm.getPlayer().getInventory().getItems().size(); i++) {
        	System.out.println(i + ": " + gm.getPlayer().getInventory().getItems().get(i).toString());
        }
        
        System.out.println("\n\n\n");
	}
}
