package application;

import appclass.Login;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

	@FXML
	private PasswordField passwordtf;

	@FXML
	private TextField usernametf;

	@FXML
	private Button loginbt;

	@FXML
	private Label logintf;

	@FXML
	void login(ActionEvent event) {
		logintf.setVisible(true);
		appclass.Login n = new Login();
		n.initialAccount("login.txt");

		if (usernametf.getText().isBlank() == false && passwordtf.getText().isBlank() == false) {
			if (n.validateLogin(usernametf.getText(), passwordtf.getText()) != true)
				logintf.setText("invalid username or password");
			else
				logintf.setText("welcome");
		} else
			logintf.setText("Enter username & password");
	}

}
