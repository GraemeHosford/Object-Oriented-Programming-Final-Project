// Name: Graeme Hosford
// Student ID: R00147327
// OOP Final Project

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OrdersTab extends Tab {

	// Get references to the controller class and GUI objects
	private OrdersTabController controller;

	private Label errorLabel;

	private Label explainOrderInputLabel;
	private Label enterCustomerIDLabel;
	private Label enterProductIDLabel;
	private Label enterQuantityLabel;

	private TextField enterCustomerIDTextField;
	private TextField enterProductIDTextField;
	private TextField enterQuantityTextField;

	private Button cancelOrderButton;
	private Button addToOrderButton;
	private Button finishOrderButton;

	private Label explainOutputLabel;
	private TextField enterOrderID;
	private Button findOrder;

	private Label orderOutputOrderIDLabel;
	private Label orderOutputCustomerIDLabel;
	private Label orderOutputCustomerFirstNameLabel;
	private Label orderOutputCustomerLastNameLabel;
	private Label orderOutputCustomerAddressLabel;
	private Label orderOutputProductIDLabel;
	private Label orderOutputProductNameLabel;
	private Label orderOutputProductDescriptionLabel;
	private Label orderOutputProductPriceLabel;
	private Label orderOutputQuantityLabel;
	private Label orderOutputTotalPriceLabel;

	private Label orderOutputOrderIDDataLabel;
	private Label orderOutputCustomerIDDataLabel;
	private Label orderOutputCustomerFirstNameDataLabel;
	private Label orderOutputCustomerLastNameDataLabel;
	private Label orderOutputCustomerAddressDataLabel;
	private Label orderOutputProductIDDataLabel;
	private Label orderOutputProductNameDataLabel;
	private Label orderOutputProductDescriptionDataLabel;
	private Label orderOutputProductPriceDataLabel;
	private Label orderOutputQuantityDataLabel;
	private Label orderOutputTotalPriceDataLabel;

	private Button orderOutputNextOrderButton;
	private Button orderOutputPrevOrderButton;

	private ResultSet results;

	public OrdersTab() {

		// Set up basics
		controller = new OrdersTabController();

		setText("Orders");
		setClosable(false);

		GridPane grid = new GridPane();

		grid.add(setUpOrderInput(), 0, 0);
		grid.add(setUpGetOrder(), 0, 2);
		grid.add(setUpOrderOutput(), 0, 3);

		errorLabel = new Label();
		grid.add(errorLabel, 0, 4);

		setContent(grid);

	}

	/**
	 * This method sets up the GUI for the input of Orders
	 * @return A VBox object containing all necessary GUI
	 */
	private VBox setUpOrderInput() {

		explainOrderInputLabel = new Label("Click Add to Order to add items, when finished with order click Finish Order");
		enterCustomerIDLabel = new Label("Enter Customer ID: ");
		enterProductIDLabel = new Label("Enter Product ID: ");
		enterQuantityLabel = new Label("Enter Product Quantity: ");

		enterCustomerIDTextField = new TextField();
		enterProductIDTextField = new TextField();
		enterQuantityTextField = new TextField();

		cancelOrderButton = new Button("Cancel Order");
		cancelOrderButton.setOnAction(e -> cancelOrder());

		addToOrderButton = new Button("Add to Order");
		addToOrderButton.setOnAction(e -> addToOrder());

		finishOrderButton = new Button("Finish Order");
		finishOrderButton.setOnAction(e -> saveOrder());

		HBox custIDLine = new HBox();
		custIDLine.getChildren().addAll(enterCustomerIDLabel, enterCustomerIDTextField);

		HBox prodIDLine = new HBox();
		prodIDLine.getChildren().addAll(enterProductIDLabel, enterProductIDTextField);

		HBox quantityLine = new HBox();
		quantityLine.getChildren().addAll(enterQuantityLabel, enterQuantityTextField);

		HBox buttons = new HBox();
		buttons.getChildren().addAll(cancelOrderButton, addToOrderButton, finishOrderButton);

		VBox orderInputBox = new VBox();
		orderInputBox.getChildren().addAll(explainOrderInputLabel, custIDLine, prodIDLine, quantityLine, buttons);

		orderInputBox.setPadding(new Insets(8, 8, 8, 8));

		return orderInputBox;
	}

	/**
	 * This method sets up the GUI for getting an Order
	 * @return VBox object containing all necessary GUI
	 */
	private VBox setUpGetOrder() {

		explainOutputLabel = new Label("Enter a specific ID or enter 0 to get all results. Input can't be negative");
		enterOrderID = new TextField();
		findOrder = new Button("Find Order");
		findOrder.setOnAction(e -> findOrder());

		VBox findOrderBox = new VBox();
		findOrderBox.getChildren().addAll(explainOutputLabel, enterOrderID, findOrder);

		return findOrderBox;
	}

	/**
	 * Sets up GUI for showing Order data
	 * @return VBox containing necessary GUI
	 */
	private VBox setUpOrderOutput() {

		orderOutputOrderIDLabel = new Label("Order ID: ");
		orderOutputOrderIDDataLabel = new Label();

		HBox orderIDLine = new HBox();
		orderIDLine.getChildren().addAll(orderOutputOrderIDLabel, orderOutputOrderIDDataLabel);

		orderOutputCustomerIDLabel = new Label("Customer ID: ");
		orderOutputCustomerIDDataLabel = new Label();

		HBox custIDLine = new HBox();
		custIDLine.getChildren().addAll(orderOutputCustomerIDLabel, orderOutputCustomerIDDataLabel);

		HBox mainOrderInfoBox = new HBox();
		mainOrderInfoBox.getChildren().addAll(orderIDLine, custIDLine);

		orderOutputCustomerFirstNameLabel = new Label("Customer's First Name: ");
		orderOutputCustomerFirstNameDataLabel = new Label();

		HBox custFirstNameLine = new HBox();
		custFirstNameLine.getChildren().addAll(orderOutputCustomerFirstNameLabel, orderOutputCustomerFirstNameDataLabel);

		orderOutputCustomerLastNameLabel = new Label("Customer's Last Name: ");
		orderOutputCustomerLastNameDataLabel = new Label();

		HBox custLastNameLine = new HBox();
		custLastNameLine.getChildren().addAll(orderOutputCustomerLastNameLabel, orderOutputCustomerLastNameDataLabel);

		orderOutputCustomerAddressLabel = new Label("Customer's Address: ");
		orderOutputCustomerAddressDataLabel = new Label();

		HBox custAddressLine = new HBox();
		custAddressLine.getChildren().addAll(orderOutputCustomerAddressLabel, orderOutputCustomerAddressDataLabel);

		orderOutputProductIDLabel = new Label("Product ID: ");
		orderOutputProductIDDataLabel = new Label();

		HBox prodIDLine = new HBox();
		prodIDLine.getChildren().addAll(orderOutputProductIDLabel, orderOutputProductIDDataLabel);

		orderOutputProductNameLabel = new Label("Product Name: ");
		orderOutputProductNameDataLabel = new Label();

		HBox prodNameLine = new HBox();
		prodNameLine.getChildren().addAll(orderOutputProductNameLabel, orderOutputProductNameDataLabel);

		orderOutputProductDescriptionLabel = new Label("Product Description: ");
		orderOutputProductDescriptionDataLabel = new Label();

		HBox prodDescLine = new HBox();
		prodDescLine.getChildren().addAll(orderOutputProductDescriptionLabel, orderOutputProductDescriptionDataLabel);

		orderOutputProductPriceLabel = new Label("Product Price: ");
		orderOutputProductPriceDataLabel = new Label();

		HBox prodPriceLine = new HBox();
		prodPriceLine.getChildren().addAll(orderOutputProductPriceLabel, orderOutputProductPriceDataLabel);

		orderOutputQuantityLabel = new Label("Quantity: ");
		orderOutputQuantityDataLabel = new Label();

		HBox quantityLine = new HBox();
		quantityLine.getChildren().addAll(orderOutputQuantityLabel, orderOutputQuantityDataLabel);

		orderOutputTotalPriceLabel = new Label("Total Price (€): ");
		orderOutputTotalPriceDataLabel = new Label();

		HBox totalPriceLine = new HBox();
		totalPriceLine.getChildren().addAll(orderOutputTotalPriceLabel, orderOutputTotalPriceDataLabel);

		orderOutputNextOrderButton = new Button("Next");
		orderOutputNextOrderButton.setOnAction(e -> nextOrder());
		orderOutputNextOrderButton.setDisable(true);

		orderOutputPrevOrderButton = new Button("Previous");
		orderOutputPrevOrderButton.setOnAction(e -> prevOrder());
		orderOutputPrevOrderButton.setDisable(true);

		HBox buttons = new HBox();
		buttons.getChildren().addAll(orderOutputPrevOrderButton, orderOutputNextOrderButton);

		VBox orderOutputBox = new VBox();
		orderOutputBox.getChildren().addAll(mainOrderInfoBox, 
				custFirstNameLine, custLastNameLine, custAddressLine, prodIDLine, prodNameLine, 
				prodDescLine, prodPriceLine, quantityLine, totalPriceLine, buttons);

		return orderOutputBox;
	}

	/**
	 * Cancels an order that the user is in the process of making
	 */
	private void cancelOrder() {

		enterCustomerIDTextField.setDisable(false);
		enterCustomerIDTextField.setText("");
		enterProductIDTextField.setText("");
		enterQuantityTextField.setText("");

		controller.cancelOrder();
	}

	/**
	 * Adds an additional Order object to the order currently in progress
	 */
	private void addToOrder() {

		int returnValue = 0;

		if(InputHelper.isInteger(enterCustomerIDTextField.getText()) && 
				InputHelper.isInteger(enterProductIDTextField.getText()) && 
				InputHelper.isInteger(enterQuantityTextField.getText())) {

			enterCustomerIDTextField.setDisable(true);

			returnValue = controller.addToOrder(Integer.parseInt(enterCustomerIDTextField.getText()), 
					Integer.parseInt(enterProductIDTextField.getText()), 
					Integer.parseInt(enterQuantityTextField.getText()));

			if(returnValue == 1) {
				errorLabel.setText("Added to order successfully");
			} else {
				cancelOrder();
				errorLabel.setText("Either the customer ID or product ID you entered does not exist.");
			}

		} else {
			errorLabel.setText("An error has occurred. Please check all fields have numeric inputs");
		}

	}

	/**
	 * Saves an Order to the database
	 */
	private void saveOrder() {

		enterCustomerIDTextField.setDisable(false);
		enterCustomerIDTextField.setText("");
		enterProductIDTextField.setText("");
		enterQuantityTextField.setText("");

		controller.saveOrder();
	}

	/**
	 * Retrieves an Order from the database
	 */
	private void findOrder() {

		if(InputHelper.isInteger(enterOrderID.getText()) ) {

			results = controller.getOrder(Integer.parseInt(enterOrderID.getText()));

			if(results != null) {
				nextOrder();
				orderOutputNextOrderButton.setDisable(false);
				orderOutputPrevOrderButton.setDisable(false);
			}

		} else {
			errorLabel.setText("Please ensure that the textfield has a numeric input");
		}
	}

	/**
	 * Moves to the next piece of order data when the next button is clicked
	 */
	private void nextOrder() {

		try {

			if(results.next()) {

				int quantity = results.getInt(10);
				double unitPrice = results.getDouble(9);
				double totalPrice = quantity * unitPrice;

				orderOutputOrderIDDataLabel.setText(String.valueOf(results.getInt(1)));
				orderOutputCustomerIDDataLabel.setText(String.valueOf(results.getInt(2)));
				orderOutputCustomerFirstNameDataLabel.setText(results.getString(3));
				orderOutputCustomerLastNameDataLabel.setText(results.getString(4));
				orderOutputCustomerAddressDataLabel.setText(results.getString(5));
				orderOutputProductIDDataLabel.setText(results.getString(6));
				orderOutputProductNameDataLabel.setText(results.getString(7));
				orderOutputProductDescriptionDataLabel.setText(results.getString(8));
				orderOutputProductPriceDataLabel.setText(String.valueOf(unitPrice));
				orderOutputQuantityDataLabel.setText(String.valueOf(quantity));

				orderOutputTotalPriceDataLabel.setText(String.valueOf(totalPrice));

			} else {
				results.previous();
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Moves to the previous piece of order data when the previous button is clicked
	 */
	private void prevOrder() {

		try {

			if(results.previous()) {

				int quantity = results.getInt(10);
				double unitPrice = results.getDouble(9);
				double totalPrice = quantity * unitPrice;

				orderOutputOrderIDDataLabel.setText(String.valueOf(results.getInt(1)));
				orderOutputCustomerIDDataLabel.setText(String.valueOf(results.getInt(2)));
				orderOutputCustomerFirstNameDataLabel.setText(results.getString(3));
				orderOutputCustomerLastNameDataLabel.setText(results.getString(4));
				orderOutputCustomerAddressDataLabel.setText(results.getString(5));
				orderOutputProductIDDataLabel.setText(results.getString(6));
				orderOutputProductNameDataLabel.setText(results.getString(7));
				orderOutputProductDescriptionDataLabel.setText(results.getString(8));
				orderOutputProductPriceDataLabel.setText(String.valueOf(unitPrice));
				orderOutputQuantityDataLabel.setText(String.valueOf(quantity));

				orderOutputTotalPriceDataLabel.setText(String.valueOf(totalPrice));

			} else {
				results.next();
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
