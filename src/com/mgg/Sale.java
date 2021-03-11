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
	private List<Item> items;

	public Sale(String saleCode, String storeCode, String customerCode, String employeeCode, List<Item> items) {
		this.saleCode = saleCode;
		this.storeCode = storeCode;
		this.customerCode = customerCode;
		this.employeeCode = employeeCode;
		this.items = items;
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

	public List<Item> getItems() {
		return items;
	}

	public static List<Sale> importSaleDate(String fileName, List<Item> items) {
		List<Sale> sales = new ArrayList<Sale>();
		File input = new File(fileName);
		int numberOfSales = 0;
		Item type;
		try (Scanner scan = new Scanner(input)) {
			numberOfSales = Integer.parseInt(scan.nextLine());
			while (scan.hasNextLine()) {
				String tokens[] = scan.nextLine().split(",");
				String saleCode = tokens[0];
				String storeCode = tokens[1];
				String customerCode = tokens[2];
				String employeeCode = tokens[3];
				List<Item> cart = new ArrayList<Item>();
				// checks each token to see if it is an items code, then associates other
				// variable with it in the List
				for (int i = 4; i < tokens.length; i++) {
					type = Item.checkCode(items, tokens[i]);
					if (type.getType().equals("PU") || type.getType().equals("PN")) {
						Product specifiedType = (Product) type;
						specifiedType.setQuantity(Double.parseDouble(tokens[i + 1]));
						cart.add(specifiedType);
						// To Skip Over Used Quantity Number
						i += 1;
					}
					if (type.getType().equals("PG")) {
						// TODO: Change parameters to Gift cards with price instead of quantity
						GiftCard specifiedType = (GiftCard) type;
						specifiedType.setAmount(Double.parseDouble(tokens[i + 1]));
						cart.add(specifiedType);
						// To Skip Over Used Quantity Number
						i += 1;
					}
					if (type.getType().equals("SB")) {
						Subscription specifiedType = (Subscription) type;
						specifiedType.setBeginDate(tokens[i + 1]);
						specifiedType.setEndDate(tokens[i + 2]);
						cart.add(specifiedType);
						// To Skip Over Used
						i += 2;
					}
					if (type.getType().equals("SV")) {
						Service specifiedType = (Service) type;
						specifiedType.setEmployeeCode(tokens[i + 1]);
						specifiedType.setNumOfHours(Double.parseDouble(tokens[i + 2]));
						cart.add(specifiedType);
						// To Skip Over Used
						i += 2;
					}

				}
				Sale s = new Sale(saleCode, storeCode, customerCode, employeeCode, cart);
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
	
	
	public static void printReport(List<Store> stores,List<Item> items, List<Person> people,List<Sale> allSales,List<Employee> employees){
		
		System.out.println("Sales Person Summary Report");
		System.out.println("------------------------------------");
		System.out.printf("%-20s%-15s%-15s\n","SalesPerson","# Sales","Total");
		for(Employee e: employees) {
			System.out.printf("%-20s%-15d%-15f\n",e.getName(),e.getSalesCount(),e.getTotalOfSales());
		}
		
		
		
	}
	

}
