package application;

import java.io.*;

import appclass.Login;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
	
	public static appclass.Login n;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hotel System - Login");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		n = new Login();
		n.initialAccount("login.txt");
		launch(args);
	}
}
