package com.mgg;
/*
 * Class for store data file, constructor/getters/ and setters
 * @author Matthew Bigge and David Ryckman
 * @cseLogin mbigge
 * @date 
 */

import java.util.ArrayList;
import java.util.List;

public class Store {
	private String code;
	private String managerCode;
	private Address address;
	private List<Sale> saleAtStore;

	public Store(String code, String managerCode, Address address) {
		this.code = code;
		this.managerCode = managerCode;
		this.address = address; // possibly make a list<> or array of Strings or split into parts and combine
								// elsewhere
		// Initializes Blank but is set after sales data is imported
		this.saleAtStore = new ArrayList<Sale>();
	}

	// code
	public String getCode() {
		return this.code;
	}

	// managerCode
	public String getManagerCode() {
		return this.managerCode;
	}

	// store address
	public Address getAddress() {
		return this.address;
	}

	// Requires a list of people to locate the manager in
	public String toXMLString(int tabs, List<Person> people) {
		String data, offset = "";
		for (int i = 1; i <= tabs; i++) {
			offset += "\t";
		}
		data = offset + "<Store>\n";
		data += offset + "\t<StoreCode>" + this.getCode() + "</StoreCode>\n";
		data += offset + "\t<Manager>\n";
		for (Person man : people) {
			if (man.getCode().equals(this.getManagerCode())) {
				data += man.toXMLString(tabs + 2);
				data += offset + "\t</Manager>\n";
			}
		}

		// Address XML
		data += this.getAddress().toXMLString(tabs + 1);
		data += "\t</Store>\n";

		return data;
	}

	public Person getManager(List<Person> people) {
		return Person.checkCode(people, this.managerCode);
	}

	public void setListOfSales(List<Sale> allSales) {
		for (Sale s : allSales) {
			if (s.getStoreCode().equals(this.code)) {
				this.saleAtStore.add(s);
			}
		}
	}

	
	public static void printAllStoresReport(List<Sale> sales, List<Store> stores, List<Person> people) {
		for (Store store : stores) {
			System.out.println(
					"-----------------------------------------------------------------------------------------------");

			// store code
			System.out.println("Store Code: " + store.getCode());

			store.getAddress().printReport(1);

			// manager code and details
			System.out.println("\nManager Code: " + store.getManagerCode());
			Person manager = store.getManager(people);
			manager.printReport(1);

			// items for each store
			// TODO: some for-loop going thorugh each sale, calling a getCost() for each,
			// then
			// TODO: printing a total amount of sales and money made by this store
			System.out.println("Sales:");
			System.out.println("Amount: ");
		}

	}

}