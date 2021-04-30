package nullpointerexception.tbag.managers;

import java.util.ArrayList;

import nullpointerexception.tbag.actors.Npc;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;

public class TalkManager extends Manager {
	public TalkManager(ArrayList<String> commandParams, GameManagerModel gm, DerbyDatabase db) {
		super(commandParams, gm, db);
		
		executeCommand();
	}
	
	private void executeCommand() {
		if (commandParams.size() > 1) {
			Npc npc = gm.getRooms().get(gm.getCurrentRoomIndex()).getNpc(commandParams.get(1));
			
			if (npc != null) {
				if (!npc.getHostile()) {
					output.add(npc.getName() + ": '" + npc.getRandomNormalDialogue() + "' ");
				}
				else {
					output.add("You sense that they're not in the mood to talk right now. ");
				}
			}
			else {
				output.add("Who is that? ");
			}
		}
		else {
			output.add("They're not here right now. ");
		}
	}
}
	
