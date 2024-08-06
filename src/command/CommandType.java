package command;

public enum CommandType {
    HELP("HELP", null, "Display all available commands with descriptions.", null),
    PRINT_LIBRARY("PRINT-LIBRARY", null, "Print detailed information about the library and all its branches.", null),
    ADD_BRANCH("ADD-BRANCH", "[branchCode] [name] [address]",
            "Add a new library branch.",
            "Example: ADD-BRANCH B001 Main_Branch 123_Library_St."),
    REMOVE_BRANCH("REMOVE-BRANCH", "[branchCode]",
            "Remove an existing library branch by branch code.",
            "Example: REMOVE-BRANCH B001"),
    UPDATE_BRANCH("UPDATE-BRANCH", "[branchCode] [name] [address]",
            "Update details of an existing library branch.",
            "Example: UPDATE-BRANCH B001 New_Name 456_Library_Ave."),
    PRINT_BRANCH("PRINT-BRANCH", "[branchCode:optional]",
            "Print details of a specific branch or all branches if no branch code is provided.",
            "Example: PRINT-BRANCH B001"),
    ADD_BOOK("ADD-BOOK", "[isbn] [title] [authors] [genres] [publicationYear]",
            "Add a new book to the library collection.",
            "Example: ADD-BOOK 978-3-16-148410-0 Book_Title Author_1,Author_2 Genre_1,Genre_2 2023"),
    UPDATE_BOOK("UPDATE-BOOK", "[isbn] [title] [authors] [genres] [publicationDate]",
            "Update information of an existing book.",
            "Example: UPDATE-BOOK 978-3-16-148410-0 New_Title Author_1,Author_2 New_Genre_1,Genre_2 2024"),
    SEARCH_BOOKS("SEARCH-BOOKS", "[searchBy] [query]",
            "Search for books by various criteria such as ISBN, title, author, genre or publication year.",
            "Example: SEARCH-BOOKS title BookTitle"),
    REMOVE_BOOK("REMOVE-BOOK", "[isbn]",
            "Remove a book from the library collection by ISBN.",
            "Example: REMOVE-BOOK 978-3-16-148410-0"),
    RESERVE_BOOK("RESERVE-BOOK", "[patronId] [isbn]",
            "Reserve a book for a patron.",
            "Example: RESERVE-BOOK P001 978-3-16-148410-0"),
    CANCEL_RESERVATION("CANCEL-RESERVATION", "[reservationId]",
            "Cancel an existing book reservation by reservation Id.",
            "Example: CANCEL-RESERVATION R001"),
    BOOK_AVAILABILITY("BOOK-AVAILABILITY", "[isbn]",
            "Check the availability status of a book by ISBN.",
            "Example: BOOK-AVAILABILITY 978-3-16-148410-0"),
    AVAILABLE_BOOKS("AVAILABLE-BOOKS", "[branchCode:optional]",
            "Get a list of available books in a specific branch or all branches if no branch Id is provided.",
            "Example: AVAILABLE-BOOKS B001"),
    BORROWED_BOOKS("BORROWED-BOOKS", "[branchCode:optional]",
            "Get a list of borrowed books in a specific branch or all branches if no branch Id is provided.",
            "Example: BORROWED-BOOKS B001"),
    ADD_PATRON("ADD-PATRON", "[name] [email] [preferences]",
            "Add a new patron to the library system.",
            "Example: ADD-PATRON John_Doe john.doe@example.com Fiction,Science"),
    PATRON_HISTORY("PATRON-HISTORY", "[patronId]",
            "Get the borrowing history of a patron by patron Id.",
            "Example: PATRON-HISTORY P001"),
    GET_RECOMMENDATIONS("GET-RECOMMENDATIONS", "[patronId]",
            "Get book recommendations for a patron based on borrowing history.",
            "Example: GET-RECOMMENDATIONS P001"),
    PRINT_PATRON("PRINT-PATRON", "[patronId]",
            "Print detailed information about a patron by patron Id.",
            "Example: PRINT-PATRON P001"),
    UPDATE_PATRON("UPDATE-PATRON", "[patronId] [name] [email] [preferences]",
            "Update details of an existing patron.",
            "Example: UPDATE-PATRON P001 Jane_Doe jane.doe@example.com Non-Fiction,Mystery"),
    ADD_BOOK_COPY("ADD-BOOK-COPY", "[isbn] [branchCode]",
            "Add a copy of a book to a specific branch.",
            "Example: ADD-BOOK-COPY 978-3-16-148410-0 B001"),
    CHECKOUT_BOOK_COPY("CHECKOUT-BOOK-COPY", "[patronId] [bookCopyId] [branchCode]",
            "Checkout a copy of a book for a patron from a specific branch.",
            "Example: CHECKOUT-BOOK-COPY P001 BC001 B001"),
    PRINT_BOOK_COPY("PRINT-BOOK-COPY", "[bookCopyId]",
            "Print details of a specific book copy by book copy Id.",
            "Example: PRINT-BOOK-COPY BC001"),
    RENEW_BOOK_COPY("RENEW-BOOK-COPY", "[reservationId]",
            "Renew the checkout period for a book copy by reservation Id.",
            "Example: RENEW-BOOK-COPY R001"),
    RETURN_BOOK_COPY("RETURN-BOOK-COPY", "[reservationId]",
            "Return a borrowed book copy by reservation Id.",
            "Example: RETURN-BOOK-COPY R001"),
    TRANSFER_BOOK_COPY("TRANSFER-BOOK-COPY", "[sourceBranchCode] [destinationBranchCode] [bookCopyId:optional]",
            "Transfer book copies between branches. Optionally specify a book copy Id to transfer a specific copy.",
            "Example: TRANSFER-BOOK-COPY B001 B002 BC001");

    private final String commandName;
    private final String parameters;
    private final String description;
    private final String example;

    CommandType(String commandName, String parameters, String description, String example) {
        this.commandName = commandName;
        this.parameters = parameters;
        this.description = description;
        this.example = example;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getParameters() {
        return parameters;
    }

    public String getDescription() {
        return description;
    }

    public String getExample() {
        return example;
    }

    public String getFullSyntax() {
        return parameters != null ? commandName + " " + parameters : commandName;
    }
}