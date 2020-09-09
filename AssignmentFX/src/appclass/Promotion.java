package appclass;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Promotion {

	private String code;
	private double discount;
	private static Scanner x;

	// constructor
	public Promotion(String code, double discount) {
		this.code = code;
		this.discount = discount;
	}

	public Promotion(String code) {
		this.code = code;
		this.discount = 1;
	}

	// accessor
	public String getCode() {
		return this.code;
	}

	public double getDiscount() {
		return this.discount;
	}

	// method
	public static ArrayList<Promotion> initializePromotion(String filepath) {
		ArrayList<Promotion> promo = new ArrayList<Promotion>();

		String code, discount;

		try {
			x = new Scanner(new File(filepath));
			x.useDelimiter("[,\n]");

			while (x.hasNext()) {
				code = x.next();
				discount = x.next();

				promo.add(new Promotion(code, Double.parseDouble(discount)));
			}
		} catch (Exception e) {
			System.out.println("create arrayPromo promotion.txt has error!");
		}

		return promo;
	}

	public boolean validateCode(ArrayList<Promotion> arrPromo) {
		for (int i = 0; i < arrPromo.size(); i++)
			if (arrPromo.get(i).getCode().equals(this.code)) {
				this.discount = arrPromo.get(i).getDiscount();
				return true;
			}

		return false;
	}
}
