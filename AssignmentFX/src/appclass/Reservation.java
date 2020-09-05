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
	private String roomNumber;
	private String checkinDate;
	private String checkoutDate;
	private int adultPax;
	private int childPax;
	private String status;
	private double price;
	private static Scanner x;

	// constructor
	public Reservation(String resID, Guest guest, Room room, String roomNumber, String checkinDate, String checkoutDate,
			int adultPax, int childPax, String status) {
		this.resID = resID;
		this.guest = guest;
		this.room = room; // no need
		this.roomNumber = roomNumber;
		this.checkinDate = checkinDate;
		this.checkoutDate = checkoutDate;
		this.adultPax = adultPax;
		this.childPax = childPax;
		this.status = status;
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

	public int getAdultPax() {
		return adultPax;
	}

	public int getChildPax() {
		return childPax;
	}

	public String getStatus() {
		return status;
	}

	public double getPrice() {
		return price;
	}

	// mutators
	public void setRoom(ArrayList<Room> arrRoom) {
		for (int i = 0; i < arrRoom.size(); i++)
			if (arrRoom.get(i).getRoomNumber().equalsIgnoreCase(this.roomNumber)) {
				room = arrRoom.get(i);
				break;
			}
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public void setAdultPax(int adultPax) {
		this.adultPax = adultPax;
	}

	public void setChildPax(int childPax) {
		this.childPax = childPax;
	}

	public void setStatus(String status) {
		this.status = status;
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

				reservation.add(new Reservation(resID, guest, room, roomNumber, checkinDate, checkoutDate,
						Integer.parseInt(adultPax), Integer.parseInt(childPax), status));
			}
		} catch (Exception e) {
			System.out.println("create arrayReservation reservation.txt has error!");
		}

		return reservation;
	}

	public int searchReservation(ArrayList<Reservation> arrReservation, String resID) {
		int index = 0;

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
				index = i;
				break;
			}
		}
		return index;
	}

	public void newReservation(ArrayList<Reservation> arrReservation) {
		String lastID = arrReservation.get(arrReservation.size() - 1).getID();
		int newID = Integer.parseInt(lastID.substring(1)) + 1;
		String ID = "R" + Integer.toString(newID);

		this.resID = ID;
		this.status = "Process";
	}

	public void addReservation(String filepath) throws IOException {
		FileWriter newReservation = new FileWriter(filepath, true);
		newReservation
				.write("\n" + this.resID + "," + this.guest.getIC() + "," + this.roomNumber + "," + this.checkinDate
						+ "," + this.checkoutDate + "," + this.adultPax + "," + this.childPax + "," + this.status);
		newReservation.close();
	}

	public double calculatePrice() {
		double stay_day = (LOCAL_DATE(this.checkoutDate).toEpochDay() - LOCAL_DATE(this.checkinDate).toEpochDay());

		return stay_day * this.room.getSessionCharge(LOCAL_DATE(this.checkinDate).getMonth());
	}

	public void generatePaxList() {
		this.adultPax = this.room.getAdultPaxLimit();
		this.childPax = this.room.getChildPaxLimit();
	}
}
