package com.wipro.library.service;

import com.wipro.library.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Manages the book catalog for the library.
 * Maintains three lookup structures:
 *  - booksById: quick lookup of a Book by its unique ID
 *  - booksByAuthor: books grouped by author
 *  - booksByCategory: books grouped by category
 *
 * Provides search functionality using Java Streams.
 */
public class LibraryCatalogService {

    private Map<String, Book> booksById = new HashMap<>();
    private Map<String, List<Book>> booksByAuthor = new HashMap<>();
    private Map<String, List<Book>> booksByCategory = new HashMap<>();

    /**
     * Adds a new book to the catalog and updates all lookup maps.
     */
    public void addBook(Book book) {
        booksById.put(book.getBookId(), book);

        // Add to author map (create list if author not seen before)
        booksByAuthor.computeIfAbsent(book.getAuthor(), k -> new ArrayList<>()).add(book);

        // Add to category map (create list if category not seen before)
        booksByCategory.computeIfAbsent(book.getCategory(), k -> new ArrayList<>()).add(book);
    }

    /**
     * Searches for books whose title contains the given keyword,
     * using a case-insensitive partial match, implemented with Java Streams.
     */
    public List<Book> searchByTitle(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return booksById.values()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    /**
     * Returns the list of books written by the given author.
     * Returns an empty list if the author is not found.
     */
    public List<Book> getBooksByAuthor(String author) {
        return booksByAuthor.getOrDefault(author, new ArrayList<>());
    }

    /**
     * Returns the list of books belonging to the given category.
     * Returns an empty list if the category is not found.
     */
    public List<Book> getBooksByCategory(String category) {
        return booksByCategory.getOrDefault(category, new ArrayList<>());
    }

    /**
     * Retrieves a book by its unique ID. Returns null if not found.
     */
    public Book getBookById(String bookId) {
        return booksById.get(bookId);
    }

    /**
     * Returns all books currently in the catalog.
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(booksById.values());
    }

    /**
     * Returns the full author -> books map (used for the "Display Books by Author" menu option).
     */
    public Map<String, List<Book>> getBooksByAuthorMap() {
        return booksByAuthor;
    }
}
