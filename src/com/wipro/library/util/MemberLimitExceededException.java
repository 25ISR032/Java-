package com.wipro.library.util;

/**
 * Custom checked exception thrown when a member attempts to issue
 * more than 3 books simultaneously.
 */
public class MemberLimitExceededException extends Exception {

    public MemberLimitExceededException() {
        super("Member cannot issue more than 3 books.");
    }

    public MemberLimitExceededException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "MemberLimitExceededException: " + getMessage();
    }
}
