package appclass;

import java.time.Month;

public class Studio extends Room {
	private static double price = 100;
	private static int adultPax = 2;
	private static int childPax = 1;

	public Studio(String roomNumber, String numberOfBeds) {
		super(roomNumber, numberOfBeds, price, adultPax, childPax);
		super.setRoomType(this.getClass().getSimpleName());
	}

	@Override
	public double getSessionCharge(Month m) {
		if (m.getValue() == 1 && m.getValue() == 2)
			return 1.2 * price;
		else if (m.getValue() == 11 && m.getValue() == 12)
			return 1.4 * price;
		else
			return 1.0 * price;
	}
}
