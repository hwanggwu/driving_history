import java.util.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
public class Main {
	public static void readFile(String pathname, Map<String, Driver> nameToDriver) throws IOException {
		try (FileReader reader = new FileReader(pathname);
			BufferedReader br = new BufferedReader(reader)){
				String line;
				while ((line = br.readLine()) != null) {
					String[] command = line.trim().split("\\s+"); // skip extra spaces
					/* --- Command Driver --- */
					if (command[0].equals("Driver")) {
						try {
							String driverName = command[1];
							if (!nameToDriver.containsKey(driverName)) {
								Driver driver = new Driver(driverName, new ArrayList<>(), 0.0, 0.0);
								nameToDriver.putIfAbsent(driverName, driver); // prevent drivers with identical names from creating multiple driver records
							} else {
								throw new IllegalArgumentException("Warning: Driver "+ driverName + " has already existed");
							} 
							/* Skip two types of invalid Driver command: ① Identical userName
							 * 				    						 ② Not meet the standard format of Trip command, such as: 'Driver'
							 * Pop-up corresponding prompt warning then																						*/
						} catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
								System.err.println("Warning: Command line '" + line + "' is invalid");
								e.printStackTrace();
							}
					/* --- Command Trip -- */
					} else if (command[0].equals("Trip")) {
						try {
							String driverName = command[1];
							String startTime = command[2];
							String endTime = command[3];
							double miles = Double.parseDouble(command[4]);
							double hours = parseTime(startTime, endTime);
							int mph = (int)Math.round(miles / hours);
							/* Get driver from hashmap */
							Driver d = nameToDriver.get(driverName); // null pointer exception if not create the driver first
							/* Discard any trips that average a speed of < 5 mph or > 100 mph */
							if (mph >= 5 && mph <= 100) {
								Trip trip = new Trip(driverName, miles, hours);
								d.trips.add(trip);
								d.mileage += miles;
								d.time += hours;
							}
						/* Skip three types of invalid Trip command: ① DateTime can't be parsed such as: "27:04", "10.21"
						 * 											 ② EndTime is before the startTime such as: start at "16:04", and end at "11:01"
						 * 											 ③ Not meet the standard format of Trip command, such as: 'Trip 10:25 12:22 55'
						 * Pop-up corresponding prompt warning then																						*/
						} catch (DateTimeParseException | IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
							System.err.println("Warning: Command line '" + line + "' is invalid");
							e.printStackTrace();
						} catch (NullPointerException e) {
							System.err.println("Warning: Driver " + command[1] + " does not exist! You must create an user by Driver command first");
							e.printStackTrace();
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw e;
			}
	}
	
	public static double parseTime(String startTime, String endTime) {
		LocalTime localStartTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm")); 
		LocalTime localEndTime = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm")); 
		double start = localStartTime.get(ChronoField.CLOCK_HOUR_OF_DAY) + localStartTime.get(ChronoField.MINUTE_OF_HOUR) / 60.0;
		double end = localEndTime.get(ChronoField.CLOCK_HOUR_OF_DAY) + localEndTime.get(ChronoField.MINUTE_OF_HOUR) / 60.0;
		if (start > end) {
			throw new IllegalArgumentException("Error: the end Time is before the start Time");
		} else {
			// Get the driving time by (endTime - startTime) after converting mins to hours
			double hours =  end - start; 
			return hours;
		}
	}
	
	public static void writeFile(PriorityQueue<Map.Entry<String, Driver>> pq) {
		/* ----- Write File -----*/
		try {
			File writeName = new File("report.txt");
			writeName.createNewFile();
			try (FileWriter writer = new FileWriter(writeName);
					BufferedWriter out = new BufferedWriter(writer)
				) {
				while (!pq.isEmpty()) {
					Driver d = pq.poll().getValue();
					/* Write two different line according to driver's movement status */
					if (!d.isUnMoved()) {
						out.write(d.driverName + ": " + (int)Math.round(d.mileage) + " miles @ " + d.getMph() + " mph\r\n");
					} else {
						out.write(d.driverName + ": " + (int)Math.round(d.mileage) + " miles\r\n");
					}
					out.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {
		Map<String, Driver> nameToDriver = new HashMap<>(); // Use hashmap <Name, Driver> to create a dictionary to connect trip and driver
		// accept input file via stdin
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the input file name (w/ .txt):");
		String pathname = sc.nextLine();
		sc.close();
		readFile(pathname, nameToDriver);
		/* Use PriorityQueue to sort by mileage, Time complexity: O(nlogn)*/
		PriorityQueue<Map.Entry<String, Driver>> pq = new PriorityQueue<>((e1, e2) -> (int)Math.round(e2.getValue().mileage) - (int)Math.round(e1.getValue().mileage));
		for (Map.Entry<String, Driver> entry : nameToDriver.entrySet()) {
			pq.offer(entry);
		}
		if (!pq.isEmpty()){
			writeFile(pq);
			System.out.println("Your driving report is ready!");
		} else { //if writable content is empty
			System.out.println("We are unable to make a report this time, the input file may be empty or invalid.");
		}
	}
}
