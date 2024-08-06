package exception.bookcheckout;

import exception.category.LimitExceededException;
import model.BookCheckout;

public class BookCheckoutMaxRenewalsException extends LimitExceededException {
    public BookCheckoutMaxRenewalsException(final String bookCheckoutId, final int maxRenewals) {
        super(BookCheckout.class.getSimpleName(), bookCheckoutId, maxRenewals, "renewals");
    }
}