package nullpointerexception.tbag.actors;

import java.util.StringTokenizer;

import nullpointerexception.tbag.inventory.Inventory;
import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.LightSource;
import nullpointerexception.tbag.items.Weapon;

public class Player extends Actor {
	private int vaccineUseCount;
	private String statString;
	private int playerId;
	
	public Player() {
		super();
		vaccineUseCount = 0;
		statString = "";
		playerId = 0;
		
		calcStatString();
	}
	public Player(String data) {
		super();
		
		String itemString = "";
		int itemType = 0;
		
		StringTokenizer st = new StringTokenizer(data, "/");
		
		super.setName(st.nextToken());
		super.setRoomNumber(Integer.parseInt(st.nextToken()));
		super.setHealth(Integer.parseInt(st.nextToken()));
		
		vaccineUseCount = Integer.parseInt(st.nextToken());
		
		super.setEquippedWeaponIndex(Integer.parseInt(st.nextToken()));
		
		super.setInventoryModel(new Inventory());
		
		while (st.hasMoreTokens()) {
			itemString = st.nextToken();
			String[] itemStringArray = itemString.split(",");
			itemType = Integer.parseInt(itemStringArray[itemStringArray.length - 1]);
			
			// Determine item type to add
			if (itemType == 0 || itemType == 1) {
				super.addItem(new Item(itemString));
			}
			else if (itemType == 2) {
				super.addItem(new LightSource(itemString));
			}
			else if (itemType == 3) {
				super.addItem(new Clothing(itemString));
			}
			else if (itemType == 4) {
				super.addItem(new Weapon(itemString));
			}
		}
		
		calcStatString();
	}
	
	public int getVaccineUseCount() {
		return vaccineUseCount;
	}
	public String getStatString() {
		return statString;
	}
	
	public void setVaccineUseCount(int vaccineUseCount) {
		this.vaccineUseCount = vaccineUseCount;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getPlayerId() {
		return playerId;
	}
	public String fileOutput() {
		String fileOutputStr = "";
		fileOutputStr += (super.getName() + "/" + super.getCurrentRoom() + "/" + super.getHealth() + "/" + vaccineUseCount + "/" + super.getEquippedWeaponIndex() + "/");
		
		for (int i = 0; i < super.getInventory().getItemCount(); i++) {
			Item item = super.getInventory().getItems().get(i);
			
			System.out.println("Writing '" + item.fileOutput() + "' to file ... ");
			
			if (i != super.getInventory().getItemCount() - 1) {
				fileOutputStr += (item.fileOutput() + "/");
			}
			else {
				fileOutputStr += (item.fileOutput());
			}
		}
		
		return fileOutputStr;
	}
	
	public void calcStatString() {
		statString = (super.getName() + "	 |	  " + "Health: " + super.getHealth() + "	 |	   " + "Vaccine doses: " + vaccineUseCount);
	}
	public String toString() {
		return ("Name: " + super.getName() + " Health: " + super.getHealth() + " Vaccine doses: " + vaccineUseCount);
	}
}
