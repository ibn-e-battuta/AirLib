import command.Command;
import command.CommandFactory;
import model.Library;
import repository.*;
import service.*;
import service.strategy.ConsoleNotificationStrategy;
import util.Logger;
import util.Logger.LogLevel;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AirLibSystem {
    private final CommandFactory _commandFactory;
    private final Logger _logger;

    public AirLibSystem(Scanner scanner) {
        _logger = new Logger(LogLevel.INFO, true);
        initializeLibrary(scanner);

        var bookRepo = new BookRepository();
        var bookCopyRepo = new BookCopyRepository();
        var patronRepo = new PatronRepository();
        var bookCheckoutRepo = new BookCheckoutRepository();
        var bookReservationRepo = new BookReservationRepository();
        var libraryBranchRepo = new LibraryBranchRepository();
        var consoleStrategy = new ConsoleNotificationStrategy();
        var notificationService = new NotificationService(_logger, consoleStrategy);

        var libraryService = new LibraryService(libraryBranchRepo, _logger);
        var bookService = new BookService(bookRepo, bookCopyRepo, libraryBranchRepo, _logger);
        var patronService = new PatronService(patronRepo, _logger);
        var reservationService = new ReservationService(bookRepo, bookCopyRepo, patronRepo, bookCheckoutRepo,
                bookReservationRepo, notificationService, _logger);
        var recommendationService = new RecommendationService(bookCheckoutRepo, bookRepo, patronRepo);

        _commandFactory = new CommandFactory(bookService, patronService, reservationService, recommendationService,
                libraryService, _logger);

        _logger.info("AirLib System initialized");
    }

    private void initializeLibrary(Scanner scanner) {
        System.out.println("Welcome to AirLib Library Management System Setup!");
        System.out.print("Enter the name of the library: ");
        String libraryName = scanner.nextLine();
        Library.getInstance().initialize(libraryName);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AirLibSystem system = new AirLibSystem(scanner);
        system.run(scanner);
        scanner.close();
    }

    private void run(Scanner scanner) {
        System.out.println("Welcome to " + Library.getInstance().getName());
        System.out.println("Type 'HELP' to see available commands or 'EXIT' to quit the program.");

        while (true) {
            System.out.print("\nEnter command: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("EXIT")) {
                _logger.info("System shutdown initiated");
                _logger.info("Exiting. Thank you for using AirLib!");
                break;
            }

            List<String> parts = Arrays.asList(input.split("\\s+"));
            if (parts.isEmpty()) {
                continue;
            }

            String commandName = parts.get(0).toUpperCase();
            List<String> args = parts.subList(1, parts.size());

            _logger.info("Command executed: " + input);

            Command command = _commandFactory.getCommand(commandName);
            if (command == null) {
                _logger.info("Invalid command. Type 'HELP' to see available commands.");
                continue;
            }

            try {
                command.execute(args);
            } catch (Exception e) {
                _logger.exception("An error occurred during command execution", e);
                _logger.info("An error occurred: " + e.getMessage());
            }
        }
    }
}
