package nullpointerexception.tbag.rooms;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Door {
	
	private boolean isUnlocked;
	private ArrayList<Integer> roomCoords = new ArrayList<>();
	private String description;
	private boolean isOneWay;
	private boolean used;
	private String doorKey;
	private String directionString;
	private int doorId;
	
	
	public Door (boolean isUnlocked, String description, boolean isOneWay, boolean used, String doorKeyName, int roomCoord1, int roomCoord2) {
		this.isUnlocked = isUnlocked;
		this.description = description;
		this.isOneWay = isOneWay;
		this.used = used;
		this.doorKey = doorKeyName;
		this.directionString = "";
		
		roomCoords.add(roomCoord1);
		roomCoords.add(roomCoord2);
		
		//System.out.println("DoorModel default constructor called. ");
	}
	public Door(String data) {
		StringTokenizer st = new StringTokenizer(data, ",");
		
		isUnlocked = Boolean.parseBoolean(st.nextToken());
		description = st.nextToken();
		isOneWay = Boolean.parseBoolean(st.nextToken());
		used = Boolean.parseBoolean(st.nextToken());
		doorKey = st.nextToken();
		directionString = "";
		
		while (st.hasMoreTokens()) {
			roomCoords.add(Integer.parseInt(st.nextToken()));
		}
		
		//System.out.println("Created door with roomCoords of: ");
		for (int i = 0; i < roomCoords.size(); i++) {
			//System.out.println(roomCoords.get(i) + " ");
		}
	}
	public Door() {
		
	}
	public void addRoomCoords(int roomCoord) {
		roomCoords.add(roomCoord);
		
		//System.out.println("Added: " + roomCoords.get(roomCoords.size() - 1) + " to roomCoords. ");
	}
	
	public void unlock() {
		isUnlocked = true;
		
		//System.out.println("Unlocked door. ");
	}
	
	public int getDoorId() {
		return doorId;
	}
	public boolean getIsOneWay() {
		return isOneWay;
	}
	public boolean getUsed() {
		return used;
	}
	public boolean getIsUnlocked() {
		return isUnlocked;
	}
	public String getIsUnlockedString() {
		if (isUnlocked == true) {
			return "true";
		}
		else if (isUnlocked == false) {
			return "false";
		}
		return null;
	}
	public String getIsOneWayString() {
		if (isOneWay == true) {
			return "true";
		}
		else if (isOneWay == false) {
			return "false";
		}
		return null;
	}
	public String getIsUsedString() {
		if (used == true) {
			return "true";
		}
		else if (used == false) {
			return "false";
		}
		return null;
	}
  	public ArrayList<Integer> getRoomCoords() {
		return roomCoords;
	}
	public String getDescription() {
		return description;
	}
	public String getDoorKey() {
		return doorKey;
	}
	public String getDirectionString() {
		return directionString;
	}
	public int getNextRoom(int playerCoord) {
		if (playerCoord == roomCoords.get(0)) {
			return roomCoords.get(1);
		}
		else if (playerCoord == roomCoords.get(1)) {
			return roomCoords.get(0);
		}
		else {
			return playerCoord;
		}
	}
	
	public void setDoorId(int doorId) {
		this.doorId = doorId;
	}
	public void setIsUnlocked(boolean isUnlocked) {
		this.isUnlocked = isUnlocked;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setIsOneWay(boolean isOneWay) {
		this.isOneWay = isOneWay; 
	}
	public void setDirectionString(String directionString) {
		this.directionString = directionString;
	}
	public void setUsed(boolean used) {
		this.used = used;
	}
	public void setDoorKey(String doorKey) {
		this.doorKey = doorKey;
	}
	
	public String fileOutput() {
		String fileOutputStr = "";
		
		fileOutputStr += (isUnlocked + "," + description + "," + isOneWay + "," + used + "," + doorKey + ",");
		
		for (int i = 0; i < roomCoords.size(); i++) {
			if (i != roomCoords.size() - 1) {
				fileOutputStr += (roomCoords.get(i) + ",");
			}
			else {
				fileOutputStr += roomCoords.get(i);
			}
		}
		
		return fileOutputStr;
	}
	
	public String toString() {
		return (doorId + "      " + isUnlocked + "      " + description + "      " + isOneWay + "      " + used + "      " + doorKey + "      " + roomCoords.get(0) + "      " + roomCoords.get(1));
	}
}
