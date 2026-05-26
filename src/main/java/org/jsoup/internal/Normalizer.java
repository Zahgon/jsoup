package org.jsoup.internal;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jspecify.annotations.Nullable;
import java.util.Locale;

/**
 * Util methods for normalizing strings. Jsoup internal use only, please don't depend on this API.
 */
public final class Normalizer {

    /**
     * Drops the input string to lower case.
     */
    public static String lowerCase(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Lower-cases and trims the input string.
     */
    public static String normalize(final String input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     If a string literal, just lower case the string; otherwise lower-case and trim.
     *     @deprecated internal helper; replace with {@link #lowerCase(String)} for no-trim, or {@link #normalize(String)} for trim + lowercase.
     *     Will be removed in jsoup 1.24.1.
     */
    @Deprecated
    public static String normalize(final String input, boolean isStringLiteral) {
        return isStringLiteral ? lowerCase(input) : normalize(input);
    }

    /**
     * Minimal helper to get an otherwise OK HTML name like "foo&lt;bar" to "foo_bar".
     */
    @Nullable
    public static String xmlSafeTagName(final String tagname) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
