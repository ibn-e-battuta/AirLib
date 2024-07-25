package service;

import model.Book;
import model.BookCopy;
import model.Patron;
import service.strategy.NotificationStrategy;
import util.Logger;

public class NotificationService {
    private final Logger logger;
    private final NotificationStrategy strategy;

    public NotificationService(Logger logger, NotificationStrategy strategy) {
        this.logger = logger;
        this.strategy = strategy;
    }

    public void sendOverdueNotification(Patron patron, BookCopy bookCopy) {
        String message = "Dear " + patron.getName() + ", the book '" + bookCopy.getBook().getTitle() + "' is overdue.";
        logger.info("Sending overdue notification: " + message);
        strategy.sendOverdueNotification(patron, bookCopy);
    }

    public void sendBookAvailableNotification(Patron patron, Book book) {
        String message = "Dear " + patron.getName() + ", the book '" + book.getTitle() + "' is now available for checkout.";
        logger.info("Sending book available notification: " + message);
        strategy.sendBookAvailableNotification(patron, book);
    }
}
