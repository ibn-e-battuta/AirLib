package exception.category;

import exception.base.AirLibException;

public abstract class AlreadyExistsException extends AirLibException {
    protected AlreadyExistsException(final String entity, final String identifier) {
        super(entity + " with identifier " + identifier + " already exists");
    }
}