package com.mgg;

import java.util.List;

/**
 * Demo Runner for whole project
 * 
 * @author David Ryckman and Matt Bigge
 *
 */
public class SalesReport {

	private static final String PERSONSFILE_NAME = "data/Persons.csv";
	private static final String STORESFILE_NAME = "data/Stores.csv";
	private static final String ITEMSFILE_NAME = "data/Items.csv";

	public static void main(String[] args) {
		//TODO UnComment out main code
		/**
		 * // Creates List for CSV data DatabaseConnection dc = new
		 * DatabaseConnection(); List<Person> people = dc.generatePeople(); List<Store>
		 * stores = dc.generateStore(); List<Item> items = dc.generateItem();
		 * 
		 * 
		 * // Picks Employees out of people for later use List<Employee> employees =
		 * Employee.pickEmployees(people); List<Customer> customers =
		 * Customer.pickCustomers(people);
		 * 
		 * // To XML for People Stores and Items DataInOut.exportPeopleToXML(people);
		 * DataInOut.exportStoresToXML(stores, people);
		 * DataInOut.exportItemsToXML(items);
		 * 
		 * // Loads sales form CSV List<Sale> sales = dc.generateSale(items, customers,
		 * employees); dc.close(); // Gives sales list to every store and every employee
		 * where appropriate Sale.assignSalesToStores(stores, sales);
		 * Sale.assignSalesToEmployees(employees, sales);
		 * 
		 * 
		 * 
		 * DataInOut.printReport(stores, items, people, sales, employees);
		 **/
		SalesData.addStore("TITTY", "aa887a",  "SUCCCDICC",  "OMAHA",  "NE", "68666",  "US");


	}
}
