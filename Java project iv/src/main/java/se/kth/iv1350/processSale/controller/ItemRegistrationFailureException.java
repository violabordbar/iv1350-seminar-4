package se.kth.iv1350.processSale.controller;

/**
 * The ItemRegistrationFailureException class represents an exception that is thrown when there is a failure registering an item.
 * It extends the Exception class and provides a constructor to create an instance of the exception with a specific message and cause.
 */
public class ItemRegistrationFailureException extends Exception {
    /**
     * Creates a new instance of the ItemRegistrationFailureException class with a specific message and the cause of the exception.
     * 
     * @param message The message describing the exception.
     * @param cause The cause of the exception.
     */
    public ItemRegistrationFailureException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
