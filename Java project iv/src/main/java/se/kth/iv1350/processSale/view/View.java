package se.kth.iv1350.processSale.view;

import se.kth.iv1350.processSale.controller.Controller;
import se.kth.iv1350.processSale.dto.ItemDTO;
import se.kth.iv1350.processSale.integration.ItemNotFoundException;
import se.kth.iv1350.processSale.controller.ItemRegistrationFailureException;
import java.io.IOException;

/**
 * The View class handles user interaction and displays relevant sale information to the user.
 * This class simulates the user interface by calling controller methods and displaying results.
 * It also logs exceptions that may occur during the execution of the sale.
 */
public class View{
	private final Controller contr;

	/**
	 * Creates a new instance of the View class and registers sale observers to 
	 * display and record total revenue after each sale.
	 * 
	 * @param contr The controller that handles the interaction between the view and the model.
	 */
	public View(Controller contr) {
		this.contr = contr;

		try {
			TotalRevenueView revenueView = new TotalRevenueView();
			TotalRevenueFileOutput revenueFileOutput = new TotalRevenueFileOutput();
			contr.addSaleObserver(revenueView);
			contr.addSaleObserver(revenueFileOutput);
		}

		catch (IOException e) {
			System.out.println("Failed to create revenue file output.");
			e.printStackTrace();
		}
	}

	private void printItemDetails(ItemDTO item) {
		System.out.println(
			"Item ID: " + item.getItemID() + "\n" + 
			"Item name: " + item.getName() + "\n" +
			"Item price: " + item.getItemPrice() + " SEK" + "\n" +
			"VAT: " + item.getVAT() * 100 + "%" + "\n" +
			"Item description: " + item.getDescription() + "\n"
		);

		System.out.println("Total price (incl VAT): " + contr.getRunningTotal() + " SEK");
		System.out.println("Total VAT: " + contr.getTotalVAT() + " SEK" + "\n");
	}
	
	/**
	 * Simulates a sale process to demonstrate the system's functionality.
	 * The method includes registering items, calculating totals, receiving a payment,
	 * and printing out the result of each step.
	 * It also handles any exceptions that may occur during the sale process.
	 */
	public void runFakeExecution() {
		contr.startSale();

		try{
			ItemDTO firstRegisteredItem = contr.registerNewItem(1234, 3);
			System.out.println("Add 3 items with item ID 1234:");
			printItemDetails(firstRegisteredItem);

			ItemDTO secondRegisteredItem = contr.registerNewItem(5678, 1);
			System.out.println("Add 1 item with item ID 5678:");
			printItemDetails(secondRegisteredItem);

			ItemDTO thirdRegisteredItem = contr.registerNewItem(7891, 2);
			System.out.println("Add 2 items with item ID 7891:");
			printItemDetails(thirdRegisteredItem);

			System.out.println("End sale:");
			System.out.println("Total price (incl VAT): " + contr.getRunningTotal() + " SEK" + "\n");

			contr.receivePayment(500);
			contr.endSale();
		}

		catch (ItemNotFoundException e) {
			System.out.println("Item with ID" + e.getItemID() + "could not be found");
		}

		catch (ItemRegistrationFailureException e) {
			System.out.println(e.getMessage() + "\n");
		}

	}
}
