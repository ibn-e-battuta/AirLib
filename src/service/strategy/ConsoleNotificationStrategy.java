package service.strategy;

import model.Book;
import model.BookCopy;
import model.Patron;

public class ConsoleNotificationStrategy implements NotificationStrategy {
    @Override
    public void sendOverdueNotification(Patron patron, BookCopy bookCopy) {
        System.out.println("Sending notification to " + patron.getName() + ": Overdue book - " + bookCopy.getBook().getTitle());
    }

    @Override
    public void sendBookAvailableNotification(Patron patron, Book book) {
        System.out.println("Sending email to " + patron.getEmail() + ": Book available - " + book.getTitle());
    }
}
