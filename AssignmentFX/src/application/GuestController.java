package application;

import appclass.Guest;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;

public class GuestController implements Initializable {

	final private ObservableList<String> StateList = FXCollections.observableArrayList("Johor", "Kedah", "Kelantan",
			"Kuala Lumpur", "Malacca", "Negeri Sembilan", "Pahang", "Pulau Penang", "Perak", "Perlis", "Sabah",
			"Sarawak", "Selangor", "Terengganu");

	// instance variable
	private Guest guest = null;
	private boolean add;
	private String idTemp;

	// accessor
	public Guest getGuest() {
		return guest;
	}

	@FXML
	private ChoiceBox<String> stateBox;

	@FXML
	private TextField ictf, fnametf, lnametf, add1tf, add2tf, postcodetf;

	@FXML
	private Button searchbt, addbt;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stateBox.setTooltip(new Tooltip("Select a state"));
		stateBox.setItems(StateList);

		// appclass.Guest.initializeGuest(filepath);
		// Scanner x;
		// String icno, fname, lname, add1, add2, state, postcode;
		//
		// try {
		// x = new Scanner(new File("guest.txt"));
		// x.useDelimiter("[,\n]");
		//
		// while (x.hasNext()) {
		// icno = x.next();
		// fname = x.next();
		// lname = x.next();
		// add1 = x.next();
		// add2 = x.next();
		// state = x.next();
		// postcode = x.next();
		//
		// arrGuest.add(new Guest(icno, fname, lname, add1, add2, state, postcode));
		// }
		// } catch (Exception e) {
		// System.out.println("create arrayGuest guest.txt has error!");
		// }
	}

	// button
	@FXML
	void searchIC(ActionEvent event) {
		guest = new Guest();
		ArrayList<Guest> arrGuest = Guest.initializeGuest("guest.txt");

		guest.findIC(arrGuest, ictf.getText());
		if (guest.getIC() != null) {
			setDisable(true);
			fnametf.setText(guest.getFName());
			lnametf.setText(guest.getLName());
			add1tf.setText(guest.getAdd1());
			add2tf.setText(guest.getAdd2());
			stateBox.setValue(guest.getState());
			postcodetf.setText(guest.getPostcode());

			addbt.setDisable(false);
			add = false;
		} else {
			if (addNewGuest()) {
				setDisable(false);
				addbt.setDisable(false);
				add = true;
			} else {
				setDisable(true);
				addbt.setDisable(true);
				add = false;
			}
		}
	}

	@FXML
	void addGuest(ActionEvent event) throws IOException {
		if (!validateFields()) {
		} else {
			Stage stage = (Stage) addbt.getScene().getWindow();
			stage.close();
		}
	}

	// private method
	private boolean validateFields() throws IOException {
		if (fnametf.getText().isEmpty() || lnametf.getText().isEmpty() || add1tf.getText().isEmpty()
				|| stateBox.getValue().isEmpty() || postcodetf.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Missing guest information");
			alert.setHeaderText(null);
			alert.setContentText("Please enter guest information");
			alert.showAndWait();
			return false;
		} else if (!(fnametf.getText().isEmpty() && lnametf.getText().isEmpty() && add1tf.getText().isEmpty()
				&& stateBox.getValue().isEmpty() && postcodetf.getText().isEmpty()) && add) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("New guest information");
			alert.setHeaderText(null);
			alert.setContentText("Adding guest to database?");
			Optional<ButtonType> action = alert.showAndWait();

			if (action.get() == ButtonType.OK) {
				guest = new Guest(ictf.getText(), fnametf.getText(), lnametf.getText(), add1tf.getText(),
						add2tf.getText(), stateBox.getValue(), postcodetf.getText());
				FileWriter output = new FileWriter("guest.txt", true);
				output.write("\n" + ictf.getText() + "," + fnametf.getText() + "," + lnametf.getText() + ","
						+ add1tf.getText() + "," + add2tf.getText() + "," + stateBox.getValue() + ","
						+ postcodetf.getText());
				Alert alert1 = new Alert(AlertType.WARNING);
				alert1.setTitle("New guest information");
				alert1.setHeaderText(null);
				alert1.setContentText("Update successful");
				alert1.showAndWait();
				output.close();

				return true;
			} else
				return false;
		}

		return true;
	}

	private boolean addNewGuest() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("IC not found");
		alert.setHeaderText(null);
		alert.setContentText("Adding new guest?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			return true;
		else
			return false;
	}

	private void setDisable(boolean b) {
		fnametf.setDisable(b);
		lnametf.setDisable(b);
		add1tf.setDisable(b);
		add2tf.setDisable(b);
		stateBox.setDisable(b);
		postcodetf.setDisable(b);
		addbt.setDisable(b);

		// fnametf.setStyle(MenuController.style);
		// lnametf.setStyle(MenuController.style);
		// add1tf.setStyle(MenuController.style);
		// add2tf.setStyle(MenuController.style);
		// stateBox.setStyle(MenuController.style);
		// postcodetf.setStyle(MenuController.style);
	}

	@FXML
	void icKeyTyped(KeyEvent event) {
		if (ictf.getText().matches("^\\d*$") && ictf.getText().length() <= 12) {
			idTemp = ictf.getText();
		} else {
			ictf.setText(idTemp);
		}
	}

	@FXML // this is intended if the user pastes
	void icTextChanged(InputMethodEvent event) {
		if (ictf.getText().matches("^\\d*$") && ictf.getText().length() <= 12) {
			idTemp = ictf.getText();
		} else {
			ictf.setText(idTemp);
		}
	}
}
