package application;

import appclass.Guest;

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

//simplify update @3/9 RickiE
public class GuestController implements Initializable {

	final private ObservableList<String> StateList = FXCollections.observableArrayList("Johor", "Kedah", "Kelantan",
			"Kuala Lumpur", "Malacca", "Negeri Sembilan", "Pahang", "Pulau Penang", "Perak", "Perlis", "Sabah",
			"Sarawak", "Selangor", "Terengganu");

	private Guest guest;

	public Guest getGuest() {
		return guest;
	}

	private ArrayList<Guest> arrGuest;

	private boolean add;

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
		arrGuest = Guest.initializeGuest(Main.GUEST_TXT);
	}

	// ActionEvent
	@FXML
	void searchIC(ActionEvent event) {
		guest = new Guest();
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
		} else
			addNewGuest();
	}

	@FXML
	void addGuest(ActionEvent event) throws IOException {
		if (!validateFields()) {
		} else {
			Stage stage = (Stage) addbt.getScene().getWindow();
			stage.close();
		}
	}

	// method
	private boolean validateFields() throws IOException {
//		  if any information is missing 
//		  -> return missingInfo();
//		  else if all information filled && add == true
//		  -> return confirmation(); 
//		  else 
//		  -> return true
		if (fnametf.getText().isEmpty() || lnametf.getText().isEmpty() || add1tf.getText().isEmpty()
				|| add1tf.getText().isEmpty() || stateBox.getValue().isEmpty() || postcodetf.getText().isEmpty()) {
			return missingInfo();
		} else if (!(fnametf.getText().isEmpty() && lnametf.getText().isEmpty() && add1tf.getText().isEmpty()
				&& add1tf.getText().isEmpty() && stateBox.getValue().isEmpty() && postcodetf.getText().isEmpty())
				&& add == true) {
			return confirmation();
		} else
			return true;
	}

	// alert message
	private void addNewGuest() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("IC not found");
		alert.setHeaderText(null);
		alert.setContentText("Adding new guest?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {
			setDisable(false);
			addbt.setDisable(false);
			add = true;
		} else {
			setDisable(true);
			addbt.setDisable(true);
			add = false;
		}
	}

	private boolean missingInfo() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Missing guest information");
		alert.setHeaderText(null);
		alert.setContentText("Please enter guest information");
		alert.showAndWait();
		
		return false;
	}

	private boolean confirmation() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("New guest information");
		alert.setHeaderText(null);
		alert.setContentText("Adding guest to database?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK) {
			guest = new Guest(ictf.getText(), fnametf.getText(), lnametf.getText(), add1tf.getText(), add2tf.getText(),
					stateBox.getValue(), postcodetf.getText());
			guest.addGuest(Main.GUEST_TXT);
			return true;
		} else
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
	}
}
