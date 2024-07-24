package command.book;

import java.util.List;

import command.Command;
import model.Book;
import model.SearchResponse;
import service.BookService;

public class SearchBooksCommand implements Command {
    private final BookService bookService;

    public SearchBooksCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 2) {
            throw new IllegalArgumentException("Usage: SEARCH-BOOKS [searchBy] [query]");
        }

        String searchBy = args.get(0);
        String query = args.get(1);

        List<Book> books = bookService.searchBooks(query, searchBy);
        System.out.println("Search results:");
        for (Book book : books) {
            System.out.println(new SearchResponse(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublicationYear()));
        }
    }
}
