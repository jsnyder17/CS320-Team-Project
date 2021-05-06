package nullpointerexception.tbag.other;

import nullpointerexception.tbag.controller.GameManagerController;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DatabaseProvider;
import nullpointerexception.tbag.persist.DerbyDatabase;
import nullpointerexception.tbag.persist.IDatabase;

public class TestMain {
	public static void main(String[] args) {
		DerbyDatabase db = new DerbyDatabase();
		
		GameManagerModel gm = new GameManagerModel();
		//GameManagerController gmc = new GameManagerController(gm, db);
	}
}
