import static org.junit.Assert.*;
import java.util.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
public class TestDriver {
	private List<Trip> mytrips = new ArrayList<>(); 
	private String driverName;
	private Driver testdriver;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("init all method...");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("destory all method...");
	}
	
	@Before
	public void setUp() {
		System.out.println("start setUp method");
		driverName = "Wang";
		mytrips.add(new Trip("Wang", 40, 0.5));
		mytrips.add(new Trip("Wang", 20, 0.2));
		mytrips.add(new Trip("Wang", 400, 5));
		mytrips.add(new Trip("Wang", 300, 12));		
		testdriver = new Driver(driverName, mytrips, 0, 0);
	}
	
	@After
	public void tearDown() {
		System.out.println("end tearDown method");
		System.out.println();
		driverName = null;
		testdriver = null;
		mytrips.clear();
	}
	
	@Test
	public void testgetDriverName() {
		System.out.println("testgetDriverName method");
		String name = testdriver.getDriverName();
		assertEquals("Wang", name);
	}
	
	@Test
	public void testgetDriverNameWhenEmptyDriverNameAdded() {
		System.out.println("testgetDriverName2 method with empty driver name");
		Driver testdriver2 = new Driver("", new ArrayList<Trip>(), 0, 0); // driver name is ""
		assertEquals("", testdriver2.getDriverName());
	}
	
	@Test(expected = Exception.class)
	public void testgetDriverNameWhenNullNameDriverNameAddedReturnAnError() {
		System.out.println("testgetDriverName3 method with null");
		Driver testdriver3 = new Driver(null, new ArrayList<Trip>(), 0, 0); // driver name is null or ""
		assertTrue(testdriver3.getDriverName().isEmpty());
	}
	
	@Test
	public void testsetDriverName() {
		System.out.println("testsetDriverName method");
		testdriver.setDriverName("wang");
		assertEquals("wang", testdriver.driverName);
	}
	
	@Test
	public void testgetMileage() {
		System.out.println("testgetMileage method");
		double target = 40 + 20 + 400 + 300;
		assertEquals(target, testdriver.getMileage(), 0);
	}
	
	@Test
	public void testgetTime() {
		System.out.println("testgetTime method");
		double target = 0.5 + 0.2 + 5 + 12;
		assertEquals(target,  testdriver.getTime(), 0);
	}
	
	@Test
	public void testgetMph() {
		System.out.println("testgetMph method");
		double target = (40 + 20 + 400 + 300) / (0.5 + 0.2 + 5 + 12);
		assertEquals((int)Math.round(target), testdriver.getMph());
	}
	
	@Test
	public void testclear() {
		System.out.println("testclear method");
		testdriver.clear();
		assertEquals(0,  testdriver.trips.size());
	}
	
	@Test
	public void testisUnMovedWithMovedDriver() {
		System.out.println("testisUnmoved method with moved driver");
		assertFalse(testdriver.isUnMoved());
	}
	
	@Test()
	public void testisUnmovedWithUnMovedDriver() {
		System.out.println("testisUnmoved method with unmoved driver");
		Driver testdriver2 = new Driver("henry", new ArrayList<Trip>(), 0, 0);
		assertTrue(testdriver2.isUnMoved());
	}
}
