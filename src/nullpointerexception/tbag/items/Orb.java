package nullpointerexception.tbag.items;

import java.util.StringTokenizer;

public class Orb extends Item {
	
	public Orb() {
		super();
	}
	public Orb(String name, boolean used, int type) {
		super();
		
		super.setName(name);
		super.setUsed(used);
		super.setType(type);
	}
	public Orb(String data) {
		super();
		
		StringTokenizer st = new StringTokenizer(data, ",");
		
		super.setName(st.nextToken());
		super.setUsed(Boolean.parseBoolean(st.nextToken()));
		super.setType(Integer.parseInt(st.nextToken()));
	}
	public String fileOutput() {
		return (super.getName() + "," + super.getUsed() + "," + super.getType());
	}
	public String toString() {
		return super.getItemId() + "      " + super.getName() + "      " + super.getUsed() + "      " + super.getType();
	}
}
