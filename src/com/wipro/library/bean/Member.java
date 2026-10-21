package com.wipro.library.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library member and tracks the book IDs currently
 * issued to that member (used to enforce the 3-book issue limit).
 */
public class Member {

    private String memberId;
    private String name;
    private List<String> issuedBookIds;

    public Member(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.issuedBookIds = new ArrayList<>();
    }

    // ---------- Getters ----------

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns an unmodifiable-style view is avoided here for simplicity,
     * but callers should treat this as read-only; mutation happens
     * through addIssuedBook / removeIssuedBook to preserve encapsulation.
     */
    public List<String> getIssuedBookIds() {
        return issuedBookIds;
    }

    // ---------- Behavior ----------

    public void addIssuedBook(String bookId) {
        issuedBookIds.add(bookId);
    }

    public void removeIssuedBook(String bookId) {
        issuedBookIds.remove(bookId);
    }

    public int getIssuedCount() {
        return issuedBookIds.size();
    }

    @Override
    public String toString() {
        return String.format("Member[%s - %s] Issued Books: %s", memberId, name, issuedBookIds);
    }
}
