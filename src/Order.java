// Name: Graeme Hosford
// Student ID: R00147327
// OOP Final Project

public class Order {

	private int custID, prodID, quantity;

	public Order(int customerID, int productID, int quantity) {
		this.custID = customerID;
		this.prodID = productID;
		this.quantity = quantity;
	}

	public int getCustID() {
		return custID;
	}

	public int getProdID() {
		return prodID;
	}

	public int getQuantity() {
		return quantity;
	}

}
