import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
public class TestTrip {
	private Trip mytrip;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("init all method...");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("destory all method...");
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("start setUp method");
		mytrip = new Trip("wang", 20, 0.5); //set the trip with 20 mile and 0.5 hours
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("end tearDown method");
		System.out.println();
		mytrip = null;
	}
	
	@Test
	public void testgetDriverName() {
		System.out.println("testgetDriverName method");
		String name = mytrip.getDriverName();
		assertEquals("wang", name);
	}
	
	@Test
	public void testgetDriverNameWithEmptyDriverName() {
		System.out.println("testgetDriverName2 method");
		Trip testtrip = new Trip("", 0, 0); // driver name is ""
		assertEquals("", testtrip.getDriverName());
	}
	
	@Test(expected = Exception.class)
	public void testgetDriverNameWithNullDriverNameAnErrorReturned() {
		System.out.println("testgetDriverName3 method");
		Trip testtrip2 = new Trip(null, 0, 0); // driver name is null
		assertTrue(testtrip2.getDriverName().isEmpty());
	}
	
	@Test
	public void testsetDriverName() {
		System.out.println("testsetDriverName method");
		mytrip.setDriverName("henry");
		assertEquals("henry", mytrip.driverName);
	}

	@Test
	public void testGetMiles() {
		System.out.println("testGetMiles method");
		double miles = mytrip.getMiles();
		assertEquals(20.0, miles, 0);
	}

	@Test
	public void testSetMiles() {
		System.out.println("testSetMiles method");
		mytrip.setMiles(40.0);
		assertEquals(40.0, mytrip.miles, 0);
	}

	@Test
	public void testGetHours() {
		System.out.println("testGetHours method");
		double hours = mytrip.getHours();
		assertEquals(0.5, hours, 0);
	}

	@Test
	public void testSetHours() {
		System.out.println("testSetHours method");
		mytrip.setHours(1.0);
		assertEquals(1.0, mytrip.hours, 0);
	}

}
