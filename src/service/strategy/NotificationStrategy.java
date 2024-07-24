package service.strategy;

import model.Book;
import model.BookItem;
import model.Patron;

public interface NotificationStrategy {
    void sendOverdueNotification(Patron patron, BookItem bookItem);
    void sendDueDateReminder(Patron patron, BookItem bookItem);
    void sendBookAvailableNotification(Patron patron, Book book);
    void sendReservationFulfilledNotification(Patron patron, Book book);
}
