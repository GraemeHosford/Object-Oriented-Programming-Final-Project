// Name: Graeme Hosford
// Student ID: R00147327
// OOP Final Project

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrdersTabController {

	private Connection connection;
	private static int beginningID;

	private ArrayList<Order> order;

	public OrdersTabController() {
		Database db = Database.newInstance();
		connection = db.getConnection();
		order = new ArrayList<>();
		beginningID = getBeginningID();
	}

	/**
	 * Gets the ID to begin with in the database
	 * @return The ID to begin with in the database
	 */
	private int getBeginningID() {

		int ID = 0;

		try {

			String getID = "SELECT MAX(productid) FROM orders";

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
	 * Adds an Order object to the ArrayList of orders 
	 * which can then be persisted to the database
	 * @param custID The Customer ID to be used in the Order
	 * @param prodID The Product ID to be used in the Order
	 * @param quantity The Quantity of Product to be used in the Order
	 * @return A response code based on the input, 1 for success or -1 for a failure
	 */
	public int addToOrder(int custID, int prodID, int quantity) {

		boolean custIDExists = checkIDExists("custid", "customer", custID);
		boolean productIDExists = checkIDExists("productid", "product", prodID);

		if(custIDExists && productIDExists) {
			Order newOrderPart = new Order(custID, prodID, quantity);
			order.add(newOrderPart);
			return 1;
		}

		return -1;
	}

	/**
	 * This is a helper method which checks the users ID input for an Order to make
	 * sure these ID's exist
	 * @param columnName The column to check for data
	 * @param tableName The table to check for data
	 * @param id The ID to search for
	 * @return true if the ID exists in the given table, false if not
	 */
	private boolean checkIDExists(String columnName, String tableName, int id) {

		String checkIDExistsSQL = "SELECT " + columnName + " FROM " + tableName + 
				" WHERE " + columnName + " = ?";

		try {

			PreparedStatement prepStatement = connection.prepareStatement(checkIDExistsSQL);
			prepStatement.setInt(1, id);

			ResultSet result = prepStatement.executeQuery();

			if(result.next()) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * This method takes anything in the orders ArrayList and saves the data to the Orders table
	 */
	public void saveOrder() {

		if(order.size() > 0) {

			String saveOrder = "INSERT INTO orders VALUES (?, ?, ?, ?)";

			try {

				PreparedStatement statement = connection.prepareStatement(saveOrder);

				for(Order o : order) {

					statement.setInt(1, beginningID);
					statement.setInt(2, o.getCustID());
					statement.setInt(3, o.getProdID());
					statement.setInt(4, o.getQuantity());

					statement.execute();
				}

				beginningID++;
				order.clear();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * This method removes anything in the orders ArrayList
	 */
	public void cancelOrder() {
		order.clear();
	}

	/**
	 * This method gets any requested data from the Orders table
	 * @param orderID The OrderID to search for
	 * @return A ResultSet containing any requested data
	 */
	public ResultSet getOrder(int orderID) {

		ResultSet results = null;

		if(orderID < 0) {
			return null;
		}

		String getOrderString = "SELECT o.orderid, c.custid, c.fname, c.lname, c.address,"
				+ " p.productid, p.productname, p.description, p.price, o.quantity FROM orders o, customer c, "
				+ "product p WHERE o.productid = p.productid AND o.custid = c.custid";

		if(orderID != 0) {
			getOrderString += " AND o.orderid = ?";
		}

		getOrderString += " ORDER BY o.orderid";

		try {

			PreparedStatement statement = connection.prepareStatement(getOrderString, 
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			if(orderID != 0) {
				statement.setInt(1,  orderID);
			}

			results = statement.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return results;
	}
}
