package se.kth.iv1350.processSale.startup;

import se.kth.iv1350.processSale.controller.Controller;
import se.kth.iv1350.processSale.integration.SystemCreator;
import se.kth.iv1350.processSale.view.View;
import se.kth.iv1350.processSale.util.LogHandler;
import java.io.IOException;


/**
 * The Main class is responsible for initializing the system components (Controller, SystemCreator, LogHandler and View),
 * and starts the program by calling the runFakeExecution method of the View class.
 */
public class Main{

	/**
	 * The main method creates instances of the system components and starts the program.
	 * 
	 * @param args Command line arguments (not used).
	 * @throws IOException If an I/O error occurs while creating the system components.
	 */
	public static void main(String[] args) throws IOException {
		SystemCreator sysCre = new SystemCreator();
		LogHandler logger = new LogHandler();
		Controller contr = new Controller(
			sysCre.getAccountingSystem(),
			sysCre.getInventorySystem(),
			sysCre.getDiscountHandler(),
			sysCre.getPrinter()
		);

		View view = new View(contr, logger);
		view.runFakeExecution();
	}
}

