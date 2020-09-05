package appclass;

public class Promotion {

	private String code;
	private double discount;
	private boolean status;

	// constructor
	public Promotion(String code, double discount, boolean status) {
		this.code = code;
		this.discount = discount;
		this.status = status;
	}

	// accessor
	public double getDiscount() {
		return this.discount;
	}

	public String getCode() {
		return this.code;
	}

	public boolean getStatus() {
		return this.status;
	}

}
