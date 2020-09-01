package appclass;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Guest {

	private String icno, fname, lname, add1, add2, state, postcode;
	private static Scanner x;

	// constructor
	public Guest(String icno, String fname, String lname, String add1, String add2, String state, String postcode) {
		this.icno = icno;
		this.fname = fname;
		this.lname = lname;
		this.add1 = add1;
		this.add2 = add2;
		this.state = state;
		this.postcode = postcode;
	}

	public Guest(String icno) {
		this.icno = icno;
		this.fname = null;
		this.lname = null;
		this.add1 = null;
		this.add2 = null;
		this.state = null;
		this.postcode = null;
	}

	public String getIC() {
		return icno;
	}

	public String getFName() {
		return fname;
	}

	public String getLName() {
		return lname;
	}

	public String getAdd1() {
		return add1;
	}

	public String getAdd2() {
		return add2;
	}

	public String getState() {
		return state;
	}

	public String getPostcode() {
		return postcode;
	}

	// method
	public static ArrayList<Guest> initializeGuest(String filepath) {
		ArrayList<Guest> guest = new ArrayList<Guest>();
		String icno, fname, lname, add1, add2, state, postcode;

		try {
			x = new Scanner(new File(filepath));
			x.useDelimiter("[,\n]");

			while (x.hasNext()) {
				icno = x.next();
				fname = x.next();
				lname = x.next();
				add1 = x.next();
				add2 = x.next();
				state = x.next();
				postcode = x.next();

				guest.add(new Guest(icno, fname, lname, add1, add2, state, postcode));
			}
		} catch (Exception e) {
			System.out.println("create arrayGuest guest.txt has error!");
		}

		return guest;
	}

	public void findIC(ArrayList<Guest> guest) {

		for (int i = 0; i < guest.size(); i++) {
			if (guest.get(i).icno.equalsIgnoreCase(this.icno)) {
				this.fname = guest.get(i).fname;
				this.lname = guest.get(i).lname;
				this.add1 = guest.get(i).add1;
				this.add2 = guest.get(i).add2;
				this.state = guest.get(i).state;
				this.postcode = guest.get(i).postcode;
			}
		}

	}

}
