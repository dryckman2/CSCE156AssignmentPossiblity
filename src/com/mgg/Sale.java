package com.mgg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sale {
	private String saleCode;
	private String storeCode;
	private String customerCode;
	private String employeeCode;
	private List<Purchased> purchased;
	private double subtotal;
	private double tax;
	private double discountRate;

	public Sale(String saleCode, String storeCode, String customerCode, String employeeCode, List<Purchased> items,
			double subtotal, double tax) {
		this.saleCode = saleCode;
		this.storeCode = storeCode;
		this.customerCode = customerCode;
		this.employeeCode = employeeCode;
		this.purchased = items;
		this.subtotal = subtotal;
		this.tax = tax;
		this.discountRate = 0;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public double getTax() {
		return tax;
	}
	
	public double getDiscountRate() {
		return discountRate;
	}

	public String getSaleCode() {
		return this.saleCode;
	}

	public String getStoreCode() {
		return this.storeCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public List<Purchased> getItems() {
		return purchased;
	}

	public double getTotal() {
		double total = subtotal + tax;
		// TODO: Remove
		total = total - (total * discountRate);
		return Sale.changeRound(total);
	}

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
								Double.parseDouble(tokens[i + 2]),employeeName);
						cart.add(specifiedType);
						subtotal += specifiedType.getSubTotal();
						tax += specifiedType.getTaxTotal();
						// To Skip Over Used
						i += 2;
					}

				}
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

	public static void assignSalesToStores(List<Store> stores, List<Sale> allSales) {
		for (Store thisStore : stores) {
			thisStore.setListOfSales(allSales);
		}
	}

	public static void assignSalesToEmployees(List<Employee> employees, List<Sale> allSales) {
		for (Employee thisEmployee : employees) {
			thisEmployee.setListOfSales(allSales);
		}
	}

	public void runCustomerEmployeeDiscount(List<Customer> customers, List<Employee> employees) {
		discountRate = 0;
		for (Customer c : customers) {
			if (c.getCode().equals(this.getCustomerCode())) {
				discountRate = c.getDiscount();
			}
		}
		for (Employee e : employees) {
			if (e.getCode().equals(this.getCustomerCode())) {
				discountRate = 0.15;
			}
		}
	}

	public static double changeRound(double a) {
		return Math.round(a * 100.0) / 100.0;
	}

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
		
		
		//Store Sales Summary
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
	
	
	
	public void printIndividualSaleReport(List<Person> people) {
		System.out.println("Sale  #"  + this.getSaleCode());
		System.out.println("Store  #"  + this.getStoreCode());
		System.out.println("Customer:");
		Person thisCustomer = Person.checkCode(people, this.getCustomerCode());
		thisCustomer.printReport();
		System.out.println();
		System.out.println("Sale Person:");
		Person employee = Person.checkCode(people, this.getEmployeeCode());
		employee.printReport();
		System.out.println();
		System.out.println("Item                                                               Total");
		System.out.println("------------------------------------------------------------------------");
		for(Purchased p :this.getItems()) {
			p.printReport();
		}
		System.out.println("------------------------------------------------------------------------");
		System.out.printf("%68s$ %.2f\n","Subtotal ",this.getSubtotal());
		System.out.printf("%68s$ %.2f\n","Tax ",this.getTax());
		if(this.getDiscountRate() != 0.0) {
			String discount = "Dsicount (" + this.getDiscountRate() * 100 + "%) ";
			System.out.printf("%68s$ %.2f\n",discount,(this.getDiscountRate() * (this.subtotal + this.getTax())));
		}
		System.out.printf("%68s$ %.2f\n","Total ",this.getTotal());
		
		
		System.out.printf("\n\n\n");
	}

}
