package appclass;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Room {
	private String roomType;
	private String roomNumber;
	private double price;
	private int adultPaxLimit;
	private int childPaxLimit;
	private String numberOfBeds;
	private String description;
	private static Scanner x;

	// constructor
	public Room(String roomNumber, String numberOfBeds) {
		this.roomNumber = roomNumber;
		this.numberOfBeds = numberOfBeds;
	}

	public static ArrayList<Room> initializeRoom(String filepath) {
		ArrayList<Room> room = new ArrayList<Room>();
		String roomType, roomNumber, numberOfBeds;

		try {
			x = new Scanner(new File(filepath));
			x.useDelimiter("[,\n]");

			while (x.hasNext()) {
				roomType = x.next();
				roomNumber = x.next();
				numberOfBeds = x.next();

				if (roomType.equalsIgnoreCase("SuperiorSuite"))
					room.add(new SuperiorSuite(roomNumber, numberOfBeds));
				else if (roomType.equalsIgnoreCase("Deluxe"))
					room.add(new Deluxe(roomNumber, numberOfBeds));
				else if (roomType.equalsIgnoreCase("Studio"))
					room.add(new Studio(roomNumber, numberOfBeds));
				else
					throw new Exception("roomType has error!");
			}
		} catch (Exception e) {
			System.out.println("create arrayRoom room.txt has error!");
		}

		return room;
	}

	// abstract method
	public abstract double roomCharge();

	// accessor
	public String getRoomNumber() {
		return roomNumber;
	}

	public String getRoomType() {
		return roomType;
	}

	public double getPrice() {
		return price;
	}

	public int getAdultPaxLimit() {
		return adultPaxLimit;
	}

	public int getChildPaxLimit() {
		return childPaxLimit;
	}

	public String getNumberOfBeds() {
		return numberOfBeds;
	}

	public String getDescription() {
		return description;
	}

	// mutator
	public void setPrice(double price) {
		this.price = price;
	}

	public void setAdultPaxLimit(int pax) {
		this.adultPaxLimit = pax;
	}

	public void setChildPaxLimit(int pax) {
		this.childPaxLimit = pax;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
}
