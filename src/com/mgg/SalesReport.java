package com.mgg;

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
		// Creates List for CSV data
		DatabaseConnection dc = new DatabaseConnection();
		CustomList<Person> people = dc.generatePeople();
		CustomList<Store> stores = dc.generateStore();
		CustomList<Item> items = dc.generateItem();

		// Picks Employees out of people for later use
		CustomList<Employee> employees = Employee.pickEmployees(people);
		CustomList<Customer> customers = Customer.pickCustomers(people);

		// To XML for People Stores and Items
		DataInOut.exportPeopleToXML(people);
		DataInOut.exportStoresToXML(stores, people);
		DataInOut.exportItemsToXML(items);

		// Loads sales form CSV
		CustomList<Sale> saleByCustomer = dc.generateSale(items, customers, employees,stores,new ComparebyCustomerName());
		CustomList<Sale> saleByTotal = dc.generateSale(items, customers, employees,stores,new CompareByValue());
		CustomList<Sale> salesByStore = dc.generateSale(items, customers, employees,stores,new CompareByStoreEmp());
		dc.close();
	
		System.out.println("Sales By Customer");
		System.out.println("-------------------");
		Sale.smallReport(saleByCustomer);
		System.out.println("\n\n");
		
		System.out.println("Sales By Total");
		System.out.println("-------------------");
		Sale.smallReport(saleByTotal);
		System.out.println("\n\n");
		
		System.out.println("Sales By Store");
		System.out.println("-------------------");
		Sale.smallReport(salesByStore);
		
	}
}
