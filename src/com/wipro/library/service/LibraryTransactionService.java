package com.wipro.library.service;

import com.wipro.library.Book;
import com.wipro.library.Member;
import com.wipro.library.util.BookNotAvailableException;
import com.wipro.library.util.MemberLimitExceededException;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles all issue/return transactions between members and books.
 * Enforces business rules:
 *  - A member cannot hold more than 3 books at a time.
 *  - If a book has no available copies, the member is placed on a waitlist
 *    and a BookNotAvailableException is thrown.
 *  - When a book is returned, if anyone is on the waitlist, it is
 *    automatically issued to the next member in line.
 */
public class LibraryTransactionService {

    private static final int MAX_BOOKS_PER_MEMBER = 3;

    private Map<String, Member> members = new HashMap<>();
    private LibraryCatalogService catalog;

    public LibraryTransactionService(LibraryCatalogService catalog) {
        this.catalog = catalog;
    }

    /**
     * Registers a new member in the system.
     */
    public void addMember(Member member) {
        members.put(member.getMemberId(), member);
    }

    /**
     * Retrieves a member by ID. Returns null if not found.
     */
    public Member getMember(String memberId) {
        return members.get(memberId);
    }

    public Map<String, Member> getMembers() {
        return members;
    }

    /**
     * Issues a book to a member, applying all business rules.
     *
     * @param bookId   the ID of the book to issue
     * @param memberId the ID of the member requesting the book
     * @return a success message describing the outcome
     * @throws MemberLimitExceededException if the member already has 3 books issued
     * @throws BookNotAvailableException    if no copies are available (member is queued)
     */
    public String issueBook(String bookId, String memberId) throws MemberLimitExceededException, BookNotAvailableException {

        // Check member exists
        Member member = members.get(memberId);
        if (member == null) {
            return "Error: Member with ID " + memberId + " does not exist.";
        }

        // Check book exists
        Book book = catalog.getBookById(bookId);
        if (book == null) {
            return "Error: Book with ID " + bookId + " does not exist.";
        }

        // Check member's issued book limit
        if (member.getIssuedBooks().size() >= MAX_BOOKS_PER_MEMBER) {
            throw new MemberLimitExceededException();
        }

        // Attempt to issue the book (Book.issue() decrements availableCopies if possible)
        boolean issued = book.issue();

        if (issued) {
            member.addIssuedBook(bookId);
            return "Book Issued Successfully. Available copies: " + book.getAvailableCopies();
        } else {
            // No copies available -> add member to waitlist and throw exception
            book.addToWaitList(memberId);
            int position = book.getWaitList().size();
            throw new BookNotAvailableException("Book not available. Member added to waiting list. Position: " + position);
        }
    }

    /**
     * Processes the return of a book. If a waitlist exists for the book,
     * automatically issues it to the next member in line.
     *
     * @param bookId the ID of the book being returned
     * @return a message describing the outcome of the return
     */
    public String returnBook(String bookId) {
        Book book = catalog.getBookById(bookId);
        if (book == null) {
            return "Error: Book with ID " + bookId + " does not exist.";
        }

        // Increase available copies
        book.returnBook();

        // Check waitlist for this book
        String nextMemberId = book.pollWaitList();

        if (nextMemberId != null) {
            // Automatically issue the book to the next waiting member
            Member nextMember = members.get(nextMemberId);
            if (nextMember != null) {
                // Decrement availableCopies again since it's being auto-issued
                book.issue();
                nextMember.addIssuedBook(bookId);
                return "Book returned successfully. Automatically issued to waiting member: "
                        + nextMember.getName() + " (" + nextMember.getMemberId() + ")";
            } else {
                return "Book returned successfully. Available copies: " + book.getAvailableCopies();
            }
        } else {
            return "Book returned successfully. Available copies: " + book.getAvailableCopies();
        }
    }
}
