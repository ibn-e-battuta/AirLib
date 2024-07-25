package command;

import command.book.*;
import command.bookCopy.*;
import command.branch.*;
import command.patron.*;
import command.util.HelpCommand;
import service.*;
import util.Logger;

import java.util.LinkedHashMap;
import java.util.Map;


public class CommandFactory {
    private final Map<String, Command> commands = new LinkedHashMap<>();
    private final Map<String, String> commandDescriptions = new LinkedHashMap<>();

    public CommandFactory(BookService bookService, PatronService patronService, ReservationService reservationService, RecommendationService recommendationService, LibraryService libraryService, Logger logger) {
        addCommand("HELP", "", new HelpCommand(commandDescriptions), "Display all available commands with descriptions.");

        addCommand("PRINT-LIBRARY", "", new PrintLibraryDetailsCommand(logger), "Print detailed information about the library and all its branches.");
        addCommand("ADD-BRANCH", "[branchCode] [name] [address]", new AddBranchCommand(libraryService), "Add a new library branch. Example: ADD-BRANCH B001 Main_Branch 123_Library_St.");
        addCommand("REMOVE-BRANCH", "[branchCode]", new RemoveBranchCommand(libraryService), "Remove an existing library branch by branch code. Example: REMOVE-BRANCH B001");
        addCommand("UPDATE-BRANCH", "[branchCode] [name] [address]", new UpdateBranchCommand(libraryService), "Update details of an existing library branch. Example: UPDATE-BRANCH B001 New_Name 456_Library_Ave.");
        addCommand("PRINT-BRANCH", "[branchCode:optional]", new PrintBranchCommand(libraryService, logger), "Print details of a specific branch or all branches if no branch code is provided. Example: PRINT-BRANCH B001");

        addCommand("ADD-BOOK", "[isbn] [title] [authors] [genres] [publicationYear]", new AddBookCommand(bookService), "Add a new book to the library collection. Example: ADD-BOOK 978-3-16-148410-0 Book_Title Author_1,Author_2 Genre_1,Genre_2 2023");
        addCommand("UPDATE-BOOK", "[isbn] [title] [authors] [genres] [publicationDate]", new UpdateBookCommand(bookService), "Update information of an existing book. Example: UPDATE-BOOK 978-3-16-148410-0 New_Title Author_1,Author_2 New_Genre_1,Genre_2 2024");
        addCommand("SEARCH-BOOKS", "[searchBy] [query]", new SearchBooksCommand(bookService, logger), "Search for books by various criteria such as ISBN, title, author, genre or publication year. Example: SEARCH-BOOKS title BookTitle");
        addCommand("REMOVE-BOOK", "[isbn]", new RemoveBookCommand(bookService), "Remove a book from the library collection by ISBN. Example: REMOVE-BOOK 978-3-16-148410-0");
        addCommand("RESERVE-BOOK", "[patronId] [isbn]", new ReserveBookCommand(reservationService), "Reserve a book for a patron. Example: RESERVE-BOOK P001 978-3-16-148410-0");
        addCommand("CANCEL-RESERVATION", "[reservationId]", new ReserveBookCommand(reservationService), "Cancel an existing book reservation by reservation Id. Example: CANCEL-RESERVATION R001");
        addCommand("BOOK-AVAILABILITY", "[isbn]", new BookAvailabilityCommand(bookService, logger), "Check the availability status of a book by ISBN. Example: BOOK-AVAILABILITY 978-3-16-148410-0");
        addCommand("AVAILABLE-BOOKS", "[branchId:optional]", new AvailableBooksCommand(bookService, logger), "Get a list of available books in a specific branch or all branches if no branch Id is provided. Example: AVAILABLE-BOOKS B001");
        addCommand("BORROWED-BOOKS", "[branchId:optional]", new BorrowedBooksCommand(bookService, logger), "Get a list of borrowed books in a specific branch or all branches if no branch Id is provided. Example: BORROWED-BOOKS B001");

        addCommand("ADD-PATRON", "[name] [email] [preferences]", new AddPatronCommand(patronService), "Add a new patron to the library system. Example: ADD-PATRON John_Doe john.doe@example.com Fiction,Science");
        addCommand("PATRON-HISTORY", "[patronId]", new GetPatronHistoryCommand(reservationService, logger), "Get the borrowing history of a patron by patron Id. Example: PATRON-HISTORY P001");
        addCommand("GET-RECOMMENDATIONS", "[patronId]", new GetRecommendationsCommand(recommendationService, logger), "Get book recommendations for a patron based on borrowing history. Example: GET-RECOMMENDATIONS P001");
        addCommand("PRINT-PATRON", "[patronId]", new PrintPatronCommand(patronService, logger), "Print detailed information about a patron by patron Id. Example: PRINT-PATRON P001");
        addCommand("UPDATE-PATRON", "[patronId] [name] [email] [preferences]", new UpdatePatronCommand(patronService), "Update details of an existing patron. Example: UPDATE-PATRON P001 Jane_Doe jane.doe@example.com Non-Fiction,Mystery");

        addCommand("ADD-BOOK-COPY", "[isbn] [branchCode]", new AddBookCopyCommand(bookService), "Add a copy of a book to a specific branch. Example: ADD-BOOK-COPY 978-3-16-148410-0 B001");
        addCommand("CHECKOUT-BOOK-COPY", "[patronId] [bookCopyId] [branchCode]", new CheckoutBookCopyCommand(reservationService), "Checkout a copy of a book for a patron from a specific branch. Example: CHECKOUT-BOOK-COPY P001 BC001 B001");
        addCommand("PRINT-BOOK-COPY", "[bookCopyId]", new PrintBookCopyCommand(bookService, logger), "Print details of a specific book copy by book copy Id. Example: PRINT-BOOK-COPY BC001");
        addCommand("RENEW-BOOK-COPY", "[reservationId]", new RenewBookCopyCommand(reservationService), "Renew the checkout period for a book copy by reservation Id. Example: RENEW-BOOK-COPY R001");
        addCommand("RETURN-BOOK-COPY", "[reservationId]", new ReturnBookCopyCommand(reservationService), "Return a borrowed book copy by reservation Id. Example: RETURN-BOOK-COPY R001");
        addCommand("TRANSFER-BOOK-COPY", "[sourceBranchCode] [destinationBranchCode] [bookCopyId:optional]", new TransferBookCopyCommand(bookService), "Transfer book copies between branches. Optionally specify a book copy Id to transfer a specific copy. Example: TRANSFER-BOOK-COPY B001 B002 BC001");
    }

    private void addCommand(String name, String parameters, Command command, String description) {
        commands.put(name, command);
        commandDescriptions.put(name + " " + parameters, description);
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName.toUpperCase());
    }
}