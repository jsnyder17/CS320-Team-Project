package nullpointerexception.tbag.items;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Item {
	private int itemId;
	private String name;
	private ArrayList<String> description;
	private boolean used;
	int type; //0 normal item, 1 clothing 
	
	public Item() {
		itemId = 0;
		name = "";
		description = new ArrayList<String>();
		used = false;
		type = 0;
		
		//System.out.println("ItemModel default constructor called. ");
	}
	public Item(String name, ArrayList<String> description, boolean used) {
		this.name = name; 
		
		for (int i = 0; i < description.size(); i++) {
			this.description.add(description.get(i));
		}
		
		this.used = used; 
		
		//System.out.println("ItemModel secondary constructor called. ");
	}
	public Item(String name, boolean used, int type) {
		this.name = name;
		this.used = used;
		this.type = type;
	}
	public Item(String data) {
		StringTokenizer st = new StringTokenizer(data, ",");
		
		name = st.nextToken();
		used = Boolean.parseBoolean(st.nextToken());
		description = new ArrayList<String>();
		type = Integer.parseInt(st.nextToken());
	}
	
	public int getItemId() {
		return itemId;
	}
	public String getName() {
		return name;
	}
	public ArrayList<String> getDescription() {
		return description;
	}
	public String getStringDescription() {
		String stringDescription = "";
		
		for (int i = 0; i < description.size(); i++) {
			stringDescription += description.get(i);
		}
		
		return stringDescription;
	}
	public boolean getUsed() {
		return used;
	}
	public int getType() {
		return type;
	}
	public String getUsedString() {
		if (used == true) {
			return "true";
		}
		else if (used == false) {
			return "false";
		}
		return null;
	}
	
	public void setItemId(int itemId) {
		this.itemId = itemId;
		//System.out.println("Set itemId to '" + this.itemId + "'");
	}
	public void setName(String name) {
		this.name = name;
		
		//System.out.println("Set name to '" + this.name + "'");
	}
	public void setDescription(ArrayList<String> description) {
		this.description.clear();
		
		for (int i = 0; i < description.size(); i++) {
			this.description.add(description.get(i));
			//System.out.println("Added '" + this.description.get(this.description.size() - 1) + "' to description. ");
		}
	}
	public void setUsed(boolean used) {
		this.used = used;
		
		//System.out.println("Set used to " + this.used);
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public boolean equals(Item item) {
		boolean same = false;
		
		//System.out.println("Checking eqaulity of items ... ");
		
		if (this.name.compareTo(item.name) == 0 && this.used == item.used) {
			same = true;
			//System.out.println("Items are equal. ");
		}
		
		return same;
	}
	
	public String fileOutput() {
		return (name + "," + used + "," + type);
	}
	
	public String toString() {
		return (itemId + "      " + name + "      " + used + "      " + type);
	}
}
