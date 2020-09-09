package appclass;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Guest {

	private String icno, fname, lname, add1, add2, state, postcode;

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

	public Guest() {
	}

	// accessor
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
			Scanner x = new Scanner(new File(filepath));
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
			System.out.println("create arrayGuest has error!" + e.getMessage());
		}
		return guest;
	}

	public boolean findIC(ArrayList<Guest> arrGuest, String icno) {
		for (int i = 0; i < arrGuest.size(); i++) {
			if (arrGuest.get(i).icno.equalsIgnoreCase(icno)) {
				this.icno = arrGuest.get(i).icno;
				this.fname = arrGuest.get(i).fname;
				this.lname = arrGuest.get(i).lname;
				this.add1 = arrGuest.get(i).add1;
				this.add2 = arrGuest.get(i).add2;
				this.state = arrGuest.get(i).state;
				this.postcode = arrGuest.get(i).postcode;
				return true;
			}
		}
		return false;
	}

	public void addGuest(String filepath) throws IOException {
		FileWriter newGuest = new FileWriter("guest.txt", true);
		newGuest.write("\n" + this.icno + "," + this.fname + "," + this.lname + "," + this.add1 + "," + this.add2 + ","
				+ this.state + "," + this.postcode);
		newGuest.close();
		
		Alert alert1 = new Alert(AlertType.WARNING);
		alert1.setTitle("New guest information");
		alert1.setHeaderText(null);
		alert1.setContentText("Update successful");
		alert1.showAndWait();
	}

}
