package application;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

import appclass.Login;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;

// *******Kindly read the ReadMe file if it is unable to launch*******

public class Main extends Application {

	final static protected String titleIcon = "TitleIcon.png";
	final static protected String RESERVATION_TXT = "reservation.txt";
	final static protected String GUEST_TXT = "guest.txt";
	final static protected String PROMO_TXT = "promotion.txt";
	final static protected String ROOM_TXT = "room.txt";
	final static protected String PARITY_TXT = "parity.txt";
	protected static Login n;
	
	// Calculates the SHA-256 value of the designated filename
	private String parityCalculation(String file) throws NoSuchAlgorithmException, IOException {
		byte [] buffer = new byte[16384];
		int count;
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		
		//it reads the data from file to buffer, until it returns 0, which means the file has finished reading
		//count is the number of bytes read
		while ((count = bis.read(buffer)) > 0) {
			md.update(buffer, 0, count);
		}
		bis.close();
		
		byte[] hash = md.digest();
		
		StringBuilder sb = new StringBuilder();
		String generatedhash = "";
		
		for (int j = 0; j < hash.length; j++) {
			sb.append(Integer.toString((hash[j] & 0xff) + 0x100, 16).substring(1));
		}
		generatedhash = sb.toString();
		
		return generatedhash;
	}
	
	// The following code prevents and disallow file tampering outside of the program, if it is detected, the program quits directly without launching.
	private boolean parityChecking() throws IOException, NoSuchAlgorithmException{
		boolean tampered = false;
		Scanner fileRead = new Scanner(new File("parity.txt"));
		String [] parity = new String[5];
		String [] files = {"login.txt","reservation.txt","guest.txt","room.txt","promotion.txt"};
		
		for (int i = 0; i < parity.length; i++) {
			parity[i] = fileRead.nextLine();
			
			String hash = parityCalculation(files[i]); 
			
			if (!hash.equalsIgnoreCase(parity[i])) {
				tampered = true;
				break;
			}
		}
		return tampered;
	}

	@Override
	public void start(Stage stage) throws IOException, NoSuchAlgorithmException {
		if(parityChecking()) {
			alertMsg.warning("File Tampered!", "File has been tampered! Please recover the backup files from the backup folder and try again, or contact an administrator.");
		} else {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

		Scene scene = new Scene(root);
		stage.getIcons().add(new Image(Main.class.getResourceAsStream(titleIcon)));
		stage.setScene(scene);
		stage.setTitle("Hotel System - Login");
		stage.setResizable(false);
		stage.show();
		}
	}

	public static void main(String[] args) {
		n = new Login();
		n.initialAccount("login.txt");
		launch(args);
	}
}
