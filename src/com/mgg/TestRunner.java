package com.mgg;

import java.io.File;
import java.util.List;

public class TestRunner {
	
	private static final String PERSONSFILE_NAME = "data/Persons.csv";
	private static final String STORESFILE_NAME = "data/Stores.csv";
	private static final String ITEMSFILE_NAME = "data/Items.csv";
	
	
	
	
	public static void main(String[]args) {
		
		//Creates List for CSV data
		List<Person> people = DataConverter.inportPersons(PERSONSFILE_NAME);
		List<Store> stores = DataConverter.inportStores(STORESFILE_NAME);
		List<Item> items = DataConverter.inportItems(STORESFILE_NAME);
		
		//To XML for People Stores and Items
		DataConverter.exportPeopleToXML(people);
		DataConverter.exportStoresToXML(stores,people);
		DataConverter.exportItemsToXML(items);
		
		//Loads sales form CSV
		//List<Sale> sales = Sale.inportSaleDate("data/Sales.csv", items);

		//Sale.printStoreReport(sales, stores, people);
		
		
		
		
		
		
	}
}
