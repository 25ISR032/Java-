package com.wipro.library;

import com.wipro.library.service.LibraryCatalogService;
import com.wipro.library.service.LibraryTransactionService;
import com.wipro.library.util.BookNotAvailableException;
import com.wipro.library.util.MemberLimitExceededException;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Menu-driven console application for the Library Book Management System.
 * Pre-loads test data (3 books, 3 members) as specified in the requirements,
 * then lets the user interact with the system through a text menu.
 */
public class Main {

    private static LibraryCatalogService catalogService = new LibraryCatalogService();
    private static LibraryTransactionService transactionService = new LibraryTransactionService(catalogService);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        loadTestData();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleAddBook();
                    break;
                case "2":
                    handleAddMember();
                    break;
                case "3":
                    handleIssueBook();
                    break;
                case "4":
                    handleReturnBook();
                    break;
                case "5":
                    handleSearchByTitle();
                    break;
                case "6":
                    handleDisplayByAuthor();
                    break;
                case "7":
                    handleDisplayAllBooks();
                    break;
                case "8":
                    running = false;
                    System.out.println("Exiting Library Management System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number between 1 and 8.");
            }
        }

        scanner.close();
    }

    /**
     * Pre-loads the sample books and members described in the project's test data.
     */
    private static void loadTestData() {
        catalogService.addBook(new Book("B101", "Java Basics", "James Gosling", "Programming", 2));
        catalogService.addBook(new Book("B102", "Advanced Java", "Herbert Schildt", "Programming", 1));
        catalogService.addBook(new Book("B103", "Data Structures", "Mark Allen", "Computer Science", 0));

        transactionService.addMember(new Member("M101", "Alice"));
        transactionService.addMember(new Member("M102", "Bob"));
        transactionService.addMember(new Member("M103", "Charlie"));

        System.out.println("Test data loaded: 3 books, 3 members.\n");
    }

    private static void printMenu() {
        System.out.println("===== Library Book Management System =====");
        System.out.println("1. Add Book");
        System.out.println("2. Add Member");
        System.out.println("3. Issue Book");
        System.out.println("4. Return Book");
        System.out.println("5. Search Book by Title");
        System.out.println("6. Display Books by Author");
        System.out.println("7. Display All Books");
        System.out.println("8. Exit");
        System.out.print("Enter your choice: ");
    }

    private static void handleAddBook() {
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine().trim();
        System.out.print("Enter Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine().trim();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine().trim();
        System.out.print("Enter Total Copies: ");
        int totalCopies = readInt();

        Book book = new Book(bookId, title, author, category, totalCopies);
        catalogService.addBook(book);
        System.out.println("Book added successfully: " + book + "\n");
    }

    private static void handleAddMember() {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine().trim();

        Member member = new Member(memberId, name);
        transactionService.addMember(member);
        System.out.println("Member added successfully: " + member + "\n");
    }

    private static void handleIssueBook() {
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine().trim();
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine().trim();

        try {
            String result = transactionService.issueBook(bookId, memberId);
            System.out.println(result + "\n");
        } catch (MemberLimitExceededException e) {
            System.out.println("Transaction failed: " + e + "\n");
        } catch (BookNotAvailableException e) {
            System.out.println("Transaction failed: " + e + "\n");
        }
    }

    private static void handleReturnBook() {
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine().trim();
        System.out.print("Enter Member ID (member returning the book): ");
        String memberId = scanner.nextLine().trim();

        // Update the returning member's own record first
        Member returningMember = transactionService.getMember(memberId);
        if (returningMember != null) {
            returningMember.removeIssuedBook(bookId);
        }

        String result = transactionService.returnBook(bookId);
        System.out.println(result + "\n");
    }

    private static void handleSearchByTitle() {
        System.out.print("Enter keyword to search in title: ");
        String keyword = scanner.nextLine().trim();

        List<Book> results = catalogService.searchByTitle(keyword);
        if (results.isEmpty()) {
            System.out.println("No books found matching: " + keyword + "\n");
        } else {
            System.out.println("Search results:");
            results.forEach(System.out::println);
            System.out.println();
        }
    }

    private static void handleDisplayByAuthor() {
        System.out.print("Enter Author name: ");
        String author = scanner.nextLine().trim();

        List<Book> books = catalogService.getBooksByAuthor(author);
        if (books.isEmpty()) {
            System.out.println("No books found for author: " + author + "\n");
        } else {
            System.out.println("Books by " + author + ":");
            books.forEach(System.out::println);
            System.out.println();
        }
    }

    private static void handleDisplayAllBooks() {
        Map<String, List<Book>> byAuthor = catalogService.getBooksByAuthorMap();
        if (byAuthor.isEmpty()) {
            System.out.println("No books in catalog.\n");
            return;
        }
        System.out.println("All books in catalog:");
        catalogService.getAllBooks().forEach(System.out::println);
        System.out.println();
    }

    /**
     * Safely reads an integer from the console, re-prompting on invalid input.
     */
    private static int readInt() {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number, please enter again: ");
            }
        }
    }
}
