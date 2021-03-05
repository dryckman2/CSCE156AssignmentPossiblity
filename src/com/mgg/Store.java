package com.mgg;
/*
 * Class for store data file, constructor/getters/ and setters
 * @author Matthew Bigge and David Ryckman
 * @cseLogin mbigge
 * @date 
 */

import java.util.List;

public class Stores {
	private String storeCode;
	private String managerCode;
	private Address address;

	public Stores(String storeCode, String managerCode, Address address) {
		this.storeCode = storeCode;
		this.managerCode = managerCode;
		this.address = address; // possibly make a list<> or array of Strings or split into parts and combine
								// elsewhere
	}

	// code
	public String getStoreCode() {
		return this.storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	// managerCode
	public String getManagerCode() {
		return this.managerCode;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	// store address
	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	// Requires a list of people to locate the manager in
	public String toXMLString(int tabs, List<Persons> people) {
		String data, offset = "";
		for (int i = 1; i <= tabs; i++) {
			offset += "\t";
		}
		data = offset +"<Store>\n";
		data += offset +"\t<StoreCode>" + this.getStoreCode() + "</StoreCode>\n";
		data += offset +"\t<Manager>\n";
		for (Persons man : people) {
			if (man.getPersonCode().equals(this.getManagerCode())) {
				data += man.toXMLString(tabs + 2);
				data += offset +"\t</Manager>\n";
			}
		}

		// Address XML
		data += this.getAddress().toXMLString(tabs + 1);
		data += "\t</Store>\n";

		return data;
	}

}