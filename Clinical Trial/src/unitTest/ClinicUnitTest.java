package unitTest;

import trial.Clinic;
import org.junit.Test;
import org.junit.Assert;

/**
 * The Class ClinicUnitTest.
 */
public class ClinicUnitTest {
	
	/** The c. */
	Clinic c;
	
	/** The i. */
	String nm, i;
	
	/**
	 * Test all.
	 */
	@Test
	public void testAll() {
		nm = "Clinic 1";
		i = "001";
		
		c = new Clinic(nm, i);
		
		Assert.assertNotNull(c);
		
		Assert.assertEquals(nm, c.getName());
		Assert.assertEquals(i, c.getId());
		
		c.setID("002");
		c.setName("Clinic 2");
		
		Assert.assertEquals("002", c.getId());
		Assert.assertEquals("Clinic 2", c.getName());
	}

}
