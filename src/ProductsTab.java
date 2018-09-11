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

public class ProductsTab extends Tab {

	// Sets up controller and GUI elements
	private ProductsTabController controller;

	private Label errorLabel;

	private Label enterProdNameLabel;
	private Label enterProdDescriptionLabel;
	private Label enterProdPriceLabel;

	private TextField enterProductName;
	private TextField enterProductDescription;
	private TextField enterProductPrice;

	private Label explainProductOutputLabel;
	private TextField enterProductID;
	private Button getProducts;

	private Button enterProductInfo;

	private Label productIDResultLabel;
	private Label productNameResultLabel;
	private Label productDescriptionResultLabel;
	private Label productPriceResultLabel;

	private Label productIDResult;
	private Label productNameResult;
	private Label productDescriptionResult;
	private Label productPriceResult;

	private Button nextProduct;
	private Button prevProduct;

	private ResultSet results;

	public ProductsTab() {

		controller = new ProductsTabController();

		setText("Products");
		setClosable(false);

		GridPane grid = new GridPane();

		grid.add(setUpEnterProduct(), 0, 0);

		grid.add(setUpGetProductInput(), 0, 3);

		grid.add(setUpProductResultOutput(), 0, 5);

		errorLabel = new Label();
		grid.add(errorLabel, 0, 6);

		setContent(grid);
	}

	/**
	 * Sets up the GUI for entering a new product
	 * @return A VBox object containing all needed GUI
	 */
	private VBox setUpEnterProduct() {

		enterProdNameLabel = new Label("Product Name:");
		enterProdDescriptionLabel = new Label("Description:");
		enterProdPriceLabel = new Label("Price:");

		enterProductName = new TextField();
		enterProductDescription = new TextField();
		enterProductPrice = new TextField();

		enterProductInfo = new Button("Enter Product");
		enterProductInfo.setOnAction(e -> saveProduct());

		HBox prodNameLine = new HBox();
		prodNameLine.getChildren().addAll(enterProdNameLabel, enterProductName);

		HBox prodDescLine = new HBox();
		prodDescLine.getChildren().addAll(enterProdDescriptionLabel, enterProductDescription);

		HBox prodPriceLine = new HBox();
		prodPriceLine.getChildren().addAll(enterProdPriceLabel, enterProductPrice);

		VBox allProductInfo = new VBox();
		allProductInfo.getChildren().addAll(prodNameLine, prodDescLine, prodPriceLine, enterProductInfo);

		return allProductInfo;
	}

	/**
	 * This methods calls on the controller to save data to the database while
	 * doing error checking and any GUI changes here so as to not break the MVC pattern
	 */
	private void saveProduct() {

		if(!enterProductName.getText().equals("") && 
				!enterProductDescription.getText().equals("") && 
				!enterProductPrice.getText().equals("")) {

			int returnValue = controller.saveProductInfo(enterProductName.getText(), 
					enterProductDescription.getText(), Double.parseDouble(enterProductPrice.getText()));

			if(returnValue == -1) {
				errorLabel.setText("One of the fields exceeds the character limits.");
			} else {
				errorLabel.setText("Product Saved");
			}

		} else {
			errorLabel.setText("All fields must have input");
		}

	}

	/**
	 * Sets up the GUI needed for getting a products data
	 * @return A VBox object containing the needed GUI
	 */
	private VBox setUpGetProductInput() {

		explainProductOutputLabel = new Label("Enter a specific ID or enter 0 to get all results");
		enterProductID = new TextField();
		getProducts = new Button("Get Product(s)");
		getProducts.setOnAction(e -> getProductData(enterProductID.getText()));

		VBox productSelectionBox = new VBox();
		productSelectionBox.getChildren().addAll(explainProductOutputLabel, enterProductID, getProducts);

		return productSelectionBox;
	}

	/**
	 * Sets up the GUI for showing product data
	 * @return A VBox object with all the needed GUI
	 */
	private VBox setUpProductResultOutput() {

		productIDResultLabel = new Label("Product ID:");
		productIDResult = new Label();

		HBox productIDLine = new HBox();
		productIDLine.getChildren().addAll(productIDResultLabel, productIDResult);

		productNameResultLabel = new Label("Product Name:");
		productNameResult = new Label();

		HBox productNameLine = new HBox();
		productNameLine.getChildren().addAll(productNameResultLabel, productNameResult);

		productDescriptionResultLabel = new Label("Product Description:");
		productDescriptionResult = new Label();

		HBox productDescriptionLine = new HBox();
		productDescriptionLine.getChildren().addAll(productDescriptionResultLabel, productDescriptionResult);

		productPriceResultLabel = new Label("Product Price (€):");
		productPriceResult = new Label();

		HBox productPriceLine = new HBox();
		productPriceLine.getChildren().addAll(productPriceResultLabel, productPriceResult);

		nextProduct = new Button("Next");
		nextProduct.setOnAction(e -> showNextProduct());
		nextProduct.setDisable(true);

		prevProduct = new Button("Previous");
		prevProduct.setOnAction(e -> showPreviousProduct());
		prevProduct.setDisable(true);

		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(prevProduct, nextProduct);

		VBox productInfoBox = new VBox();
		productInfoBox.getChildren().addAll(productIDLine, productNameLine, productDescriptionLine, productPriceLine, buttonBox);

		return productInfoBox;
	}

	/**
	 * Gets product data from the database by calling the controller to do so, makes
	 * any changes to GUI in this method to not break MVC pattern
	 * @param productID
	 */
	private void getProductData(String productID) {

		int prodID = -1;

		if(InputHelper.isInteger(productID)) {
			prodID = Integer.parseInt(productID);
		}

		results = controller.getProducts(prodID);

		if(results != null) {
			nextProduct.setDisable(false);
			prevProduct.setDisable(false);
			showNextProduct();
		} else {
			errorLabel.setText("No results found");
		}

	}

	/**
	 * This methods shows the next set of data retrieved from the database
	 */
	private void showNextProduct() {

		try {

			if(results.next())  {

				productIDResult.setText(String.valueOf(results.getInt(1)));
				productNameResult.setText(results.getString(2));
				productDescriptionResult.setText(results.getString(3));
				productPriceResult.setText(String.valueOf(results.getDouble(4)));

			} else {
				results.previous();
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method shows the previous set of data retrieved from the database
	 */
	private void showPreviousProduct() {

		try {

			if(results.previous())  {

				productIDResult.setText(String.valueOf(results.getInt(1)));
				productNameResult.setText(results.getString(2));
				productDescriptionResult.setText(results.getString(3));
				productPriceResult.setText(String.valueOf(results.getDouble(4)));

			} else {
				results.previous();
			}

		} catch(SQLException e) {
			e.printStackTrace();
		}

	}

}
