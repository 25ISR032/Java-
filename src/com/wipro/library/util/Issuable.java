package com.wipro.library.util;

/**
 * Contract defining issue/return behavior for library items.
 * Any class that can be "issued" and "returned" (like Book)
 * should implement this interface.
 */
public interface Issuable {

    /**
     * Attempts to issue the item.
     * @return true if the issue succeeded, false otherwise.
     */
    boolean issue();

    /**
     * Handles returning the item.
     */
    void returnBook();
}
