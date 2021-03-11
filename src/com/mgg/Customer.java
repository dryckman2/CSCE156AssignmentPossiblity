package com.mgg;

/*
 * Customers of the Games Store
 *
 * @author Matthew Bigge and David Ryckman
 * @cseLogin mbigge
 * @date 
 */

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {

	private double discount;

	public Customer(Person p) {
		super(p.getCode(), p.getType(), p.getLastName(), p.getFirstName(), p.getAddress(), p.getEmailAddresses());
		if (p.getType().equals("P")) {
			this.discount = .10;
		}
		if (p.getType().equals("G")) {
			this.discount = .05;
		}
		if (p.getType().equals("C")) {
			this.discount = 0;
		}
	}

	public double getDiscount() {
		return discount;
	}

	/**
	 * Checks all people and creates list of only customers
	 * 
	 * @param allPeople
	 * @return List of Only Customers
	 */
	public static List<Customer> pickCustomers(List<Person> allPeople) {
		List<Customer> customers = new ArrayList<Customer>();
		for (Person p : allPeople) {
			if ((p.getType().equals("P")) || (p.getType().equals("C")) || (p.getType().equals("G"))) {
				customers.add(new Customer(p));
			}
		}

		return customers;
	}

}
