// Name: Graeme Hosford
// Student ID: R00147327
// OOP Final Project

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomersTab extends Tab {

	// Make references to the controller class as well as all the GUI elements which will be used
	private CustomerTabController controller;

	private Label errorLabel;

	private Label firstName;
	private Label lastName;
	private Label address;
	private Label custIDLabel;
	private Label custFirstNameLabel;
	private Label custLastNameLabel;
	private Label custAddressLabel;
	private Label custIDData;
	private Label custFirstNameData;
	private Label custLastNameData;
	private Label custAddressData;

	private TextField enterFirstName;
	private TextField enterLastName;
	private TextField enterAddress;

	private Label explainOutputLabel;
	private TextField enterCustomerID;

	private Button addCustomer;
	private Button showCustomers;
	private Button nextCustomer;
	private Button prevCustomer;

	private ResultSet results;

	public CustomersTab() {

		// Initialise the basics of this tab
		setText("Customers");
		setClosable(false);

		controller = new CustomerTabController();

		GridPane grid = new GridPane();

		grid.add(setUpNewCustomerInput(), 0, 1);

		grid.add(setUpGetCustomerInput(), 0, 3);

		grid.add(setUpDataDisplay(), 0, 5);

		errorLabel = new Label();
		grid.add(errorLabel, 0, 6);

		setContent(grid);

	}

	/**
	 * This method sets up the input required for making a new customer
	 * @return A VBox which contains all GUI elements needed for customer input
	 */
	private VBox setUpNewCustomerInput() {

		HBox firstNameLine = new HBox();
		firstName = new Label("First Name:");
		enterFirstName = new TextField();
		firstNameLine.getChildren().addAll(firstName, enterFirstName);

		HBox lastNameLine = new HBox();
		lastName = new Label("Last Name:");
		enterLastName = new TextField();
		lastNameLine.getChildren().addAll(lastName, enterLastName);

		HBox addressLine = new HBox();
		address = new Label("Address:");
		enterAddress = new TextField();
		addressLine.getChildren().addAll(address, enterAddress);

		addCustomer = new Button("Add Customer");
		addCustomer.setOnAction(e -> addCustomer());

		VBox enterFields = new VBox();
		enterFields.getChildren().addAll(firstNameLine, lastNameLine, addressLine, addCustomer);

		return enterFields;
	}

	/**
	 * Adds a new customer to the database
	 */
	private void addCustomer() {

		if(!enterFirstName.getText().equals("") && 
				!enterLastName.getText().equals("") && 
				!enterAddress.getText().equals("")) {

			int returnVal = controller.insertCustomer(enterFirstName.getText(), 
					enterLastName.getText(), enterAddress.getText());

			if(returnVal == -1) {
				errorLabel.setText("The input on one of the fields is too long");
			}

		} else {
			errorLabel.setText("Make sure all customer fields have input in them");
		}
	}

	/**
	 * This methods sets up the GUI elements needed for retrieving customer data from thee database
	 * @return
	 */
	private VBox setUpGetCustomerInput() {

		explainOutputLabel = new Label("Enter a specific ID or enter 0 to get all results");
		enterCustomerID = new TextField();
		showCustomers = new Button("Show Customer(s)");
		showCustomers.setOnAction(e -> showCustomerData());

		VBox getCustLayout = new VBox();

		getCustLayout.getChildren().addAll(explainOutputLabel, enterCustomerID, showCustomers);

		return getCustLayout;
	}

	/**
	 * This methods sets up the GUI elements needed for viewing customer data
	 * @return
	 */
	private VBox setUpDataDisplay() {

		custIDLabel = new Label("Customer's ID:");

		custFirstNameLabel = new Label("Customer's First Name:");

		custLastNameLabel = new Label("Customer's Last Name:");

		custAddressLabel = new Label("Customer's Address");

		custIDData = new Label();
		custFirstNameData = new Label();
		custLastNameData = new Label();
		custAddressData = new Label();

		nextCustomer = new Button("Next");
		nextCustomer.setOnAction(e -> showNextCustomer());
		nextCustomer.setDisable(true);

		prevCustomer = new Button("Previous");
		prevCustomer.setOnAction(e -> showPrevCustomer());
		prevCustomer.setDisable(true);

		HBox custIDLine = new HBox();
		custIDLine.getChildren().addAll(custIDLabel, custIDData);

		HBox custFirstNameLine = new HBox();
		custFirstNameLine.getChildren().addAll(custFirstNameLabel, custFirstNameData);

		HBox custLastNameLine = new HBox();
		custLastNameLine.getChildren().addAll(custLastNameLabel, custLastNameData);

		HBox custAddressLine = new HBox();
		custAddressLine.getChildren().addAll(custAddressLabel, custAddressData);

		HBox buttons = new HBox();
		buttons.getChildren().addAll(prevCustomer, nextCustomer);

		VBox custInfoBox = new VBox();
		custInfoBox.getChildren().addAll(custIDLine, custFirstNameLine, 
				custLastNameLine, custAddressLine, buttons);

		return custInfoBox;
	}

	/**
	 * This methods calls on the controller class to retrieve customer data from the
	 * database and then shows that data to the user
	 */
	private void showCustomerData() {

		if(InputHelper.isInteger(enterCustomerID.getText())) {

			results = controller.getCustomerData(Integer.parseInt(enterCustomerID.getText()));

			if(results != null) {
				nextCustomer.setDisable(false);
				prevCustomer.setDisable(false);
				showNextCustomer();
			}

		} else {
			errorLabel.setText("Make sure there is a numberic value in the customerID field");
		}
	}

	/**
	 * This method is called when the next button is clicked and show the next result
	 */
	private void showNextCustomer() {

		try {

			if(results.next()) {

				custIDData.setText(String.valueOf(results.getInt(1)));
				custFirstNameData.setText(results.getString(2));
				custLastNameData.setText(results.getString(3));
				custAddressData.setText(results.getString(4));

			} else {
				results.previous();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method is called when the previous button is clicked and shows the previous result
	 */
	private void showPrevCustomer() {

		try {

			if(results.previous()) {

				custIDData.setText(String.valueOf(results.getInt(1)));
				custFirstNameData.setText(results.getString(2));
				custLastNameData.setText(results.getString(3));
				custAddressData.setText(results.getString(4));

			} else {
				results.next();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}