package service.strategy;

import model.Book;
import model.BookItem;
import model.Patron;

public class EmailNotificationStrategy implements NotificationStrategy {
    @Override
    public void sendOverdueNotification(Patron patron, BookItem bookItem) {
        // In a real implementation, this would use an email service
        System.out.println("Sending email to " + patron.getEmail() + ": Overdue book - " + bookItem.getBook().getTitle());
    }

    @Override
    public void sendDueDateReminder(Patron patron, BookItem bookItem) {
        System.out.println("Sending email to " + patron.getEmail() + ": Due date reminder - " + bookItem.getBook().getTitle());
    }

    @Override
    public void sendBookAvailableNotification(Patron patron, Book book) {
        System.out.println("Sending email to " + patron.getEmail() + ": Book available - " + book.getTitle());
    }

    @Override
    public void sendReservationFulfilledNotification(Patron patron, Book book) {
        System.out.println("Sending email to " + patron.getEmail() + ": Reservation fulfilled - " + book.getTitle());
    }
}
