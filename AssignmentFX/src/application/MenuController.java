package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import appclass.Date;
import appclass.Guest;
import appclass.Reservation;
import appclass.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController extends Date implements Initializable {

	final private double SERVICE_CHARGE = 1.1;

	final private ObservableList<String> PMethodList = FXCollections.observableArrayList("Cash",
			"Credit Card/Debit Card", "Online Banking");

	private ObservableList<String> AdultList;
	private ObservableList<String> ChildList;
	private ObservableList<String> RoomTypeList;
	private ObservableList<String> RoomNumberList;

	private ArrayList<Guest> arrGuest;
	private ArrayList<Reservation> arrReservation;
	private ArrayList<Room> arrRoom;
	private Reservation reservation;

	@FXML
	private ComboBox<String> rTypeBox, rNumberBox, childBox, adultBox, pmethodBox;

	@FXML
	private TextField idtf, otherCtf, discountCtf;

	@FXML
	private DatePicker checkindate, checkoutdate;

	@FXML
	private Button newbt, searchbt, editbt, cancelbt, checkinbt, checkoutbt, statusbt, cancelResbt, confirmResbt, addbt;

	@FXML
	private CheckBox breakfastbt, lunchbt;

	@FXML
	private Label statuslb, bedlb, roomClb, serviceClb, totalClb;

	@FXML
	private Label iclb, fnamelb, lnamelb, add1lb, add2lb, statelb, postcodelb;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.arrGuest = appclass.Guest.initializeGuest("guest.txt");
		this.arrRoom = appclass.Room.initializeRoom("room.txt");
		this.arrReservation = appclass.Reservation.initializeReservation("reservation.txt", arrRoom, arrGuest);

		adultBox.setTooltip(new Tooltip("Select Adult Number"));
		childBox.setTooltip(new Tooltip("Select Children Number")); // tooltip for other box
		pmethodBox.setTooltip(new Tooltip("Select Payment Method"));

		pmethodBox.setItems(PMethodList);
	}

	// button
	@FXML // searching for a reservation
	void searchRes(ActionEvent event) {
		if (idtf.getText().isBlank()) {
			missingID();
		} else {
			reservation = new Reservation();
			reservation.searchReservation(arrReservation, idtf.getText());

			if (reservation.getID() != null)
				foundRes();
			else
				invalidRes();
		}
	}

	@FXML // filling in new Reservation
	void newRes(ActionEvent event) {
		if (!addNewReservation()) {
			idtf.setText(null);
			statuslb.setText(null);
		} else {
			newResSetting();

			reservation = new Reservation();
			reservation.newReservation(arrReservation); // generate a new Reservation ID

			idtf.setText(reservation.getID());
			statuslb.setText(reservation.getStatus());
		}
	}

	@FXML // edit a reservation record
	void editRes(ActionEvent event) {

	}

	@FXML // cancel
	void cancel(ActionEvent event) {
		if (cancelAdd())
			defaultSetting();
	}

	@FXML // open new stage and add Guest Information
	void addGuest(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Guest.fxml"));
		Parent root = loader.load();

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.getIcons().add(new Image(Main.class.getResourceAsStream(Main.titleIcon)));
		stage.setTitle("Hotel System - Guest Information");
		stage.setResizable(false);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.showAndWait();

		// wait for Guest Information to return
		GuestController controller = loader.getController();
		Guest guest = controller.getGuest();
		if (guest != null) {
			iclb.setText(guest.getIC());
			fnamelb.setText(guest.getFName());
			lnamelb.setText(guest.getLName());
			add1lb.setText(guest.getAdd1());
			add2lb.setText(guest.getAdd2());
			statelb.setText(guest.getState());
			postcodelb.setText(guest.getPostcode());
			reservation.setGuest(guest);
		}
	}

	@FXML
	void confirmRes(ActionEvent event) throws IOException {
		if (validateFields()) {
			if (addConfirmation())
				reservation.addReservation(Main.RESERVATION_TXT);
		} else
			addReservationError();
	}

	@FXML
	void checkindateField(ActionEvent event) {
		if (checkindate.getValue() != null) {
			reservation.setCheckindate(checkindate.getValue().toString());
			checkoutdate.setDisable(false);
		}
	}

	@FXML
	void checkoutdateField(ActionEvent event) {
		if (checkindate.getValue() != null && checkoutdate.getValue() != null) {
			reservation.setCheckoutdate(checkoutdate.getValue().toString());
			rTypeBox.setDisable(false);
			setRoomTypeList();
		}
	}

	@FXML
	void rTypeField(ActionEvent event) {
		if (rTypeBox.getValue() != null) {
			rNumberBox.setDisable(false);
			setRoomNumberList(rTypeBox.getValue());
		}
	}

	@FXML
	void rNumberField(ActionEvent event) {
		if (rNumberBox.getValue() != null) {
			reservation.setRoomNumber(rNumberBox.getValue());
			reservation.setRoom(arrRoom);
			setPaxList();
			bedlb.setText(reservation.getRoom().getNumberOfBeds());

			reservation.setPrice(reservation.calculatePrice());
			roomClb.setText(Double.toString((reservation.getPrice())));
			serviceClb.setText(Double.toString((reservation.getPrice() * SERVICE_CHARGE)));
			calculateTotal();
			formatSetting();
		}
	}

	@FXML
	void paxField(ActionEvent event) {
		if (adultBox.getValue() != null & childBox.getValue() != null) {
			reservation.setAdultPax(Integer.parseInt(adultBox.getValue()));
			reservation.setChildPax(Integer.parseInt(childBox.getValue()));
		}
	}

	// method
	private void setRoomTypeList() {
		RoomTypeList = FXCollections.observableArrayList();
		ChronoLocalDate arrCheckin, arrCheckout;
		ChronoLocalDate checkin = checkindate.getValue(), checkout = checkoutdate.getValue();

		for (int i = 0; i < arrReservation.size(); i++) {
			if (arrReservation.get(i).getStatus().equalsIgnoreCase("Process\r")) {
				arrCheckin = LocalDate.parse(arrReservation.get(i).getCheckinDate(), formatter);
				arrCheckout = LocalDate.parse(arrReservation.get(i).getCheckoutDate(), formatter);
				if (!(checkin.toEpochDay() >= arrCheckin.toEpochDay()
						&& checkout.toEpochDay() <= arrCheckout.toEpochDay()))
					if (!RoomTypeList.contains(arrReservation.get(i).getRoom().getRoomType()))
						RoomTypeList.add(arrReservation.get(i).getRoom().getRoomType());
			}
		}

		rTypeBox.setItems(RoomTypeList);
	}

	private void setRoomNumberList(String rType) {
		RoomNumberList = FXCollections.observableArrayList();

		ChronoLocalDate arrCheckin, arrCheckout;
		ChronoLocalDate checkin = checkindate.getValue(), checkout = checkoutdate.getValue();
		ArrayList<String> tempNumber = new ArrayList<String>();

		for (int i = 0; i < arrReservation.size(); i++) { // find not available roomNumber
			if (arrReservation.get(i).getStatus().equalsIgnoreCase("Process\r")
					|| arrReservation.get(i).getStatus().equalsIgnoreCase("Check in\r")) {
				arrCheckin = LocalDate.parse(arrReservation.get(i).getCheckinDate(), formatter);
				arrCheckout = LocalDate.parse(arrReservation.get(i).getCheckoutDate(), formatter);
				if ((checkin.toEpochDay() >= arrCheckin.toEpochDay()
						&& checkout.toEpochDay() <= arrCheckout.toEpochDay()))
					if (arrReservation.get(i).getRoom().getRoomType().equalsIgnoreCase(rType))
						tempNumber.add(arrReservation.get(i).getRoomNumber());
			}
		}

		for (int i = 0; i < arrRoom.size(); i++) { // show available roomNumber of the selected roomType
			if (arrRoom.get(i).getRoomType().equalsIgnoreCase(rType))
				if (tempNumber.size() != 0) {
					for (int x = 0; x < tempNumber.size(); x++)
						if (!arrRoom.get(i).getRoomNumber().equalsIgnoreCase(tempNumber.get(x)))
							RoomNumberList.add(arrRoom.get(i).getRoomNumber());
				} else
					RoomNumberList.add(arrRoom.get(i).getRoomNumber());
		}

		rNumberBox.setItems(RoomNumberList);
	}

	private void setPaxList() {
		AdultList = FXCollections.observableArrayList();
		ChildList = FXCollections.observableArrayList();

		for (int i = 1; i <= reservation.getRoom().getAdultPaxLimit(); i++) {
			AdultList.add(Integer.toString(i));
		}

		for (int i = 0; i <= reservation.getRoom().getChildPaxLimit(); i++) {
			ChildList.add(Integer.toString(i));
		}

		adultBox.setItems(AdultList);
		childBox.setItems(ChildList);
	}

	private void calculateTotal() {
		totalClb.setText(Double.toString(Double.parseDouble(roomClb.getText())
				+ Double.parseDouble(serviceClb.getText()) + Double.parseDouble(otherCtf.getText())));
	}
	
	private boolean addConfirmation() throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add new reservation");
		alert.setHeaderText(null);
		alert.setContentText("Confirm adding new reservation?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			return true;
		else
			return false;

	}

	private boolean validateFields() {
		if (reservation.getID() == null || reservation.getGuest() == null || reservation.getRoom() == null
				|| reservation.getRoomNumber() == null || reservation.getCheckinDate() == null
				|| reservation.getCheckoutDate() == null || Integer.toString(reservation.getAdultPax()) == null
				|| Integer.toString(reservation.getChildPax()) == null || reservation.getStatus() == null
				|| Double.toString(reservation.getPrice()) == null || pmethodBox.getValue() == null)
			return false;
		else
			return true;
	}

	// alert message
	private void missingID() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Missing reservation ID");
		alert.setHeaderText(null);
		alert.setContentText("Please enter reservation ID");
		alert.showAndWait();

		editbt.setDisable(false);
		checkinbt.setVisible(false);
		checkoutbt.setVisible(false);
		cancelResbt.setVisible(false);
		confirmResbt.setVisible(false);
	}

	private void foundRes() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Reservation ID");
		alert.setHeaderText(null);
		alert.setContentText(reservation.getID() + " result found");
		alert.showAndWait();

		// reservation section
		checkindate.setValue(LOCAL_DATE(reservation.getCheckinDate()));
		checkoutdate.setValue(LOCAL_DATE(reservation.getCheckoutDate()));
		adultBox.setValue(Integer.toString(reservation.getAdultPax()));
		childBox.setValue(Integer.toString(reservation.getChildPax()));
		statuslb.setText(reservation.getStatus());
		rTypeBox.setValue(reservation.getRoom().getRoomType());
		rNumberBox.setValue(reservation.getRoomNumber());
		bedlb.setText(reservation.getRoom().getNumberOfBeds());

		// guest information section
		iclb.setText(reservation.getGuest().getIC());
		fnamelb.setText(reservation.getGuest().getFName());
		lnamelb.setText(reservation.getGuest().getLName());
		add1lb.setText(reservation.getGuest().getAdd1());
		add2lb.setText(reservation.getGuest().getAdd2());
		statelb.setText(reservation.getGuest().getState());
		postcodelb.setText(reservation.getGuest().getPostcode());

		disableAll();
		editbt.setDisable(false);
		otherCtf.setDisable(false);
		discountCtf.setDisable(false);
		pmethodBox.setDisable(false);
		checkinbt.setVisible(true);
		checkoutbt.setVisible(true);
		cancelResbt.setVisible(true);
		confirmResbt.setVisible(false);
	}

	private void invalidRes() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Invalid reservation ID");
		alert.setHeaderText(null);
		alert.setContentText("Result not found");
		alert.showAndWait();

		setText();
		disableAll();
		checkinbt.setVisible(false);
		checkoutbt.setVisible(false);
		cancelResbt.setVisible(false);
		confirmResbt.setVisible(false);
	}

	private boolean addNewReservation() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Add new reservation");
		alert.setHeaderText(null);
		alert.setContentText("Adding new reservation?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			return true;
		else
			return false;
	}

	private void addReservationError() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Missing information");
		alert.setHeaderText(null);
		alert.setContentText("Fill in reservation information");
		alert.showAndWait();
	}

	private boolean cancelAdd() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Cancellation");
		alert.setHeaderText(null);
		alert.setContentText("Discard this reservation?");
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			return true;
		else
			return false;
	}

	// KeyEvent
	@FXML
	void resIDtab(KeyEvent event) {
		if (event.getCode() == KeyCode.TAB) {
			searchbt.requestFocus();
		} else if (event.getCode() == KeyCode.ENTER)
			searchbt.fire();
	}

	@FXML
	void searchtab(KeyEvent event) {
		if (event.getCode() == KeyCode.TAB) {
			newbt.requestFocus();
		}
	}

	@FXML
	void newtab(KeyEvent event) {
		if (event.getCode() == KeyCode.TAB) {
			idtf.requestFocus();
		}
	}

	// format method
	private void newResSetting() {
		setText();
		idtf.setEditable(false);
		searchbt.setVisible(false);
		newbt.setVisible(false);
		editbt.setVisible(false);
		cancelbt.setVisible(true);

		confirmResbt.setVisible(true);
		checkinbt.setVisible(false);
		checkoutbt.setVisible(false);
		cancelResbt.setVisible(false);

		addbt.setDisable(false);
		statuslb.setDisable(false);
		checkindate.setDisable(false);
	}

	private void defaultSetting() {
		setText();
		disableAll();
		idtf.setEditable(true);
		searchbt.setVisible(true);
		newbt.setVisible(true);
		editbt.setVisible(true);
		cancelbt.setVisible(false);

		confirmResbt.setVisible(false);
		checkinbt.setVisible(false);
		checkoutbt.setVisible(false);
		cancelResbt.setVisible(false);
	}

	private void formatSetting() {
		adultBox.setDisable(false);
		childBox.setDisable(false);
		breakfastbt.setDisable(false);
		lunchbt.setDisable(false);

		iclb.setDisable(false);
		iclb.setDisable(false);
		fnamelb.setDisable(false);
		lnamelb.setDisable(false);
		add1lb.setDisable(false);
		add2lb.setDisable(false);
		statelb.setDisable(false);
		postcodelb.setDisable(false);

		roomClb.setDisable(false);
		serviceClb.setDisable(false);
		otherCtf.setDisable(false);
		discountCtf.setDisable(false);
		totalClb.setDisable(false);
		pmethodBox.setDisable(false);
	}

	private void setText() {
		idtf.setText(null);
		checkindate.setValue(null);
		checkoutdate.setValue(null);
		rTypeBox.setValue(null);
		rNumberBox.setValue(null);
		adultBox.setValue(null);
		childBox.setValue(null);
		bedlb.setText(null);
		statuslb.setText(null);

		iclb.setText(null);
		fnamelb.setText(null);
		lnamelb.setText(null);
		add1lb.setText(null);
		add2lb.setText(null);
		statelb.setText(null);
		postcodelb.setText(null);

		roomClb.setText(null);
		serviceClb.setText(null);
		otherCtf.setText(null);
		discountCtf.setText(null);
		totalClb.setText(null);
		pmethodBox.setValue(null);

	}

	private void disableAll() {
		checkindate.setDisable(true);
		checkoutdate.setDisable(true);
		adultBox.setDisable(true);
		childBox.setDisable(true);
		statuslb.setDisable(true);
		rTypeBox.setDisable(true);
		rNumberBox.setDisable(true);
		breakfastbt.setDisable(true);
		lunchbt.setDisable(true);
		bedlb.setDisable(true);

		addbt.setDisable(true);
		iclb.setDisable(true);
		fnamelb.setDisable(true);
		lnamelb.setDisable(true);
		add1lb.setDisable(true);
		add2lb.setDisable(true);
		statelb.setDisable(true);
		postcodelb.setDisable(true);

		roomClb.setDisable(true);
		serviceClb.setDisable(true);
		otherCtf.setDisable(true);
		discountCtf.setDisable(true);
		totalClb.setDisable(true);
		pmethodBox.setDisable(true);
	}
}
