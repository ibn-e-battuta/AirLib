package repository.contract;

import java.util.List;

import model.Book;
import repository.contract.base.IBaseRepository;

public interface IBookRepository extends IBaseRepository<Book, String> {
    List<Book> findByGenre(final String genre);

    List<Book> findByAuthor(final String author);

    List<Book> findByTitleOrGenreOrAuthor(final String query);

    List<Book> search(final String query, final String searchBy);

    void remove(final String isbn);
}