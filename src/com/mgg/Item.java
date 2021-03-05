package com.mgg;
/*
 * Class for items data file, constructor/getters/ and setters
 * @author Matthew Bigge and David Ryckman
 * @cseLogin mbigge
 * @date 
 */

public abstract class Items {

	private String code;
	private String name;
	private String basePrice;

	public Items(String code, String name, String basePrice) {
		this.code = code;
		this.name = name;
		this.basePrice = basePrice;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	// type
	public abstract String getType();

	// name
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// basePrice
	public String getBasePrice() {
		return this.basePrice;
	}

	public void setBasePrice(String basePrice) {
		this.basePrice = basePrice;
	}

	public abstract String toXMLString(int tabs);
}
