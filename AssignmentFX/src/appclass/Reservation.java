package appclass;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Reservation {
	private String resID;
	private Guest guest;
	private Room room;
	private String roomNumber;
	private String checkinDate;
	private String checkoutDate;
	private String adultPax;
	private String childPax;
	private String status;
	private double price;
	private static Scanner x;

	// constructor
	public Reservation(String resID, Guest guest, Room room, String roomNumber, String checkinDate, String checkoutDate,
			String adultPax, String childPax, String status) {
		this.resID = resID;
		this.guest = guest;
		this.room = room;
		this.roomNumber = roomNumber;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.adultPax = adultPax;
		this.childPax = childPax;
		this.status = status;
		this.price = 0;
	}

	public Reservation(String resID) {
		this.resID = resID;
		this.guest = null;
		this.room = null;
		this.roomNumber = null;
		this.checkinDate = null;
		this.checkoutDate = null;
		this.adultPax = null;
		this.childPax = null;
		this.status = null;
		this.price = 0;
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

	public String getRoomNumber() {
		return roomNumber;
	}

	public String getCheckinDate() {
		return checkinDate;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public String getAdultPax() {
		return adultPax;
	}

	public String getChildPax() {
		return childPax;
	}

	public String getStatus() {
		return status;
	}

	public double getPrice() {
		return price;
	}

	// mutators
	public void setRoom(Room room) {
		this.room = room;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public void setPrice(Room r) {
		this.price = r.getPrice();
	}

	// method
	public static ArrayList<Reservation> initializeReservation(String filepath, ArrayList<Room> arrroom,
			ArrayList<Guest> arrguest) {

		ArrayList<Reservation> reservation = new ArrayList<Reservation>();
		String resID, icno, roomNumber, checkinDate, checkoutDate, adultPax, childPax, status;
		Guest guest = null;
		Room room = null;

		try {
			x = new Scanner(new File(filepath));
			x.useDelimiter("[,\n]");

			while (x.hasNext()) {
				resID = x.next();
				icno = x.next();
				roomNumber = x.next();
				checkinDate = x.next();
				checkoutDate = x.next();
				adultPax = x.next();
				childPax = x.next();
				status = x.next();

				for (int i = 0; i < arrguest.size(); i++)
					if (arrguest.get(i).getIC().equalsIgnoreCase(icno)) {
						guest = arrguest.get(i);
						break;
					}

				for (int i = 0; i < arrroom.size(); i++)
					if (arrroom.get(i).getRoomNumber().equalsIgnoreCase(roomNumber)) {
						room = arrroom.get(i);
						break;
					}

				reservation.add(new Reservation(resID, guest, room, roomNumber, checkinDate, checkoutDate, adultPax,
						childPax, status));
			}
		} catch (Exception e) {
			System.out.println("create arrayReservation reservation.txt has error!");
		}

		return reservation;
	}

	public void searchReservation(ArrayList<Reservation> reservation) {
		for (int i = 0; i < reservation.size(); i++) {
			if (reservation.get(i).resID.equalsIgnoreCase(this.resID)) {
				this.guest = reservation.get(i).getGuest();
				this.room = reservation.get(i).getRoom();
				this.roomNumber = reservation.get(i).getRoomNumber();
				this.checkinDate = reservation.get(i).getCheckinDate();
				this.checkoutDate = reservation.get(i).getCheckoutDate();
				this.adultPax = reservation.get(i).getAdultPax();
				this.childPax = reservation.get(i).getChildPax();
				this.status = reservation.get(i).getStatus();
			}
		}
	}
}
