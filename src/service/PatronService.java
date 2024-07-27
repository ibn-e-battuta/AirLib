package service;

import java.util.List;
import java.util.Optional;

import exception.patron.PatronExistsException;
import exception.patron.PatronNotFoundException;
import model.Patron;
import repository.contract.IPatronRepository;
import response.BookCheckoutResponse;
import response.PatronResponse;
import service.enums.EntityCode;
import util.Logger;

public class PatronService {
    private final IPatronRepository patronRepository;
    private final Logger logger;

    private int patronCount = 0;

    public PatronService(IPatronRepository patronRepository, Logger logger) {
        this.patronRepository = patronRepository;
        this.logger = logger;
    }

    public void addPatron(final String name, final String email, final List<String> preferences) {
        Optional<Patron> optionalPatron = patronRepository.getByEmail(email);
        if (optionalPatron.isPresent()) {
            throw new PatronExistsException(email);
        }

        patronCount++;
        final Patron patron = new Patron(EntityCode.PATRON.getCode() + patronCount, name, email, preferences);
        patronRepository.add(patron);
        logger.consoleOutput("Patron added: " + patron.getId());
    }

    public PatronResponse getPatron(final String patronId) {
        final Patron patron = patronRepository.getById(patronId).orElseThrow(() -> new PatronNotFoundException(patronId));
        return new PatronResponse(patron);
    }

    public void updatePatron(final String patronId, final String name, final String email,
            final List<String> preferences) {
        final Patron patron = patronRepository.getById(patronId).orElseThrow(() -> new PatronNotFoundException(patronId));

        patron.setName(name);
        patron.setEmail(email);
        patron.setPreferences(preferences);

        patronRepository.update(patron);
        logger.consoleOutput("Patron updated: " + patron.getId());
    }

    public List<BookCheckoutResponse> getPatronBookCheckouts(final String patronId) {
        final Patron patron = patronRepository.getById(patronId)
                .orElseThrow(() -> new PatronNotFoundException(patronId));

        return patron.getBookCheckouts().stream()
                .map(c -> new BookCheckoutResponse(c.getBookCopy().getBook().getTitle(), c.getIssueDate().toString(),
                        c.getReturnDate().toString()))
                .toList();
    }
}