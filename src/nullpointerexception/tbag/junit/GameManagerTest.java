package nullpointerexception.tbag.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import nullpointerexception.tbag.actors.Actor;
import nullpointerexception.tbag.actors.Player;
import nullpointerexception.tbag.model.GameManagerModel;



public class GameManagerTest {
	private GameManagerModel model;
	
	@Before
	public void setup() {
		model = new GameManagerModel();
	}
	
	@Test
	public void testPlayer() {
		Player player = new Player();
		model.setPlayer(player);
		assertEquals(player, model.getPlayer());
	}
	@Test
	public void testOutput() {
		ArrayList<String> list = new ArrayList<String>();
		model.setOutput(list);
		assertEquals(list, model.getOutputHistory());
	}
	@Test
	public void testRoomIndex() {
		int index = 1;
		model.setCurrentRoomIndex(index);
		assertEquals(index, model.getCurrentRoomIndex());
	}
	@Test
	public void testDeathEnding() {
		boolean death = true;
		model.setDeathEnding(death);
		
		assertEquals(true, model.getDeathEnding());
	}
	@Test
	public void testCompletedEnding() {
		boolean comp = true;
		model.setCompletedEnding(comp);
		assertEquals(true, model.getCompletedEnding());
	}
	@Test
	public void testKiller() {
		String killer = "joe";
		model.setKiller(killer);
		assertEquals(killer, model.getKiller());
	}
}
