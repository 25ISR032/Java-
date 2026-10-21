package com.wipro.library;

import com.wipro.library.util.Issuable;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Represents a single book in the library catalog.
 * Implements Issuable to support issue/return behavior.
 * Maintains a waitlist (Queue of member IDs) for members waiting
 * for a copy of this book when none are available.
 */
public class Book implements Issuable {

    private String bookId;
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int availableCopies;

    // Queue of memberIds waiting for this book (FIFO order)
    private Queue<String> waitList = new LinkedList<>();

    // Default constructor
    public Book() {
    }

    // Parameterized constructor
    public Book(String bookId, String title, String author, String category, int totalCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies; // Initially all copies are available
    }

    // ---------- Getters and Setters ----------

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Queue<String> getWaitList() {
        return waitList;
    }

    public void setWaitList(Queue<String> waitList) {
        this.waitList = waitList;
    }

    /**
     * Adds a member to the waitlist queue for this book.
     */
    public void addToWaitList(String memberId) {
        waitList.add(memberId);
    }

    /**
     * Removes and returns the next member in line from the waitlist.
     * Returns null if the waitlist is empty.
     */
    public String pollWaitList() {
        return waitList.poll();
    }

    /**
     * Attempts to issue a single copy of this book.
     * Decrements availableCopies if a copy is available.
     *
     * @return true if a copy was successfully issued, false if no copies are available.
     */
    @Override
    public boolean issue() {
        if (availableCopies > 0) {
            availableCopies--;
            return true;
        }
        return false;
    }

    /**
     * Handles the return of a single copy of this book.
     * Increments availableCopies (bounded by totalCopies).
     */
    @Override
    public void returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", totalCopies=" + totalCopies +
                ", availableCopies=" + availableCopies +
                ", waitListSize=" + waitList.size() +
                '}';
    }
}
