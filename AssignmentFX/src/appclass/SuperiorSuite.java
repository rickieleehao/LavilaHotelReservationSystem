package appclass;

import java.time.Month;

public class SuperiorSuite extends Room {

	private static double price = 300;
	private static int adultPax = 4;
	private static int childPax = 4;

	public SuperiorSuite(String roomNumber, String numberOfBeds) {
		super(roomNumber, numberOfBeds);
		super.setRoomType(this.getClass().getSimpleName());
		super.setPrice(price);
		super.setAdultPaxLimit(adultPax);
		super.setChildPaxLimit(childPax);

	}

	@Override
	public double getSessionCharge(Month m) {
		if (m.getValue() == 1 && m.getValue() == 2)
			return 2.0*price;
		else if (m.getValue() == 11 && m.getValue() == 12)
			return 2.5*price;
		else if (m.getValue() == 5 && m.getValue() == 6)
			return 1.8*price;
		else
			return 1.0*price;
	}

}
