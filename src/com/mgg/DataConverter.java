package com.mgg;

/** 
 * Program to 
 * @author Matthew Bigge and David Ryckman
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DataConverter {
	private static final String PERSONSFILE_NAME = "data/Persons.csv";
	private static final String STORESFILE_NAME = "data/Stores.csv";
	private static final String ITEMSFILE_NAME = "data/Items.csv";

	/*
	 * public static List<Persons> loadPersonsData(){ List<Persons> people = new
	 * ArrayList<>(); }
	 */

	public static void main(String[] args) {
		// TODO: need to read in/load csv files from data directory, each has a first

		// loading persons file input from csv file
		File personInput = new File(PERSONSFILE_NAME);
		List<Persons> people = new ArrayList<>();
		int numberOfPeople;
		try (Scanner scan = new Scanner(personInput)) {
			numberOfPeople = Integer.parseInt(scan.nextLine());
			while (scan.hasNextLine()) {
				String tokens[] = scan.nextLine().split(",");
				String personCode = tokens[0];
				String type = tokens[1];
				String name = tokens[2] + ", " + tokens[3];
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
				Persons p = new Persons(personCode, type, name, a, emails);
				people.add(p);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		// loading stores data from input .csv file
		File storesInput = new File(STORESFILE_NAME);
		List<Stores> stores = new ArrayList<>();
		int numberOfStores;
		try (Scanner scan = new Scanner(storesInput)) {
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
				Stores s = new Stores(storeCode, managerCode, a);
				stores.add(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		// loading items data from input .csv file
		File itemsInput = new File(ITEMSFILE_NAME);
		List<Items> items = new ArrayList<>();
		int numberOfItems;
		try (Scanner scan = new Scanner(itemsInput)) {
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
				Items i;
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

		// TODO: convert the data into xml or JSON and place them into data directory
		// implement output as XML File
		File personOutputXML = new File("data/Persons.xml");
		File storesOutputXML = new File("data/Stores.xml");
		File itemsOutputXML = new File("data/Items.xml");
		// TODO: implement output as JSON files
		/*
		 * File personsOutputJSON = new File("data/Persons.json"); File storesOutputJSON
		 * = new File("data/Stores.json"); File itemsOutputJSON = new
		 * File("data/Items.json");
		 */

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
		for (Persons temp : people) {
			p.print(temp.toXMLString(1));
		}
		p.println("</People>");
		p.close();

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
		for (Stores temp : stores) {
			printStore.print(temp.toXMLString(1,people));
		}
		printStore.println("</Stores>");
		printStore.close();

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
		for (Items temp : items) {
			printItem.print(temp.toXMLString(1));
		}
		printItem.print("</Items>");
		printItem.close();

	}
}