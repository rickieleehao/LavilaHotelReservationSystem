package appclass;

import java.util.*;

public class Reservation {
	private int resID;
	private Room room;
	private Guest guest;
	private int day;
	private int month;
	private int year;
	private Date arrivalDate;
	private Date departureDate;
	private double price;

	// constructor
	public Reservation(Room r, Guest g, Date ad, Date dd, int id) {
		this.room = r;
		this.guest = g;
		this.arrivalDate = ad;
		this.departureDate = dd;
		this.resID = id;
		this.price = r.getPrice();
	}

	// accessors
	public int getID() {
		return resID;
	}

	public Room getRoom() {
		return room;
	}

	public Guest getGuest() {
		return guest;
	}

	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public Date getDepartureDate() {
		return departureDate;
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

	public void setDay(int day) {
		this.day = day;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setArrivaldate(Date ad) {
		this.arrivalDate = ad;
	}

	public void setDeparturedate(Date dd) {
		this.departureDate = dd;
	}

	public void setPrice(Room r) {
		this.price = r.getPrice();
	}
}
