// Name: Graeme Hosford
// Student ID: R00147327
// OOP Final Project

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {

		// Get the singleton instance of the database to do the initial setup
		Database db = Database.newInstance();
		db.createDatabase();

		// Start the GUI once the database is setup
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("R00147327 - OOP Final Project");

		// Add tabs for each part of the project
		TabPane tabPane = new TabPane();

		OrdersTab ordersTab = new OrdersTab();
		ProductsTab productsTab = new ProductsTab();
		CustomersTab customersTab = new CustomersTab();

		tabPane.getTabs().add(ordersTab);
		tabPane.getTabs().add(productsTab);
		tabPane.getTabs().add(customersTab);

		primaryStage.setScene(new Scene(tabPane, 500, 500));
		primaryStage.show();

	}

}
