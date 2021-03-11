package com.mgg;
/*
 * Class for store data file
 * @author Matthew Bigge and David Ryckman
 * @cseLogin mbigge
 * @date 
 */

import java.util.ArrayList;
import java.util.List;

public class Store {
	private String code;
	private String managerCode;
	private Address address;
	private List<Sale> saleAtStore;

	public Store(String code, String managerCode, Address address) {
		this.code = code;
		this.managerCode = managerCode;
		this.address = address; // possibly make a list<> or array of Strings or split into parts and combine
								// elsewhere
		// Initializes Blank but is set after sales data is imported
		this.saleAtStore = new ArrayList<Sale>();
	}

	// code
	public String getCode() {
		return this.code;
	}

	// managerCode
	public String getManagerCode() {
		return this.managerCode;
	}

	// store address
	public Address getAddress() {
		return this.address;
	}

	/**
	 * Turns store to XML String. Needs People to find manager in
	 * 
	 * @param tabs
	 * @param people
	 * @return
	 */
	public String toXMLString(int tabs, List<Person> people) {
		String data, offset = "";
		for (int i = 1; i <= tabs; i++) {
			offset += "\t";
		}
		data = offset + "<Store>\n";
		data += offset + "\t<StoreCode>" + this.getCode() + "</StoreCode>\n";
		data += offset + "\t<Manager>\n";
		for (Person man : people) {
			if (man.getCode().equals(this.getManagerCode())) {
				data += man.toXMLString(tabs + 2);
				data += offset + "\t</Manager>\n";
			}
		}

		// Address XML
		data += this.getAddress().toXMLString(tabs + 1);
		data += "\t</Store>\n";

		return data;
	}

	/**
	 * Returns the manager as a person from people
	 * 
	 * @param people
	 * @return Manager
	 */
	public Person getManager(List<Person> people) {
		return Person.checkCode(people, this.managerCode);
	}

	public void setListOfSales(List<Sale> allSales) {
		for (Sale s : allSales) {
			if (s.getStoreCode().equals(this.code)) {
				this.saleAtStore.add(s);
			}
		}
	}

	public int getSalesCount() {
		return saleAtStore.size();
	}

	public double getTotalOfSales() {
		double totalSales = 0;
		for (Sale s : saleAtStore) {
			totalSales += s.getTotal();

		}

		return totalSales;
	}

}