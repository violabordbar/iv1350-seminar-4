package se.kth.iv1350.processSale.controller;

import se.kth.iv1350.processSale.dto.ItemDTO;
import se.kth.iv1350.processSale.dto.SaleDTO;
import se.kth.iv1350.processSale.integration.AccountingSystem;
import se.kth.iv1350.processSale.integration.DiscountHandler;
import se.kth.iv1350.processSale.integration.InventorySystem;
import se.kth.iv1350.processSale.integration.Printer;
import se.kth.iv1350.processSale.model.Payment;
import se.kth.iv1350.processSale.model.Sale;
import se.kth.iv1350.processSale.integration.ItemNotFoundException;
import se.kth.iv1350.processSale.integration.DataBaseFailureException;
import se.kth.iv1350.processSale.util.LogHandler;
import se.kth.iv1350.processSale.view.SaleObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * The Controller class is responsible for managing the flow of the sale process.
 * It interacts with the accounting system, inventory system, discount handler, and printer.
 * It handles the sale process by starting a new sale, entering items, calculating total price,
 * applying discounts and printing receipts.
 */
public class Controller {
	private final AccountingSystem accountingSystem;
	private final InventorySystem inventorySystem;
	private final DiscountHandler discountHandler;
	private final Printer printer;
	private Sale sale;
	private LogHandler logger;
	private List<SaleObserver> saleObservers = new ArrayList<>();

	/**
 	 * Creates a new instance of the Controller class, passing references to the external systems.
     * 
     * @param accSys The accounting system used to manage financial updates.
     * @param invSys The inventory system used to manage item stock.
     * @param disHndlr The discount handler used to manages discounts.
     * @param printer The printer used to print the receipt.
	 * @param logger The logger used to record system errors.
     */
	public Controller(AccountingSystem accSys, InventorySystem invSys, DiscountHandler disHndlr, Printer printer, LogHandler logger) {
		this.accountingSystem = accSys;
		this.inventorySystem = invSys;
		this.discountHandler = disHndlr;
		this.printer = printer;
		this.logger = logger;
	}

	/**
	 * Initializes a new sale by creating a new sale object and adding all registered observers to the new sale.
	 */
	public void startSale() {
        sale = new Sale();
		for (SaleObserver saleObserver : saleObservers) {
			sale.addSaleObserver(saleObserver);
		}
	}

	/**
	 * Registers a new item in the sale using the item ID and quantity.
	 * If the quantity is 0, it defaults to 1.
	 * 
	 * @param itemID The identification number of the item.
	 * @param quantity The quantity of the item to be registered.
	 * @return the item that is registered.
	 * @throws ItemNotFoundException if the item does not exist in the inventory system.
	 * @throws ItemRegistrationFailureException if the item could not be registered due to system error.
	 */
	public ItemDTO registerNewItem(int itemID, int quantity) throws ItemNotFoundException, ItemRegistrationFailureException
	{
		if(quantity == 0) {
			quantity = 1;
		}

		try {
			ItemDTO registeredItem = inventorySystem.validateIdentifier(itemID);
			sale.addItem(registeredItem, quantity);
			return registeredItem;
		}

		catch (DataBaseFailureException e) {
			logger.logException(e);
			throw new ItemRegistrationFailureException(itemID);
		}

		catch (ItemNotFoundException e) {
			logger.logException(e);
			throw new ItemNotFoundException(itemID);
		}
	}

	/**
	 * Adds a SaleObserver to the sale, allowing it to be notified of the changes in the sale.
	 * 
	 * @param saleObserver The SaleObserver to be added.
	 */
	public void addSaleObserver(SaleObserver saleObserver) {
		saleObservers.add(saleObserver);
	}

	/**
	 * Gets the current running total price of the sale after each new item has been added.
	 * 
	 * @return The current running total price of the sale.
	 */
	public int getRunningTotal() {
		return sale.calculateCurrentPrice();
	}

	/**
	 * Gets the total VAT of the current items in the sale.
	 * 
	 * @return The total VAT of the current items in the sale.
	 */
	public double getTotalVAT() {
		return sale.calculateTotalVAT();
	}

	/**
	 * Retrieves the sale information from the Sale class.
	 * 
	 * @return A SaleDTO object containing the sale information.
	 */
	public SaleDTO getSaleInfo() {
		return sale.registerSaleInfo();
	}

	/**
	 * Ends the sale by sending the sale information to external systems and printing the receipt.
	 * 
	 */
	public void endSale() {
		sendSaleInformation();
		printReceipt();
	}

	/**
	 * Requests a discount to be applied to the sale.
	 * 
	 * @param customerID The identification number of the customer.
	 * @return The total price after the discount is applied.
	 */
	public double discountRequest(int customerID) {
		return discountHandler.findDiscount(customerID, sale);
	}

	/**
	 * Recieves a payment from the customer and registers it for the sale.
	 * 
	 * @param paidAmount The amount paid by the customer.
	 */
	public void receivePayment(int paidAmount) {
		Payment payment = new Payment(paidAmount);
		sale.registerPayment(payment);
	}

	/**
	 * Sends the sale information (including the list of items) to the accounting and inventory systems.
	 * 
	 */
	public void sendSaleInformation(){
		inventorySystem.updateInventory(getSaleInfo().getListOfItems());
		accountingSystem.updateAccounting(getSaleInfo());
	}

	/**
	 * Prints the receipt for the sale.
	 * 
	 */
	public void printReceipt() {
		printer.printReceipt(getSaleInfo());
	}

}