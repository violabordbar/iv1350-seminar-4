package se.kth.iv1350.processSale.view;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The TotalRevenueFileOutput class implements the SaleObserver interface and is responsible for logging the total revenue
 * of all sales to a file. It maintains a running total of the revenue and writes it to a log file each time a new sale occurs.
 */
public class TotalRevenueFileOutput implements SaleObserver {
    private static final String LOG_FILE_NAME = "total_revenue_log.txt";
    private PrintWriter logStream;
    private double totalRevenue = 0.0;

    /**
     * Creates a new instance of the TotalRevenueFileOutput class and initializes the log file.
     * The log file is opened in append mode, so new log entries are added to the end of the file.
     * 
     * @throws IOException If an I/O error occurs while opening the log file.
     */
    public TotalRevenueFileOutput() throws IOException {
        logStream = new PrintWriter(new FileWriter(LOG_FILE_NAME, true), true);
    }

    /**
     * Adds the sale's revenue to the total revenue and logs the updated total revenue.
     * 
     * @param saleRevenue The revenue from the latest sale.
     */
    @Override
    public void newSale(double saleRevenue) {
        totalRevenue += saleRevenue;
        logStream.println("Total revenue so far: " + totalRevenue + " SEK");
    }
}
