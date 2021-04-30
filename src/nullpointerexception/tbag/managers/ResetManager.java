package nullpointerexception.tbag.managers;

import java.util.ArrayList;

import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class ResetManager extends Manager {
	public ResetManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		super(commandParams, gm, db);
		
		executeCommand();
	}
	
	private void executeCommand() {
		db.dropTables();
		db.createTables();
		db.loadInitialData();
		
		output.add("Reset successful! ");
	}
}
