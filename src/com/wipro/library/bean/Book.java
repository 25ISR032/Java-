package com.wipro.library.bean;

import com.wipro.library.util.Issuable;

/**
 * Represents a single book title in the library catalog.
 * Implements Issuable to provide its own issue/return behavior,
 * demonstrating abstraction and encapsulation - the internal copy
 * count is never exposed for direct external modification.
 */
public class Book implements Issuable {

    private String bookId;
    private String title;
    private String author;
    private String category;
    private int totalCopies;
    private int availableCopies;

    /**
     * Creates a new Book. Available copies is initialized to the
     * total number of copies purchased by the library.
     */
    public Book(String bookId, String title, String author, String category, int totalCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    // ---------- Getters ----------

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    // ---------- Issuable implementation ----------

    /**
     * Issues one copy of this book if available.
     * @return true if a copy was available and has now been issued,
     *         false if there were zero available copies.
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
     * Returns one copy of this book, making it available again.
     * Available copies can never exceed the total copies owned.
     */
    @Override
    public void returnBook() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] \"%s\" by %s (%s) - Available: %d/%d",
                bookId, title, author, category, availableCopies, totalCopies);
    }
}
