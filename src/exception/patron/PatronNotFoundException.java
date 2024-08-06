package exception.patron;

import exception.category.NotFoundException;
import model.Patron;

public class PatronNotFoundException extends NotFoundException {
    public PatronNotFoundException(String patronId) {
        super(Patron.class.getSimpleName(), patronId);
    }
}