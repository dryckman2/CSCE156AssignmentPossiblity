package com.mgg;




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
	private Person customer;
	private Person employee;
	private CustomList<Purchased> purchased;
	private double subtotal;
	private double tax;
	private double discountRate;

	public Sale(String saleCode, String storeCode, Person customer, Person employee, CustomList<Purchased> items,
			double subtotal, double tax) {
		this.saleCode = saleCode;
		this.storeCode = storeCode;
		this.customer = customer;
		this.employee = employee;
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

	public Person getCustomer() {
		return customer;
	}

	public Person getEmployee() {
		return employee;
	}

	public CustomList<Purchased> getItems() {
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
	public static void assignSalesToStores(CustomList<Store> stores, CustomList<Sale> allSales) {
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
	public static void assignSalesToEmployees(CustomList<Employee> employees, CustomList<Sale> allSales) {
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
	public void runCustomerEmployeeDiscount(CustomList<Customer> customers, CustomList<Employee> employees) {
		discountRate = 0;
		for (Customer c : customers) {
			if (c.getCode().equals(this.customer.getCode())) {
				discountRate = c.getDiscount();
			}
		}
		for (Employee e : employees) {
			if (e.getCode().equals(this.customer.getCode())) {
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
	public void printIndividualSaleReport(CustomList<Person> people) {
		System.out.println("Sale  #" + this.getSaleCode());
		System.out.println("Store  #" + this.getStoreCode());
		System.out.println("Customer:");
		Person thisCustomer = Person.checkCode(people, this.customer.getCode());
		thisCustomer.printReport();
		System.out.println();
		System.out.println("Sale Person:");
		Person employee = Person.checkCode(people, this.employee.getCode());
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
	
	public static void smallReport(CustomList<Sale> sales) {
		System.out.printf("%-10s%-10s%-20s%-20s%-10s\n", "Sale","Store", "Customer", "Employee", "Total");
		System.out.println("------------------------------------------------------------------------");
		for(Sale s : sales) {
			System.out.printf("%-10s%-10s%-20s%-20s%-10s\n", s.getSaleCode(), s.getStoreCode(), s.getCustomer().getName(), s.getEmployee().getName(),s.getTotal());
		}
		
		
	}

}
