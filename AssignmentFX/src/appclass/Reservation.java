package appclass;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Reservation extends Date {
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
		this.room = room; // no need
		this.roomNumber = roomNumber;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.adultPax = adultPax;
		this.childPax = childPax;
		this.status = status;
		this.price = 0;
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
	public void setRoom(ArrayList<Room>arrRoom) {
		for (int i = 0; i < arrRoom.size(); i++)
			if (arrRoom.get(i).getRoomNumber().equalsIgnoreCase(this.roomNumber)) {
				room = arrRoom.get(i);
				break;
			}
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public void setPrice(Room r) {
		this.price = r.getPrice(); // ??
	}

	public void setCheckindate(String checkindate) {
		this.checkinDate = checkindate;
	}
	
	public void setCheckoutdate(String checkoutdate) {
		this.checkoutDate = checkoutdate;
	}
	
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public void setNumberOfBeds(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public void setAdultPax(String adultPax) {
		this.adultPax = adultPax;
	}
	
	public void setChildPax(String childPax) {
		this.childPax = childPax;
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

	public void searchReservation(ArrayList<Reservation> arrReservation, String resID) {
		for (int i = 0; i < arrReservation.size(); i++) {
			if (arrReservation.get(i).resID.equalsIgnoreCase(resID)) {
				this.resID = resID;
				this.guest = arrReservation.get(i).getGuest();
				this.room = arrReservation.get(i).getRoom();
				this.roomNumber = arrReservation.get(i).getRoomNumber();
				this.checkinDate = arrReservation.get(i).getCheckinDate();
				this.checkoutDate = arrReservation.get(i).getCheckoutDate();
				this.adultPax = arrReservation.get(i).getAdultPax();
				this.childPax = arrReservation.get(i).getChildPax();
				this.status = arrReservation.get(i).getStatus();
			}
		}
	}

	public void newReservation(ArrayList<Reservation> arrReservation) {
		String lastID = arrReservation.get(arrReservation.size() - 1).getID();
		int newID = Integer.parseInt(lastID.substring(1)) + 1;
		String ID = "R" + Integer.toString(newID);

		this.resID = ID;
		this.status = "Process";
	}
}
