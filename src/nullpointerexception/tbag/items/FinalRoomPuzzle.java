package nullpointerexception.tbag.items;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class FinalRoomPuzzle extends Item {
	private ArrayList<Orb> inserts;
	
	public FinalRoomPuzzle() {
		super();
		inserts = new ArrayList<Orb>(3);
	}
	public FinalRoomPuzzle(String name, boolean used, Orb orb1, Orb orb2, Orb orb3, int type) {
		super();
		
		super.setName(name);
		super.setUsed(used);
		
		inserts = new ArrayList<Orb>(3);
		inserts.add(orb1);
		inserts.add(orb2);
		inserts.add(orb3);
		
		super.setType(type);	
	}
	public FinalRoomPuzzle(String data) {
		super();
		
		StringTokenizer st = new StringTokenizer(data, ",");
		
		super.setName(st.nextToken());
		super.setUsed(Boolean.parseBoolean(st.nextToken()));

		inserts = new ArrayList<Orb>(3);
		
		for (int i = 0; i < 3; i++) {
			inserts.add(new Orb(st.nextToken()));
		}
		
		super.setType(Integer.parseInt(st.nextToken()));
	}
	
	public ArrayList<Orb> getInserts() {
		return inserts;
	}
	
	public boolean insert(Orb orb) {
		boolean success = false;
		
		if (orb.getName().equals("red_orb") || orb.getName().equals("green_orb") || orb.getName().equals("blue_orb")) {
			if (inserts.size() < 3) {
				inserts.add(orb);
				success = true;
			}
		}
		
		return success;
	}
	
	public boolean checkOrder() {
		boolean goodOrder = false;
		
		if (inserts.get(0).getName().equals("red_orb") && inserts.get(1).getName().equals("green_orb") && inserts.get(2).getName().equals("blue_orb")) {
			goodOrder = true;
		}
		
		return goodOrder;
	}
	
	public void clearInserts() {
		inserts.clear();
	}
	
	public String fileOutput() {
		String outputStr = "";
		
		outputStr = (super.getName() + "," + super.getUsed() + "," + super.getType());
		
		return outputStr;
	}
	
	public String toString() {
		return (super.getItemId() + "      " + super.getName() + "      " + super.getUsed());
	}
}
