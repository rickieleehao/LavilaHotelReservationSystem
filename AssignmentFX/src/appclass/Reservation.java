package appclass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Reservation extends Date {
	private String resID;
	private Guest guest;
	private Room room;
	private Promotion promo;
	private String checkinDate;
	private String checkoutDate;
	private int adultPax;
	private int childPax;
	private String status;
	private double otherPrice;
	private String paymentType;

	// constructor
	public Reservation(String resID, Guest guest, Room room, Promotion promo, String checkinDate, String checkoutDate,
			int adultPax, int childPax, String status, double otherPrice, String paymentType) {
		this.resID = resID;
		this.guest = guest;
		this.room = room;
		this.promo = promo;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.adultPax = adultPax;
		this.childPax = childPax;
		this.status = status;
		this.otherPrice = otherPrice;
		this.paymentType = paymentType;
	}

	public Reservation() {
	}

	// accessors
	public String getID() {
		return resID;
	}

	public Guest getGuest() {
		return guest;
	}

	public Room getRoom() {
		return room;
	}

	public String getCheckinDate() {
		return checkinDate;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public int getAdultPax() {
		return adultPax;
	}

	public int getChildPax() {
		return childPax;
	}

	public String getStatus() {
		return status;
	}

	public double getOtherPrice() {
		return otherPrice;
	}

	public Promotion getPromo() {
		return promo;
	}

	public String getPaymentType() {
		return paymentType;
	}

	// mutators
	public void setRoom(Room room) {
		this.room = room;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public void setCheckindate(String checkindate) {
		this.checkinDate = checkindate;
	}

	public void setCheckoutdate(String checkoutdate) {
		this.checkoutDate = checkoutdate;
	}

	public void setAdultPax(int adultPax) {
		this.adultPax = adultPax;
	}

	public void setChildPax(int childPax) {
		this.childPax = childPax;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setOtherPrice(double otherPrice) {
		this.otherPrice = otherPrice;
	}

	public void setPromo(Promotion promo) {
		this.promo = promo;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	// method
	public static ArrayList<Reservation> initializeReservation(String filepath, ArrayList<Room> arrRoom,
			ArrayList<Guest> arrGuest, ArrayList<Promotion> arrPromo) {

		ArrayList<Reservation> reservation = new ArrayList<Reservation>();
		String resID, icno, roomNumber, checkinDate, checkoutDate, adultPax, childPax, status, otherPrice, discountCode,
				paymentType;
		Guest guest = null;
		Room room = null;
		Promotion promo = null;

		try {
			Scanner x = new Scanner(new File(filepath));
			x.useDelimiter("[,\n]");

			while (x.hasNext()) {
				resID = x.next();
				icno = x.next();
				roomNumber = x.next();
				discountCode = x.next();
				checkinDate = x.next();
				checkoutDate = x.next();
				adultPax = x.next();
				childPax = x.next();
				status = x.next();
				otherPrice = x.next();
				paymentType = x.next();

				for (int i = 0; i < arrGuest.size(); i++)
					if (arrGuest.get(i).getIC().equalsIgnoreCase(icno)) {
						guest = arrGuest.get(i);
						break;
					}

				for (int i = 0; i < arrRoom.size(); i++)
					if (arrRoom.get(i).getRoomNumber().equalsIgnoreCase(roomNumber)) {
						room = arrRoom.get(i);
						break;
					}

				for (int i = 0; i < arrPromo.size(); i++)
					if (arrPromo.get(i).getCode().equalsIgnoreCase(discountCode)) {
						promo = arrPromo.get(i);
						break;
					}

				reservation.add(new Reservation(resID, guest, room, promo, checkinDate, checkoutDate,
						Integer.parseInt(adultPax), Integer.parseInt(childPax), status, Double.parseDouble(otherPrice),
						paymentType));
			}
		} catch (Exception e) {
			System.out.println("create arrayReservation reservation.txt has error!");
		}

		return reservation;
	}

	public Reservation searchReservation(ArrayList<Reservation> arrReservation, String resID) {
		for (int i = 0; i < arrReservation.size(); i++) {
			if (arrReservation.get(i).resID.equalsIgnoreCase(resID)) {
				return arrReservation.get(i);
			}
		}
		return null;
	}

	public void newReservation(ArrayList<Reservation> arrReservation) {
		String lastID = arrReservation.get(arrReservation.size() - 1).getID();
		int newID = Integer.parseInt(lastID.substring(1)) + 1;
		String ID = "R" + Integer.toString(newID);

		resID = ID;
		status = "Process";
	}

	public void addReservation(String filepath) throws IOException {
		FileWriter newReservation = new FileWriter(filepath, true);
		newReservation.write("\n" + resID + "," + guest.getIC() + "," + room.getRoomNumber() + "," + promo.getCode()
				+ "," + checkinDate + "," + checkoutDate + "," + adultPax + "," + childPax + "," + status + ","
				+ otherPrice + "," + paymentType);
		newReservation.close();
	}

	public void updateReservation(ArrayList<Reservation> arrReservation, String filepath) throws IOException {
		FileWriter old = new FileWriter(filepath);
		old.write("");
		old.close();

		FileWriter update = new FileWriter(filepath, true);
		for (int i = 0; i < arrReservation.size(); i++) {
			if (arrReservation.get(i).getID().equals(resID))
				arrReservation.set(i, this);

			update.write(arrReservation.get(i).getID() + "," + arrReservation.get(i).getGuest().getIC() + ","
					+ arrReservation.get(i).room.getRoomNumber() + "," + arrReservation.get(i).promo.getCode() + ","
					+ arrReservation.get(i).checkinDate + "," + arrReservation.get(i).checkoutDate + ","
					+ arrReservation.get(i).adultPax + "," + arrReservation.get(i).childPax + ","
					+ arrReservation.get(i).status + "," + +arrReservation.get(i).otherPrice + ","
					+ arrReservation.get(i).paymentType + "\n");
		}

		update.close();
	}
}
