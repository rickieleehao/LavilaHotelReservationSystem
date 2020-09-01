package appclass;

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
	public double roomCharge() {
		// TODO Auto-generated method stub
		return 0;
	}
}
