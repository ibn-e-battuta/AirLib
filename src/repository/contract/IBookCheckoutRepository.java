package repository.contract;

import java.util.List;

import model.Book;
import model.BookCheckout;
import model.Patron;
import repository.contract.base.IBaseRepository;

public interface IBookCheckoutRepository extends IBaseRepository<BookCheckout, String> {
    List<BookCheckout> getByPatron(final Patron patron);

    List<Book> getMostPopularBooks(final int limit);
}