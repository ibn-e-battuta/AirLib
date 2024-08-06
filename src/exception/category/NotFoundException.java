package exception.category;

import exception.base.AirLibException;

public abstract class NotFoundException extends AirLibException {
    protected NotFoundException(final String entity, final String identifier) {
        super(entity + " with identifier: " + identifier + " not found");
    }
}