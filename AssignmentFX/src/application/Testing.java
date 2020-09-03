package application;

import java.util.ArrayList;

import appclass.Date;
import appclass.Guest;

public class Testing extends Date {

	public static void main(String[] args) {
		ArrayList<Guest> g = new ArrayList<Guest>();
		g.add(new Guest("941018-14-5874", "Wei Lun", "Lee", "11 Jalan Puteri 11/12", "Bandar Puteri", "Selangor",
				"47100"));
		String s = "2000-12-08";
		long x = LOCAL_DATE(s).toEpochDay();

		System.out.print(g.get(0));
	}
}
