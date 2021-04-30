package nullpointerexception.tbag.items;

import java.util.StringTokenizer;

public class Clothing extends Item {
	private boolean wearing;
	
	public Clothing() {
		super();
		wearing = false;
	}
	public Clothing(String name, boolean used, boolean wearing, int type) {
		super();
		
		super.setName(name);
		super.setUsed(used);
		this.wearing = wearing;
		super.setType(type);
	}
	public Clothing(String data) {
		super();
		
		StringTokenizer st = new StringTokenizer(data, ",");
		
		super.setName(st.nextToken());
		super.setUsed(Boolean.parseBoolean(st.nextToken()));
		
		wearing = Boolean.parseBoolean(st.nextToken());
		
		super.setType(Integer.parseInt(st.nextToken()));
	}
	
	public boolean getWearing() {
		return wearing;
	}
	
	public String getWearingString() {
		if (wearing == true) {
			return "true";
		}
		else if (wearing == false) {
			return "false";
		}
		return null;
	}
	
	public void setWearing(boolean wearing) {
		this.wearing = wearing;
	}
	
	public String fileOutput() {
		return (super.getName() + "," + super.getUsed() + "," + wearing + "," + type);
	}
	
	public String toString() {
		return (super.getItemId() + "      " + super.getName() + "      " + super.getUsed() + "      " + wearing + "      " + type);
	}
}
