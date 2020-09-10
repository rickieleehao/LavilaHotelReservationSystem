package application;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

// Done by RickiE @9/9
public class LoginController implements alertMsg{

	@FXML
	private PasswordField passwordtf;

	@FXML
	private TextField usernametf;

	@FXML
	private Button loginbt;

	@FXML
	private Label logintf;

	// ActionEvent
	@FXML
	void login(ActionEvent event) throws IOException, NoSuchAlgorithmException {
		logintf.setVisible(true);
		logintf.setText(null);
		
		/* This is a fun project of my own, I plan to implement cryptographic security for password
		 * as such, the below is changed to represent such an effort to use PBKDF2
		 */
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		byte[] hash = md.digest(passwordtf.getText().getBytes());
		StringBuilder sb = new StringBuilder();
		String generatedPassword = "";
		
		for (int i = 0; i < hash.length; i++) {
			sb.append(Integer.toString((hash[i] & 0xff) + 0x100, 16).substring(1));
		}
		generatedPassword = sb.toString();
		
		
		if (usernametf.getText().isBlank() == false && passwordtf.getText().isBlank() == false) {
			if (Main.n.validateLogin(usernametf.getText(), generatedPassword) == true) {

				alertMsg.info("Welcome", "Login Successful");
				
				Parent menuViewParent = FXMLLoader.load(getClass().getResource("Menu.fxml"));
				Scene menuViewScene = new Scene(menuViewParent);

				Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.setScene(menuViewScene);
				stage.getIcons().add(new Image(Main.class.getResourceAsStream(Main.titleIcon)));
				stage.setTitle("Hotel System - Menu");
				stage.show();
			} else
				logintf.setText("invalid username or password");
				passwordtf.setText("");
		} else
			logintf.setText("Enter username & password");
	}
	
	// KeyEvent
	@FXML
	void usernametab(KeyEvent event) {
		if (event.getCode() == KeyCode.TAB) {
			passwordtf.requestFocus();
		} else if (event.getCode() == KeyCode.ENTER)
			loginbt.fire();
	}

	@FXML
	void passwordtab(KeyEvent event) {
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
}
