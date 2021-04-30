package nullpointerexception.tbag.actors;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

import nullpointerexception.tbag.items.Clothing;
import nullpointerexception.tbag.items.FinalRoomPuzzle;
import nullpointerexception.tbag.items.Item;
import nullpointerexception.tbag.items.LightSource;
import nullpointerexception.tbag.items.Orb;
import nullpointerexception.tbag.items.Weapon;
import nullpointerexception.tbag.inventory.Inventory;

public class Npc extends Actor {
	private boolean hostile;
	private ArrayList<String> combatDialogue;
	private ArrayList<String> normalDialogue;
	private Random rand;
	private int npcId;
	
	public Npc() {
		super();
		npcId = 0;
		hostile = false;
		combatDialogue = new ArrayList<String>();
		normalDialogue = new ArrayList<String>();
		rand = new Random();
	}
	public Npc(String name, int roomNumber, int health, boolean hostile) {
		super();
		
		super.setName(name);
		super.setRoomNumber(roomNumber);
		super.setHealth(health);
		this.hostile = hostile;
		
		combatDialogue = new ArrayList<String>();
		normalDialogue = new ArrayList<String>();
	}
	public Npc(String data) {
		super();
		
		String itemString = "";
		int itemType = 0;
		int combatDialogueSize = 0;
		int normalDialogueSize = 0;
		
		StringTokenizer st = new StringTokenizer(data, "$");
		
		super.setName(st.nextToken());
		super.setRoomNumber(Integer.parseInt(st.nextToken()));
		super.setHealth(Integer.parseInt(st.nextToken()));
		super.setEquippedWeaponIndex(Integer.parseInt(st.nextToken()));
		
		hostile = Boolean.parseBoolean(st.nextToken());
		combatDialogueSize = Integer.parseInt(st.nextToken());
		
		combatDialogue = new ArrayList<String>();
		
		for (int i = 0; i < combatDialogueSize; i++) {
			combatDialogue.add(st.nextToken());
		}
		
		normalDialogueSize = Integer.parseInt(st.nextToken());
		
		normalDialogue = new ArrayList<String>();
		
		for (int i = 0; i < normalDialogueSize; i++) {
			normalDialogue.add(st.nextToken());
		}
		
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
		
		rand = new Random();
	}
	
	public int getNpcId() {
		return npcId;
	}
	public boolean getHostile() {
		return hostile;
	}
	
	public void setNpcId(int npcId) {
		this.npcId = npcId;
	}
	public void setHostile(boolean hostile) {
		this.hostile = hostile;
	}
	
	public String getHostileString() {
		if (hostile == true) {
			return "true";
		}
		else if (hostile == false) {
			return "false";
		}
		return null;
	}
	
	public String getRandomNormalDialogue() {
		return (normalDialogue.get(rand.nextInt(normalDialogue.size())));
	}
	public String getRandomCombatDialogue() {
		return (combatDialogue.get(rand.nextInt(combatDialogue.size())));
		//return "fuck you";
	}
	
	public void addNormalDialogue(String dialogue) {
		normalDialogue.add(dialogue);
	}
	public void addCombatDialogue(String dialogue) {
		combatDialogue.add(dialogue);
	}
	public void setNormalDialogue(ArrayList<String> dialogue) {
		this.normalDialogue = dialogue;
	}
	public void setCombatDialogue(ArrayList<String> dialogue) {
		this.combatDialogue = dialogue;
	}
	
	public String fileOutput() {
		String fileOutputStr = "";
		fileOutputStr += (super.getName() + "$" + super.getCurrentRoom() + "$" + super.getHealth() + "$" + super.getEquippedWeaponIndex() + "$" + hostile + "$" + combatDialogue.size() + "$");
		
		// Dialogues
		for (int i = 0; i < combatDialogue.size(); i++) {
			fileOutputStr += (combatDialogue.get(i) + "$");
		}
		
		fileOutputStr += (normalDialogue.size() + "$");
		
		for (int i = 0; i < normalDialogue.size(); i++) {
			fileOutputStr += (normalDialogue.get(i) + "$");
		}
		
		// Inventory 
		for (int i = 0; i < super.getInventory().getItemCount(); i++) {
			if (i != super.getInventory().getItemCount() - 1) {
				fileOutputStr += (super.getInventory().getItems().get(i).fileOutput() + "$");
			}
			else {
				fileOutputStr += (super.getInventory().getItems().get(i).fileOutput());
			}
		}
		
		return fileOutputStr;
	}
	
	public String toString() {
		String str = this.npcId + "      " + super.getName() + "      " + super.getCurrentRoom() + "      " + super.getHealth() + "      " + super.getEquippedWeaponIndex() + "      " + hostile;
		
		str += "\n====== " + super.getName() + " INVENTROY ======\n";
		for (Item i : super.getInventory().getItems()) {
			switch (i.getType()) {
				case 0:
					str += i.toString();
					break;
				case 1:
					str += i.toString();
					break;
				case 2:
					str += (LightSource)i;
					break;
				case 3:
					str += (Clothing)i;
					break;
				case 4:
					str += (Weapon)i;
					break;
				case 5:
					str += (FinalRoomPuzzle)i;
					break;
				case 6:
					str += (Orb)i;
					break;
			}
			
			str += "\n";
			
			for (String s : normalDialogue) {
				str += "\"" + s + "\"\n";
			}
			
			for (String s : combatDialogue) {
				str += "\"" + s + "\"\n";
			}
		}
		
		return str;
	}
}
