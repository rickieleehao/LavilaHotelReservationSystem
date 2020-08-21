package appclass;

import java.util.*;
import java.io.*;

public class Login {

	private String username;
	private String password;
	private static Scanner x;

	public String getUsername() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}
//	private void setUsername(String username) {
//		this.username = username;
//	}
//
//	private void setPassword(String password) {
//		this.password = password;
//	}

	public void initialAccount(String filepath) {
		try {
			x = new Scanner(new File(filepath));
			x.useDelimiter("[,\n]");

			while (x.hasNext()) {
				this.username = x.next();
				this.password = x.next();
			}
		} catch (Exception e) {
			System.out.println("initialAccount has error!");
		}
	}

	public boolean validateLogin(String username, String password) {

		try {
			if (username.trim().equals(this.username) && password.trim().equals(this.password))
				return true;
		} catch (Exception e) {
			System.out.println("verifyLogin has error.");
		}
		return false;
	}

}
