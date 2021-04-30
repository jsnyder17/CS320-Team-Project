package nullpointerexception.tbag.actors;

import java.util.StringTokenizer;

import nullpointerexception.tbag.inventory.InventoryInterface;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.LightSource;
import nullpointerexception.tbag.items.Weapon;
import nullpointerexception.tbag.inventory.Inventory;

public class Actor implements InventoryInterface {
	private String name;
	private int roomNumber;
	private int health;
	private Inventory inventory;
	private String statString;
	private int equippedWeaponIndex;
	private int actorId;
	private int inventoryIndex;
	
	public Actor() {
		name = "";
		roomNumber = -1;
		health = -1;
		inventory = new Inventory();
		equippedWeaponIndex = -1;
		inventory = new Inventory();
		//System.out.println("ActorModel default constructor called. ");
	}
	public Actor(String name, int roomNumber, int health) {
		this.name = name;
		this.roomNumber = roomNumber;
		this.health = health;
		inventory = new Inventory();
		equippedWeaponIndex = -1;

		//System.out.println("ActorModel secondary constructor called. ");
	}
	public Actor(String data) {
		StringTokenizer st = new StringTokenizer(data, "/");
		
		name = st.nextToken();
		roomNumber = Integer.parseInt(st.nextToken());
		health = Integer.parseInt(st.nextToken());
		equippedWeaponIndex = Integer.parseInt(st.nextToken());
		
		inventory = new Inventory();
		
		while (st.hasMoreTokens()) {
			inventory.addItem(new Item(st.nextToken()));
		}
	}
	
	public String getName() {
        return name;
    }
	public int getCurrentRoom() {
        return roomNumber;
    }
	public int getHealth() {
        return health;
    }
	public int getEquippedWeaponIndex() {
		return equippedWeaponIndex;
	}
	public String getStatString() {
		return statString;
	}
	
	public void setName(String names) {
        this.name = names;
        
        //System.out.println("Set name to '" + this.name + "'");
    }
	public void setRoomNumber(int RoomNumber) {
        this.roomNumber = RoomNumber;
        
        //System.out.println("Set roomNumber to '" + this.roomNumber + "'");
    }
	public void setHealth(int currentHealth) {
        this.health = currentHealth;
        
        //System.out.println("Set currentHealth to '" + this.health + "'");
    }
	public void setEquippedWeaponIndex(int equippedWeaponIndex) {
		this.equippedWeaponIndex = equippedWeaponIndex;
	}
	public void setInventoryModel(Inventory inventory) {
		this.inventory = inventory;
	}
	
	public void decrementHealth() {
		health -= 1;
	}
	
	public void setActorId(int actorId) {
		this.actorId = actorId;
	}
	
	public int getActorId() {
		return actorId;
	}
	
	public void setInventoryIndex(int inventoryIndex) {
		this.inventoryIndex = inventoryIndex;
	}
	
	public int getInventoryIndex() {
		return inventoryIndex;
	}
	
	public String fileOutput() {
		String fileOutputStr = "";
		fileOutputStr += (name + "/" + roomNumber + "/" + health + "/" + equippedWeaponIndex + "/");
		
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
	
	public Inventory getInventory() {
		return inventory;
	}
	public void addItem(Item item) {
		inventory.addItem(item);
	}
	public void removeItem(Item item) {
		inventory.removeItem(item);
	}
}
