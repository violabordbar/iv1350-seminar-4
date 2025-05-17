package se.kth.iv1350.processSale.controller;

/**
 * The ItemRegistrationFailureException class represents an exception that is thrown when there is a failure registering an item.
 * It extends the Exception class and provides a constructor to create an instance of the exception with a specific message and cause.
 */
public class ItemRegistrationFailureException extends Exception {
    
    private int itemID;

    /**
     * Creates a new instance of the ItemRegistrationFailureException class with a specific error message.
     * 
     * @param itemID The itemID that caused the registration failure.
     */
    public ItemRegistrationFailureException(int itemID) {
        super("Could not register item with item ID " + itemID + " due to system failure");
        this.itemID = itemID;
    }
    
    /**
     * Returns the item ID of the item that failed to register.
     * 
     * @return The itemID that caused the registration failure.
    */
    public int getItemID() {
        return itemID;
    }
}
