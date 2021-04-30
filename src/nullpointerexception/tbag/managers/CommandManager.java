package nullpointerexception.tbag.managers;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class CommandManager {
	private String command;
	private ArrayList<String> commandParams;
	
	public CommandManager() {
		command = "";
		commandParams = new ArrayList<String>();
		
		System.out.println("CommandManager default constructor called. ");
	}
	
	public String getCommand() {
		return command;
	}
	
	public ArrayList<String> getCommandParams() {
		int tokens = 0;
		
		commandParams.clear();
		
		if (checkInput()) {
			
			StringTokenizer st = new StringTokenizer(command, " ");
			
			tokens = st.countTokens();
			
			for (int i = 0; i < tokens; i++) {
				commandParams.add(st.nextToken());
			}
		}
		else {
			commandParams.add("ERROR");
		}
		
		return commandParams;
	}
	
	public void setCommand(String command) {
		this.command = command.toLowerCase();
		
		System.out.println("Set command to '" + this.command + "'");
	}
	
	private boolean checkInput() {
		boolean goodData = false;
		
		System.out.println("Checking input ... ");
		
		if (command.length() > 0) {
			goodData = true;
			System.out.println("Input is valid. ");
		}
		
		return goodData;
	}
}
