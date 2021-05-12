package nullpointerexception.tbag.junit;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import nullpointerexception.tbag.items.Weapon;

public class WeaponTest {
private Weapon model;
	
	@Before
	public void setup() {
		model = new Weapon();
	}
	
	@Test
	public void testGetDamage() {
		equals(model.getDamage());
	}
	
	@Test
	public void testSetDamage() {
		int Damage = 1;
		
		model.setDamage(Damage);
		
		assertEquals(Damage, model.getDamage());
	}
}
