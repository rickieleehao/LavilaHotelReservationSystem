package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

public class MenuController {
	
	final private ObservableList<String> AdultList = FXCollections.observableArrayList("0","1","2","3","4");
	ObservableList<String> ChildList = FXCollections.observableArrayList("0","1","2","3","4");
	ObservableList<String> StateList = FXCollections.observableArrayList("Johor","Kedah","Kelantan","Kuala Lumpur","Malacca","Negeri Sembilan","Pahang","Penang","Perak","Perlis","Sabah","Sarawak","Selangor","Terengganu");
	ObservableList<String> JohorList = FXCollections.observableArrayList("79xxx","8xxxx");
	ObservableList<String> KedahList = FXCollections.observableArrayList("0xxxx");
	ObservableList<String> KelantanList = FXCollections.observableArrayList("16xxx","17xxx");
	ObservableList<String> KLList = FXCollections.observableArrayList("4xxxx","5xxxx");
	ObservableList<String> MalaccaList = FXCollections.observableArrayList("75xxx","76xxx","77xxx");
	ObservableList<String> NSList = FXCollections.observableArrayList("70xxx","71xxx","72xxx","73xxx");
	ObservableList<String> PahangList = FXCollections.observableArrayList("25xxx","26xxx","27xxx","28xxx");
	ObservableList<String> PenangList = FXCollections.observableArrayList("10xxx","11xxx");
	ObservableList<String> PerakList = FXCollections.observableArrayList("3xxxx");
	ObservableList<String> PerlisList = FXCollections.observableArrayList("02xxx");
	ObservableList<String> SabahList = FXCollections.observableArrayList("88xxx","89xxx");
	ObservableList<String> SarawakList = FXCollections.observableArrayList("9xxxx");
	ObservableList<String> SelangorList = FXCollections.observableArrayList("4xxxx","6xxxx");
	ObservableList<String> TerengganuList = FXCollections.observableArrayList("20xxx","21xxx","22xxx","23xxx","24xxx");
	ObservableList<String> PMethodList = FXCollections.observableArrayList("Cash","Credit Card/Debit Card","Online Banking");

	@FXML
	private ChoiceBox<String> kidcb;

	@FXML
	private ChoiceBox<?> adultcb;

	@FXML
	private TextField idtf;

	@FXML
	private DatePicker checkindate;

	@FXML
	private DatePicker checkoutdate;

	@FXML
	private TextField rNumbertf;

	@FXML
	private Button newbt;

	@FXML
	private Button searchbt;

	@FXML
	private TextField ictf;

	@FXML
	private TextField fnametf;

	@FXML
	private TextField lnametf;

	@FXML
	private TextField add1tf;

	@FXML
	private TextField add2tf;

	@FXML
	private TextField statetf;

	@FXML
	private TextField postcodetf;

	@FXML
	private TextField roomCtf;

	@FXML
	private TextField serviceCtf;

	@FXML
	private TextField otherCtf;

	@FXML
	private TextField discountCtf;

	@FXML
	private TextField totalCtf;

	@FXML
	private ChoiceBox<?> paymentcb;

	@FXML
	private Button editbt;

	@FXML
	private Button checkinbt;

	@FXML
	private Button checkoutbt;

	@FXML
	private TextField statustf;

	@FXML
	private Button statusbt;

	@FXML
	private ComboBox<?> rTypecb;

	@FXML
	private TextField bedtf;

	@FXML
	private CheckBox breakfastbt;

	@FXML
	private CheckBox lunchbt;
	
    @FXML
    private Button cancelResbt;
    
    @FXML
    private ChoiceBox<String> PmethodBox;
    
    @FXML
    private ChoiceBox<String> AdultBox;
    
    @FXML
    private ChoiceBox<String> ChildBox;
    
    @FXML
    private ComboBox StateBox;
    
    @FXML
    private ComboBox PostcodeBox;
    
	@FXML
    private void adultinitialize() {
		AdultBox.setTooltip(new Tooltip("Select Adult Number"));
    	AdultBox.setItems(AdultList);
    }
	
	@FXML
	private void childinitialize() {
		ChildBox.setTooltip(new Tooltip("Select Children Number"));
		ChildBox.setItems(ChildList);
	}
	
	@FXML
	private void pmethodinitialize() {;
		PmethodBox.setTooltip(new Tooltip("Select Payment Method"));
		PmethodBox.setItems(PMethodList);
	}
	
	@FXML
    private void stateinitialize() {
		StateBox.setTooltip(new Tooltip("Select state before postcode"));
		StateBox.setItems(StateList);
    }
	
	@FXML
	private void postcodeinitialize() {
		PostcodeBox.setItems(JohorList);
	}
    
	@FXML 
	void postcodechoice() {
		switch (StateBox.getValue().toString()){
		case "Johor":
			PostcodeBox.setItems(JohorList);
			PostcodeBox.setValue("79xxx");
			break;
		case "Kedah": 
			PostcodeBox.setValue("0xxxx");
			PostcodeBox.setItems(KedahList);
			break;
		case "Kelantan":
	 		PostcodeBox.setValue("16xxx");
			PostcodeBox.setItems(KelantanList);
			break;
		case "Kuala Lumpur":
			PostcodeBox.setValue("4xxxx");
			PostcodeBox.setItems(KLList);
			break;
		case "Malacca": 
			PostcodeBox.setValue("75xxx");
			PostcodeBox.setItems(MalaccaList);
			break;
		case "Negeri Sembilan":
			PostcodeBox.setValue("70xxx");
			PostcodeBox.setItems(NSList);
			break;
		case "Pahang":
			PostcodeBox.setValue("25xxx");
			PostcodeBox.setItems(PahangList);
			break;
		case "Penang": 
			PostcodeBox.setValue("10xxx");
			PostcodeBox.setItems(PenangList);
			break;
		case "Perak":
			PostcodeBox.setValue("3xxxx");
			PostcodeBox.setItems(PerakList);
			break;
		case "Perlis":
			PostcodeBox.setValue("02xxx");
			PostcodeBox.setItems(PerlisList);
			break;
		case "Sabah": 
			PostcodeBox.setValue("88xxx");
			PostcodeBox.setItems(SabahList);
			break;
		case "Sarawak":
			PostcodeBox.setValue("9xxxx");
			PostcodeBox.setItems(SarawakList);
			break;	
		case "Selangor":
			PostcodeBox.setValue("4xxxx");
			PostcodeBox.setItems(SelangorList);
			break;
		case "Terengganu": 
			PostcodeBox.setValue("20xxx");
			PostcodeBox.setItems(TerengganuList);
			break;
		}
	}
//    @FXML
//    void editRes(ActionEvent event) {
//
//    }

//	@FXML
//	void newRes(ActionEvent event) {
//		// active textfield
//		checkindate.setVisible(true);
//		checkoutdate.setVisible(true);
//		adultcb.setVisible(true);
//		kidcb.setVisible(true);
//		statustf.setVisible(true);
//		rTypecb.setVisible(true);
//		rNumbercb.setVisible(true);
//
//		ictf.setVisible(true);
//		fnametf.setVisible(true);
//		lnametf.setVisible(true);
//		add1tf.setVisible(true);
//		add2tf.setVisible(true);
//		statetf.setVisible(true);
//		postcodetf.setVisible(true);
//
//	}

//    @FXML
//    void searchRes(ActionEvent event) {
//
//    }

}
