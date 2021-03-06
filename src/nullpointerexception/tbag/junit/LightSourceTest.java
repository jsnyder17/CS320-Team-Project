package nullpointerexception.tbag.junit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import nullpointerexception.tbag.items.LightSource;

public class LightSourceTest {
private LightSource model;
	
	@Before
	public void setup() {
		model = new LightSource("Light,false,false,0");
	}
	
	@Test
	public void testgetLit() {
		
		assertFalse(model.getLit());
	}
	@Test
	public void testSetLit() {
		model.setLit(true);
		
		assertTrue(model.getLit());
	}
}
