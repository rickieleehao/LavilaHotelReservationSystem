package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class MenuController {

	@FXML
	private ChoiceBox<?> List_Children;

	@FXML
	private ChoiceBox<?> List_Adult;

	@FXML
	private TextField Text_ResID;

	@FXML
	private DatePicker Date_In;

	@FXML
	private DatePicker Date_Out;

	@FXML
	private TextField Text_RoomNumber;

	@FXML
	private Button Button_New;

	@FXML
	private Button Button_Search;

	@FXML
	private TextField Text_IC;

	@FXML
	private TextField Text_FName;

	@FXML
	private TextField Text_LName;

	@FXML
	private TextField Text_Add1;

	@FXML
	private TextField Text_Add2;

	@FXML
	private TextField Text_State;

	@FXML
	private TextField Text_Postcode;

	@FXML
	private TextField Text_RoomC;

	@FXML
	private TextField Text_ServiceC;

	@FXML
	private TextField Text_OtherC;

	@FXML
	private TextField Text_Discount;

	@FXML
	private TextField Text_TotalC;

	@FXML
	private ChoiceBox<?> List_Payment;

	@FXML
	private Button Button_Edit;

	@FXML
	private Button Button_CheckIn;

	@FXML
	private Button Button_CheckOut;

	@FXML
	private TextField Text_Status;

	@FXML
	private ChoiceBox<?> List_RoomT;

	@FXML
	private Button Button_RoomS;

}
