package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class MenuController implements Initializable {

	ObservableList<String> AdultList = FXCollections.observableArrayList("0", "1", "2", "3", "4");
	ObservableList<String> ChildList = FXCollections.observableArrayList("0", "1", "2", "3", "4");
	ObservableList<String> StateList = FXCollections.observableArrayList("Johor", "Kedah", "Kelantan", "Kuala Lumpur",
			"Malacca", "Negeri Sembilan", "Pahang", "Penang", "Perak", "Perlis", "Sabah", "Sarawak", "Selangor",
			"Terengganu");
	ObservableList<String> JohorList = FXCollections.observableArrayList("79xxx", "8xxxx");
	ObservableList<String> KedahList = FXCollections.observableArrayList("0xxxx");
	ObservableList<String> KelantanList = FXCollections.observableArrayList("16xxx", "17xxx");
	ObservableList<String> KLList = FXCollections.observableArrayList("4xxxx", "5xxxx");
	ObservableList<String> MalaccaList = FXCollections.observableArrayList("75xxx", "76xxx", "77xxx");
	ObservableList<String> NSList = FXCollections.observableArrayList("70xxx", "71xxx", "72xxx", "73xxx");
	ObservableList<String> PahangList = FXCollections.observableArrayList("25xxx", "26xxx", "27xxx", "28xxx");
	ObservableList<String> PenangList = FXCollections.observableArrayList("10xxx", "11xxx");
	ObservableList<String> PerakList = FXCollections.observableArrayList("3xxxx");
	ObservableList<String> PerlisList = FXCollections.observableArrayList("02xxx");
	ObservableList<String> SabahList = FXCollections.observableArrayList("88xxx", "89xxx");
	ObservableList<String> SarawakList = FXCollections.observableArrayList("9xxxx");
	ObservableList<String> SelangorList = FXCollections.observableArrayList("4xxxx", "6xxxx");
	ObservableList<String> TerengganuList = FXCollections.observableArrayList("20xxx", "21xxx", "22xxx", "23xxx",
			"24xxx");
	ObservableList<String> PMethodList = FXCollections.observableArrayList("Cash", "Credit Card/Debit Card",
			"Online Banking");

	@FXML
	private ChoiceBox<String> childBox;

	@FXML
	private ChoiceBox<String> adultBox;

	@FXML
	private TextField idtf;

	@FXML
	private DatePicker checkindate;

	@FXML
	private DatePicker checkoutdate;

	@FXML
	private Button newbt;

	@FXML
	private Button searchbt;

	@FXML
	private TextField otherCtf;

	@FXML
	private TextField discountCtf;

	@FXML
	private Button editbt;

	@FXML
	private Button checkinbt;

	@FXML
	private Button checkoutbt;

	@FXML
	private Button statusbt;

	@FXML
	private ComboBox<String> rTypecb;

	@FXML
	private CheckBox breakfastbt;

	@FXML
	private CheckBox lunchbt;

	@FXML
	private Button cancelResbt;

	@FXML
	private ChoiceBox<String> pmethodBox;

	@FXML
	private ComboBox<String> rNumbercb;

	@FXML
	private Label statuslb;

	@FXML
	private Label bedlb;

	@FXML
	private Label roomClb;

	@FXML
	private Label serviceClb;

	@FXML
	private Label totalClb;

	@FXML
	private Button addbt;

	@FXML
	private Label iclb;

	@FXML
	private Label fnamelb;

	@FXML
	private Label lnamelb;

	@FXML
	private Label add1lb;

	@FXML
	private Label add2lb;

	@FXML
	private Label statelb;

	@FXML
	private Label postcodelb;

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

//	@FXML
//	void postcodechoice() {
//		switch (StateBox.getValue().toString()) {
//		case "Johor":
//			PostcodeBox.setItems(JohorList);
//			PostcodeBox.setValue("79xxx");
//			break;
//		case "Kedah":
//			PostcodeBox.setValue("0xxxx");
//			PostcodeBox.setItems(KedahList);
//			break;
//		case "Kelantan":
//			PostcodeBox.setValue("16xxx");
//			PostcodeBox.setItems(KelantanList);
//			break;
//		case "Kuala Lumpur":
//			PostcodeBox.setValue("4xxxx");
//			PostcodeBox.setItems(KLList);
//			break;
//		case "Malacca":
//			PostcodeBox.setValue("75xxx");
//			PostcodeBox.setItems(MalaccaList);
//			break;
//		case "Negeri Sembilan":
//			PostcodeBox.setValue("70xxx");
//			PostcodeBox.setItems(NSList);
//			break;
//		case "Pahang":
//			PostcodeBox.setValue("25xxx");
//			PostcodeBox.setItems(PahangList);
//			break;
//		case "Penang":
//			PostcodeBox.setValue("10xxx");
//			PostcodeBox.setItems(PenangList);
//			break;
//		case "Perak":
//			PostcodeBox.setValue("3xxxx");
//			PostcodeBox.setItems(PerakList);
//			break;
//		case "Perlis":
//			PostcodeBox.setValue("02xxx");
//			PostcodeBox.setItems(PerlisList);
//			break;
//		case "Sabah":
//			PostcodeBox.setValue("88xxx");
//			PostcodeBox.setItems(SabahList);
//			break;
//		case "Sarawak":
//			PostcodeBox.setValue("9xxxx");
//			PostcodeBox.setItems(SarawakList);
//			break;
//		case "Selangor":
//			PostcodeBox.setValue("4xxxx");
//			PostcodeBox.setItems(SelangorList);
//			break;
//		case "Terengganu":
//			PostcodeBox.setValue("20xxx");
//			PostcodeBox.setItems(TerengganuList);
//			break;
//		}
//	}

}
