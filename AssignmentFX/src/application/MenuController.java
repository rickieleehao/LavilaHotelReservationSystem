package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import appclass.Date;
import appclass.Guest;
import appclass.Promotion;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class MenuController extends Date implements Initializable, alertMsg {

	final private double SERVICE_CHARGE = 0.1;
	final private ObservableList<String> PMethodList = FXCollections.observableArrayList("Cash",
			"Credit Card/Debit Card", "Online Banking");

	private ObservableList<String> AdultList;
	private ObservableList<String> ChildList;
	private ObservableList<String> RoomTypeList;
	private ObservableList<String> RoomNumberList;

	private ArrayList<Guest> arrGuest;
	private ArrayList<Reservation> arrReservation;
	private ArrayList<Room> arrRoom;
	private ArrayList<Promotion> arrPromo;
	private Reservation reservation;

	@FXML
	private ComboBox<String> rTypeBox, rNumberBox, childBox, adultBox, pmethodBox;

	@FXML
	private TextField idtf, otherCtf, discountCtf;

	@FXML
	private DatePicker checkindate, checkoutdate;

	@FXML
	private Button newbt, searchbt, editbt, cancelbt, checkinbt, checkoutbt, cancelResbt, confirmResbt, updatebt, addbt;

	@FXML
	private Label statuslb, bedlb, roomClb, serviceClb, totalClb;

	@FXML
	private Label iclb, fnamelb, lnamelb, add1lb, add2lb, statelb, postcodelb;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.arrGuest = Guest.initializeGuest(Main.GUEST_TXT);
		this.arrRoom = Room.initializeRoom(Main.ROOM_TXT);
		this.arrPromo = Promotion.initializePromotion(Main.PROMO_TXT);
		this.arrReservation = Reservation.initializeReservation(Main.RESERVATION_TXT, arrRoom, arrGuest, arrPromo);

		pmethodBox.setItems(PMethodList);
	}

	// button
	@FXML // searching for a booking
	void searchRes(ActionEvent event) {
		if (idtf.getText() == null) {
			alertMsg.warning("Missing reservation ID", "Please enter reservation ID");
			defaultSetting();
		} else {
			reservation = new Reservation();
			reservation = reservation.searchReservation(arrReservation, idtf.getText());
			if (reservation != null) {
				alertMsg.info("Reservation ID", reservation.getID() + " result found");
				foundResSetting();
			} else {
				alertMsg.warning("Invalid reservation ID", "Result not found");
				defaultSetting();
			}
		}
	}

	@FXML // edit a reservation record
	void editRes(ActionEvent event) {
		if (alertMsg.confirmation("Editing", "Edit this reservation?"))
			editingSetting();
			statuslb.setText("Processing");
	}

	@FXML
	void updateRes(ActionEvent event) throws IOException {
		if (validateFields()) {
			if (alertMsg.confirmation("Update reservation", "Confirm changing this reservation?")) {
				reservation.updateReservation(arrReservation, Main.RESERVATION_TXT);
				defaultSetting();
			}
		} else
			alertMsg.warning("Missing information", "Fill in reservation information");
	}

	@FXML // cancel
	void cancel(ActionEvent event) {
		if (alertMsg.confirmation("Cancellation", "Discard changes?"))
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
			initialize(null, null);
		}

	}

	@FXML // make a new booking
	void newRes(ActionEvent event) {
		if (alertMsg.confirmation("Add new reservation", "Adding new reservation?")) {
			newResSetting();

			reservation = new Reservation();
			reservation.newReservation(arrReservation); // generate a new Reservation ID

			idtf.setText(reservation.getID());
			statuslb.setText("Processing");
		}
	}

	@FXML // newRes part1
	void checkindateField(ActionEvent event) {
		if (checkindate.getValue() != null) {
			// Basically, in this instance, the code is checked,
			// so that every date on and before the checkindate is disabled and coloured
			// differently

			final Callback<DatePicker, DateCell> endDateDayCellFactory = new Callback<DatePicker, DateCell>() {
				@Override
				public DateCell call(final DatePicker datePicker) {
					return new DateCell() {
						@Override
						public void updateItem(LocalDate item, boolean empty) {
							super.updateItem(item, empty);

							if (item.isBefore(checkindate.getValue().plusDays(1))) {
								setDisable(true);
								setStyle("-fx-background-color: #EEEEEE;");
							}
						}
					};
				}
			};
			checkoutdate.setDayCellFactory(endDateDayCellFactory);

			// if checkoutdate invalid, automatically set a valid one
			if (checkoutdate.getValue() == null
					|| checkoutdate.getValue().isBefore(checkindate.getValue().plusDays(1))) {
				checkoutdate.setValue(checkindate.getValue().plusDays(1));
				checkoutdate.setDisable(false);
			}
			
			setRoomTypeList();
			setRoomNumberList("");
		}
	}

	@FXML // newRes part2
	void checkoutdateField(ActionEvent event) {
		if (checkindate.getValue() != null && checkoutdate.getValue() != null) {
			rTypeBox.setValue(null);
			rNumberBox.setValue(null);

			rTypeBox.setDisable(false);
			rNumberBox.setDisable(true);

			bedlb.setText(null);
			adultBox.setDisable(false);
			childBox.setDisable(false);

			setRoomTypeList();
		}
	}

	@FXML // newRes part3
	void rTypeField(ActionEvent event) {
		if (rTypeBox.getValue() != null) {
			rNumberBox.setDisable(false);
			setRoomNumberList(rTypeBox.getValue());
		}
	}

	@FXML // newRes end
	void rNumberField(ActionEvent event) {
		if (rNumberBox.getValue() != null) {
			Room r = new Room();
			reservation.setRoom(r.findRoom(arrRoom, rNumberBox.getValue()));
			bedlb.setText(reservation.getRoom().getNumberOfBeds());
			calculateTotal();
			formatSetting();
			setPaxList();

		}
	}

	@FXML
	void confirmRes(ActionEvent event) throws IOException {
		if (validateFields()) {
			if (alertMsg.confirmation("Add new reservation", "Confirm adding this reservation?")) {
				if (reservation.getPaymentType() == null)
					reservation.setPaymentType("NO");
				reservation.setAdultPax(Integer.parseInt(adultBox.getValue()));
				reservation.setChildPax(Integer.parseInt(childBox.getValue()));
				reservation.setCheckindate(checkindate.getValue().toString());
				reservation.setCheckoutdate(checkoutdate.getValue().toString());
				reservation.addReservation(Main.RESERVATION_TXT);
				initialize(null, null);
				defaultSetting();
			}
		} else
			alertMsg.warning("Missing information", "Fill in reservation information");
	}

	@FXML
	void checkIn(ActionEvent event) throws IOException {
		String text = "Checked in";
		if (alertMsg.confirmation("Check In", "Proceed check in?")) {
			checkinbt.setVisible(false);
			editbt.setVisible(false);
			statuslb.setText(text);
			reservation.setStatus(text);

			reservation.updateReservation(arrReservation, Main.RESERVATION_TXT);
		}
	}

	@FXML
	void checkOut(ActionEvent event) throws IOException {
		String text = "Checked out";
		if (alertMsg.confirmation("Check Out", "Proceed check out?"))
			if (statuslb.getText() == "Checked in")
				if (pmethodBox.getValue() != null || pmethodBox.getValue() != "NO") {
					statuslb.setText(text);
					reservation.setStatus(text);
					reservation.setPaymentType(pmethodBox.getValue().toString());
					checkoutSetting();

					reservation.updateReservation(arrReservation, Main.RESERVATION_TXT);
				} else {
					alertMsg.warning("Check out denied", "Please select payment before checking out");
				}
			else
				alertMsg.warning("Check out denied", "Please check in before checking out.");
	}

	@FXML
	void cancelReservation(ActionEvent event) throws IOException {
		String text = "Cancelled";
		if (alertMsg.confirmation("Cancel Reservation", "Are you sure you want to cancel reservation?")) {
			statuslb.setText(text);
			reservation.setStatus(text);
			reservation.updateReservation(arrReservation, Main.RESERVATION_TXT);
			checkinbt.setVisible(false);
			checkoutbt.setVisible(false);
			cancelResbt.setVisible(false);
			otherCtf.setDisable(true);
			discountCtf.setDisable(true);
			pmethodBox.setDisable(true);
			editbt.setVisible(false);
		}
	}

	// method
	private void setRoomTypeList() {
		RoomTypeList = FXCollections.observableArrayList();
		ChronoLocalDate arrCheckin, arrCheckout;
		ChronoLocalDate checkin = checkindate.getValue(), checkout = checkoutdate.getValue();
		String temp = "";
		ArrayList<String> arrTemp = new ArrayList<String>(); // unavailable roomNumber

		for (int i = 0; i < arrReservation.size(); i++) { // to find available roomType within the booking
			if (arrReservation.get(i).getStatus().equalsIgnoreCase("Processed")
					|| arrReservation.get(i).getStatus().equalsIgnoreCase("Checked in")) {
				
				if (arrReservation.get(i).getID().matches(reservation.getID()))
					continue;
				
				arrCheckin = LocalDate.parse(arrReservation.get(i).getCheckinDate(), formatter);
				arrCheckout = LocalDate.parse(arrReservation.get(i).getCheckoutDate(), formatter);

				if (!(checkin.toEpochDay() > arrCheckout.toEpochDay()
						|| checkout.toEpochDay() < arrCheckin.toEpochDay())) {
					temp = arrReservation.get(i).getRoom().getRoomNumber();
					if (!arrTemp.contains(temp))
						arrTemp.add(temp);
				}
			}
		}

		for (Room room : arrRoom) {
			temp = room.getRoomNumber();
			if (!arrTemp.contains(temp) && !RoomTypeList.contains(room.getRoomType()))
				RoomTypeList.add(room.getRoomType());
		}
		rTypeBox.setItems(RoomTypeList);
	}

	private void setRoomNumberList(String rType) {
		RoomNumberList = FXCollections.observableArrayList();

		ChronoLocalDate arrCheckin, arrCheckout;
		ChronoLocalDate checkin = checkindate.getValue(), checkout = checkoutdate.getValue();
		String temp = "";
		ArrayList<String> tempNumber = new ArrayList<String>();

		for (int i = 0; i < arrReservation.size(); i++) { // find unavailable roomNumber
			if (arrReservation.get(i).getStatus().equalsIgnoreCase("Processed")
					|| arrReservation.get(i).getStatus().equalsIgnoreCase("Checked in")) {
				
				if (arrReservation.get(i).getID().matches(reservation.getID()))
						continue;
				
				arrCheckin = LocalDate.parse(arrReservation.get(i).getCheckinDate(), formatter);
				arrCheckout = LocalDate.parse(arrReservation.get(i).getCheckoutDate(), formatter);

				if (!(checkin.toEpochDay() > arrCheckout.toEpochDay()
						|| checkout.toEpochDay() < arrCheckin.toEpochDay())) {
					temp = arrReservation.get(i).getRoom().getRoomNumber();
					if (!tempNumber.contains(temp))
						tempNumber.add(temp);
				}
			}
		}

		for (Room room : arrRoom) {
			temp = room.getRoomNumber();
			if (!tempNumber.contains(temp) && room.getRoomType().equalsIgnoreCase(rTypeBox.getValue()))
				RoomNumberList.add(temp);
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
		double checkin = LOCAL_DATE(checkindate.getValue().toString()).toEpochDay();
		double checkout = LOCAL_DATE(checkoutdate.getValue().toString()).toEpochDay();
		double stay_day = checkout - checkin;
		double subPrice = stay_day
				* reservation.getRoom().getSessionCharge(LOCAL_DATE(checkindate.getValue().toString()).getMonth());
		double serviceCharge = subPrice * SERVICE_CHARGE;
		double otherCharge = 0;
		double totalPrice;

		if (otherCtf.getText() == null)
			otherCtf.setText("0.0");
		else
			try {
				otherCharge = Double.parseDouble(otherCtf.getText());
				reservation.setOtherPrice(otherCharge);
			} catch (Exception ex) {
				otherCharge = 0.0;
				otherCtf.setText("0.0");
				reservation.setOtherPrice(otherCharge);
				alertMsg.warning("Invalid Amount", "Please enter a valid amount");
			}

		if (discountCtf.getText() == null) {
			Promotion promo = new Promotion("NOPRO");
			reservation.setPromo(promo);
			discountCtf.setDisable(false);
		} else
			try {
				Promotion promo = new Promotion(discountCtf.getText());
				reservation.setPromo(promo);
				if (!reservation.getPromo().validateCode(arrPromo))
					throw new Exception();
			} catch (Exception ex) {
				Promotion promo = new Promotion("NOPRO");
				reservation.setPromo(promo);
				discountCtf.setText(null);
				alertMsg.warning("Promotion code has error", "This promotion code is invalid");
			}

		totalPrice = (subPrice + serviceCharge + otherCharge) * reservation.getPromo().getDiscount();
		roomClb.setText(Double.toString(subPrice));
		serviceClb.setText(Double.toString(serviceCharge));
		totalClb.setText(Double.toString(totalPrice));
	}

	private boolean validateFields() {
		if (checkindate.getValue() == null || checkoutdate.getValue() == null || rTypeBox.getValue() == null
				|| rNumberBox.getValue() == null || adultBox.getValue() == null || childBox.getValue() == null
				|| iclb.getText() == null)
			return false;
		else
			return true;
	}

	// format method
	private void newResSetting() {
		clearText();

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

		otherCtf.setDisable(false);
		discountCtf.setDisable(true);
		pmethodBox.setDisable(false);
	}

	private void editingSetting() {
		idtf.setEditable(false);
		searchbt.setVisible(false);
		newbt.setVisible(false);
		editbt.setVisible(false);
		cancelbt.setVisible(true);

		disableAll(false);

		checkinbt.setVisible(false);
		checkoutbt.setVisible(false);
		cancelResbt.setVisible(false);
		updatebt.setVisible(true);
	}

	private void defaultSetting() {
		clearText();
		disableAll(true);
		idtf.setEditable(true);
		searchbt.setVisible(true);
		newbt.setVisible(true);
		editbt.setVisible(false);
		cancelbt.setVisible(false);

		confirmResbt.setVisible(false);
		checkinbt.setVisible(false);
		checkoutbt.setVisible(false);
		cancelResbt.setVisible(false);
		updatebt.setVisible(false);
	}

	private void formatSetting() {
		adultBox.setDisable(false);
		childBox.setDisable(false);

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

	private void foundResSetting() {

		// payment section
		otherCtf.setText(Double.toString(reservation.getOtherPrice()));
		discountCtf.setText(reservation.getPromo().getCode());
		pmethodBox.setValue(reservation.getPaymentType());

		// reservation section
		checkindate.setValue(LOCAL_DATE(reservation.getCheckinDate()));
		checkoutdate.setValue(LOCAL_DATE(reservation.getCheckoutDate()));
		adultBox.setValue(Integer.toString(reservation.getAdultPax()));
		childBox.setValue(Integer.toString(reservation.getChildPax()));
		statuslb.setText(reservation.getStatus());
		rTypeBox.setValue(reservation.getRoom().getRoomType());
		rNumberBox.setValue(reservation.getRoom().getRoomNumber());
		bedlb.setText(reservation.getRoom().getNumberOfBeds());

		// guest information section
		iclb.setText(reservation.getGuest().getIC());
		fnamelb.setText(reservation.getGuest().getFName());
		lnamelb.setText(reservation.getGuest().getLName());
		add1lb.setText(reservation.getGuest().getAdd1());
		add2lb.setText(reservation.getGuest().getAdd2());
		statelb.setText(reservation.getGuest().getState());
		postcodelb.setText(reservation.getGuest().getPostcode());

		disableAll(true);
		otherCtf.setDisable(false);
		discountCtf.setDisable(false);
		pmethodBox.setDisable(false);
		confirmResbt.setVisible(false);

		if (reservation.getStatus().matches("Cancelled") || reservation.getStatus().matches("Checked out")) {
			checkinbt.setVisible(false);
			checkoutbt.setVisible(false);
			cancelResbt.setVisible(false);
			otherCtf.setDisable(true);
			discountCtf.setDisable(true);
			pmethodBox.setDisable(true);
			editbt.setVisible(false);
		} else if (reservation.getStatus().matches("Checked in")) {
			checkinbt.setVisible(false);
			checkoutbt.setVisible(true);
			cancelResbt.setVisible(true);
			editbt.setVisible(true);
		} else {
			checkinbt.setVisible(true);
			checkoutbt.setVisible(true);
			cancelResbt.setVisible(true);
			editbt.setVisible(true);
		}
	}

	private void checkoutSetting() {
		otherCtf.setDisable(true);
		discountCtf.setDisable(true);
		pmethodBox.setDisable(true);

		checkoutbt.setVisible(false);
		cancelResbt.setVisible(false);
	}

	private void clearText() {
		idtf.setText(null);
		checkoutdate.setValue(null);
		checkindate.setValue(null);
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

	private void disableAll(boolean b) {
		checkindate.setDisable(b);
		checkoutdate.setDisable(b);
		adultBox.setDisable(b);
		childBox.setDisable(b);
		statuslb.setDisable(b);
		rTypeBox.setDisable(b);
		rNumberBox.setDisable(b);
		bedlb.setDisable(b);

		addbt.setDisable(b);
		iclb.setDisable(b);
		fnamelb.setDisable(b);
		lnamelb.setDisable(b);
		add1lb.setDisable(b);
		add2lb.setDisable(b);
		statelb.setDisable(b);
		postcodelb.setDisable(b);

		roomClb.setDisable(b);
		serviceClb.setDisable(b);
		otherCtf.setDisable(b);
		discountCtf.setDisable(b);
		totalClb.setDisable(b);
		pmethodBox.setDisable(b);
	}

	// KeyEvent
	@FXML
	void resIDtab(KeyEvent event) {
		if (event.getCode() == KeyCode.TAB)
			searchbt.requestFocus();
		else if (event.getCode() == KeyCode.ENTER)
			searchbt.fire();
	}

	@FXML
	void searchtab(KeyEvent event) {
		if (event.getCode() == KeyCode.TAB)
			newbt.requestFocus();
	}

	@FXML
	void newtab(KeyEvent event) {
		if (event.getCode() == KeyCode.TAB)
			idtf.requestFocus();
	}

	@FXML
	void otherCField(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
			calculateTotal();
			discountCtf.requestFocus();
		}
	}

	@FXML
	void discountCField(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
			calculateTotal();
			pmethodBox.requestFocus();
		}
	}
}
