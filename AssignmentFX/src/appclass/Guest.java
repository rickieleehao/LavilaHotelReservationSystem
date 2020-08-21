package appclass;

public class Guest {

	private String email;
	private String firstName;
	private int icNo;
	private String lastName;
	private String phoneNumber;

	public Guest(String fn, int ic, String ln, String pn) {
		this.firstName = fn;
		this.icNo = ic;
		this.lastName = ln;
		this.phoneNumber = pn;

	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public int getIcno() {
		return icNo;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String fn) {
		this.firstName = fn;
	}

	public void setIcNo(int ic) {
		this.icNo = ic;
	}

	public void setLastName(String ln) {
		this.lastName = ln;
	}

	public void setPhoneNumber(String pn) {
		this.phoneNumber = pn;
	}

}
