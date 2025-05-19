package se.kth.iv1350.processSale.integration;

/**
 * The DataBaseFailureException class represents an exception that is thrown when there is a failure calling the database server.
 * It extends the Exception class and provides a constructor to create an instance of the exception with a specific message.
 */
public class DataBaseFailureException extends RuntimeException{

    /**
     * Creates a new instance of the DataBaseFailureException class with a specific message.
     * 
     * @param message The message describing the exception.
     */
    public DataBaseFailureException(String message) {
        super(message);
    }
}
