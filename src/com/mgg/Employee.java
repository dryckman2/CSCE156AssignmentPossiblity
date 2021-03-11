package com.mgg;

import java.util.ArrayList;
import java.util.List;

public class Employee extends Person {

	private List<Sale> salesByEmployee;

	public Employee(Person p) {
		super(p.getCode(), "E", p.getLastName(), p.getFirstName(), p.getAddress(), p.getEmailAddresses());
		this.salesByEmployee = new ArrayList<Sale>();
	}

	public static List<Employee> pickEmployees(List<Person> allPeople) {
		List<Employee> employeesOnly = new ArrayList<Employee>();
		for (Person p : allPeople) {
			if (p.getType().equals("E")) {
				employeesOnly.add(new Employee(p));
			}
		}

		return employeesOnly;
	}

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

	public double getTotalOfSales() {
		double totalSales = 0;
		for (Sale s : salesByEmployee) {
			totalSales += s.getTotal();

		}

		return totalSales;
	}

}
