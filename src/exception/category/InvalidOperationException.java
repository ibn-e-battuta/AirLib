package exception.category;

import exception.base.AirLibException;

public abstract class InvalidOperationException extends AirLibException {
    protected InvalidOperationException(final String entity, final String identifier, final String message) {
        super(entity + " with identifier " + identifier + " " + message);
    }
}