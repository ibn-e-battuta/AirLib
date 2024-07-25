package repository;

import model.Patron;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PatronRepository {
    private final Map<String, Patron> patrons = new HashMap<>();

    public void addPatron(Patron patron) {
        patrons.put(patron.getId(), patron);
    }

    public void updatePatron(Patron patron) {
        patrons.put(patron.getId(), patron);
    }

    public Optional<Patron> getPatron(String patronId) {
        return patrons.values().stream().filter(p -> p.getId().equals(patronId)).findAny();
    }

    public Optional<Patron> getPatronByEmail(String email) {
        return Optional.ofNullable(patrons.get(email));
    }
}
