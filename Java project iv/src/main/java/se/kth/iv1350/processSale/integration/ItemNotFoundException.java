package se.kth.iv1350.processSale.integration;

/**
 * The ItemNotFoundException class represents an exception that is thrown when an item with a specified ID cannot be found.
 * It extends the Exception class and provides a constructor to create an instance of the exception with a specific message.
 */
public class ItemNotFoundException extends Exception {

    /**
     * Creates a new instance of the ItemNotFoundException class with a pecific message.
     * 
     * @param message The message describing the exception.
     */
    public ItemNotFoundException(String message) {
        super(message);
    }
}
