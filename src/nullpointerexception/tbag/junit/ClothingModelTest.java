package nullpointerexception.tbag.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import nullpointerexception.tbag.items.Clothing;



public class ClothingModelTest {
	private Clothing model;
	
	@Before
	public void setup() {
		model = new Clothing("Deep_V_Neck,false,false,1");
	}
	
	@Test
	public void testsetWearing() {
		model.setWearing(true);
		
		assertTrue(model.getWearing());
	}
}