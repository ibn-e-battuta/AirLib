package exception.category;

import exception.base.AirLibException;

public abstract class LimitExceededException extends AirLibException {
    protected LimitExceededException(final String entity, final String identifier, final int maxCount,
            final String operation) {
        super(entity + " with identifier " + identifier + ", has reached maximum number (" + maxCount + ") of "
                + operation);
    }
}