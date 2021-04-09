package com.mgg;

import java.util.List;
/**
 * Demo Runner for whole project
 * @author David Ryckman and Matt Bigge
 *
 */
public class SalesReport {
             
	private static final String PERSONSFILE_NAME = "data/Persons.csv";
	private static final String STORESFILE_NAME = "data/Stores.csv";
	private static final String ITEMSFILE_NAME = "data/Items.csv";

	public static void main(String[] args) {

		// Creates List for CSV data
		DatabaseConnection.connectionStart();
		List<Person> people = DatabaseConnection.generatePeople();
		List<Store> stores = DatabaseConnection.generateStore();
		List<Item> items = DatabaseConnection.generateItem();
		
		
		// Picks Employees out of people for later use
		List<Employee> employees = Employee.pickEmployees(people);
		List<Customer> customers = Customer.pickCustomers(people);

		// To XML for People Stores and Items
		DataInOut.exportPeopleToXML(people);
		DataInOut.exportStoresToXML(stores, people);
		DataInOut.exportItemsToXML(items);

		// Loads sales form CSV
		List<Sale> sales = DatabaseConnection.generateSale(items, customers, employees);
		DatabaseConnection.close();
		// Gives sales list to every store and every employee where appropriate
		Sale.assignSalesToStores(stores, sales);
		Sale.assignSalesToEmployees(employees, sales);

		
		
		DataInOut.printReport(stores, items, people, sales, employees);
		
		
		
		
		
	}
}
