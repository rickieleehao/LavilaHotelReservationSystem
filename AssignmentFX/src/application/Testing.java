package application;

import appclass.Date;

public class Testing extends Date{
	
		public static void main (String [] args) {
			String s = "2000-12-08";
			long x = LOCAL_DATE(s).toEpochDay();
			
			System.out.print(x);
		}
}
