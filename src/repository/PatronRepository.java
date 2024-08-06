package repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import model.Patron;
import repository.contract.IPatronRepository;

public class PatronRepository implements IPatronRepository {
    private final Map<String, Patron> patrons = new HashMap<>();

    public void add(final Patron patron) {
        patrons.put(patron.getId(), patron);
    }

    public void update(final Patron patron) {
        patrons.put(patron.getId(), patron);
    }

    public Optional<Patron> getById(final String patronId) {
        return patrons.values().stream().filter(p -> p.getId().equals(patronId)).findAny();
    }

    public Optional<Patron> getByEmail(final String email) {
        return Optional.ofNullable(patrons.get(email));
    }
}