package nullpointerexception.tbag.items;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Weapon extends Item {
	private int damage;
	private boolean equipped;
	
	public Weapon() {
		super();
		damage = 0;
		equipped = false;
	}
	public Weapon(String name, boolean used, int damage, boolean equipped, int type) {
		super();
		
		super.setName(name);
		super.setUsed(used);
		this.damage = damage;
		this.equipped = equipped;
		super.setType(type);
	}
	public Weapon(String data) {
		super();
		
		StringTokenizer st = new StringTokenizer(data, ",");
		
		super.setName(st.nextToken());
		super.setDescription(new ArrayList<String>());
		super.setUsed(Boolean.parseBoolean(st.nextToken()));
		
		damage = Integer.parseInt(st.nextToken());
		equipped = Boolean.parseBoolean(st.nextToken());
		
		super.setType(Integer.parseInt(st.nextToken()));
	}
	
	public int getDamage() {
		return damage;
	}
	public boolean getEquipped() {
		return equipped;
	}
	
	public String getEquippedString() {
		if (equipped == true) {
			return "true";
		}
		else if (equipped == false) {
			return "false";
		}
		return null;
	}
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	public void setEquipped(boolean equipped) {
		this.equipped = equipped;
	}
	
	public String fileOutput() {
		return (super.getName() + "," + super.getUsed() + "," + damage + "," + equipped + "," + super.getType());
	}
	
	public String toString() {
		return (super.getItemId() + "      " + super.getName() + "      " + super.getUsed() + "      " + damage + "      " + equipped + "      " + super.getType());
	}
}
