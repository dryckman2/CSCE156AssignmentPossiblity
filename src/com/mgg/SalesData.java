package com.mgg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database interface class
 */
public class SalesData {

	private static final String USER = "dryckman";
	private static final String PASSWORD = "3pF-my";
	private static final String URL = "jdbc:mysql://cse.unl.edu/dryckman?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	/**
	 * Removes all sales records from the database.
	 */
	public static void removeAllSales() {
		// Connection Setup
		Connection conn = connSetUp();
		PreparedStatement ps = null;

		// SQL
		// SoldItem Needs to be drop and recreated to all Sale to be truncated
		String deleteItem = "delete from SoldItem;";
		String deleteSale = "delete from Sale;";
		// Execution
		try {
			ps = conn.prepareStatement(deleteItem);
			ps.executeUpdate();
			ps = conn.prepareStatement(deleteSale);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Removes the single sales record associated with the given
	 * <code>saleCode</code>
	 * 
	 * @param saleCode
	 */
	public static void removeSale(String saleCode) {
		// Connection Setup
		Connection conn = connSetUp();
		PreparedStatement ps = null;
		ResultSet rs = null;

		// SQL
		String idQuery = "select saleId from Sale where referenceCode = ?;";
		String selectsoldItem = "select soldItemId from SoldItem where saleId = ?;";
		String deleteSoldItem = "delete from SoldItem where soldItemId = ?;";
		String delete = "delete from Sale where saleId = ?";

		// Execution
		try {
			// Gets id for sale
			ps = conn.prepareStatement(idQuery);
			ps.setString(1, saleCode);
			rs = ps.executeQuery();
			rs.next();
			int id = rs.getInt("saleId");
			// Gets and removes items of sale
			ps = conn.prepareStatement(selectsoldItem);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				ps = conn.prepareStatement(deleteSoldItem);
				ps.setInt(1, rs.getInt("soldItemId"));
				ps.executeUpdate();
			}

			// Sale Deletion
			ps = conn.prepareStatement(delete);
			ps.setInt(1, id);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			ps.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Clears all tables of the database of all records.
	 */
	public static void clearDatabase() {
		// Connection Setup
		Connection conn = connSetUp();
		PreparedStatement ps = null;

		// SQL
		String[] deleteAll = { "delete from SoldItem;", "delete from ItemTemplate;", "delete from Sale;",
				"delete from Store;", "delete from Email;", "delete from EmployeePerson;", "delete from Person;",
				"delete from Address;", "delete from State;", "delete from Country;" };

		// Execution
		try {
			for (String deleteT : deleteAll) {
				ps = conn.prepareStatement(deleteT);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to add a person record to the database with the provided data. The
	 * <code>type</code> will be one of "E", "G", "P" or "C" depending on the type
	 * (employee or type of customer).
	 * 
	 * @param personCode
	 * @param type
	 * @param firstName
	 * @param lastName
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addPerson(String personCode, String type, String firstName, String lastName, String street,
			String city, String state, String zip, String country) {
		// Connection Setup
		Connection conn = connSetUp();
		PreparedStatement ps = null;
		ResultSet rs = null;

		// SQL
		String create = "insert into Person (referenceCode,personType,lastName,firstName,addressId) values(?,?,?,?,?);";

		// Execution
		int addressId = pingAddress(street, city, state, zip, country, conn);
		try {
			ps = conn.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, personCode);
			ps.setString(2, type);
			ps.setString(3, lastName);
			ps.setString(4, firstName);
			ps.setInt(5, addressId);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			rs.next();
			int personId = rs.getInt(1);
			if (type.equals("E")) {
				makePersonIdEmp(personId, conn);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			rs.close();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adds an email record corresponding person record corresponding to the
	 * provided <code>personCode</code>
	 * 
	 * @param personCode
	 * @param email
	 */
	public static void addEmail(String personCode, String email) {
		// Connection Setup
		Connection conn = connSetUp();
		PreparedStatement ps = null;

		// SQL
		String create = "insert into Email (emailAddress,personId) values (?,?);";

		// Execution
		try {
			int personId = getPerson(personCode, conn);
			ps = conn.prepareStatement(create);
			ps.setString(1, email);
			ps.setInt(2, personId);
			;
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adds a store record to the database managed by the person identified by the
	 * given code.
	 * 
	 * @param storeCode
	 * @param managerCode
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 */
	public static void addStore(String storeCode, String managerCode, String street, String city, String state,
			String zip, String country) {
		// Connection Setup
		Connection conn = connSetUp();
		PreparedStatement ps = null;

		// SQL
		String create = "insert into Store (referenceCode,addressId,managerId) values (?,?,?);";

		// Execution
		int addressId = pingAddress(street, city, state, zip, country, conn);
		int personId = getPerson(managerCode, conn);
		int empId = personIdToEmpID(personId, conn);
		try {
			ps = conn.prepareStatement(create);
			ps.setString(1, storeCode);
			ps.setInt(2, addressId);
			ps.setInt(3, empId);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a sales item (product, service, subscription) record to the database
	 * with the given <code>name</code> and <code>basePrice</code>. The type of item
	 * is specified by the <code>type</code> which may be one of "PN", "PU", "PG",
	 * "SV", or "SB". These correspond to new products, used products, gift cards
	 * (for which <code>basePrice</code> will be <code>null</code>), services, and
	 * subscriptions.
	 * 
	 * @param itemCode
	 * @param type
	 * @param name
	 * @param basePrice
	 */
	public static void addItem(String itemCode, String type, String name, Double basePrice) {
		// Connection Setup
		Connection conn = connSetUp();
		PreparedStatement ps = null;

		// SQL
		String create = "insert into ItemTemplate (referenceCode,itemType,name,basePrice) values (?,?,?,?);";

		//BasePrice Fixing
		double newBasePrice;
		if(basePrice == null) {
			newBasePrice = -1;
		}else {
			newBasePrice = basePrice;
		}
		// Execution
		try {
			ps = conn.prepareStatement(create);
			ps.setString(1, itemCode);
			ps.setString(2, type);
			ps.setString(3, name);
			ps.setDouble(4, newBasePrice);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adds a sales record to the database with the given data.
	 * 
	 * @param saleCode
	 * @param storeCode
	 * @param customerCode
	 * @param salesPersonCode
	 */
	public static void addSale(String saleCode, String storeCode, String customerCode, String salesPersonCode) {
		// Connection Setup
		Connection conn = connSetUp();
		PreparedStatement ps = null;

		// SQL
		String create = "insert into Sale (referenceCode,storeId,customerId,employeeId) values (?,?,?,?);";

		// Execution
		try {
			ps = conn.prepareStatement(create);
			ps.setString(1, saleCode);
			ps.setInt(2, getStoreId(storeCode, conn));
			ps.setInt(3, getPerson(customerCode, conn));
			ps.setInt(4, personIdToEmpID(getPerson(salesPersonCode, conn), conn));
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Adds a particular product (new or used, identified by <code>itemCode</code>)
	 * to a particular sale record (identified by <code>saleCode</code>) with the
	 * specified quantity.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param quantity
	 */
	public static void addProductToSale(String saleCode, String itemCode, int quantity) {
		// Connection Setup
		Connection conn = connSetUp();
		PreparedStatement ps = null;

		// SQL
		String create = "insert into SoldItem(saleId,itemTemplateId,quantity) values(?,?,?);";

		// Execution
		try {
			ps = conn.prepareStatement(create);
			ps.setInt(1, getSaleId(saleCode, conn));
			ps.setInt(2, getItemId(itemCode, conn));
			ps.setInt(3, quantity);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a particular gift card (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) in the specified
	 * amount.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param amount
	 */
	public static void addGiftCardToSale(String saleCode, String itemCode, double amount) {
		// Connection Setup
		Connection conn = connSetUp();
		PreparedStatement ps = null;

		// SQL
		String create = "insert into SoldItem(saleId,itemTemplateId,amount) values(?,?,?);";

		// Execution
		try {
			ps = conn.prepareStatement(create);
			ps.setInt(1, getSaleId(saleCode, conn));
			ps.setInt(2, getItemId(itemCode, conn));
			ps.setDouble(3, amount);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a particular service (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) which will be
	 * performed by the given employee for the specified number of hours.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param employeeCode
	 * @param billedHours
	 */
	public static void addServiceToSale(String saleCode, String itemCode, String employeeCode, double billedHours) {
		// Connection Setup
		Connection conn = connSetUp();
		PreparedStatement ps = null;

		// SQL
		String create = "insert into SoldItem(saleId,itemTemplateId,employeeId,numofHours) values(?,?,?,?);";

		// Execution
		try {
			ps = conn.prepareStatement(create);
			ps.setInt(1, getSaleId(saleCode, conn));
			ps.setInt(2, getItemId(itemCode, conn));
			ps.setInt(3, personIdToEmpID(getPerson(employeeCode,conn),conn));
			ps.setDouble(4, billedHours);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds a particular subscription (identified by <code>itemCode</code>) to a
	 * particular sale record (identified by <code>saleCode</code>) which is
	 * effective from the <code>startDate</code> to the <code>endDate</code>
	 * inclusive of both dates.
	 * 
	 * @param saleCode
	 * @param itemCode
	 * @param startDate
	 * @param endDate
	 */
	public static void addSubscriptionToSale(String saleCode, String itemCode, String startDate, String endDate) {
		// Connection Setup
		Connection conn = connSetUp();
		PreparedStatement ps = null;

		// SQL
		String create = "insert into SoldItem(saleId,itemTemplateId,beginDate,endDate) values(?,?,?,?);";

		// Execution
		try {
			ps = conn.prepareStatement(create);
			ps.setInt(1, getSaleId(saleCode, conn));
			ps.setInt(2, getItemId(itemCode, conn));
			ps.setString(3, startDate);
			ps.setString(4, endDate);
			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Setup Connection to database
	private static Connection connSetUp() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	// Returns Id of address, Creates if does not exist
	// conn is passed to reduce needed connections
	private static int pingAddress(String street, String city, String state, String zip, String country,
			Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		// SQL
		String search = "select addressId from Address where street = ? and city = ? and zip = ? and stateId = ? and countryId = ?;";
		String create = "insert into Address (street,city,zip,stateId,countryId) values (?,?,?,?,?);";

		// Execution
		int addressId = -1;
		int stateId = pingState(state, conn);
		int countryId = pingCountry(country, conn);
		try {
			// Search
			ps = conn.prepareStatement(search);
			ps.setString(1, street);
			ps.setString(2, city);
			ps.setInt(3, Integer.parseInt(zip));
			ps.setInt(4, stateId);
			ps.setInt(5, countryId);
			rs = ps.executeQuery();
			if (rs.next()) {
				addressId = rs.getInt("addressId");
			} else {
				// On Fail Create
				ps = conn.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, street);
				ps.setString(2, city);
				ps.setInt(3, Integer.parseInt(zip));
				ps.setInt(4, stateId);
				ps.setInt(5, countryId);
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				rs.next();
				addressId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return addressId;
	}

	// Returns Id of state, Creates if does not exist
	// conn is passed to reduce needed connections
	private static int pingState(String state, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		// SQL
		String search = "select stateId from State where name = ?;";
		String create = "insert into State(name) values(?);";

		// Execution
		int stateId = -1;
		try {
			// Search
			ps = conn.prepareStatement(search);
			ps.setString(1, state);
			rs = ps.executeQuery();
			if (rs.next()) {
				stateId = rs.getInt("stateId");
			} else {
				// On Fail Create
				ps = conn.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, state);
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				rs.next();
				stateId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stateId;
	}

	// Returns Id of country, Creates if does not exist
	// conn is passed to reduce needed connections
	private static int pingCountry(String country, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		// SQL
		String search = "select countryId from Country where name = ?;";
		String create = "insert into Country(name) values(?);";

		// Execution
		int countryId = -1;
		try {
			// Search
			ps = conn.prepareStatement(search);
			ps.setString(1, country);
			rs = ps.executeQuery();
			if (rs.next()) {
				countryId = rs.getInt("countryId");
			} else {
				// On Fail Create
				ps = conn.prepareStatement(create, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, country);
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				rs.next();
				countryId = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return countryId;
	}

	// Assumes Person Exist; Returns ID
	private static int getPerson(String code, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String getPerson = "select personId from Person where referenceCode = ?";
		int personId = -1;
		try {
			ps = conn.prepareStatement(getPerson);
			ps.setString(1, code);
			rs = ps.executeQuery();
			rs.next();
			personId = rs.getInt("personId");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return personId;
	}

	// Takes a personId and returns their emp id
	private static int personIdToEmpID(int pid, Connection conn) {
		// Connection Setup
		PreparedStatement ps = null;
		ResultSet rs = null;

		// SQL
		String query = "select employeeId from EmployeePerson where personId = ?;";

		// Execution
		int eid = -1;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, pid);
			rs = ps.executeQuery();
			rs.next();
			eid = rs.getInt("employeeId");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return eid;
	}

	// Makes Person at personId into an Employee
	private static void makePersonIdEmp(int pid, Connection conn) {
		// Connection Setup
		PreparedStatement ps = null;

		// SQL
		String create = "insert into EmployeePerson(personId) values(?);";

		// Execution
		try {
			ps = conn.prepareStatement(create);
			ps.setInt(1, pid);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// Close on Finish
		try {
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//Gets store from code
	private static int getStoreId(String code, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String getStore = "select storeId from Store where referenceCode = ?";
		int storeId = -1;
		try {
			ps = conn.prepareStatement(getStore);
			ps.setString(1, code);
			rs = ps.executeQuery();
			rs.next();
			storeId = rs.getInt("storeId");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return storeId;
	}
	//Gets sale from code
	private static int getSaleId(String code, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String getSale = "select saleId from Sale where referenceCode = ?";
		int saleId = -1;
		try {
			ps = conn.prepareStatement(getSale);
			ps.setString(1, code);
			rs = ps.executeQuery();
			rs.next();
			saleId = rs.getInt("saleId");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return saleId;
	}
	//Gets item from code
	private static int getItemId(String code, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		String getItem = "select itemTemplateId from ItemTemplate where referenceCode = ?";
		int itemId = -1;
		try {
			ps = conn.prepareStatement(getItem);
			ps.setString(1, code);
			rs = ps.executeQuery();
			rs.next();
			itemId = rs.getInt("itemTemplateId");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return itemId;
	}
}
