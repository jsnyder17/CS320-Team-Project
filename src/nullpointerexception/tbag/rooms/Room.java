package nullpointerexception.tbag.rooms;

import java.util.ArrayList;
import java.util.StringTokenizer;

import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.inventory.InventoryInterface;
import nullpointerexception.tbag.inventory.Inventory;
import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.items.FinalRoomPuzzle;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.LightSource;
import nullpointerexception.tbag.items.Weapon;

public class Room implements InventoryInterface {
    private String name;
    private int roomNumber;
    private String description;
    private String roomDescription;
    private boolean prevDiscovered;
    private boolean secretDiscovered;
    private boolean isDark;
    private ArrayList<Door> doorLocations;
    private ArrayList<Npc> npcs;
    private Inventory inventory = new Inventory();
    private int roomId;
    
    private int nDoorId;
    private int sDoorId;
    private int eDoorId;
    private int wDoorId;
    private int uDoorId;
    private int dDoorId;
    
    private int inventoryId;
    /*
    private DoorModel northDoor;
    private DoorModel southDoor;
    private DoorModel eastDoor;
    private DoorModel westDoor;
    private DoorModel upDoor;
    private DoorModel downDoor;
    */
    public Room() {
    	roomId = 0;
    	name = "";
    	roomDescription = "";
    	prevDiscovered = false;
    	secretDiscovered = false;
    	isDark = false;
    	inventory = new Inventory();
    	doorLocations = new ArrayList<Door>();
    	npcs = new ArrayList<Npc>();
    	doorLocations.add(null);
    	doorLocations.add(null);
    	doorLocations.add(null);
    	doorLocations.add(null);
    	doorLocations.add(null);
    	doorLocations.add(null);
    }
    public Room(String name, int roomNumber, String description, boolean prevDiscovered, boolean secretDiscovered, boolean isDark, Door north, Door south, Door east, Door west, Door up, Door down){
    	name = this.name;
        this.roomNumber = roomNumber;
        this.roomDescription = description;
        this.prevDiscovered = prevDiscovered;
        this.secretDiscovered = secretDiscovered;
        this.isDark = isDark;
        //location 0 north
        doorLocations.add(north);
        //northDoor = north;
        //location 1 south
        doorLocations.add(south);
        //outhDoor = south;
        //location 2 east
        doorLocations.add(east);
        //eastDoor = east;
        //location 3 west
        doorLocations.add(west);
        //westDoor = west;
        //location 4 roof
        doorLocations.add(up);
        //upDoor = up;
        //location 5 floor
        doorLocations.add(down);
        //downDoor = down;
        
        // Add room numbers to each door 
        for (int i = 0; i < doorLocations.size(); i++) {
        	if (doorLocations.get(i) != null) {
        		doorLocations.get(i).addRoomCoords(this.roomNumber);
        	}
        }
        
        //System.out.println("RoomModel secondary constructor called. ");
    }
    public Room(String data) {
    	StringTokenizer st = new StringTokenizer(data, "/");
    	String doorInfo = "";
    	String itemString = "";
    	int itemType = 0;
    	int npcsSize = 0;
    	
    	name = st.nextToken();
    	roomNumber = Integer.parseInt(st.nextToken());
    	roomDescription = st.nextToken();
    	prevDiscovered = Boolean.parseBoolean(st.nextToken());
    	secretDiscovered = Boolean.parseBoolean(st.nextToken());
    	isDark = Boolean.parseBoolean(st.nextToken());
    	
    	for (int i = 0; i < 6; i++) {
    		doorInfo = st.nextToken();
    		if (doorInfo.equals("null")) {
    			doorLocations.add(null);
    		}
    		else {
    			Door door = new Door(doorInfo);
    			
    			// Set direction strings of door 
    			if (i == 0 || i == 1) {
    				door.setDirectionString("|");
    			}
    			else if (i == 2 || i == 3) {
    				door.setDirectionString("-");
    			}
    			
    			doorLocations.add(door);
    		}
    	}
    	
    	// Add NPCs 
    	npcsSize = Integer.parseInt(st.nextToken());
    	
    	npcs = new ArrayList<Npc>();
    	
    	for (int i = 0; i < npcsSize; i++) {
    		npcs.add(new Npc(st.nextToken()));
    	}
    	
    	// Inventory 
    	inventory = new Inventory();
    	
    	while (st.hasMoreTokens()) {
    		itemString = st.nextToken();
    		String[] itemArray = itemString.split(",");
    		itemType = Integer.parseInt(itemArray[itemArray.length - 1]);

    		// Determine item type to add
    		if (itemType == 0 || itemType == 1) {
    			addItem(new Item(itemString));
    		}
    		else if (itemType == 2) {
    			addItem(new LightSource(itemString));
    		}
    		else if (itemType == 3) {
    			addItem(new Clothing(itemString));
    		}
    		else if (itemType == 4) {
    			addItem(new Weapon(itemString));
    		}
    		else if (itemType == 5) {
    			//addItem(new FinalRoomPuzzle(itemString));
    		}
    	}

    	calcDescription();
    }
    
