public class Trip {
	String driverName;
	double miles;
	double hours;
	
	public Trip(String driverName, double miles, double hours) {
		this.driverName = driverName;
		this.miles = miles;
		this.hours = hours;
	}
	
	public String getDriverName() {
		return driverName;
	}
	
	public void setDriverName(String newName) {
		this.driverName = newName;
	}
	
	public double getMiles() {
		return miles; 
	}
	
	public void setMiles(double mile) {
		this.miles = mile;
	}
	
	public Double getHours() {
		return hours;
	}
	
	public void setHours(double hours) {
		this.hours = hours;
	}
}
