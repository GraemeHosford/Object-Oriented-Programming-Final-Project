// Name: Graeme Hosford
// Student ID: R00147327
// OOP Final Project

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private static final String DRIVER  = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String DATABASE_NAME = "R00147327 - FinalOOPDB";

	private static Connection connection;

	private static Database instance;

	private boolean dbCreated = false;

	private Database() {}

	/**
	 * This method works on the singleton pattern and ensures that 
	 * only one instance of this database is active at any given time
	 * @return The instance of this class
	 */
	public static Database newInstance() {

		if(instance == null) {
			instance = new Database();
		}

		return instance;
	}

	/**
	 * This methods handles the creation of the database
	 */
	public void createDatabase() {

		if(!dbCreated) {

			try {
				Class.forName(DRIVER).newInstance();
				connection = DriverManager.getConnection("jdbc:derby:" + DATABASE_NAME + 
						";create=true");

				connection.setCatalog(DATABASE_NAME);

				createProductTable();
				createCustomerTable();
				createOrderTable();

			} catch(Exception e) {

				/*
				 * Because Derby does not support the 'CREATE TABLE IF NOT EXISTS' command
				 * this piece of code checks to see if the error code being thrown is
				 * the same as the code for trying to create a duplicate table and if this
				 * is the case it is ignored. Action is only taken when this is not the case
				 */
				if(e instanceof SQLException) {
					SQLException exc = (SQLException) e;

					if(!exc.getSQLState().equals("X0Y32")) {
						e.printStackTrace();
						System.exit(0);
					}
				}
			}

			dbCreated = true;
		}

	}

	/**
	 * This method creates the product table in the database
	 * @throws SQLException An exception is thrown if an SQL related error occurs
	 */
	private void createProductTable() throws SQLException {

		String productTable = "CREATE TABLE product (productid INTEGER NOT NULL,"
				+ " productname VARCHAR(40) NOT NULL, description VARCHAR(50) NOT NULL,"
				+ " price DOUBLE NOT NULL,"
				+ " PRIMARY KEY (productid))";

		Statement createProductTable = connection.createStatement();

		createProductTable.execute(productTable);

		createProductTable.close();

	}

	/**
	 * This method creates the customer table in the database
	 * @throws SQLException thrown if an error occurs
	 */
	private void createCustomerTable() throws SQLException {

		String customerTable = "CREATE TABLE customer (custid INTEGER NOT NULL,"
				+ " fname VARCHAR(25) NOT NULL,"
				+ " lname VARCHAR(25) NOT NULL,"
				+ " address VARCHAR(50) NOT NULL, PRIMARY KEY (custid))";

		Statement createCustomerTable = connection.createStatement();

		createCustomerTable.execute(customerTable);

		createCustomerTable.close();
	}

	/**
	 * Creates the Orders table in the database
	 * @throws SQLException Thrown if an SQL error occurs
	 */
	private void createOrderTable() throws SQLException {

		String orderTable = "CREATE TABLE orders (orderid INTEGER NOT NULL,"
				+ " custid INTEGER NOT NULL,"
				+ " productid INTEGER NOT NULL,"
				+ " quantity INTEGER NOT NULL,"
				+ " FOREIGN KEY (custid) REFERENCES customer(custid) ON DELETE RESTRICT,"
				+ " FOREIGN KEY (productid) REFERENCES product(productid) ON DELETE RESTRICT,"
				+ " PRIMARY KEY (orderid, custid, productid, quantity))";

		Statement createOrderTable = connection.createStatement();

		createOrderTable.execute(orderTable);

		createOrderTable.close();

	}

	/**
	 * Gets the connection to the database
	 * @return the connection to the database
	 */
	public Connection getConnection() {
		return connection;
	}

}
