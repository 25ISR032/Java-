package com.wipro.library.util;

/**
 * Custom checked exception thrown when a requested book has zero
 * available copies and the member is added to the waiting list.
 */
public class BookNotAvailableException extends Exception {

    public BookNotAvailableException() {
        super("Book not available. Member added to waiting list.");
    }

    public BookNotAvailableException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "BookNotAvailableException: " + getMessage();
    }
}
