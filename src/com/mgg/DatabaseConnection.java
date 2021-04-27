package com.mgg;
/**
 * Class Connects to database and receives Data
 * 
 * @author David Ryckman and Matt Bigge
 *
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;



public class DatabaseConnection {

	//SQL Login
	private static final String USER = "dryckman";
	private static final String PASSWORD = "3pF-my";
	private static final String URL = "jdbc:mysql://cse.unl.edu/dryckman?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	
	//Global Connection so each query doesn't make its own
	private  Connection conn;
	private  PreparedStatement ps;
	private  ResultSet rs;

	public DatabaseConnection() {
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Closes Standard connection made to database
	 */
	public void close() {
		try {
			conn.close();
			ps.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * generates list of People form the database
	 */
	public CustomList<Person> generatePeople() {

		CustomList<Person> people = new CustomList<>();
		String query = "select p.personId, p.referencecode, p.personType,p.lastName, p.firstName, a.street, a.city, s.name as stateName, a.zip,c.name as countryName"
				+ " from Person p " + "join Address a on a.addressId = p.addressId "
				+ "join State s on s.stateId = a.stateId " + "join Country c on c.countryId = a.countryId;";
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				//Gather From Results
				int personId = rs.getInt("personId");
				String personCode = rs.getString("referencecode");
				String type = rs.getString("personType");
				String lastName = rs.getString("lastName");
				String firstName = rs.getString("firstName");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("stateName");
				String zip = rs.getString("zip");
				String country = rs.getString("countryName");
				
				//Second Query to get Email Addresses
				CustomList<String> emails = new CustomList<>();
				query = "select emailAddress from Email where personId = ?;";
				ps = conn.prepareStatement(query);
				ps.setInt(1, personId);
				ResultSet rs2 = ps.executeQuery();
				while (rs2.next()) {
					emails.add(rs2.getString("emailAddress"));
				}
				rs2.close();
				
				//Creation
				Address a = new Address(street, city, state, zip, country);
				Person p = new Person(personCode, type, lastName, firstName, a, emails);
				people.add(p);
			}
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}
		return people;

	}
	/**
	 * generates list of Stores from the database
	 */
	public CustomList<Store> generateStore() {
		CustomList<Store> stores = new CustomList<>();
		String query = "select s.referencecode,p.referencecode as managerCode, a.street,a.city, st.name as stateName, a.zip,c.name as countryName " + 
				"from Store s " + 
				"join Address a on a.addressId = s.addressId " + 
				"join State st on st.stateId = a.stateId " + 
				"join Country c on c.countryId = a.countryId " + 
				"join EmployeePerson ep on ep.employeeId = s.managerId " + 
				"join Person p on p.personId = ep.personId;";
		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				//Gather data from query
				String storeCode = rs.getString("referencecode");
				String managerCode = rs.getString("managerCode");
				String street = rs.getString("street");
				String city = rs.getString("city");
				String state = rs.getString("stateName");
				String zip = rs.getString("zip");
				String country = rs.getString("countryName");
				
				//Creation
				Address a = new Address(street, city, state, zip, country);
				Store s = new Store(storeCode, managerCode, a);
				stores.add(s);
			}
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}
		return stores;
	}
	/**
	 * generates list of items from the database
	 */
	public CustomList<Item> generateItem() {
		CustomList<Item> items = new CustomList<>();
		String query = "select referenceCode, name,basePrice,itemType from ItemTemplate;";

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				//Data From Query
				String itemCode = rs.getString("referenceCode");
				String name = rs.getString("name");
				double price = rs.getDouble("basePrice");
				String type = rs.getString("itemType");

				//Construction
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
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}
		return items;
	}
	/**
	 * generates list of Sales from the database, cmp is how to sort sales
	 */
	public CustomList<Sale> generateSale(CustomList<Item> items, CustomList<Customer> customers, CustomList<Employee> employees,CustomList<Store> stores,Comparator<Sale> cmp) {
		CustomList<Sale> sales = new CustomList<Sale>(cmp);
		Item type;
		double subtotal, tax;
		String query = "select s.saleId,s.referenceCode as saleCode, st.referenceCode as storeCode, p.referenceCode as customerCode  , emp.referenceCode as employeeCode "
				+ "from Sale s join Store st on  st.storeId = s.storeId" + " join Person p on s.customerId = p.personId"
				+ " join ( select ep.employeeId,p.referenceCode from EmployeePerson ep"
				+ " join Person p on p.personId = ep.personId) emp on emp.employeeId = s.employeeId;";

		try {
			ps = conn.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				//Data From Query
				subtotal = 0;
				tax = 0;
				String saleCode = rs.getString("saleCode");
				String storeCode = rs.getString("storeCode");
				String customerCode = rs.getString("customerCode");
				String employeeCode = rs.getString("employeeCode");
				int saleId = rs.getInt("saleId");
				
				//Second Query for Items
				CustomList<Purchased> cart = new CustomList<Purchased>();
				query = "select it.referenceCode as itemCode,si.amount,si.quantity,si.beginDate,si.endDate,emp.referenceCode as serviceEmployee, si.numOfHours from Sale  s "
						+ "join SoldItem si on si.saleId = s.saleId "
						+ "join ItemTemplate it on it.itemTemplateId = si.itemTemplateId "
						+ "left join (select ep.employeeId,p.referenceCode from EmployeePerson ep join Person p on p.personId = ep.personId) emp on emp.employeeId = si.employeeId "
						+ "where s.saleId = ?;";
				ps = conn.prepareStatement(query);
				ps.setInt(1, saleId);
				ResultSet rs2 = ps.executeQuery();
				//Construction
				while (rs2.next()) {
					type = Item.checkCode(items, rs2.getString("itemCode"));
					if (type.getType().equals("PU") || type.getType().equals("PN")) {
						OrderedProduct specifiedType = new OrderedProduct((Product) type, rs2.getInt("quantity"));
						cart.add(specifiedType);
						subtotal += specifiedType.getSubTotal();
						tax += specifiedType.getTaxTotal();
					}
					if (type.getType().equals("PG")) {
						OrderedGiftCard specifiedType = new OrderedGiftCard((GiftCard) type, rs2.getDouble("amount"));
						cart.add(specifiedType);
						subtotal += specifiedType.getSubTotal();
						tax += specifiedType.getTaxTotal();
					}
					if (type.getType().equals("SB")) {
						OrderedSubscription specifiedType = new OrderedSubscription((Subscription) type,
								rs2.getString("beginDate"), rs2.getString("endDate"));
						cart.add(specifiedType);
						subtotal += specifiedType.getSubTotal();
						tax += specifiedType.getTaxTotal();
					}
					if (type.getType().equals("SV")) {
						String employeeName = Person.checkCode(employees, rs2.getString("serviceEmployee")).getName();
						 //                   
						OrderedService specifiedType = new OrderedService((Service) type,
								rs2.getString("serviceEmployee"), rs2.getDouble("numOfHours"), employeeName);
						cart.add(specifiedType);
						subtotal += specifiedType.getSubTotal();
						tax += specifiedType.getTaxTotal();
					}
				}
				subtotal = Sale.changeRound(subtotal);
				tax = Sale.changeRound(tax);
				Person customer = Person.checkCode(customers, customerCode);
				if(customer == null) {
					customer = Person.checkCode(employees,customerCode);
				}
				Person employee = Person.checkCode(employees,employeeCode);
				Sale s = new Sale(saleCode, storeCode, customer, employee, cart, subtotal, tax);
				s.runCustomerEmployeeDiscount(customers, employees);
				rs2.close();
				sales.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Sale.assignSalesToStores(stores, sales);
		Sale.assignSalesToEmployees(employees, sales);

		return sales;
	}

}
