package service;

import exception.PatronExistsException;
import exception.PatronNotFoundException;
import model.Patron;
import model.response.PatronResponse;
import repository.PatronRepository;
import util.Logger;

import java.util.List;

import static util.Constants.PATRON_CODE;

public class PatronService {
    private final PatronRepository patronRepository;
    private final Logger logger;

    private int patronCount = 0;

    public PatronService(PatronRepository patronRepository, Logger logger) {
        this.patronRepository = patronRepository;
        this.logger = logger;
    }

    public void addPatron(String name, String email, List<String> preferences) throws PatronExistsException {
        var response = patronRepository.getPatronByEmail(email);
        if (response.isPresent()) {
            throw new PatronExistsException(email);
        }

        patronCount++;
        var patron = new Patron(PATRON_CODE + patronCount, name, email, preferences);
        patronRepository.addPatron(patron);
        logger.info("Patron added: " + patron.getId());
    }

    public PatronResponse getPatron(String patronId) throws PatronNotFoundException {
        var patron = patronRepository.getPatron(patronId)
            .orElseThrow(() -> new PatronNotFoundException(patronId));

        return new PatronResponse(patron.getName(), patron.getEmail(), patron.getBorrowingHistory().size());
    }

    public void updatePatron(String patronId, String name, String email, List<String> preferences) throws PatronNotFoundException {
        var patron = patronRepository.getPatron(patronId).orElseThrow(() -> new PatronNotFoundException(patronId));

        patron.setName(name);
        patron.setEmail(email);
        patron.setPreferences(preferences);

        patronRepository.updatePatron(patron);
        logger.info("Patron updated: " + patron.getId());
    }
}
