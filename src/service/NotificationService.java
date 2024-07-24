package service;

import model.Book;
import model.BookItem;
import model.Patron;
import service.strategy.NotificationStrategy;
import util.Logger;

public class NotificationService implements NotificationStrategy {
    private final Logger logger;
    private final NotificationStrategy strategy;

    public NotificationService(Logger logger, NotificationStrategy strategy) {
        this.logger = logger;
        this.strategy = strategy;
    }

    @Override
    public void sendOverdueNotification(Patron patron, BookItem bookItem) {
        String message = "Dear " + patron.getName() + ", the book '" + bookItem.getBook().getTitle() + "' is overdue.";
        logger.info("Sending overdue notification: " + message);
        strategy.sendOverdueNotification(patron, bookItem);
    }

    @Override
    public void sendDueDateReminder(Patron patron, BookItem bookItem) {
        String message = "Dear " + patron.getName() + ", the book '" + bookItem.getBook().getTitle() + "' is due in 2 days.";
        logger.info("Sending due date reminder: " + message);
        strategy.sendDueDateReminder(patron, bookItem);
    }

    @Override
    public void sendBookAvailableNotification(Patron patron, Book book) {
        String message = "Dear " + patron.getName() + ", the book '" + book.getTitle() + "' is now available for checkout.";
        logger.info("Sending book available notification: " + message);
        strategy.sendBookAvailableNotification(patron, book);
    }

    @Override
    public void sendReservationFulfilledNotification(Patron patron, Book book) {
        String message = "Dear " + patron.getName() + ", your reservation for the book '" + book.getTitle() + "' has been fulfilled.";
        logger.info("Sending reservation fulfilled notification: " + message);
        strategy.sendReservationFulfilledNotification(patron, book);
    }
}
