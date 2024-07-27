package exception.bookcheckout;

import exception.category.NotFoundException;
import model.BookCheckout;

public class BookCheckoutNotFoundException extends NotFoundException {
    public BookCheckoutNotFoundException(final String bookCheckoutId) {
        super(BookCheckout.class.getSimpleName(), bookCheckoutId);
    }
}