package application;

import appclass.Guest;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class GuestController implements Initializable {

	final private ObservableList<String> StateList = FXCollections.observableArrayList("Johor", "Kedah", "Kelantan",
			"Kuala Lumpur", "Malacca", "Negeri Sembilan", "Pahang", "Pulau Penang", "Perak", "Perlis", "Sabah",
			"Sarawak", "Selangor", "Terengganu");

	@FXML
	private ChoiceBox<String> stateBox;

	@FXML
	private TextField ictf, fnametf, lnametf, add1tf, add2tf, postcodetf;

	@FXML
	private Button searchbt, addbt;

	// instance variable
	private Guest guest = null;

	// accessor
	public Guest getGuest() {
		return guest;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		if (!fnametf.getText().isBlank() && !lnametf.getText().isBlank() && !add1tf.getText().isBlank()
//				&& !add2tf.getText().isBlank() && !stateBox.getValue().isBlank() && !postcodetf.getText().isBlank())
//			addbt.setDisable(false);

		stateBox.setTooltip(new Tooltip("Select a state"));
		stateBox.setItems(StateList);

//		appclass.Guest.initializeGuest(filepath);
//		Scanner x;
//		String icno, fname, lname, add1, add2, state, postcode;
//
//		try {
//			x = new Scanner(new File("guest.txt"));
//			x.useDelimiter("[,\n]");
//
//			while (x.hasNext()) {
//				icno = x.next();
//				fname = x.next();
//				lname = x.next();
//				add1 = x.next();
//				add2 = x.next();
//				state = x.next();
//				postcode = x.next();
//
//				arrGuest.add(new Guest(icno, fname, lname, add1, add2, state, postcode));
//			}
//		} catch (Exception e) {
//			System.out.println("create arrayGuest guest.txt has error!");
//		}
	}

	@FXML
	void searchIC(ActionEvent event) {
		guest = new Guest(ictf.getText());
		ArrayList<appclass.Guest> arrGuest = appclass.Guest.initializeGuest("guest.txt");

		guest.findIC(arrGuest);
		if (guest.getFName() != null) {
			setDisable(false);
			setEditable(false);
			fnametf.setText(guest.getFName());
			lnametf.setText(guest.getLName());
			add1tf.setText(guest.getAdd1());
			add2tf.setText(guest.getAdd2());
			stateBox.setValue(guest.getState());
			postcodetf.setText(guest.getPostcode());

			addbt.setDisable(false);
		} else {
			setDisable(false);
			setEditable(true);
		}

	}

	@FXML
	void addGuest(ActionEvent event) throws IOException {
		Stage stage = (Stage) addbt.getScene().getWindow();
		stage.close();
	}

	void setDisable(boolean b) {
		fnametf.setDisable(b);
		lnametf.setDisable(b);
		add1tf.setDisable(b);
		add2tf.setDisable(b);
		stateBox.setDisable(!b);
		postcodetf.setDisable(b);
	}

	void setEditable(boolean b) {
		fnametf.setEditable(b);
		lnametf.setEditable(b);
		add1tf.setEditable(b);
		add2tf.setEditable(b);
		postcodetf.setEditable(b);
	}

}