    public int getRoomId() {
    	return roomId;
    }
    public String getDescription() {
    	calcDescription();
        return description + "(" + inventory.getItems().size() + ") items. ";
    }
    public String getRoomDescription() {
    	return roomDescription;
    }
    public boolean getPrevDiscovered() {
        return prevDiscovered;
    }
    public String getPrevDiscoveredString()
    {
    	if (prevDiscovered == true) {
    		return "true";
    	}
    	else if (prevDiscovered == false) {
    		return "false";
    	}
    	return null;
    }
    public boolean getSecretDiscovered() {
        return secretDiscovered;
    }
    
    public String getSecretDiscoveredString() {
    	if (secretDiscovered == true) {
    		return "true";
    	}
    	else if (secretDiscovered == false) {
    		return "false";
    	}
    	return null;
    }
    
    public boolean getIsDark() {
    	return isDark;
    }
    public String getIsDarkString() {
    	if (isDark == true) {
    		return "true";
    	}
    	else if (isDark == false) {
    		return "false";
    	}
    	return null;
    }
    public int getRoomNumber() {
        return roomNumber;
    }
    public ArrayList<Door> getDoorLocations() {
    	return doorLocations;
    }
    public Door getNorthDoor() {
    	return doorLocations.get(0);
    }
    public Door getSouthDoor() {
    	return doorLocations.get(1);
    }
    public Door getEastDoor() {
    	return doorLocations.get(2);
    }
    public Door getWestDoor() {
    	return doorLocations.get(3);
    }
    public Door getUpDoor() {
    	return doorLocations.get(4);
    }
    public Door getDownDoor() {
    	return doorLocations.get(5);
    }
    
    public void setInventoryId(int inventoryId) {
    	this.inventoryId = inventoryId;
    }
    
    public void setNorthDoorId(int nDoorId) {
    	this.nDoorId = nDoorId;
    }
    
    public void setSouthDoorId(int sDoorId) {
    	this.sDoorId = sDoorId;
    }
    public void setEastDoorId(int eDoorId) {
    	this.eDoorId = eDoorId;
    }
    public void setWestDoorId(int wDoorId) {
    	this.wDoorId = wDoorId;
    }
    public void setUpDoorId(int uDoorId) {
    	this.uDoorId = uDoorId;
    }
    public void setDownDoorId(int dDoorId) {
    	this.dDoorId = dDoorId;
    }
    
    public int getInventoryId() {
    	return inventoryId;
    }
    
    public int getNorthDoorId() {
    	return nDoorId;
    }
    public int getSouthDoorId() {
    	return sDoorId;
    }
    public int getEastDoorId() {
    	return eDoorId;
    }
    public int getWestDoorId() {
    	return wDoorId;
    }
    public int getUpDoorId() {
    	return uDoorId;
    }
    public int getDownDoorId() {
    	return dDoorId;
    }
    
    public ArrayList<Npc> getNpcs() {
    	return npcs;
    }
    
    public void setRoomId(int roomId) {
    	this.roomId = roomId;
    }
    public void setName(String name) {
    	this.name = name;
    	//System.out.println("Name: " + this.name);
    }
    public void setRoomNumber(int roomNumber) {
    	this.roomNumber = roomNumber;
    }
    public void setRoomDescription(String roomDescription) {
    	this.roomDescription = roomDescription;
    }
    public void setPrevDiscovered(boolean discoveredStatus) {
        this.prevDiscovered = discoveredStatus;
        
        //System.out.println("Set prevDiscovered to '" + this.prevDiscovered + "'");
    }
    public void setSecretDiscovered(boolean secretStatus) {
        this.secretDiscovered = secretStatus;
        
        //System.out.println("Set secretDiscovered to '" + this.secretDiscovered + "'");
    }
    public void setIsDark(boolean isDark) {
    	this.isDark = isDark;
    	
    	//System.out.println("Set isDark to: '" + this.isDark + "'");
    }
    public void addNorthDoor(Door door) {
    	this.doorLocations.add(door);
    }
    public void addSouthDoor(Door door) {
    	this.doorLocations.add(door);
    }
    public void addEastDoor(Door door) {
    	this.doorLocations.add(door);
    }
    public void addWestDoor(Door door) {
    	this.doorLocations.add(door);
    }
    public void addUpDoor(Door door) {
    	this.doorLocations.add(door);
    }
    public void addDownDoor(Door door) {
    	this.doorLocations.add(door);
    }
    
    public void addNpc(Npc npc) {
    	//System.out.println("Adding npc" + npc.toString() + " ********************************************************************************************** ");
    	npcs.add(npc);
    }
    
