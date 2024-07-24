import command.*;
import model.Library;

import repository.BookRepository;
import repository.PatronRepository;
import repository.ReservationRepository;
import service.*;
import service.strategy.EmailNotificationStrategy;
import service.strategy.NotificationStrategy;
import util.Logger;
import util.Logger.LogLevel;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AirLibSystem {
    private final CommandFactory commandFactory;
    private final Logger logger;
    private final ReservationService reservationService;
    private final Library library;

    public AirLibSystem(Scanner scanner) {
        this.logger = new Logger(LogLevel.INFO, true);
        this.library = initializeLibrary(scanner);
        
        BookRepository bookRepository = new BookRepository();
        PatronRepository patronRepository = new PatronRepository();
        ReservationRepository reservationRepository = new ReservationRepository();
        NotificationStrategy emailStrategy = new EmailNotificationStrategy();
        NotificationService notificationService = new NotificationService(logger, emailStrategy);

        
        PatronService patronService = new PatronService(patronRepository, logger);
        this.reservationService = new ReservationService(reservationRepository, notificationService, bookRepository, logger);
        RecommendationService recommendationService = new RecommendationService(reservationRepository, bookRepository);
        LibraryService libraryService = new LibraryService(library, logger);
        BookService bookService = new BookService(bookRepository, libraryService, logger);

        this.commandFactory = new CommandFactory(bookService, patronService, this.reservationService, recommendationService, libraryService, logger);

        logger.info("AirLib System initialized");
        // startScheduledTasks();
    }

    private void startScheduledTasks() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule overdue book check daily
        scheduler.scheduleAtFixedRate(() -> {
            try {
                new CheckOverdueBooksCommand(reservationService).execute(List.of());
            } catch (Exception e) {
                logger.exception("Error checking overdue books", e);
            }
        }, 1, 24, TimeUnit.HOURS);

        // Schedule upcoming due date check daily
        scheduler.scheduleAtFixedRate(() -> {
            try {
                reservationService.checkUpcomingDueBooks();
            } catch (Exception e) {
                logger.exception("Error checking upcoming due books", e);
            }
        }, 1, 24, TimeUnit.HOURS);

        // Schedule reservation fulfillment check hourly
        scheduler.scheduleAtFixedRate(() -> {
            try {
                reservationService.checkAndFulfillReservations();
            } catch (Exception e) {
                logger.exception("Error checking and fulfilling reservations", e);
            }
        }, 1, 1, TimeUnit.HOURS);
    }

    private Library initializeLibrary(Scanner scanner) {
        System.out.println("Welcome to AirLib Library Management System Setup!");
        System.out.print("Enter the name of the library: ");
        String libraryName = scanner.nextLine();
        return new Library(libraryName);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AirLibSystem system = new AirLibSystem(scanner);
        system.run(scanner);
        scanner.close();
    }

    private void run(Scanner scanner) {        
        System.out.println("Welcome to " + library.getName());
        System.out.println("Type 'HELP' to see available commands or 'EXIT' to quit the program.");

        while (true) {
            System.out.print("\nEnter command: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("EXIT")) {
                logger.info("System shutdown initiated");
                logger.info("Exiting. Thank you for using AirLib!");
                break;
            }

            List<String> parts = Arrays.asList(input.split("\\s+"));
            if (parts.isEmpty()) {
                continue;
            }

            String commandName = parts.get(0).toUpperCase();
            List<String> args = parts.subList(1, parts.size());

            logger.info("Command executed: " + input);

            Command command = commandFactory.getCommand(commandName);
            if (command == null) {
                logger.info("Invalid command. Type 'HELP' to see available commands.");
                continue;
            }

            try {
                command.execute(args);
            } catch (Exception e) {
                logger.exception("An error occurred during command execution", e);
                logger.info("An error occurred: " + e.getMessage());
            }
        }
    }
}
