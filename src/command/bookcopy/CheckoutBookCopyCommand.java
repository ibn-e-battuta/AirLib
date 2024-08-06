package command.bookcopy;

import java.util.List;

import command.Command;
import service.BookCheckoutService;

public class CheckoutBookCopyCommand implements Command {
    private final BookCheckoutService bookCheckoutService;

    public CheckoutBookCopyCommand(BookCheckoutService reservationService) {
        this.bookCheckoutService = reservationService;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 3) {
            throw new IllegalArgumentException("Usage: CHECKOUT-BOOK-COPY [patronId] [bookCopyId] [branchCode]");
        }

        final String patronId = args.get(0);
        final String bookCopyId = args.get(1);
        final String branchCode = args.get(2);

        bookCheckoutService.checkoutBook(patronId, bookCopyId, branchCode);
    }
}