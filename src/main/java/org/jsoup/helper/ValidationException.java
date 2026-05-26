package org.jsoup.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Validation exceptions, as thrown by the methods in {@link Validate}.
 */
public class ValidationException extends IllegalArgumentException {

    public static final String Validator = Validate.class.getName();

    public ValidationException(String msg) {
        super(msg);
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
