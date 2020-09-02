package appclass;

import java.time.Month;

public class Deluxe extends Room {
	private double price = 200;
	private int adultPax = 2;
	private int childPax = 2;

	public Deluxe(String roomNumber, String numberOfBeds) {
		super(roomNumber, numberOfBeds);
		super.setRoomType(this.getClass().getSimpleName());
		super.setAdultPaxLimit(adultPax);
		super.setChildPaxLimit(childPax);
	}

	@Override
	public double getSessionCharge(Month m) {
		if (m.getValue() == 1 && m.getValue() ==2) 
			return 1.5*price;
		else if (m.getValue() == 11 && m.getValue() ==12) 
			return 1.7*price;
		else
			return 1.0*price;
	}
}
