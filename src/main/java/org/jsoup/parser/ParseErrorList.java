package org.jsoup.parser;

import java.util.ArrayList;

/**
 * A container for ParseErrors.
 *
 * @author Jonathan Hedley
 */
public class ParseErrorList extends ArrayList<ParseError> {

    private static final int INITIAL_CAPACITY = 16;

    private final int initialCapacity;

    private final int maxSize;

    ParseErrorList(int initialCapacity, int maxSize) {
        super(initialCapacity);
        this.initialCapacity = initialCapacity;
        this.maxSize = maxSize;
    }

    /**
     *     Create a new ParseErrorList with the same settings, but no errors in the list
     *     @param copy initial and max size details to copy
     */
    ParseErrorList(ParseErrorList copy) {
        this(copy.initialCapacity, copy.maxSize);
    }

    boolean canAddError() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int getMaxSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ParseErrorList noTracking() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static ParseErrorList tracking(int maxSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object clone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
