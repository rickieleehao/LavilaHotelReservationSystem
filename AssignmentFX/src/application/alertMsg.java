package application;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public interface alertMsg {

	public static void warning(String title, String context) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(context);
		alert.showAndWait();
	}
	
	public static boolean confirmation(String title, String context) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(context);
		Optional<ButtonType> action = alert.showAndWait();

		if (action.get() == ButtonType.OK)
			return true;
		else
			return false;
	}
}
