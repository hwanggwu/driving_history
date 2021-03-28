import java.util.*;
public class Driver {
	String driverName;
	List<Trip> trips;
	double mileage;
	double time;
	
	public Driver(String driverName, List<Trip> trips, double mileage, double time) {
		this.driverName = driverName;
		this.trips = trips;
		this.mileage = mileage;
		this.time = time;
	}

	public String getDriverName() {
		return driverName;
	}
	
	public void setDriverName(String newName) {
		this.driverName = newName;
	}
	
	public double getMileage() {
		return mileage; 
	}

	public double getTime() {
		return time;
	}

	// Use mileage to check if the driver has moved
	public boolean isUnMoved() {
		if (Math.abs(mileage) < 0.00001) { 
			return true;
		}
		return false;
	}
	
	public void clear() {
		this.trips.clear();
	}
	
	public int getMph() {
		return (int)Math.round(mileage / time);
	}
}
