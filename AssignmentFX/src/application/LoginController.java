package application;

import java.io.IOException;

import appclass.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

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
	void login(ActionEvent event) throws IOException {
		logintf.setVisible(true);
//		appclass.Login n = new Login();
//		n.initialAccount("login.txt");

		if (usernametf.getText().isBlank() == false && passwordtf.getText().isBlank() == false) {
			if (Main.n.validateLogin(usernametf.getText(), passwordtf.getText()) == true) {
				Parent menuViewParent = FXMLLoader.load(getClass().getResource("MenuDraft.fxml"));
				Scene menuViewScene = new Scene(menuViewParent);

				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(menuViewScene);
				window.show();
			} else
				logintf.setText("invalid username or password");
		} else
			logintf.setText("Enter username & password");
	}

	@FXML
	void usernametab(KeyEvent event) {
		if (event.getCode() == KeyCode.TAB) {
			passwordtf.requestFocus();
		}
	}

	@FXML
	void passwordenter(KeyEvent event) {
		if (event.getCode() == KeyCode.TAB) {
			loginbt.requestFocus();
		} else if (event.getCode() == KeyCode.ENTER)
			loginbt.fire();
	}

	@FXML
	void logintab(KeyEvent event) {
		if (event.getCode() == KeyCode.TAB) {
			usernametf.requestFocus();
		}
	}

	public static void readFile() {
		appclass.Login n = new Login();
		n.initialAccount("login.txt");
	}
}
