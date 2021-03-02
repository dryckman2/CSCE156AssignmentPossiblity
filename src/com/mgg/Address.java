package com.mgg;
/*
 * Class for store data file, constructor/getters/ and setters
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

	// street
	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	// city
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	// state
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	// zip
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	// country
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

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

}
