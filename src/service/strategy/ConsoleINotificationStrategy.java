package service.strategy;

import model.Book;
import model.Patron;
import util.Logger;

public class ConsoleINotificationStrategy implements INotificationStrategy {
    private final Logger logger;

    public ConsoleINotificationStrategy(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void sendBookAvailableNotification(Patron patron, Book book) {
        logger.info("Sending email to " + patron.getEmail() + ": Book available - " + book.getTitle());
    }
}