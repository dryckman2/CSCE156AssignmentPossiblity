package com.mgg;

/** 
 * Program to act as POS and data creation for Modern Geek Gaming
 * @author Matthew Bigge and David Ryckman
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DataInOut {

	public static List<Person> importPersons(String filepath) {

		// loading persons file input from csv file
		File personInput = new File(filepath);
		List<Person> people = new ArrayList<>();
		int numberOfPeople;
		try (Scanner scan = new Scanner(personInput)) {
			// Number of People is unused as list.size() does the same thing
			numberOfPeople = Integer.parseInt(scan.nextLine());
			while (scan.hasNextLine()) {
				String tokens[] = scan.nextLine().split(",");
				String personCode = tokens[0];
				String type = tokens[1];
				String lastName = tokens[2];
				String firstName = tokens[3];
				String street = tokens[4];
				String city = tokens[5];
				String state = tokens[6];
				String zip = tokens[7];
				String country = tokens[8];
				// email addresses
				List<String> emails = new ArrayList<>();
				for (int i = 9; i < tokens.length; i++) {
					emails.add(tokens[i]);
				}
				// construct address and persons
				Address a = new Address(street, city, state, zip, country);
				Person p = new Person(personCode, type, lastName, firstName, a, emails);
				people.add(p);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return people;
	}

	public static List<Store> importStores(String filepath) {
		// loading stores data from input .csv file
		File storesInput = new File(filepath);
		List<Store> stores = new ArrayList<>();
		int numberOfStores;
		try (Scanner scan = new Scanner(storesInput)) {
			// Number of Stores is unused as list.size() does the same thing
			numberOfStores = Integer.parseInt(scan.nextLine());
			while (scan.hasNextLine()) {
				String tokens[] = scan.nextLine().split(",");
				String storeCode = tokens[0];
				String managerCode = tokens[1];
				String street = tokens[2];
				String city = tokens[3];
				String state = tokens[4];
				String zip = tokens[5];
				String country = tokens[6];

				// construct Address and Stores
				Address a = new Address(street, city, state, zip, country);
				Store s = new Store(storeCode, managerCode, a);
				stores.add(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return stores;
	}

	public static List<Item> importItems(String filepath) {
		// loading items data from input .csv file
		File itemsInput = new File(filepath);
		List<Item> items = new ArrayList<>();
		int numberOfItems;
		try (Scanner scan = new Scanner(itemsInput)) {
			// Number of Items is unused as list.size() does the same thing
			numberOfItems = Integer.parseInt(scan.nextLine());
			while (scan.hasNextLine()) {
				String tokens[] = scan.nextLine().split(",");
				String itemCode = tokens[0];
				String type = tokens[1];
				String name = tokens[2];
				String price;
				if (!type.equals("PG")) {
					price = tokens[3];
				} else {
					price = null;
				}
				// Construct Items
				Item i;
				switch (type) {
				case "SB":
					i = new Subscription(itemCode, name, price);
					break;
				case "SV":
					i = new Service(itemCode, name, price);
					break;
				default:
					i = new Product(itemCode, type, name, price);
				}
				items.add(i);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return items;
	}

	public static void exportPeopleToXML(List<Person> people) {

		// implement output as XML File
		File personOutputXML = new File("data/Persons.xml");

		// printing person to Persons.XML
		PrintWriter p = null;
		try {
			p = new PrintWriter(personOutputXML);
		} catch (FileNotFoundException e1) {
			System.err.println("File Not Found");
			System.exit(1);
		}
		p.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		p.println("<People>");
		for (Person temp : people) {
			p.print(temp.toXMLString(1));
		}
		p.println("</People>");
		p.close();
	}

	public static void exportStoresToXML(List<Store> stores, List<Person> people) {

		File storesOutputXML = new File("data/Stores.xml");

		// printing Stores to Stores.XML
		PrintWriter printStore = null;
		try {
			printStore = new PrintWriter(storesOutputXML);
		} catch (FileNotFoundException e1) {
			System.err.println("File Not Found");
			System.exit(1);
		}
		printStore.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		printStore.println("<Stores>");
		for (Store temp : stores) {
			printStore.print(temp.toXMLString(1, people));
		}
		printStore.println("</Stores>");
		printStore.close();
	}

	public static void exportItemsToXML(List<Item> items) {

		File itemsOutputXML = new File("data/Items.xml");

		// printing Items to Items.XML
		PrintWriter printItem = null;
		try {
			printItem = new PrintWriter(itemsOutputXML);
		} catch (FileNotFoundException e1) {
			System.err.println("File Not Found");
			System.exit(1);
		}
		printItem.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		printItem.println("<Items>");
		for (Item temp : items) {
			printItem.print(temp.toXMLString(1));
		}
		printItem.print("</Items>");
		printItem.close();

	}
}