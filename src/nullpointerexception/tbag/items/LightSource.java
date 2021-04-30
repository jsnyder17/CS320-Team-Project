package nullpointerexception.tbag.items;

import java.util.StringTokenizer;

public class LightSource extends Item {
	private boolean lit;
	
	public LightSource() {
		super();
		lit = false;
	}
	public LightSource(String name, boolean used, boolean lit, int type) {
		super();
		
		super.setName(name);
		super.setUsed(used);
		this.lit = lit;
		super.setType(type);
	}
	public LightSource(String data) {
		super();
		
		StringTokenizer st = new StringTokenizer(data, ",");
		
		super.setName(st.nextToken());
		super.setUsed(Boolean.parseBoolean(st.nextToken()));
		
		lit = Boolean.parseBoolean(st.nextToken());
		
		super.setType(Integer.parseInt(st.nextToken()));
	}
	
	public boolean getLit() {
		return lit;
	}
	
	public String getLitString() {
		if (lit == true) {
			return "true";
		}
		else if (lit == false) {
			return "false";
		}
		return null;
	}
	
	public void setLit(boolean lit) {
		this.lit = lit;
	}
	
	public String fileOutput() {
		return (super.getName() + "," + super.getUsed() + "," + lit + "," + type);
	}
	public String toString() {
		return super.getItemId() + "      " + super.getName() + "      " + super.getUsed() + "      " + lit + "      " + super.getType();
	}
}
