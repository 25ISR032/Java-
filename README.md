<<<<<<< HEAD
# Library Book Management System (Collections + Interfaces)

A Java console application that tracks books by author and category using
Collections, issues/returns books through a common `Issuable` interface,
and manages a per-book waitlist for popular titles.

## Package Structure

```
com.wipro.library
├── bean
│   ├── Book.java                       (implements Issuable)
│   └── Member.java
├── util
│   ├── Issuable.java                   (interface)
│   ├── BookNotAvailableException.java  (checked exception)
│   └── MemberLimitExceededException.java (checked exception)
└── service
    ├── LibraryCatalogService.java      (catalog + Streams search)
    └── LibraryTransactionService.java  (issue/return/waitlist + main())
```

## Requirements Implemented

- `Map<String, List<Book>>` catalog keyed by **author** and by **category**.
- `Issuable` interface (`issue()`, `returnBook()`) implemented by `Book`.
- Per-book waitlist using `Queue<String>` (member IDs) in
  `LibraryTransactionService`.
- Automatic assignment of a returned book to the next waitlisted member.
- `BookNotAvailableException` thrown when a book has 0 copies and the
  member declines to join the waitlist.
- `MemberLimitExceededException` thrown when a member tries to hold more
  than 3 books simultaneously.
- Partial-title search implemented with Java **Streams**
  (`LibraryCatalogService.searchByTitle`).

## How to Compile & Run (command line)

```bash
cd LibraryManagementSystem
javac -d bin $(find src -name "*.java")
java -cp bin com.wipro.library.service.LibraryTransactionService
```

## Running in Eclipse

1. `File -> New -> Java Project`, name it `LibraryManagementSystem`, uncheck
   "Use default location" if you want to point it at this extracted folder,
   or simply create the project and then `File -> Import -> General ->
   File System`, importing the `src` folder contents into your project's
   `src` folder (Eclipse will auto-detect the `com.wipro.library...`
   packages).
2. Right-click `LibraryTransactionService.java` (in
   `com.wipro.library.service`) -> `Run As -> Java Application`.
3. Console output appears in Eclipse's Console view.

## Running in IntelliJ IDEA

1. `File -> New -> Project from Existing Sources...`, select the
   `LibraryManagementSystem` folder.
2. When prompted, choose "Create project from existing sources" and mark
   the `src` folder as **Sources Root** (right-click `src` -> `Mark
   Directory as -> Sources Root`) if it isn't detected automatically.
3. Open `LibraryTransactionService.java`, click the green run arrow next
   to `public static void main(String[] args)`.
4. Output appears in the Run tool window.

## Running in VS Code

1. Install the **Extension Pack for Java** (Microsoft) from the
   Extensions marketplace.
2. `File -> Open Folder...` and open `LibraryManagementSystem`.
3. VS Code will detect the `src` layout automatically. Open
   `LibraryTransactionService.java`.
4. Click **Run** above the `main` method (appears automatically via
   CodeLens), or use the Run and Debug panel.
5. Alternatively, use the integrated terminal with the command-line steps
   above.

## Sample Console Output (abbreviated)

```
--- Test Case 1: Issue 'Java Basics' (2 copies available) to member M1 ---
  Result: Book Issued Successfully. Available copies: 1

--- Test Case 2: Issue 'Design Patterns' (0 copies) to member M2 -> joins waitlist ---
  Result: Added to waitlist. Position: 1

--- Test Case 3: Member limit enforcement (max 3 books per member) ---
  ...
  Caught expected exception -> MemberLimitExceededException: Member has already
  issued the maximum of 3 books (Member ID: M1)
```
=======
# Java-
>>>>>>> 3233efc915d15fcc694a71b06a001f5e45f28e21
