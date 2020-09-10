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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;

public class GuestController implements Initializable {

	final private ObservableList<String> StateList = FXCollections.observableArrayList("Johor", "Kedah", "Kelantan",
			"Kuala Lumpur", "Malacca", "Negeri Sembilan", "Pahang", "Pulau Penang", "Perak", "Perlis", "Sabah",
			"Sarawak", "Selangor", "Terengganu", "Others");

	private Guest guest = null;
	private boolean add;
	private String idUsed = "";
	private String idTemp1 = "";
	private String idTemp2 = "";
	private String idTemp3 = "";
	private String postTemp = "";
	private ArrayList<Guest> arrGuest;

	public Guest getGuest() {
		return guest;
	}

	@FXML
	private ChoiceBox<String> stateBox;

	@FXML
	private TextField ictf1, ictf2, ictf3, passporttf, fnametf, lnametf, add1tf, add2tf, postcodetf;

	@FXML
	private Button searchbt, addbt;

	@FXML
	private FlowPane ictf;

	@FXML
	private RadioButton passportbt, icbt;

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
		
		if (icbt.isSelected() && !idUsed.matches("^\\d{6}-\\d{2}-\\d{4}$")) {
			alertMsg.warning("Search not completed", "IC must be in the format of xxxxxx-xx-xxxx");
		}else{
		
			if (guest.findIC(arrGuest, idUsed)) {
				setDisable(true);
				fnametf.setText(guest.getFName());
				lnametf.setText(guest.getLName());
				add1tf.setText(guest.getAdd1());
				add2tf.setText(guest.getAdd2());
				stateBox.setValue(guest.getState());
				postcodetf.setText(guest.getPostcode());
			
				addbt.setDisable(false);
				add = false;
			} else if (alertMsg.confirmation("IC not found", "Adding new guest?")) {
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
		if (validateFields()) {
			if (add == true) {
				if (alertMsg.confirmation("New guest information", "Adding guest to database?")) {
					guest = new Guest(idUsed, fnametf.getText(), lnametf.getText(), add1tf.getText(), add2tf.getText(),
							stateBox.getValue(), postcodetf.getText());
					guest.addGuest(Main.GUEST_TXT);
					Stage stage = (Stage) addbt.getScene().getWindow();
					stage.close();
				}
			}else {
				guest = new Guest(idUsed, fnametf.getText(), lnametf.getText(), add1tf.getText(), add2tf.getText(),
						stateBox.getValue(), postcodetf.getText());
				Stage stage = (Stage) addbt.getScene().getWindow();
				stage.close();
			}
		} else {
			alertMsg.warning("Missing guest information", "Please fill in guest information");
		}
	}

	// method
	private boolean validateFields() {
		// if any information is missing
		// -> return false
		// else if all information filled && add == true
		// -> return true (update file and add info to Menu)
		// else
		// -> return true (direct add info to Menu)
		if (fnametf.getText().isEmpty() || lnametf.getText().isEmpty() || add1tf.getText().isEmpty()
				|| add1tf.getText().isEmpty() || stateBox.getValue().isEmpty() || postcodetf.getText().isEmpty()) {
			return false;
		} else if (!(fnametf.getText().isEmpty() && lnametf.getText().isEmpty() && add1tf.getText().isEmpty()
				&& add1tf.getText().isEmpty() && stateBox.getValue().isEmpty() && postcodetf.getText().isEmpty())
				&& add == true) {
			return true;
		} else
			return true;
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

	@FXML
	void icSelected(ActionEvent event) {
		passporttf.setVisible(false);
		ictf.setVisible(true);
	}

	@FXML
	void passportSelected(ActionEvent event) {
		ictf.setVisible(false);
		passporttf.setVisible(true);
	}

	@FXML
	void ictf1Changed(KeyEvent event) {
		int caretPos = ictf1.getCaretPosition();

		if (ictf1.getText().matches("^\\d{0,6}$")) {
			idTemp1 = ictf1.getText();
		} else {
			ictf1.setText(idTemp1);
		}

		ictf1.positionCaret(caretPos);
		idUsed = ictf1.getText() + "-" + ictf2.getText() + "-" + ictf3.getText();
	}

	@FXML
	void ictf2Changed(KeyEvent event) {
		int caretPos = ictf2.getCaretPosition();

		if (ictf2.getText().matches("^\\d{0,2}$")) {
			idTemp2 = ictf2.getText();
		} else {
			ictf2.setText(idTemp2);
		}

		ictf2.positionCaret(caretPos);
		idUsed = ictf1.getText() + "-" + ictf2.getText() + "-" + ictf3.getText();
	}

	@FXML
	void ictf3Changed(KeyEvent event) {
		int caretPos = ictf3.getCaretPosition();

		if (ictf3.getText().matches("^\\d{0,4}$")) {
			idTemp3 = ictf3.getText();
		} else {
			ictf3.setText(idTemp3);
		}

		ictf3.positionCaret(caretPos);
		idUsed = ictf1.getText() + "-" + ictf2.getText() + "-" + ictf3.getText();
	}

	@FXML
	void passportChanged(KeyEvent event) {
		int caretPos = passporttf.getCaretPosition();

		if (passporttf.getText().matches("^\\w{0,16}$")) {
			idTemp1 = passporttf.getText();
		} else {
			passporttf.setText(idTemp1);
		}

		passporttf.positionCaret(caretPos);
		idUsed = passporttf.getText();
	}

	@FXML
	void passportPasted(InputMethodEvent event) {
		int caretPos = passporttf.getCaretPosition();

		if (passporttf.getText().matches("^\\w{0,16}$")) {
			idTemp1 = passporttf.getText();
		} else {
			passporttf.setText(idTemp1);
		}

		passporttf.positionCaret(caretPos);
		idUsed = passporttf.getText();
	}

	@FXML
	void ictf1Pasted(InputMethodEvent event) {
		int caretPos = ictf1.getCaretPosition();

		if (ictf1.getText().matches("^\\d{0,6}$")) {
			idTemp1 = ictf1.getText();
		} else {
			ictf1.setText(idTemp1);
		}

		ictf1.positionCaret(caretPos);
		idUsed = ictf1.getText() + "-" + ictf2.getText() + "-" + ictf3.getText();
	}

	@FXML
	void ictf2Pasted(InputMethodEvent event) {
		int caretPos = ictf2.getCaretPosition();

		if (ictf2.getText().matches("^\\d{0,2}$")) {
			idTemp2 = ictf2.getText();
		} else {
			ictf2.setText(idTemp2);
		}

		ictf2.positionCaret(caretPos);
		idUsed = ictf1.getText() + "-" + ictf2.getText() + "-" + ictf3.getText();
	}

	@FXML
	void ictf3Pasted(InputMethodEvent event) {
		int caretPos = ictf3.getCaretPosition();

		if (ictf3.getText().matches("^\\d{0,4}$")) {
			idTemp3 = ictf3.getText();
		} else {
			ictf3.setText(idTemp3);
		}

		ictf3.positionCaret(caretPos);
		idUsed = ictf1.getText() + "-" + ictf2.getText() + "-" + ictf3.getText();
	}

	@FXML
	void postcodePasted(InputMethodEvent event) {
		int caretPos = postcodetf.getCaretPosition();

		if (postcodetf.getText().matches("^\\d*$")) {
			postTemp = postcodetf.getText();
		} else {
			postcodetf.setText(postTemp);
		}

		postcodetf.positionCaret(caretPos);
	}

	@FXML
	void postcodeChanged(KeyEvent event) {
		int caretPos = postcodetf.getCaretPosition();

		if (postcodetf.getText().matches("^\\d*$")) {
			postTemp = postcodetf.getText();
		} else {
			postcodetf.setText(postTemp);
		}

		postcodetf.positionCaret(caretPos);
	}
}
