import java.util.*;
import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
public class main {
	//默认没有重复的名字
	public static void main(String[] args) {
		Map<String, Driver> nameToDriver = new HashMap<>(); // Use <Name, Driver> to make a connection between trip and driver
		String pathname = "input.txt";
		/* ----- Read File -----*/
		try (FileReader reader = new FileReader(pathname);
			 BufferedReader br = new BufferedReader(reader)){
			String line;
			while ((line = br.readLine()) != null) {
				String[] command = line.trim().split("\\s+"); // if much more spaces
				if (command[0].equals("Driver")) {
					String driverName = command[1];
					if (!nameToDriver.containsKey(driverName)) {
						Driver driver = new Driver(driverName, new ArrayList<>());
						nameToDriver.put(driverName, driver);
					} else {
						System.err.println("Driver: "+ driverName + " is already existed!");
					} // exception
				} else if (command[0].equals("Trip")) {
					String driverName = command[1];
					String startTime = command[2];
					String endTime = command[3];
					double miles = Double.parseDouble(command[4]);
					LocalTime localStartTime = LocalTime.parse(startTime, DateTimeFormatter.ofPattern("HH:mm")); // time exception?
					LocalTime localEndTime = LocalTime.parse(endTime, DateTimeFormatter.ofPattern("HH:mm")); // time exception?
					double hours = (localEndTime.get(ChronoField.CLOCK_HOUR_OF_DAY) + localEndTime.get(ChronoField.MINUTE_OF_HOUR) / 60.0) - 
							(localStartTime.get(ChronoField.CLOCK_HOUR_OF_DAY) + localStartTime.get(ChronoField.MINUTE_OF_HOUR) / 60.0); // get the driving time by (endTime - startTime) after converting mins to hours
					int mph = (int)Math.round(miles / hours);
					/* Discard any trips that average a speed of < 5 mph or > 100 mph*/
					if (mph >= 5 && mph <= 100) {
						try {
							Trip trip = new Trip(driverName, miles, hours);
							nameToDriver.get(driverName).addTrips(trip);
						} catch (NullPointerException e) {
							e.printStackTrace();
							System.err.println("Command Error: Driver " + driverName + " does not exist!");
						}
					} 
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		/* Use PriorityQueue to sort by milleage O(nlogn)*/
		PriorityQueue<Map.Entry<String, Driver>> pq = new PriorityQueue<>((e1, e2) -> (int)Math.round(e2.getValue().getMilleage()) - (int)Math.round(e1.getValue().getMilleage()));
		for (Map.Entry<String, Driver> entry : nameToDriver.entrySet()) {
			pq.offer(entry);
		}
		/* ----- Write File -----*/
		try {
			File writeName = new File("report.txt");
			writeName.createNewFile();
			try (FileWriter writer = new FileWriter(writeName);
					BufferedWriter out = new BufferedWriter(writer)
				) {
				while (!pq.isEmpty()) {
					Driver d = pq.poll().getValue();
					if (!d.isEmpty()) {
						out.write(d.driverName + ": " + (int)Math.round(d.getMilleage()) + " miles @ " + d.getMph() + " mph\r\n");
					} else {
						out.write(d.driverName + ": " + (int)Math.round(d.getMilleage()) + " miles\r\n");
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

}
