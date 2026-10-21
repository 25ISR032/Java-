package com.wipro.library;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library member who can borrow books.
 * Tracks the list of bookIds currently issued to this member.
 */
public class Member {

    private String memberId;
    private String name;
    private List<String> issuedBooks = new ArrayList<>();

    // Default constructor
    public Member() {
    }

    // Parameterized constructor
    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    // ---------- Getters and Setters ----------

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIssuedBooks() {
        return issuedBooks;
    }

    public void setIssuedBooks(List<String> issuedBooks) {
        this.issuedBooks = issuedBooks;
    }

    /**
     * Adds a bookId to this member's list of currently issued books.
     */
    public void addIssuedBook(String bookId) {
        issuedBooks.add(bookId);
    }

    /**
     * Removes a bookId from this member's list of currently issued books.
     */
    public void removeIssuedBook(String bookId) {
        issuedBooks.remove(bookId);
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberId='" + memberId + '\'' +
                ", name='" + name + '\'' +
                ", issuedBooks=" + issuedBooks +
                '}';
    }
}
