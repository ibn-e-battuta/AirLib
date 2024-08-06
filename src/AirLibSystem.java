import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import command.Command;
import command.CommandFactory;
import model.Library;
import repository.BookCheckoutRepository;
import repository.BookCopyRepository;
import repository.BookRepository;
import repository.BookReservationRepository;
import repository.LibraryBranchRepository;
import repository.PatronRepository;
import repository.contract.IBookCheckoutRepository;
import repository.contract.IBookCopyRepository;
import repository.contract.IBookRepository;
import repository.contract.IBookReservationRepository;
import repository.contract.ILibraryBranchRepository;
import repository.contract.IPatronRepository;
import service.BookCheckoutService;
import service.BookCopyService;
import service.BookRecommendationService;
import service.BookReservationService;
import service.BookService;
import service.LibraryBranchService;
import service.NotificationService;
import service.PatronService;
import service.strategy.ConsoleINotificationStrategy;
import service.strategy.INotificationStrategy;
import util.Logger;
import util.Logger.LogLevel;

public class AirLibSystem {
    private final CommandFactory commandFactory;
    private final Logger logger;
    private final Scanner scanner;

    public AirLibSystem(Scanner scanner) {
        this.scanner = scanner;
        this.logger = new Logger(LogLevel.INFO);
        initializeLibrary();

        IBookRepository bookRepository = new BookRepository();
        IBookCopyRepository bookCopyRepository = new BookCopyRepository();
        IPatronRepository patronRepository = new PatronRepository();
        IBookCheckoutRepository bookCheckoutRepository = new BookCheckoutRepository();
        IBookReservationRepository bookReservationRepository = new BookReservationRepository();
        ILibraryBranchRepository libraryBranchRepository = new LibraryBranchRepository();
        INotificationStrategy notificationStrategy = new ConsoleINotificationStrategy(logger);
        final NotificationService notificationService = new NotificationService(notificationStrategy, logger);

        final LibraryBranchService libraryBranchService = new LibraryBranchService(libraryBranchRepository, logger);
        final BookService bookService = new BookService(bookRepository, bookCopyRepository, logger);
        final BookCopyService bookCopyService = new BookCopyService(bookRepository, bookCopyRepository,
                libraryBranchRepository, logger);
        final PatronService patronService = new PatronService(patronRepository, logger);
        final BookCheckoutService bookCheckoutService = new BookCheckoutService(bookCopyRepository, patronRepository,
                bookCheckoutRepository, bookReservationRepository, libraryBranchRepository, notificationService, logger);
        final BookReservationService bookReservationService = new BookReservationService(bookRepository,
                patronRepository, bookReservationRepository, logger);
        final BookRecommendationService bookRecommendationService = new BookRecommendationService(
                bookCheckoutRepository, bookRepository, patronRepository);

        this.commandFactory = new CommandFactory(bookService, bookCopyService, patronService, bookCheckoutService,
                bookReservationService, bookRecommendationService, libraryBranchService, logger);

        logger.info("AirLib System initialized");
    }

    public void run() {
        printWelcomeMessage();

        while (true) {
            String input = promptForCommand();

            if (input.equalsIgnoreCase("EXIT")) {
                handleExit();
                break;
            }

            handleCommand(input);
        }
    }

    private void printWelcomeMessage() {
        String libraryName = Library.getInstance().getName();
        logger.consoleMessage("Welcome to " + libraryName + ".");
        logger.consoleHelp("Here are some tips to get you started:");
        logger.consoleHelp("- Type 'HELP' to see a list of all available commands");
        logger.consoleHelp("- Type any command followed by '--help' for specific command information");
        logger.consoleHelp("- Type 'EXIT' when you're ready to quit the program");
        logger.console(); // Empty line
    }

    private String promptForCommand() {
        logger.console();
        logger.consoleInput("Enter a command (or 'HELP' for assistance): ");
        return scanner.nextLine().trim();
    }

    private void handleExit() {
        logger.info("System shutdown initiated");
        logger.consoleMessage("Thank you for using " + Library.getInstance().getName() + " Management System.");
        logger.consoleMessage("Goodbye!");
    }

    private void handleCommand(String input) {
        logger.info("Command entered: " + input);

        Command command = commandFactory.getCommand(input);
        if (command == null) {
            printInvalidCommandMessage();
            return;
        }

        try {
            List<String> args = parseCommandArguments(input);
            logger.console("");
            command.execute(args);
        } catch (Exception e) {
            handleCommandError(e);
        }
    }

    private List<String> parseCommandArguments(String input) {
        List<String> args = new ArrayList<>(Arrays.asList(input.split("\\s+")));
        args.remove(0); // Remove the command name
        if (!args.isEmpty() && args.get(args.size() - 1).equalsIgnoreCase("--help")) {
            args.remove(args.size() - 1); // Remove the --help flag
        }
        return args;
    }

    private void printInvalidCommandMessage() {
        logger.consoleError("Oops! That doesn't seem to be a valid command.");
        logger.consoleHelp("Type 'HELP' to see a list of available commands.");
        logger.consoleHelp("You can also type a command followed by '--help' for more information.");
    }

    private void handleCommandError(Exception e) {
        logger.exception("An error occurred during command execution", e);
        logger.consoleError("An error occurred while executing the command: \n" + e.getMessage());
        logger.consoleHelp("If this problem persists, please contact system support.");
    }

    private void initializeLibrary() {
        logger.consoleMessage("Welcome to the AirLib Library Management System Setup.");
        logger.consoleInput("Please enter the name of your library: ");
        String libraryName = scanner.nextLine();
        Library.getInstance().initialize(libraryName);
        logger.consoleMessage("Great! " + libraryName + " has been successfully set up.");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AirLibSystem system = new AirLibSystem(scanner);
        system.run();
        scanner.close();
    }
}