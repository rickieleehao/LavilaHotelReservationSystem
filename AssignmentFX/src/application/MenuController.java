package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class MenuController {

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
    void searchRes(ActionEvent event) {
    	
    }
    
    @FXML
    void asdasd(ActionEvent event) {
    	
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
