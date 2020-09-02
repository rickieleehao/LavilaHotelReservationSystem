package appclass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Date {
	
	final static public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static LocalDate LOCAL_DATE(String dateString) {
		LocalDate localDate = LocalDate.parse(dateString, formatter);
		return localDate;
	}
}
