package appclass;

import java.time.Month;

public class Studio extends Room {
	private static double price = 200;
	private static int adultPax = 2;
	private static int childPax = 1;

	public Studio(String roomNumber, String numberOfBeds) {
		super(roomNumber, numberOfBeds);
		super.setRoomType(this.getClass().getSimpleName());
		super.setPrice(price);
		super.setAdultPaxLimit(adultPax);
		super.setChildPaxLimit(childPax);

	}

	@Override
	public double getSessionCharge(Month m) {
		if (m.getValue() == 1 && m.getValue() ==2) 
			return 1.2*price;
		else if (m.getValue() == 11 && m.getValue() ==12) 
			return 1.4*price;
		else
			return 1.0*price;
	}
}