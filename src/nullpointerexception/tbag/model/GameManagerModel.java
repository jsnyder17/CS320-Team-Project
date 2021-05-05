package nullpointerexception.tbag.model;

import java.util.ArrayList;

import nullpointerexception.tbag.actors.Player;
//import nullpointerexception.tbag.other.CommandManager;
import nullpointerexception.tbag.rooms.Room;

public class GameManagerModel {
	private Player player;
	//private CommandManager commandManager;
	private ArrayList<String> outputHistory;	// This field will be used in the future to display the total history of outputs 
	private ArrayList<String> commandParams;
	//private ArrayList<DoorModel> doors;
	private ArrayList<Room> rooms;
	private String output;
	private int currentRoomIndex;
	private boolean endGame;
	private boolean deathEnding;
	private boolean completedEnding;
	
	public GameManagerModel() {
		player = new Player();
		//commandManager = new CommandManager();
		outputHistory = new ArrayList<String>();
		commandParams = new ArrayList<String>();
		//doors = new ArrayList<DoorModel>();
		rooms = new ArrayList<Room>();
		output = "";
		currentRoomIndex = 0;
		endGame = false;
		deathEnding = false;
		completedEnding = false;
		
		// Add items to player's inventory
		/*
		player.addItem(new ItemModel("book", new ArrayList<String>(), false));
		player.addItem(new ItemModel("torch", new ArrayList<String>(), false));
		player.addItem(new ItemModel("compass", new ArrayList<String>(), false));
		
		// Initalize doors
		DoorModel door = new DoorModel(false, "This is the north door. ", false);
		door.addRoomCoords(1);
		door.addRoomCoords(2);
		doors.add(door);
		
		// Initialize rooms 
		RoomModel room1 = new RoomModel("Room 1", 1, "This is room 1. ", false, false, door, null, null, null, null, null);
		room1.addItem(new ItemModel("box", new ArrayList<String>(), false));
		room1.addItem(new ItemModel("paper", new ArrayList<String>(), false));
		rooms.add(room1);
		
		RoomModel room2 = new RoomModel("Room 2", 2, "This is room 2. ", false, false, null, door, null, null, null, null);
		rooms.add(room2);
		*/	
	}
	public Player getPlayer() {
		return player;
	}
	/*
	public CommandManager getCommandManager() {
		return commandManager;
	}
	*/
	/*
	public ArrayList<String> getCommandParams() {
		return commandManager.getCommandParams();
	}
	*/
	public ArrayList<String> getOutputHistory() {
		return outputHistory;
	}
	public ArrayList<Room> getRooms() {
		return rooms;
	}
	/*
	public String getCommand() { 
		return commandManager.getCommand();
	}
	*/
	public String getOutput() {
		return output;
	}
	public int getCurrentRoomIndex() {
		return currentRoomIndex;
	}
	public boolean getDeathEnding() {
		return deathEnding;
	}
	public boolean getCompletedEnding() {
		return completedEnding;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	/*
	public void setCommand(String command) {
		commandManager.setCommand(command);
		
		System.out.println("Set commandManager command to '" + commandManager.getCommand() + "'");
	}
	public void setOutputHistory(ArrayList<String> outputHistory) {
		this.outputHistory.clear();
		
		for (int i = 0; i < outputHistory.size(); i++) {
			this.outputHistory.add(outputHistory.get(i));
			System.out.println("Added '" + this.outputHistory.get(this.outputHistory.size() - 1) + " to new outputHistory ... ");
		}
	}
	public void setOutput(String output) {
		this.output = output;
		
		System.out.println("Set GameManagerOutput to '" + this.output + "'");
	}
	public void setCurrentRoomIndex(int currentRoomIndex) {
		this.currentRoomIndex = currentRoomIndex;
		
		System.out.println("Set currentRoomIndex to '" + this.currentRoomIndex + "'");
	}
	
	private void displayOutputHistory() {
		if (outputHistory.size() > 0) {
			for (int i = 0; i < outputHistory.size(); i++) {
				System.out.println(outputHistory.get(i));
			}
		}
	}
	*/
	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	public void setCurrentRoomIndex(int roomIndex) {
		this.currentRoomIndex = roomIndex;
	}
	public void setDeathEnding(boolean deathEnding) {
		this.deathEnding = deathEnding;
	}
	public void setCompletedEnding(boolean completedEnding) {
		this.completedEnding = completedEnding;
	}
	public void setOutput(ArrayList<String> outputList) {
		for (String s : outputList) {
			System.out.println("Adding " + s + " to output ... ");
			output += (s + "\n\n");
		}
	}
}
