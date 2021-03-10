package com.mgg;
/*
 * Class for person data file, constructor/getters/ and setters
 * @author Matthew Bigge and David Ryckman
 * @cseLogin mbigge
 * @date 
 */

import java.util.List;

public class Person {
	private String personCode;
	private String type;
	private String lastName;
	private String firstName;
	private Address address;
	private List<String> emailAddresses;

	public Person(String personCode, String type, String lastName,String firstName, Address address, List<String> emails) {
		this.personCode = personCode;
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address; // list or array for different elements or split into parts and combine
								// elsewhere
		this.emailAddresses = emails; // due to having multiple potential emails, maybe make <list> or array
	}

	// code
	public String getPersonCode() {
		return this.personCode;
	}

	// type
	public String getType() {
		return this.type;
	}

	// names
	public String getName() {
		return lastName + ", " + firstName;
	}

	// mailing addresses
	public Address getAddress() {
		return this.address;
	}

	// email addresses
	public List<String> getEmailAddresses() {
		return this.emailAddresses;
	}

	public String toXMLString(int tabs) {
		String data, offset = "";
		for(int i = 1;i <= tabs;i++) {
			offset += "\t";
		}
		data = offset + "<Person>\n";
		data += offset + "\t<PersonCode>" + this.getPersonCode() + "</PersonCode>\n";
		data += offset + "\t<Type>" + this.getType() + "</Type>\n";
		data += offset + "\t<Name>" + this.getName() + "</Name>\n";
		// Address XML
		data += this.getAddress().toXMLString(tabs + 1);

		for (String email : this.getEmailAddresses()) {
			data += offset + "\t<Email>" + email + "</Email>\n";
		}
		data += offset +  "</Person>\n";
		return data;
	}
}
