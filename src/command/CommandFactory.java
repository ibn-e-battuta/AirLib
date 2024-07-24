package command;

import command.book.AddBookCommand;
import command.book.SearchBooksCommand;
import command.book.UpdateBookCommand;
import command.bookitem.AddBookItemCommand;
import command.bookitem.PrintBookItemCommand;
import command.bookitem.TransferBookCommand;
import command.branch.AddBranchCommand;
import command.branch.PrintBranchCommand;
import command.branch.RemoveBranchCommand;
import command.branch.UpdateBranchCommand;
import service.*;
import util.Logger;

import java.util.HashMap;
import java.util.Map;


public class CommandFactory {
    private final Map<String, Command> commands = new HashMap<>();
    private final Map<String, String> commandDescriptions = new HashMap<>();

    public CommandFactory(BookService bookService, PatronService patronService, ReservationService reservationService, RecommendationService recommendationService, LibraryService libraryService, Logger logger) {
        addCommand("HELP", "", new HelpCommand(commandDescriptions), "Display all available commands");
        addCommand("PRINT-LIBRARY", "", new PrintLibraryDetailsCommand(libraryService), "Print details of the library and its branches");
        addCommand("ADD-BRANCH", "[name] [address]", new AddBranchCommand(libraryService), "Add a new library branch");
        addCommand("REMOVE-BRANCH", "[branchId]", new RemoveBranchCommand(libraryService), "Remove a library branch");
        addCommand("UPDATE-BRANCH", "[branchId] [name] [address]", new UpdateBranchCommand(libraryService), "Update a library branch's details");
        addCommand("PRINT-BRANCH", "[branchId]", new PrintBranchCommand(libraryService), "Print details of a branch or all branches");
        addCommand("ADD-BOOK", "<isbn> <title> <author> <subject> <publicationDate>", new AddBookCommand(bookService), "Add a new book to the library");
        addCommand("UPDATE-BOOK", "<isbn> <title> <author> <subject> <publicationDate>", new UpdateBookCommand(bookService), "Update book information");
        addCommand("TRANSFER-BOOK", "<sourceBranchId> <destinationBranchId> <bookItemId>", new TransferBookCommand(bookService), "Transfer a book(s) between branches");
        addCommand("SEARCH-BOOKS", "<searchBy> <query>", new SearchBooksCommand(bookService), "Search for books");
        addCommand("ADD-BOOK-ITEM", "<isbn> <branchId>", new AddBookItemCommand(bookService), "Add a new book item to the library");
        addCommand("PRINT-BOOK-ITEM", "<bookItemId>", new PrintBookItemCommand(bookService), "Add a new book item to the library");
        /*
        
        addCommand("ADD-BOOK-ITEM", new AddBookItemCommand(bookService), "Add a new book item to the library");
        addCommand("SEARCH-BOOKS", new SearchBooksCommand(bookService), "Search for books");
        addCommand("CHECKOUT-BOOK", new CheckoutBookCommand(reservationService, patronService, bookService), "Checkout a book");
        addCommand("RETURN-BOOK", new ReturnBookCommand(reservationService), "Return a book");
        addCommand("RENEW-BOOK", new RenewBookCommand(reservationService), "Renew a book");
        addCommand("RESERVE-BOOK", new ReserveBookCommand(reservationService, patronService, bookService), "Reserve a book");
        addCommand("ADD-PATRON", new AddPatronCommand(patronService), "Add a new patron");
        addCommand("GET-RECOMMENDATIONS", new GetRecommendationsCommand(recommendationService, patronService), "Get book recommendations for a patron");
        
        addCommand("CHECK-OVERDUE-BOOKS", new CheckOverdueBooksCommand(reservationService), "Check for overdue books");
        addCommand("VIEW-BOOK-AVAILABILITY", new ViewBookAvailabilityCommand(bookService), "View book availability by branch");
        addCommand("UPDATE-PATRON", new UpdatePatronCommand(patronService), "Update patron information");
        
        addCommand("DELETE-BOOK", new DeleteBookCommand(bookService), "Delete a book from the library");



         */


    }

    private void addCommand(String name, String parameters, Command command, String description) {
        commands.put(name, command);
        commandDescriptions.put(name + " " + parameters, description);
    }

    public Command getCommand(String commandName) {
        return commands.get(commandName.toUpperCase());
    }
}