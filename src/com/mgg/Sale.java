package com.mgg;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * Sale holds all data for individual transactions.
 * 
 * @author David Ryckman and Matt Bigge
 *
 */
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
		total = total - Sale.changeRound(total * discountRate);
		return Sale.changeRound(total);
	}

	/**
	 * Take list of sales and assigns them to stores Lists
	 * 
	 * @param stores
	 * @param allSales
	 */
	public static void assignSalesToStores(List<Store> stores, List<Sale> allSales) {
		for (Store thisStore : stores) {
			thisStore.setListOfSales(allSales);
		}
	}

	/**
	 * Take list of sales and assigns them to Employee Lists
	 * 
	 * @param employees
	 * @param allSales
	 */
	public static void assignSalesToEmployees(List<Employee> employees, List<Sale> allSales) {
		for (Employee thisEmployee : employees) {
			thisEmployee.setListOfSales(allSales);
		}
	}

	/**
	 * Sets Discount Rate for Sale
	 * 
	 * @param customers
	 * @param employees
	 */
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

	/**
	 * Rounds to tenth for change
	 * 
	 * @param a
	 * @return
	 */
	public static double changeRound(double a) {
		return Math.round(a * 100.0) / 100.0;
	}

	/**
	 * Prints individual Sales reports
	 * 
	 * @param people
	 */
	public void printIndividualSaleReport(List<Person> people) {
		System.out.println("Sale  #" + this.getSaleCode());
		System.out.println("Store  #" + this.getStoreCode());
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
		for (Purchased p : this.getItems()) {
			p.printReport();
		}
		System.out.println("------------------------------------------------------------------------");
		System.out.printf("%68s$ %.2f\n", "Subtotal ", this.getSubtotal());
		System.out.printf("%68s$ %.2f\n", "Tax ", this.getTax());
		if (this.getDiscountRate() != 0.0) {
			String discount = "Dsicount (" + this.getDiscountRate() * 100 + "%) ";
			System.out.printf("%68s$ %.2f\n", discount, Sale.changeRound(this.getDiscountRate() * (this.subtotal + this.getTax())));
		}
		System.out.printf("%68s$ %.2f\n", "Total ", this.getTotal());

		System.out.printf("\n\n\n");
	}

}
