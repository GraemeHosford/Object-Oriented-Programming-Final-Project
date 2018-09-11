// Name: Graeme Hosford
// Student ID: R00147327
// OOP Final Project

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerTabController {

	private Connection connection;

	private static int beginningID;

	public CustomerTabController() {
		Database db = Database.newInstance();
		connection = db.getConnection();
		beginningID = getBeginningID();
	}

	/**
	 * This method retrieves the newest ID in the customer table which 
	 * then tells what the next ID should be
	 * @return The next free ID to use in the customer table
	 */
	private int getBeginningID() {

		int ID = 0;

		try {

			String getID = "SELECT MAX(custid) FROM customer";

			Statement state = connection.createStatement();

			ResultSet result = state.executeQuery(getID);
			result.next();
			ID = result.getInt(1) + 1;

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return ID;
	}

	/**
	 * This method inserts a new customer to the customer table
	 * @param fname The first name of the customer
	 * @param lname The last name of the customer
	 * @param address The customers address
	 * @return
	 */
	public int insertCustomer(String fname, String lname, String address) {

		int returnValue = -1;

		if(fname.length() > 25 || 
				lname.length() > 25 || address.length() > 50) {
			return returnValue;
		}

		try {
			String insert = "INSERT INTO customer VALUES (?, ?, ?, ?)";

			PreparedStatement insertCustomer = connection.prepareStatement(insert);

			int custid = beginningID;

			insertCustomer.setInt(1, custid);
			insertCustomer.setString(2, fname);
			insertCustomer.setString(3,  lname);
			insertCustomer.setString(4,  address);

			returnValue = insertCustomer.executeUpdate();

			beginningID++;

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return returnValue;
	}

	/**
	 * This methods retrieves data from the customer table
	 * @param custid The ID of the requested customer, or 0 for all customer data
	 * @return A ResultSet containing the requested data
	 */
	public ResultSet getCustomerData(int custid) {

		ResultSet results = null;

		if(custid < 0) {
			return null;
		}

		try {

			String selectCust = "SELECT * FROM customer";

			if(custid > 0) {
				selectCust += " WHERE custid = ?";
			}

			PreparedStatement prepGetCust = connection.prepareStatement(selectCust, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			if(custid > 0) {
				prepGetCust.setInt(1, custid);
			}

			results = prepGetCust.executeQuery();

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return results;
	}

}
