package com.mgg;

import java.util.List;

public class TestRunner {
	
	private static final String PERSONSFILE_NAME = "data/Persons.csv";
	private static final String STORESFILE_NAME = "data/Stores.csv";
	private static final String ITEMSFILE_NAME = "data/Items.csv";
	
	
	
	
	public static void main(String[]args) {
		
		//Creates List for CSV data
		List<Person> people = DataInOut.importPersons(PERSONSFILE_NAME);
		List<Store> stores = DataInOut.importStores(STORESFILE_NAME);
		List<Item> items = DataInOut.importItems(ITEMSFILE_NAME);
		
		//To XML for People Stores and Items
		DataInOut.exportPeopleToXML(people);
		DataInOut.exportStoresToXML(stores,people);
		DataInOut.exportItemsToXML(items);
		
		//Loads sales form CSV
		List<Sale> sales = Sale.importSaleDate("data/Sales.csv", items);
		
		Sale.assignSalesToStores(stores, sales);
		
		
		Sale.printStoreReport(sales, stores, people);
		
		
		
		
		
		
	}
}
