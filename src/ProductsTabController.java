// Name: Graeme Hosford
// Student ID: R00147327
// OOP Final Project

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductsTabController {

	private Connection connection;
	private static int beginningID;

	public ProductsTabController() {
		Database db = Database.newInstance();
		connection = db.getConnection();
		beginningID = getBeginningID();
	}

	/**
	 * Gets to ID to begin working with
	 * @return The ID to begin adding to the database with
	 */
	private int getBeginningID() {

		int ID = 0;

		try {

			String getID = "SELECT MAX(productid) FROM product";

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
	 * Saves a products data to the database
	 * @param productName The products name
	 * @param prodDescription The products description
	 * @param productPrice the price of the product
	 * @return a response code with 1 for success or -1 for failure
	 */
	public int saveProductInfo(String productName, String prodDescription, double productPrice) {

		int returnValue = -1;

		if(productName.length() > 40 || prodDescription.length() > 50) {
			return returnValue;
		}

		try {
			String insert = "INSERT INTO product VALUES (?, ?, ?, ?)";

			PreparedStatement insertCustomer = connection.prepareStatement(insert);

			int prodid = beginningID;

			insertCustomer.setInt(1, prodid);
			insertCustomer.setString(2, productName);
			insertCustomer.setString(3,  prodDescription);
			insertCustomer.setDouble(4,  productPrice);

			returnValue = insertCustomer.executeUpdate();

			beginningID++;

		} catch(SQLException e) {
			e.printStackTrace();
		}

		return returnValue;
	}

	/**
	 * Retrieves data from the database
	 * @param productID The productID to search for, or 0 for all data
	 * @return A ResultSet object containing all retrieved data
	 */
	public ResultSet getProducts(int productID) {

		ResultSet results = null;

		if(productID < 0) {
			return null;
		}

		if(productID >= 0) {
			try {

				String query = "SELECT * FROM product";

				if(productID != 0) {
					query += " WHERE productid = ?";
				}

				PreparedStatement statement = connection.prepareStatement(query, 
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

				if(productID != 0) {
					statement.setInt(1,  productID);
				}

				results = statement.executeQuery();

			} catch(SQLException e) {
				e.printStackTrace();
			}

		}

		return results;
	}

}
