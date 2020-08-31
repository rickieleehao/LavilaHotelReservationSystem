package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

public class MenuController implements Initializable {

	ObservableList<String> AdultList = FXCollections.observableArrayList("0", "1", "2", "3", "4");
	ObservableList<String> ChildList = FXCollections.observableArrayList("0", "1", "2", "3", "4");
	ObservableList<String> StateList = FXCollections.observableArrayList("Johor", "Kedah", "Kelantan", "Kuala Lumpur",
			"Malacca", "Negeri Sembilan", "Pahang", "Penang", "Perak", "Perlis", "Sabah", "Sarawak", "Selangor",
			"Terengganu"); 
	ObservableList<String> PMethodList = FXCollections.observableArrayList("Cash", "Credit Card/Debit Card",
			"Online Banking");

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
	private Label statuslb, bedlb, roomClb, serviceClb, totalClb, iclb, fnamelb, lnamelb, add1lb, add2lb, statelb,
			postcodelb;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		adultBox.setTooltip(new Tooltip("Select Adult Number"));
		childBox.setTooltip(new Tooltip("Select Children Number"));
		pmethodBox.setTooltip(new Tooltip("Select Payment Method"));

		adultBox.setItems(AdultList);
		childBox.setItems(ChildList);
		pmethodBox.setItems(PMethodList);

	}

	@FXML
	void newRes(ActionEvent event) {
		// activate
		checkindate.setDisable(false);
		checkoutdate.setDisable(false);
		adultBox.setDisable(false);
		childBox.setDisable(false);
		statuslb.setDisable(false);
		rTypecb.setDisable(false);
		rNumbercb.setDisable(false);
		breakfastbt.setDisable(false);
		lunchbt.setDisable(false);
		bedlb.setDisable(false);

		addbt.setDisable(false);
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

		statuslb.setText("Progress");

	}

	@FXML
	void addGuest(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("Guest.fxml"));

		Scene scene = new Scene(root);

		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Hotel System - Guest Information");
		stage.setResizable(false);
		stage.show();
	}

}
