// Name: Graeme Hosford
// Student ID: R00147327
// OOP Final Project

public class InputHelper {

	/**
	 * This method checks if a String input can be converted to an Integer or not
	 * @param input The String input to check
	 * @return true if the String can be a valid integer, false if not
	 */
	public static boolean isInteger(String input) {

		if(!input.equals("")) {

			try {
				Integer.parseInt(input);
				return true;
			} catch(NumberFormatException e) {
				return false;
			}

		} else {
			return false;
		}

	}
}
