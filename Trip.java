
public class Trip {
	String driverName;
	double miles;
	double hours;
//与driver聚合关系 单向一对多: aggregation
	public Trip(String driverName, double miles, double hours) {
		this.driverName = driverName;
		this.miles = miles;
		this.hours = hours;
	}

	public double getMiles() {
		return miles; 
	}
	public void setMiles(double mile) {
		this.miles = mile;
	}
	public Double gethours() {
		return hours;
	}
	public void sethours(double hours) {
		this.hours = hours;
	}
}
