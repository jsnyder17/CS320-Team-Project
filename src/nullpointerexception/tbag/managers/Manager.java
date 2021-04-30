package nullpointerexception.tbag.managers;

import java.util.ArrayList;

import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class Manager {
	protected ArrayList<String> output;
	protected GameManagerModel gm;
	protected DerbyDatabase db;
	protected ArrayList<String> commandParams;
	
	public Manager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		output = new ArrayList<String>();
		
		this.commandParams = commandParams;
		this.gm = gm;
		this.db = db;
	}
	public Manager(GameManagerModel gm, DerbyDatabase db) {
		output = new ArrayList<String>();
		
		commandParams = null;
		this.gm = gm;
		this.db = db;
	}
	
	public ArrayList<String> getOutput() {
		return output;
	}
}
