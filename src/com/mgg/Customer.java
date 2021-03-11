package com.mgg;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Person {

	private double discount;

	public Customer(Person p) {
		super(p.getCode(), p.getType(), p.getLastName(), p.getFirstName(), p.getAddress(), p.getEmailAddresses());
		if(p.getType().equals("P")) {
			this.discount = .10;
		}
		if(p.getType().equals("G")) {
			this.discount = .05;
		}
		if(p.getType().equals("C")) {
			this.discount = 0;
		}
	}

	public static List<Customer> pickCustomers(List<Person> allPeople) {
		List<Customer> customers = new ArrayList<Customer>();
		for (Person p : allPeople) {
			if ((p.getType().equals("P")) || (p.getType().equals("C")) || (p.getType().equals("G"))) {
				customers.add(new Customer(p));
			}
		}

		return customers;
	}
	
	public double getDiscount(){
		return discount;
	}

}