    public Npc getNpc(String name) {
    	//System.out.println("NPC size: " + npcs.size());
    	
    	int index = 0;
    	boolean found = false;
    	Npc npc = null;
    	
    	while (!found && index < npcs.size()) {
    		//System.out.println("Is " + npcs.get(index).getName() + " == " + name + "?");
    		if (npcs.get(index).getName().compareTo(name) == 0) {
    			npc = npcs.get(index);
    			found = true;
    		}
    		else {
    			index += 1;
    		}
    	}
    	
    	return npc;
    }
    
    public String fileOutput() {
    	String fileOutputStr = "";
    	
    	fileOutputStr += (name + "/" + roomNumber + "/" + roomDescription + "/" + prevDiscovered + "/" + secretDiscovered + "/" + isDark + "/");
    	
    	for (int i = 0; i < 6; i++) {
    		if (doorLocations.get(i) != null) {
    			fileOutputStr += (doorLocations.get(i).fileOutput() + "/");
    		}
    		else {
    			fileOutputStr += ("null/");
    		}
    	}
    	
    	fileOutputStr += (npcs.size() + "/");   			
    	
    	for (int i = 0; i < npcs.size(); i++) {
    		fileOutputStr += (npcs.get(i).fileOutput()) + "/";
    	}
    	
    	for (int i = 0; i < inventory.getItemCount(); i++) {
    		if (i != inventory.getItemCount() - 1) {
    			fileOutputStr += (inventory.getItems().get(i).fileOutput() + "/");
    		}
    		else {
    			fileOutputStr += (inventory.getItems().get(i).fileOutput());
    		}
    	}
    	
    	return fileOutputStr;
    }
    
    public void calcDescription() {
    	//System.out.println("Calculating description ... ");
    	if (!isDark) {
	    	description = roomDescription;
	    	
	    	if (inventory.getItems().size() > 0) {
	    		//System.out.println("Items found. ");
	    		description += " There is a ";
	
	    		for (int i = 0; i < inventory.getItems().size(); i++) {
	    			if (i != inventory.getItems().size() - 1) {
	    				if (inventory.getItems().size() > 2) {
	    					description += inventory.getItems().get(i).getName() + ", ";
	    				}
	    				else {
	    					description += inventory.getItems().get(i).getName() + " ";
	    				}
	    			}
	    			else {
	    				if (inventory.getItems().size() > 1) {
	    					description += ("and " + inventory.getItems().get(i).getName());
	    				}
	    				else {
	    					description += inventory.getItems().get(i).getName();
	    				}
	    			}
	    		}
	    		
	    		description += " here. ";
	    	}
	    	else {
	    		description += " ";
	    	}
	    	
	    	// NPCs
	    	if (npcs.size() > 0) {
	    		description += "You see a "; 
	    		
	    		for (int i = 0; i < npcs.size(); i++) {
	    			if (i != npcs.size() - 1) {
	    				if (npcs.size() > 2) {
	    					description += (npcs.get(i).getName() + ", ");
	    				}
	    				else {
	    					description += (npcs.get(i).getName() + " ");
	    				}
	    			}
	    			else {
	    				if (npcs.size() > 1) {
	    					description += ("and a " + npcs.get(i).getName());
	    				}
	    				else {
	    					description += npcs.get(i).getName();
	    				}
	    			}
	    		}
	    		
	    		description += " here. ";
	    	}
    	}
    	else {
    		description = "It's pitch black in here. You can't see a thing. ";
    	}
    	
    	//System.out.println(description);
    }
    
    public Inventory getInventory() {
    	return inventory;
    }
    public void addItem(Item item) {
    	inventory.addItem(item);
    	
    	calcDescription();
    }
    public void removeItem(Item item) {
    	inventory.removeItem(item);
    	
    	calcDescription();
    }
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	
	public void setNorthDoor(Door door) {
		doorLocations.set(0, door);
	}
	
	public void setSouthDoor(Door door) {
		doorLocations.set(1, door);
	}
	
	public void setEastDoor(Door door) {
		doorLocations.set(2, door);
	}
	
	public void setWestDoor(Door door) {
		doorLocations.set(3,  door);
	}
	
	public void setUpDoor(Door door) {
		doorLocations.set(4,  door);
	}
	
	public void setDownDoor(Door door) {
		doorLocations.set(5, door);
	}
	public String toString() {
		String str = "ROOM #" + roomNumber + "\n";
		str += "===========================================================================\n";
		str += "ID: " + roomId + "Room	#: " + roomNumber + "	Name: " + name + "\n\n";
		str += "------------ Inventory ------------\n";
		for (Item item : inventory.getItems()) {
			str += item.toString() + "\n";
		}
		str += "\n\n\n\n";
		
		return str;
	}
}