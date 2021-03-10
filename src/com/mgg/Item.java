package com.mgg;
/*
 * Class for items data file, constructor/getters/ and setters
 * @author Matthew Bigge and David Ryckman
 * @cseLogin mbigge
 * @date 
 */

public abstract class Item {

	private String code;
	private String name;
	private String basePrice;

	public Item(String code, String name, String basePrice) {
		this.code = code;
		this.name = name;
		this.basePrice = basePrice;
	}

	public String getCode() {
		return this.code;
	}

	// type
	public abstract String getType();

	// name
	public String getName() {
		return this.name;
	}

	// basePrice
	public String getBasePrice() {
		return this.basePrice;
	}

	public abstract String toXMLString(int tabs);
}
