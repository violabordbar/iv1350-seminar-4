package se.kth.iv1350.processSale.view;

/**
 * The SaleObserver interface defines a contract for classes that want to observe and respond to new sales.
 * It contains a single method, updateRevenue, which is called when a new sale occurs.
 */
public interface SaleObserver {

    /**
     * This method is called when a new sale occurs.
     * It allows the observer to update its state or perform actions based on the new sale.
     * 
     * @param saleRevenue The revenue from the latest sale.
     */
    void updateRevenue(double saleRevenue);
    
}
