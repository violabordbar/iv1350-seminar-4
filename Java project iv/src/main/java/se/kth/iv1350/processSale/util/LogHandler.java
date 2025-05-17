package se.kth.iv1350.processSale.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * The LogHandler class is responsible for logging exceptions to a file.
 * It provides a method to log exceptions with a timestamp and the exception message.
 */
public class LogHandler {
    private static final String LOG_FILE_NAME = "error_log.txt";
    private PrintWriter logFile;

    /**
     * Creates a new instance of the LogHandler class and initializes the log file.
     * The log file is opened in append mode, so new log entries are added to the end of the file.
     * 
     * @throws IOException If an I/O error occurs while opening the log file.
     */
    public LogHandler () throws IOException{
      logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME, true), true);
    }

    /**
     * Writes the exception message and stack trace to the log file with a timestamp.
     *
     * @param exception The exception to log.
     */
    public void logException(Exception exception) {
        logFile.println("Date: " + java.time.LocalDate.now());
        logFile.println("Time: " + java.time.LocalTime.now());
        logFile.println("Exception message: " + exception.getMessage());
        exception.printStackTrace(logFile);
        logFile.println("--------------------------------------------------");
    }
}
