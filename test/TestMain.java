import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.time.format.DateTimeParseException;
import java.util.*;
public class TestMain {
	private Map<String, Driver> map = new HashMap<>();
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
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
		map = new HashMap<>();
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("end tearDown method");
		System.out.println();
	}

	@Test
	public void testReadFile() throws IOException{
		 System.out.println("readFile normal");
		 String pathname = "input.txt";
		 Main.readFile(pathname, map);
	     assertFalse(map.isEmpty());
	}
	
	@Test(expected = FileNotFoundException.class)
	public void testReadNonexistentFile() throws IOException{
		 System.out.println("readFile with nonexistent input file");
		 String pathname = "aaa.txt";
		 Main.readFile(pathname, map);
		 assertTrue(map.isEmpty());
	}
	
//	@Test(expected = IllegalArgumentException.class)
//	public void testReadFile3() throws IOException {
//		 System.out.println("readFile with same name");
//		 String pathname = "input2.txt";
//		 Main.readFile(pathname, map);
//	}
	
//	@Test(expected = NullPointerException.class)
//	public void testReadFile4() throws IOException{
//		 System.out.println("readFile with input file that has command Trip before command Driver");
//		 String pathname = "input3.txt";
//		 Main.readFile(pathname, map);
//	}
//	
	@Test
	public void testparseTime() {
		System.out.println("testparseTime");
		String startTime = "16:02";
		String endTime = "17:02";
		double hours = Main.parseTime(startTime, endTime);
		assertEquals(1.0, hours, 0);
	}
	
	@Test(expected = DateTimeParseException.class)
	public void testparseTimeWithInvalidFormatAnExceptionThrown() {
		System.out.println("testparseTime with DateTimeParseException over 24 hours");
		String startTime = "16:02";
		String endTime = "27:02";
		Main.parseTime(startTime, endTime);
	}
	
	@Test
	public void testparseTimeWithEarlierEndTimeAnExceptionThrown() {
		System.out.println("testparseTime with endTime > startTime");
		String startTime = "16:02";
		String endTime = "11:02";
		exceptionRule.expect(IllegalArgumentException.class);
		exceptionRule.expectMessage("Error: the end Time is before the start Time");
		Main.parseTime(startTime, endTime);		
	}

	@Test(expected = DateTimeParseException.class)
	public void testparseTimeWithInvalidInputAnExceptionThrown() {
		System.out.println("testparseTime with illeagal input");
		String startTime = "16:02";
		String endTime = "11.02";
		Main.parseTime(startTime, endTime);		
	}
	
	@Test
	public void testMain() throws IOException {
		System.out.println("main");
		String[] args = null;
		final InputStream original = System.in;
		Main.main(args);
		System.setIn(original); //set the original back after the main method has finished
		File file = new File("report.txt");
		assertTrue(file.exists());
	}
	
	@Test
	public void testWriteFile() throws IOException {
		System.out.println("testWriteFile");
		map.put("wang", new Driver("wang", new ArrayList<>(), 0, 0));
		map.get("wang").trips.add(new Trip("wang", 100, 2));
		map.get("wang").mileage += 100;
		map.get("wang").time += 2;
		PriorityQueue<Map.Entry<String, Driver>> pq = new PriorityQueue<>();
		for (Map.Entry<String, Driver> entry : map.entrySet()) {
			pq.offer(entry);
		}
		Main.writeFile(pq);
		File file = new File("report.txt");
		Scanner myReader = new Scanner(file);
		String actual = myReader.nextLine();
		myReader.close();
		String target = "wang: 100 miles @ 50 mph"; //target statement
		assertEquals(actual, target);
	}
}
