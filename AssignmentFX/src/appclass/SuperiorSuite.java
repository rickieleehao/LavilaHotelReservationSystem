package appclass;

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
	public double roomCharge() {
		return super.getPrice();
	}

}
