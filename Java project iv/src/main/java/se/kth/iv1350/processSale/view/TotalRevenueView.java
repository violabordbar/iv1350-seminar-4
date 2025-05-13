package se.kth.iv1350.processSale.view;

/**
 * The TotalRevenueView class implements the SaleObserver interface and is responsible for displaying the total revenue of the sales.
 * It keeps track of the total revenue and updates it whenever a new sale is made.
 */
public class TotalRevenueView implements SaleObserver {
    private double totalRevenue = 0.0;

    /**
     * Adds the sale's revenue to the total revenue and displays the updated total revenue.
     * 
     * @param saleRevenue The revenue from the latest sale.
     */
    @Override
    public void updateRevenue(double saleRevenue) {
        totalRevenue += saleRevenue;
        System.out.println("Total revenue so far: " + totalRevenue + " SEK");
    }

}
