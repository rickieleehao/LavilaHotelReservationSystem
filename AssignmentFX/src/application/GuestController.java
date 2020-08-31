package application;

import appclass.Guest;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

public class GuestController implements Initializable {

	@FXML
	private MenuButton statemb;

	@FXML
	private TextField ictf, fnametf, lnametf, add1tf, add2tf, postcodetf;

	@FXML
	private Button searchbt, addbt;

	private ArrayList<appclass.Guest> guest = new ArrayList<appclass.Guest>();
	private appclass.Guest newguest;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// appclass.Guest.initializeGuest(filepath);

		Scanner x;
		String icno, fname, lname, add1, add2, state, postcode;

		try {
			x = new Scanner(new File("guest.txt"));
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
	}

	@FXML
	void searchIC(ActionEvent event) {
		newguest.findIC(guest, ictf.getText());
		if (newguest.getFName() != null) {
			fnametf.setText(newguest.getFName());
			lnametf.setText(newguest.getLName());
			add1tf.setText(newguest.getAdd1());
			add2tf.setText(newguest.getAdd2());
		}
	}
}
