package nullpointerexception.tbag.managers;

import nullpointerexception.tbag.items.FinalRoomPuzzle;
import nullpointerexception.tbag.items.Orb;
import nullpointerexception.tbag.model.GameManagerModel;
import nullpointerexception.tbag.persist.DerbyDatabase;
import nullpointerexception.tbag.rooms.Room;

public class CheckEndGameManager extends Manager {
	public CheckEndGameManager(GameManagerModel gm, DerbyDatabase db) {
		super(gm, db);
	}
	
	public boolean determineWinCondition() {
		boolean endGame = false;
		
		// Check the final room puzzle
		FinalRoomPuzzle frp = (FinalRoomPuzzle)gm.getRooms().get(10).getInventory().getItem("orb_container");
		
		if (frp.checkOrder()) {
			endGame = true;
		}
		else {
			ejectOrbs(frp);
			
			endGame = false;
			
			output.add("After inserting the orb, the device makes a loud rattling noise and the orbs eject from the machine onto the floor. ");
		}
		
		return endGame;
	}
	
	private void ejectOrbs(FinalRoomPuzzle frp) {
		int index = frp.getInventory().getItems().size() - 1;
		Room room = gm.getRooms().get(gm.getCurrentRoomIndex());
		
		while (index > -1) {
			Orb orb = (Orb)frp.getInventory().getItems().get(index);
			
			frp.removeItem(orb);
			room.addItem(orb);
			
			// TODO - UPDATE DATABASE 
			
			index -= 1;
		}
	}
}
