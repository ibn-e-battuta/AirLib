package command;

import command.book.AddBookCommand;
import command.book.AvailableBooksCommand;
import command.book.BookAvailabilityCommand;
import command.book.BorrowedBooksCommand;
import command.book.RemoveBookCommand;
import command.book.ReserveBookCommand;
import command.book.SearchBooksCommand;
import command.bookCopy.AddBookCopyCommand;
import command.bookCopy.CheckoutBookCopyCommand;
import command.bookCopy.PrintBookCopyCommand;
import command.bookCopy.RenewBookCopyCommand;
import command.bookCopy.ReturnBookCopyCommand;
import command.bookCopy.TransferBookCopyCommand;
import command.book.UpdateBookCommand;
import command.branch.*;
import command.patron.AddPatronCommand;
import command.patron.GetPatronHistoryCommand;
import command.patron.GetRecommendationsCommand;
import command.patron.PrintPatronCommand;
import command.patron.UpdatePatronCommand;
import command.util.HelpCommand;
import service.*;
import util.Logger;

import java.util.HashMap;
import java.util.Map;


public class CommandFactory {
    private final Map<String, Command> commands = new HashMap<>();
    private final Map<String, String> commandDescriptions = new HashMap<>();

    public CommandFactory(BookService bookService, PatronService patronService, ReservationService reservationService, RecommendationService recommendationService, LibraryService libraryService, Logger logger) {
        addCommand("HELP", "", new HelpCommand(commandDescriptions), "Display all available commands");
        
        addCommand("PRINT-LIBRARY", "", new PrintLibraryDetailsCommand(logger), "Print details of the library and its branches");
        addCommand("ADD-BRANCH", "[branchCode] [name] [address]", new AddBranchCommand(libraryService), "Add a new library branch");
        addCommand("REMOVE-BRANCH", "[branchCode]", new RemoveBranchCommand(libraryService), "Remove a library branch");
        addCommand("UPDATE-BRANCH", "[branchCode] [name] [address]", new UpdateBranchCommand(libraryService), "Update a library branch's details");
        addCommand("PRINT-BRANCH", "[branchCode:optional]", new PrintBranchCommand(libraryService, logger), "Print details of a branch or all branches");
        
        addCommand("ADD-BOOK", "[isbn] [title] [authors] [genres] [publicationYear]", new AddBookCommand(bookService), "Add a new book to the library");
        addCommand("UPDATE-BOOK", "[isbn] [title] [authors] [genres] [publicationDate]", new UpdateBookCommand(bookService), "Update book information");
        addCommand("SEARCH-BOOKS", "[searchBy] [query]", new SearchBooksCommand(bookService, logger), "Search for books");
        addCommand("REMOVE-BOOK", "[isbn]", new RemoveBookCommand(bookService), "Remove a book");
        addCommand("RESERVE-BOOK", "[patronId] [isbn]", new ReserveBookCommand(reservationService), "Reserve a book");
        addCommand("CANCEL-RESERVATION", "[reservationId]", new ReserveBookCommand(reservationService), "Cancel a book reservation");
        addCommand("BOOK-AVAILABILITY", "[isbn]", new BookAvailabilityCommand(bookService, logger), "Check a book's availability details");
        addCommand("AVAILABLE-BOOKS", "[branchId:optional]", new AvailableBooksCommand(bookService, logger), "Get available books in the library or a branch");
        addCommand("BORROWED-BOOKS", "[branchId:optional]", new BorrowedBooksCommand(bookService, logger), "Get borrowed books in the library or a branch");

        addCommand("ADD-PATRON", "[name] [email] [preferences]", new AddPatronCommand(patronService), "Add a patron");
        addCommand("PATRON-HISTORY", "[patronId]", new GetPatronHistoryCommand(reservationService, logger), "Get patron's borrowing history");
        addCommand("GET-RECOMMENDATIONS", "[patronId]", new GetRecommendationsCommand(recommendationService, logger), "Get book recommendations for patron");
        addCommand("PRINT-PATRON", "[patronId]", new PrintPatronCommand(patronService, logger), "Display patron's details");
        addCommand("UPDATE-PATRON", "[patronId] [name] [email] [preferences]", new UpdatePatronCommand(patronService), "Update a patron's details");

        addCommand("ADD-BOOK-COPY", "[isbn] [branchCode]", new AddBookCopyCommand(bookService), "Add a book copy");
        addCommand("CHECKOUT-BOOK-COPY", "[patronId] [bookCopyId] [branchCode]", new CheckoutBookCopyCommand(reservationService), "Checkout a book copy");
        addCommand("PRINT-BOOK-COPY", "[bookCopyId]", new PrintBookCopyCommand(bookService, logger), "Print book copy's details");
        addCommand("RENEW-BOOK-COPY", "[reservationId]", new RenewBookCopyCommand(reservationService), "Renew a book copy");
        addCommand("RETURN-BOOK-COPY", "[reservationId]", new ReturnBookCopyCommand(reservationService), "Return a book copy");
        addCommand("TRANSFER-BOOK-COPY", "[sourceBranchCode] [destinationBranchCode] [bookCopyId:optional]", new TransferBookCopyCommand(bookService), "Transfer book copies between library branches");
    }

    private void addCommand(String name, String parameters, Command command, String description) {
        commands.put(name, command);
        commandDescriptions.put(name + " " + parameters, description);
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName.toUpperCase());
    }
}