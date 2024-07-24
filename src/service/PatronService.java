package service;

import exception.PatronExistsException;
import exception.PatronNotFoundException;
import model.Patron;
import repository.PatronRepository;
import util.Logger;

public class PatronService {
    private final PatronRepository patronRepository;
    private final Logger logger;

    private static String patronCode = "P";
    private int patronCount = 0;

    public PatronService(PatronRepository patronRepository, Logger logger) {
        this.patronRepository = patronRepository;
        this.logger = logger;
    }

    public void addPatron(String name, String email) throws PatronExistsException {
        var response = patronRepository.getPatronByEmail(email);
        if (response.isPresent()) {
            throw new PatronExistsException("Patron with email " + email + " already exists.");
        }

        patronCount++;
        var patron = new Patron(patronCode + patronCount, name, email);
        patronRepository.addPatron(patron);
        logger.info("Patron added: " + patron.getId());
    }

    public Patron getPatron(String id) throws PatronNotFoundException {
        return patronRepository.getPatron(id)
            .orElseThrow(() -> new PatronNotFoundException("Patron with id: " + id + " not found."));
    }

    public void addPreference(Patron patron, String preference) {
        patron.addPreference(preference);
        patronRepository.updatePatron(patron);
        logger.info("Preference added for patron: " + patron.getId());
    }

    public void updatePatron(String id, String name, String email) throws PatronNotFoundException {
        var response = patronRepository.getPatron(id);

        if (!response.isPresent()) {
            throw new PatronNotFoundException("Patron with id: " + id + " not found.");
        }

        var patron = response.get();
        patron.setName(name);
        patron.setEmail(email);

        patronRepository.updatePatron(patron);

        logger.info("Patron updated: " + id);
    }
}
