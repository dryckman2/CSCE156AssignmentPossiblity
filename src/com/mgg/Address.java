package com.mgg;
/*
 * Addresses for Stores and People
 * @author Matthew Bigge and David Ryckman
 * @cseLogin mbigge
 * @date 
 */

public class Address {
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;

	public Address(String street, String city, String state, String zip, String country) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}

	public String getStreet() {
		return this.street;
	}

	public String getCity() {
		return this.city;
	}

	public String getState() {
		return this.state;
	}

	public String getZip() {
		return this.zip;
	}

	public String getCountry() {
		return this.country;
	}

	/**
	 * Puts Address in XML formated String
	 * 
	 * @param tabs
	 * @return XML Formated Address
	 */
	public String toXMLString(int tabs) {
		String data, offset = "";
		for (int i = 1; i <= tabs; i++) {
			offset += "\t";
		}
		data = offset + "<Address>\n";
		data += offset + "\t<Street>" + getStreet() + "</Street>\n";
		data += offset + "\t<City>" + getCity() + "</City>\n";
		data += offset + "\t<State>" + getState() + "</State>\n";
		data += offset + "\t<ZIP>" + getZip() + "</ZIP>\n";
		data += offset + "\t<Country>" + this.getCountry() + "</Country>\n";
		data += offset + "</Address>\n";
		return data;
	}

	/**
	 * Prints Address in letter format
	 * 
	 * @param tabs
	 */
	public void printReport(int tabs) {
		String offset = "";
		for (int i = 1; i <= tabs; i++) {
			offset += "\t";
		}
		System.out.println(offset + this.street);
		System.out.println(offset + this.city + ", " + this.state + " " + this.zip + " " + this.country);
	}

}
