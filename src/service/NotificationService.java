package service;

import model.Book;
import model.Patron;
import service.strategy.INotificationStrategy;
import util.Logger;

public class NotificationService {
    private final INotificationStrategy notificationStrategy;
    private final Logger logger;

    public NotificationService(INotificationStrategy notificationStrategy, Logger logger) {
        this.notificationStrategy = notificationStrategy;
        this.logger = logger;
    }

    public void sendBookAvailableNotification(final Patron patron, final Book book) {
        String message = "Dear " + patron.getName() + ", the book '" + book.getTitle()
                + "' is now available for checkout";
        logger.info("Sending book available notification: " + message);
        notificationStrategy.sendBookAvailableNotification(patron, book);
    }
}