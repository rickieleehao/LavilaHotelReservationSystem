package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MenuController extends Date implements Initializable {

	final static public String style = "-fx-opacity: 1";
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
	private Reservation reservation;

	@FXML
	private ComboBox<String> rTypeBox, rNumberBox, childBox, adultBox, pmethodBox;

	@FXML
	private TextField idtf, otherCtf, discountCtf;

	@FXML
	private DatePicker checkindate, checkoutdate;

	@FXML
	private Button newbt, searchbt, editbt, checkinbt, checkoutbt, statusbt, cancelResbt, confirmResbt, addbt;

	@FXML
	private CheckBox breakfastbt, lunchbt;

	@FXML
	private Label statuslb, bedlb, roomClb, serviceClb, totalClb;

	@FXML
	private Label iclb, fnamelb, lnamelb, add1lb, add2lb, statelb, postcodelb, resIDwarn;

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
			resIDwarn.setText("enter a reservation ID");
			editbt.setDisable(true);
			setVisible(false);
		} else {
			resIDwarn.setText(null);
			editbt.setDisable(false);
			setVisible(true);

			reservation = new Reservation();
			reservation.searchReservation(arrReservation, idtf.getText());

			if (reservation.getID() != null) {

				// reservation section
				checkindate.setValue(LOCAL_DATE(reservation.getCheckinDate()));
				checkoutdate.setValue(LOCAL_DATE(reservation.getCheckoutDate()));
				adultBox.setValue(reservation.getAdultPax());
				childBox.setValue(reservation.getChildPax());
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

				setDisable(true);
			} else {
				resIDwarn.setText("invalid reservation ID");
				editbt.setDisable(true);
				setVisible(false);
				
				addbt.setDisable(false);
			}
		}

	}

	@FXML // filling in new Reservation
	void newRes(ActionEvent event) {
		setText();
		setVisible(false);
		confirmResbt.setVisible(true);
		addbt.setDisable(false);
		editbt.setDisable(true);
		statuslb.setDisable(false);
		checkindate.setDisable(false);

		reservation = new Reservation();
		reservation.newReservation(arrReservation); // generate a new Reservation ID

		idtf.setText(reservation.getID());
		statuslb.setText(reservation.getStatus());
	}

	@FXML // edit a reservation record
	void editRes(ActionEvent event) {

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
			setDisable(false);
			getPaxBedPayment();
			reservation.setNumberOfBeds(bedlb.getText());
		}
	}

	@FXML
	void paxField(ActionEvent event) {
		if (adultBox.getValue() != null & childBox.getValue() != null) {
			reservation.setAdultPax(adultBox.getValue());
			reservation.setChildPax(childBox.getValue());
		}
	}

	// private method

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
			if (arrReservation.get(i).getStatus().equalsIgnoreCase("Process\r")) {
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

	private void getPaxBedPayment() {
		int adultLimit = 0, childLimit = 0;
		AdultList = FXCollections.observableArrayList();
		ChildList = FXCollections.observableArrayList();

		for (int i = 0; i < arrRoom.size(); i++)
			if (arrRoom.get(i).getRoomNumber().equalsIgnoreCase(reservation.getRoomNumber())) {
				adultLimit = arrRoom.get(i).getAdultPaxLimit();
				childLimit = arrRoom.get(i).getChildPaxLimit();
				bedlb.setText(arrRoom.get(i).getNumberOfBeds());
				break;
			}

		for (int i = 1; i <= adultLimit; i++) {
			AdultList.add(Integer.toString(i));
		}

		for (int i = 0; i <= childLimit; i++) {
			ChildList.add(Integer.toString(i));
		}

		adultBox.setItems(AdultList);
		childBox.setItems(ChildList);

		double roomCharge = reservation.getRoom().getSessionCharge(checkindate.getValue().getMonth());
		double stay_day = (LOCAL_DATE(reservation.getCheckoutDate())).toEpochDay()
				- (LOCAL_DATE(reservation.getCheckinDate())).toEpochDay();
		double total = roomCharge*stay_day;
		roomClb.setText(Double.toString(total));
		serviceClb.setText("10%");
		totalClb.setText(Double.toString(total+SERVICE_CHARGE));
	}

	private void setText() {
		// reservation section
		resIDwarn.setText(null);
		checkindate.setValue(null);
		checkoutdate.setValue(null);
		rTypeBox.setValue(null);
		rNumberBox.setValue(null);
		adultBox.setValue(null);
		childBox.setValue(null);
		bedlb.setText(null);
		statuslb.setText(null);

		// guest information section
		iclb.setText(null);
		fnamelb.setText(null);
		lnamelb.setText(null);
		add1lb.setText(null);
		add2lb.setText(null);
		statelb.setText(null);
		postcodelb.setText(null);
		
		// payment section
		roomClb.setText(null);
		serviceClb.setText(null);
		otherCtf.setText(null);
		discountCtf.setText(null);
		totalClb.setText(null);
		pmethodBox.setValue(null);

	}

	private void setVisible(boolean b) {
		checkinbt.setVisible(b);
		checkoutbt.setVisible(b);
		cancelResbt.setVisible(b);
	}
	
	private void setDisable(boolean b) {
		// reservation section
		checkindate.setDisable(b);
		checkoutdate.setDisable(b);
		adultBox.setDisable(b);
		childBox.setDisable(b);
		statuslb.setDisable(b);
		rTypeBox.setDisable(b);
		rNumberBox.setDisable(b);
		breakfastbt.setDisable(b);
		lunchbt.setDisable(b);
		bedlb.setDisable(b);

		checkindate.setStyle(style);
		checkoutdate.setStyle(style);
		adultBox.setStyle(style);
		childBox.setStyle(style);
		rTypeBox.setStyle(style);
		rNumberBox.setStyle(style);

		// guest information section
		iclb.setDisable(!b);
		fnamelb.setDisable(!b);
		lnamelb.setDisable(!b);
		add1lb.setDisable(!b);
		add2lb.setDisable(!b);
		statelb.setDisable(!b);
		postcodelb.setDisable(!b);

		// payment section
		roomClb.setDisable(b);
		serviceClb.setDisable(b);

		totalClb.setDisable(b);
		if (statuslb.getText() != "Complete") {
			pmethodBox.setDisable(!b);
			otherCtf.setDisable(!b);
			discountCtf.setDisable(!b);
		} else {
			pmethodBox.setDisable(b);
			otherCtf.setDisable(b);
			discountCtf.setDisable(b);
		}
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
}
