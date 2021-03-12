package com.mgg;

/** 
 * Group of Methods that take in data and output it
 * @author Matthew Bigge and David Ryckman
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DataInOut {

	/**
	 * Takes file path to CSV data of people to create list of people for later use
	 * 
	 * @param filepath
	 * @return List of All Persons
	 */
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

	/**
	 * Takes file path to CSV data of stores to create list of stores for later use
	 * 
	 * @param filepath
	 * @return List of All Stores
	 */
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

	/**
	 * Takes file path to CSV data of Items to create list of Items for later use
	 * 
	 * @param filepath
	 * @return List of All Items
	 */
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
				// Price default to 0.0 and Doesn't matter in gift cards but is set for others
				double price = 0.0;
				if (!type.equals("PG")) {
					price = Double.parseDouble(tokens[3]);
				}
				// Construct Items
				Item i = null;
				switch (type) {
				case "SB":
					i = new Subscription(itemCode, name, price);
					break;
				case "SV":
					i = new Service(itemCode, name, price);
					break;
				case "PG":
					i = new GiftCard(itemCode, name, price);
					break;
				case "PU":
					i = new Product(itemCode, type, name, price, true);
					break;
				case "PN":
					i = new Product(itemCode, type, name, price, false);
					break;
				default:
					System.err.println("Type Of Item is Not Standard");
					System.exit(1);
					break;
				}
				items.add(i);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return items;
	}

	/**
	 * Takes Sales CSV data and creates appropriate list of all sales
	 * 
	 * @param fileName
	 * @param items
	 * @param customers
	 * @param employees
	 * @return list of all sales
	 */
	public static List<Sale> importSaleData(String fileName, List<Item> items, List<Customer> customers,
			List<Employee> employees) {
		List<Sale> sales = new ArrayList<Sale>();
		File input = new File(fileName);
		int numberOfSales = 0;
		Item type;
		double subtotal, tax;
		try (Scanner scan = new Scanner(input)) {
			numberOfSales = Integer.parseInt(scan.nextLine());
			while (scan.hasNextLine()) {
				subtotal = 0;
				tax = 0;
				String tokens[] = scan.nextLine().split(",");
				String saleCode = tokens[0];
				String storeCode = tokens[1];
				String customerCode = tokens[2];
				String employeeCode = tokens[3];
				List<Purchased> cart = new ArrayList<Purchased>();
				// checks each token to see if it is an items code, then associates other
				// variable with it in the List
				for (int i = 4; i < tokens.length; i++) {
					type = Item.checkCode(items, tokens[i]);
					if (type.getType().equals("PU") || type.getType().equals("PN")) {
						OrderedProduct specifiedType = new OrderedProduct((Product) type,
								Double.parseDouble(tokens[i + 1]));
						cart.add(specifiedType);
						subtotal += specifiedType.getSubTotal();
						tax += specifiedType.getTaxTotal();

						// To Skip Over Used Quantity Number
						i += 1;
					}
					if (type.getType().equals("PG")) {
						// TODO: Change parameters to Gift cards with price instead of quantity
						OrderedGiftCard specifiedType = new OrderedGiftCard((GiftCard) type,
								Double.parseDouble(tokens[i + 1]));
						cart.add(specifiedType);
						subtotal += specifiedType.getSubTotal();
						tax += specifiedType.getTaxTotal();
						// To Skip Over Used Quantity Number
						i += 1;
					}
					if (type.getType().equals("SB")) {
						OrderedSubscription specifiedType = new OrderedSubscription((Subscription) type, tokens[i + 1],
								tokens[i + 2]);
						cart.add(specifiedType);
						subtotal += specifiedType.getSubTotal();
						tax += specifiedType.getTaxTotal();
						// To Skip Over Used
						i += 2;
					}
					if (type.getType().equals("SV")) {
						String employeeName = Person.checkCode(employees, tokens[i + 1]).getName();
						OrderedService specifiedType = new OrderedService((Service) type, tokens[i + 1],
								Double.parseDouble(tokens[i + 2]), employeeName);
						cart.add(specifiedType);
						subtotal += specifiedType.getSubTotal();
						tax += specifiedType.getTaxTotal();
						// To Skip Over Used
						i += 2;
					}

				}
				subtotal = Sale.changeRound(subtotal);
				tax = Sale.changeRound(tax);
				Sale s = new Sale(saleCode, storeCode, customerCode, employeeCode, cart, subtotal, tax);
				s.runCustomerEmployeeDiscount(customers, employees);
				sales.add(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return sales;
	}

	/**
	 * Takes List of people to creates XML file of them
	 * 
	 * @param people
	 */
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

	/**
	 * Takes List of stores to creates XML file of them. Needs people to find
	 * manager of store
	 * 
	 * @param stores
	 * @param people
	 */
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

	/**
	 * Takes List of Items to creates XML file of them
	 * 
	 * @param items
	 */
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

	/**
	 * Prints Big Report of All Store Activity
	 * 
	 * @param stores
	 * @param items
	 * @param people
	 * @param allSales
	 * @param employees
	 */
	public static void printReport(List<Store> stores, List<Item> items, List<Person> people, List<Sale> allSales,
			List<Employee> employees) {

		System.out.println("Sales Person Summary Report");
		System.out.println("------------------------------------");
		System.out.printf("%-20s%-15s%-15s\n", "SalesPerson", "# Sales", "Total");
		int count = 0;
		double total = 0;
		for (Employee e : employees) {
			System.out.printf("%-20s%-15d%7.2f\n", e.getName(), e.getSalesCount(),
					Sale.changeRound(e.getTotalOfSales()));
			count += e.getSalesCount();
			total += Sale.changeRound(e.getTotalOfSales());
		}
		System.out.println("------------------------------------");
		System.out.printf("%-20s%-15d%7.2f\n", "", count, total);

		// Store Sales Summary
		System.out.println("\n\nStore Person Summary Report");
		System.out.println("-------------------------------------------------------");
		System.out.printf("%-20s%-15s%-15s%-15s\n", "Store", "Manager", "# Sales", "Total");
		count = 0;
		total = 0;
		for (Store e : stores) {
			System.out.printf("%-20s%-15s%-15d%7.2f\n", e.getCode(), e.getManager(people).getName(), e.getSalesCount(),
					Sale.changeRound(e.getTotalOfSales()));
			count += e.getSalesCount();
			total += Sale.changeRound(e.getTotalOfSales());
		}
		System.out.println("-------------------------------------------------------");
		System.out.printf("%-20s%-15s%-15d%7.2f\n", "", "", count, total);

		for (Sale s : allSales) {
			s.printIndividualSaleReport(people);
		}

	}

}