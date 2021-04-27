package com.mgg;

import java.util.ArrayList;
import java.util.List;

/*
 * Class for items data file
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

	public abstract String getType();

	public String getName() {
		return this.name;
	}

	public double getBasePrice() {
		return Sale.changeRound(this.basePrice);
	}

	/**
	 * Makes String that represent individual items in XML data
	 * 
	 * @param tabs
	 * @return XMLed String for Items
	 */
	public abstract String toXMLString(int tabs);

	public static List<String> itemCodeList(CustomList<Item> items) {
		List<String> codeList = new ArrayList<String>();

		for (Item i : items) {
			codeList.add(i.getCode());
		}
		return codeList;
	}

	/**
	 * Checks given code and returns that item template from list of item templates
	 * 
	 * @param <T>
	 * @param items
	 * @param code
	 * @return item from items with code
	 */
	public static <T extends Item> T checkCode(CustomList<T> items, String code) {
		for (T i : items) {
			if (i.getCode().equals(code)) {
				return i;
			}
		}
		return null;
	}
}
