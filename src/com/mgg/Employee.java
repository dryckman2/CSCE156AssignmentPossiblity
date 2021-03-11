package com.mgg;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to store employee separate from Customers
 * 
 * @author Matthew Bigge and David Ryckman
 *
 */
public class Employee extends Person {

	private List<Sale> salesByEmployee;

	public Employee(Person p) {
		super(p.getCode(), "E", p.getLastName(), p.getFirstName(), p.getAddress(), p.getEmailAddresses());
		this.salesByEmployee = new ArrayList<Sale>();
	}

	/**
	 * Picks out Employees from list of peole to return in a list
	 * 
	 * @param allPeople
	 * @return Employees from all people
	 */
	public static List<Employee> pickEmployees(List<Person> allPeople) {
		List<Employee> employeesOnly = new ArrayList<Employee>();
		for (Person p : allPeople) {
			if (p.getType().equals("E")) {
				employeesOnly.add(new Employee(p));
			}
		}

		return employeesOnly;
	}

	/**
	 * Sets all sales done by employee
	 * 
	 * @param allSales
	 */
	public void setListOfSales(List<Sale> allSales) {
		for (Sale s : allSales) {
			if (s.getEmployeeCode().equals(this.getCode())) {
				this.salesByEmployee.add(s);
			}
		}
	}

	public int getSalesCount() {
		return salesByEmployee.size();
	}

	/**
	 * 
	 * @return Dollar amount of all Sales
	 */
	public double getTotalOfSales() {
		double totalSales = 0;
		for (Sale s : salesByEmployee) {
			totalSales += s.getTotal();

		}

		return totalSales;
	}

}
