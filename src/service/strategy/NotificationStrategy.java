package service.strategy;

import model.Book;
import model.BookCopy;
import model.Patron;

public interface NotificationStrategy {
    void sendOverdueNotification(Patron patron, BookCopy bookCopy);
    void sendBookAvailableNotification(Patron patron, Book book);
}
