package se.kth.iv1350.processSale.integration;

/**
 * The ItemNotFoundException class represents an exception that is thrown when an item with a specified ID cannot be found.
 * It extends the Exception class and provides a constructor to create an instance of the exception with a specific message.
 */
public class ItemNotFoundException extends Exception {

    private int itemID;

    /**
     * Creates a new instance of the ItemNotFoundException class with a specific message.
     * 
     * @param itemID The itemID of the item that could not be found.
     */
    public ItemNotFoundException(int itemID) {
        super("Could not find item with item ID" + itemID);
        this.itemID = itemID;
    }

   /**
     * Returns the item ID of the item that could not be found.
     * 
     * @return The ID of the missing item.
    */
    public int getItemID() {
        return itemID;
    }
}
