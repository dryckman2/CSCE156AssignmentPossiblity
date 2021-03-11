package com.mgg;

import java.util.ArrayList;
import java.util.List;

/*
 * Class for items data file, constructor/getters/ and setters
 * @author Matthew Bigge and David Ryckman
 * @cseLogin mbigge
 * @date 
 */

public abstract class Item {

	private String code;
	private String name;
	private double basePrice;

	public Item(String code, String name, double basePrice) {
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
	public double getBasePrice() {
		return this.basePrice;
	}

	public abstract String toXMLString(int tabs);
	

	public static List<String> itemCodeList(List<Item> items) {
		List<String> codeList = new ArrayList<String>();

		for (Item i : items) {
			codeList.add(i.getCode());
		}
		return codeList;
	}
	
	
	public static <T extends Item> T checkCode(List<T> items, String code) {
		for (T i : items) {
			if (i.getCode().equals(code)) {
				//Return Copy of i  not i  
				return i;
			}
		}
		return null;
	}
}
