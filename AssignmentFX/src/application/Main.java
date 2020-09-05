package application;

import java.io.*;

import appclass.Login;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	final static protected String titleIcon = "TitleIcon.png";
	final static protected String style = "-fx-opacity: 1";
	final static protected String RESERVATION_TXT = "reservation.txt";
	final static protected String GUEST_TXT = "guest.txt";
	protected static Login n;

	@Override
	public void start(Stage stage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

		Scene scene = new Scene(root);
		stage.getIcons().add(new Image(Main.class.getResourceAsStream(titleIcon)));
		stage.setScene(scene);
		stage.setTitle("Hotel System - Login");
		stage.setResizable(false);
		stage.show();
	}

	public static void main(String[] args) {
		n = new Login();
		n.initialAccount("login.txt");
		launch(args);
	}
}
