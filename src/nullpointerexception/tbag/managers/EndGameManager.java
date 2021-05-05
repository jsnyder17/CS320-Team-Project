package nullpointerexception.tbag.managers;

import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class EndGameManager extends Manager {
	public EndGameManager(GameManagerModel gm, DerbyDatabase db) {
		super(gm, db);
	}
}
