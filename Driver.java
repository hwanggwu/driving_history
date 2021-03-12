import java.util.*;
public class Driver {
	String driverName;
	List<Trip> trips;
	
	public Driver(String driverName, List<Trip> trips) {
		this.driverName = driverName;
		this.trips = new ArrayList<>();
	}

	public String getDriverName() {
		return driverName;
	}
	
	public void setDriverName(String newName) {
		this.driverName = newName;
	}
	
	public void addTrips(Trip trip) {
		trips.add(trip);
	}
	
	public double getMilleage() {
		double milleage = 0;
		for (Trip trip : trips) {
			milleage += trip.miles; 
		}
		return milleage; 
	}

	public double getTime() {
		double time = 0;
		for (Trip trip : trips) {
			time += trip.hours;
		}
		return time;
	}

	public boolean isEmpty() {
		if (Math.abs(getMilleage()) < 0.00001) { // use milleage to check if the driver has driving record
			return true;
		}
		return false;
	}
	
	public void clear() {
		this.trips.clear();
	}
	
	public int getMph() {
		return (int)Math.round(getMilleage() / getTime());
	}
}
