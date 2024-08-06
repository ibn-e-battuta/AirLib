package command;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import command.book.AddBookCommand;
import command.book.CancelReservationCommand;
import command.book.PrintAvailableBooksCommand;
import command.book.PrintBookAvailabilityCommand;
import command.book.PrintBorrowedBooksCommand;
import command.book.RemoveBookCommand;
import command.book.ReserveBookCommand;
import command.book.SearchBooksCommand;
import command.book.UpdateBookCommand;
import command.bookcopy.AddBookCopyCommand;
import command.bookcopy.CheckoutBookCopyCommand;
import command.bookcopy.PrintBookCopyCommand;
import command.bookcopy.RenewBookCopyCommand;
import command.bookcopy.ReturnBookCopyCommand;
import command.bookcopy.TransferBookCopyCommand;
import command.branch.AddBranchCommand;
import command.branch.PrintBranchCommand;
import command.branch.PrintLibraryDetailsCommand;
import command.branch.RemoveBranchCommand;
import command.branch.UpdateBranchCommand;
import command.patron.AddPatronCommand;
import command.patron.GetBookRecommendationsCommand;
import command.patron.GetPatronCheckoutHistoryCommand;
import command.patron.PrintPatronCommand;
import command.patron.UpdatePatronCommand;
import command.util.HelpCommand;
import service.BookCheckoutService;
import service.BookCopyService;
import service.BookRecommendationService;
import service.BookReservationService;
import service.BookService;
import service.LibraryBranchService;
import service.PatronService;
import util.Logger;

public class CommandFactory {
    private final Map<CommandType, Command> commands = new EnumMap<>(CommandType.class);
    private final HelpCommand helpCommand;

    public CommandFactory(final BookService bookService,
            final BookCopyService bookCopyService,
            final PatronService patronService,
            final BookCheckoutService bookCheckoutService,
            final BookReservationService bookReservationService,
            final BookRecommendationService bookRecommendationService,
            final LibraryBranchService libraryBranchService,
            final Logger logger) {
        this.helpCommand = new HelpCommand(CommandType.values());
        initializeCommands(bookService, bookCopyService, patronService, bookCheckoutService,
                bookReservationService, bookRecommendationService, libraryBranchService, logger);
    }

    private void initializeCommands(BookService bookService,
            BookCopyService bookCopyService,
            PatronService patronService,
            BookCheckoutService bookCheckoutService,
            BookReservationService bookReservationService,
            BookRecommendationService bookRecommendationService,
            LibraryBranchService libraryBranchService,
            Logger logger) {

        commands.put(CommandType.HELP, helpCommand);
        commands.put(CommandType.PRINT_LIBRARY, new PrintLibraryDetailsCommand(logger));
        commands.put(CommandType.ADD_BRANCH, new AddBranchCommand(libraryBranchService));
        commands.put(CommandType.REMOVE_BRANCH, new RemoveBranchCommand(libraryBranchService));
        commands.put(CommandType.UPDATE_BRANCH, new UpdateBranchCommand(libraryBranchService));
        commands.put(CommandType.PRINT_BRANCH, new PrintBranchCommand(libraryBranchService, logger));
        commands.put(CommandType.ADD_BOOK, new AddBookCommand(bookService));
        commands.put(CommandType.UPDATE_BOOK, new UpdateBookCommand(bookService));
        commands.put(CommandType.SEARCH_BOOKS, new SearchBooksCommand(bookService, logger));
        commands.put(CommandType.REMOVE_BOOK, new RemoveBookCommand(bookService));
        commands.put(CommandType.RESERVE_BOOK, new ReserveBookCommand(bookReservationService));
        commands.put(CommandType.CANCEL_RESERVATION, new CancelReservationCommand(bookReservationService));
        commands.put(CommandType.BOOK_AVAILABILITY, new PrintBookAvailabilityCommand(bookService, logger));
        commands.put(CommandType.AVAILABLE_BOOKS, new PrintAvailableBooksCommand(bookCopyService, logger));
        commands.put(CommandType.BORROWED_BOOKS, new PrintBorrowedBooksCommand(bookCopyService, logger));
        commands.put(CommandType.ADD_PATRON, new AddPatronCommand(patronService));
        commands.put(CommandType.PATRON_HISTORY, new GetPatronCheckoutHistoryCommand(patronService, logger));
        commands.put(CommandType.GET_RECOMMENDATIONS,
                new GetBookRecommendationsCommand(bookRecommendationService, logger));
        commands.put(CommandType.PRINT_PATRON, new PrintPatronCommand(patronService, logger));
        commands.put(CommandType.UPDATE_PATRON, new UpdatePatronCommand(patronService));
        commands.put(CommandType.ADD_BOOK_COPY, new AddBookCopyCommand(bookCopyService));
        commands.put(CommandType.CHECKOUT_BOOK_COPY, new CheckoutBookCopyCommand(bookCheckoutService));
        commands.put(CommandType.PRINT_BOOK_COPY, new PrintBookCopyCommand(bookCopyService, logger));
        commands.put(CommandType.RENEW_BOOK_COPY, new RenewBookCopyCommand(bookCheckoutService));
        commands.put(CommandType.RETURN_BOOK_COPY, new ReturnBookCopyCommand(bookCheckoutService));
        commands.put(CommandType.TRANSFER_BOOK_COPY, new TransferBookCopyCommand(bookCopyService));
    }

    public Command getCommand(String commandInput) {
        String[] parts = commandInput.split("\\s+", 2);
        String commandName = parts[0].toUpperCase();
        boolean isHelpRequest = parts.length > 1 && parts[1].trim().equalsIgnoreCase("--help");

        if (isHelpRequest) {
            return args -> helpCommand.execute(List.of(commandName));
        }

        for (CommandType type : CommandType.values()) {
            if (type.getCommandName().equalsIgnoreCase(commandName)) {
                return commands.get(type);
            }
        }
        return null;
    }
}