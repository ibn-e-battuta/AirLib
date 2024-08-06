package service.strategy;

import model.Book;
import model.Patron;

public interface INotificationStrategy {
    void sendBookAvailableNotification(final Patron patron, final Book book);
}