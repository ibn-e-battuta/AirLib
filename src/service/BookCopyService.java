package service;

import java.util.List;

import exception.book.BookNotFoundException;
import exception.bookcopy.BookCopyNotFoundException;
import exception.librarybranch.LibraryBranchNotFoundException;
import model.Book;
import model.BookCopy;
import model.LibraryBranch;
import repository.contract.IBookCopyRepository;
import repository.contract.IBookRepository;
import repository.contract.ILibraryBranchRepository;
import response.BookCopyResponse;
import response.BookResponse;
import service.enums.EntityCode;
import util.Logger;

public class BookCopyService {
    private final IBookRepository bookRepository;
    private final IBookCopyRepository bookCopyRepository;
    private final ILibraryBranchRepository libraryBranchRepository;
    private final Logger logger;

    private int bookCopyCount = 0;

    public BookCopyService(IBookRepository bookRepository, IBookCopyRepository bookCopyRepository,
            ILibraryBranchRepository libraryBranchRepository, Logger logger) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.libraryBranchRepository = libraryBranchRepository;
        this.logger = logger;
    }

    public void addBookCopy(final String isbn, final String branchCode) {
        final Book book = bookRepository.getById(isbn).orElseThrow(() -> new BookNotFoundException(isbn));
        final LibraryBranch branch = libraryBranchRepository.getById(branchCode)
                .orElseThrow(() -> new LibraryBranchNotFoundException(branchCode));

        bookCopyCount++;
        final BookCopy bookCopy = new BookCopy(EntityCode.BOOK_COPY.getCode() + bookCopyCount, book, branch);
        bookCopyRepository.add(bookCopy);

        branch.addBookCopy(bookCopy);
        libraryBranchRepository.update(branch);

        logger.consoleOutput("Book copy added: " + bookCopy.getId());
    }

    public BookCopy getBookCopy(final String bookCopyId) {
        return bookCopyRepository.getById(bookCopyId)
                .orElseThrow(() -> new BookCopyNotFoundException(bookCopyId));
    }

    public void transferBookCopy(final String bookCopyId, final String sourceLibraryBranchCode,
            final String destinationLibraryBranchCode) {
        final BookCopy bookCopy = bookCopyRepository.getById(bookCopyId)
                .orElseThrow(() -> new BookCopyNotFoundException(bookCopyId));

        LibraryBranch sourceLibraryBranch = libraryBranchRepository.getById(sourceLibraryBranchCode)
                .orElseThrow(() -> new LibraryBranchNotFoundException(sourceLibraryBranchCode));
        LibraryBranch destinationLibraryBranch = libraryBranchRepository.getById(destinationLibraryBranchCode)
                .orElseThrow(() -> new LibraryBranchNotFoundException(destinationLibraryBranchCode));

        sourceLibraryBranch.removeBookCopy(bookCopy);
        destinationLibraryBranch.addBookCopy(bookCopy);
        bookCopy.setLibraryBranch(destinationLibraryBranch);

        logger.info("Book copy transferred: " + bookCopy.getId() + " from " + sourceLibraryBranch.getName()
                + " to "
                + destinationLibraryBranch.getName());
    }

    public void transferAllBookCopies(final String sourceCode, final String destinationCode) {
        final LibraryBranch sourceLibraryBranch = libraryBranchRepository.getById(sourceCode)
                .orElseThrow(() -> new LibraryBranchNotFoundException(sourceCode));
        final LibraryBranch destinationLibraryBranch = libraryBranchRepository.getById(destinationCode)
                .orElseThrow(() -> new LibraryBranchNotFoundException(destinationCode));

        List<BookCopy> bookCopies = bookCopyRepository.getAllByBranchCode(sourceLibraryBranch.getId());

        int transferredCount = 0;
        for (BookCopy bookCopy : bookCopies) {
            bookCopy.setLibraryBranch(destinationLibraryBranch);
            sourceLibraryBranch.removeBookCopy(bookCopy);
            destinationLibraryBranch.addBookCopy(bookCopy);
            bookCopyRepository.update(bookCopy);
            transferredCount++;
        }

        logger.info(transferredCount + " books transferred from " + sourceLibraryBranch.getId() + " to "
                + sourceLibraryBranch.getId());
    }

    public BookCopyResponse printBookCopy(final String bookCopyId) {
        final BookCopy bookCopy = getBookCopy(bookCopyId);

        return new BookCopyResponse(
                bookCopy.getId(),
                bookCopy.getBook().getTitle(),
                bookCopy.getBook().getAuthors(),
                bookCopy.getBranch().getName(),
                bookCopy.isAvailable());
    }

    public List<BookResponse> getBooksByAvailability(final boolean isAvailable) {
        return bookCopyRepository.getAllByAvailability(isAvailable).stream()
                .map(BookCopy::getBook)
                .distinct()
                .map(BookResponse::new)
                .toList();
    }

    public List<BookResponse> getBooksByBranchAndAvailability(final String branchCode, final boolean isAvailable) {
        final LibraryBranch libraryBranch = libraryBranchRepository.getById(branchCode)
                .orElseThrow(() -> new LibraryBranchNotFoundException(branchCode));

        return bookCopyRepository.getAllByBranchAndAvailability(libraryBranch, isAvailable).stream()
                .map(BookCopy::getBook)
                .distinct()
                .map(BookResponse::new)
                .toList();
    }
}