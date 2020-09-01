package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import appclass.Guest;
import appclass.Reservation;
import appclass.Room;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;

public class MenuController implements Initializable {

	final private ObservableList<String> AdultList = FXCollections.observableArrayList("0", "1", "2", "3", "4");
	final private ObservableList<String> ChildList = FXCollections.observableArrayList("0", "1", "2", "3", "4");
	final private ObservableList<String> PMethodList = FXCollections.observableArrayList("Cash",
			"Credit Card/Debit Card", "Online Banking");
	final private ObservableList<String> RoomTypeList = FXCollections.observableArrayList("SuperiorSuite", "Deluxe", "Studio");
	private ObservableList<String> RoomNumberList = FXCollections.observableArrayList();
	
	

	private ArrayList<Guest> arrguest;
	private ArrayList<Reservation> arrreservation;
	private ArrayList<Room> arrroom;

	public static final LocalDate LOCAL_DATE(String dateString) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}

	@FXML
	private ChoiceBox<String> childBox, adultBox, pmethodBox;

	@FXML
	private ComboBox<String> rTypecb, rNumbercb;

	@FXML
	private TextField idtf, otherCtf, discountCtf;

	@FXML
	private DatePicker checkindate, checkoutdate;

	@FXML
	private Button newbt, searchbt, editbt, checkinbt, checkoutbt, statusbt, cancelResbt, addbt;

	@FXML
	private CheckBox breakfastbt, lunchbt;

	@FXML
	private Label statuslb, bedlb, roomClb, serviceClb, totalClb;

	@FXML
	private Label iclb, fnamelb, lnamelb, add1lb, add2lb, statelb, postcodelb;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		adultBox.setTooltip(new Tooltip("Select Adult Number"));
		childBox.setTooltip(new Tooltip("Select Children Number"));
		pmethodBox.setTooltip(new Tooltip("Select Payment Method"));

		adultBox.setItems(AdultList);
		childBox.setItems(ChildList);
		pmethodBox.setItems(PMethodList);

		this.arrguest = appclass.Guest.initializeGuest("guest.txt");
		this.arrroom = appclass.Room.initializeRoom("room.txt");
		this.arrreservation = appclass.Reservation.initializeReservation("reservation.txt", arrroom, arrguest);
		
		for (int i = 0; i < arrroom.size(); i++)
			RoomNumberList.add(arrroom.get(i).getRoomNumber());
	}

	@FXML
	void searchRes(ActionEvent event) {
		Reservation reservation = new Reservation(idtf.getText());
		reservation.searchReservation(arrreservation);

		if (reservation.getGuest() != null) {
			setDisable(true);
			setEditable(false);
			// reservation section
			checkindate.setValue(LOCAL_DATE(reservation.getCheckinDate()));
			checkoutdate.setValue(LOCAL_DATE(reservation.getCheckoutDate()));
			adultBox.setValue(reservation.getAdultPax());
			childBox.setValue(reservation.getChildPax());
			statuslb.setText(reservation.getStatus());
			rTypecb.setValue(reservation.getRoom().getRoomType());
			rNumbercb.setValue(reservation.getRoomNumber());
			bedlb.setText(reservation.getRoom().getNumberOfBeds());

			// guest information section
			iclb.setText(reservation.getGuest().getIC());
			fnamelb.setText(reservation.getGuest().getFName());
			lnamelb.setText(reservation.getGuest().getLName());
			add1lb.setText(reservation.getGuest().getAdd1());
			add2lb.setText(reservation.getGuest().getAdd2());
			statelb.setText(reservation.getGuest().getState());
			postcodelb.setText(reservation.getGuest().getPostcode());
			

		}

	}

	@FXML
	void newRes(ActionEvent event) {
		setDisable(false);
		statuslb.setText("Progress");
	}

	@FXML
	void addGuest(ActionEvent event) throws IOException {
		System.out.print(checkindate.getValue());
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Guest.fxml"));
		Parent root = loader.load();

		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Hotel System - Guest Information");
		stage.setResizable(false);
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
		}
	}

	private void setDisable(boolean b) {
		// reservation section
		checkindate.setDisable(b);
		checkoutdate.setDisable(b);
		adultBox.setDisable(b);
		childBox.setDisable(b);
		statuslb.setDisable(b);
		rTypecb.setDisable(b);
		rNumbercb.setDisable(b);
		breakfastbt.setDisable(b);
		lunchbt.setDisable(b);
		bedlb.setDisable(b);
		// guest information section
		addbt.setDisable(b);
		iclb.setDisable(b);
		fnamelb.setDisable(b);
		lnamelb.setDisable(b);
		add1lb.setDisable(b);
		add2lb.setDisable(b);
		statelb.setDisable(b);
		postcodelb.setDisable(b);
		// payment section
		roomClb.setDisable(b);
		serviceClb.setDisable(b);
		otherCtf.setDisable(!b);
		discountCtf.setDisable(!b);
		totalClb.setDisable(b);
		pmethodBox.setDisable(!b);
	}

	private void setEditable(boolean b) {
		// reservation section
		checkindate.setEditable(b);
		checkoutdate.setEditable(b);
//		adultBox.setEditable(b);
//		childBox.setEditable(b);
//		statuslb.setEditable(b);
		rTypecb.setEditable(b);
		rNumbercb.setEditable(b);
//		breakfastbt.setEditable(b);
//		lunchbt.setEditable(b);
//		bedlb.setEditable(b);
		// guest information section
//		addbt.setEditable(b);
//		iclb.setEditable(b);
//		fnamelb.setEditable(b);
//		lnamelb.setEditable(b);
//		add1lb.setEditable(b);
//		add2lb.setEditable(b);
//		statelb.setEditable(b);
//		postcodelb.setEditable(b);
		// payment section
//		roomClb.setEditable(b);
//		serviceClb.setEditable(b);
		otherCtf.setEditable(b);
		discountCtf.setEditable(b);
//		totalClb.setEditable(b);
//		pmethodBox.setEditable(b);
	}
}
