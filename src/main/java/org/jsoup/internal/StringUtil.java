package org.jsoup.internal;

import org.jsoup.helper.Validate;
import org.jspecify.annotations.Nullable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * A minimal String utility class. Designed for <b>internal</b> jsoup use only - the API and outcome may change without
 * notice.
 */
public final class StringUtil {

    // memoised padding up to 21 (blocks 0 to 20 spaces)
    static final String[] padding = { "", " ", "  ", "   ", "    ", "     ", "      ", "       ", "        ", "         ", "          ", "           ", "            ", "             ", "              ", "               ", "                ", "                 ", "                  ", "                   ", "                    " };

    /**
     * Join a collection of strings by a separator
     * @param strings collection of string objects
     * @param sep string to place between strings
     * @return joined string
     */
    public static String join(Collection<?> strings, String sep) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Join a collection of strings by a separator
     * @param strings iterator of string objects
     * @param sep string to place between strings
     * @return joined string
     */
    public static String join(Iterator<?> strings, String sep) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Join an array of strings by a separator
     * @param strings collection of string objects
     * @param sep string to place between strings
     * @return joined string
     */
    public static String join(String[] strings, String sep) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     A StringJoiner allows incremental / filtered joining of a set of stringable objects.
     *     @since 1.14.1
     */
    public static class StringJoiner {

        // sets null on builder release so can't accidentally be reused
        @Nullable
        StringBuilder sb = borrowBuilder();

        final String separator;

        boolean first = true;

        /**
         *         Create a new joiner, that uses the specified separator. MUST call {@link #complete()} or will leak a thread
         *         local string builder.
         *
         *         @param separator the token to insert between strings
         */
        public StringJoiner(String separator) {
            this.separator = separator;
        }

        /**
         *         Add another item to the joiner, will be separated
         */
        public StringJoiner add(Object stringy) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Append content to the current item; not separated
         */
        public StringJoiner append(Object stringy) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Return the joined string, and release the builder back to the pool. This joiner cannot be reused.
         */
        public String complete() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Returns space padding (up to the default max of 30). Use {@link #padding(int, int)} to specify a different limit.
     * @param width amount of padding desired
     * @return string of spaces * width
     * @see #padding(int, int)
     */
    public static String padding(int width) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns space padding, up to a max of maxPaddingWidth.
     * @param width amount of padding desired
     * @param maxPaddingWidth maximum padding to apply. Set to {@code -1} for unlimited.
     * @return string of spaces * width
     */
    public static String padding(int width, int maxPaddingWidth) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests if a string is blank: null, empty, or only whitespace (" ", \r\n, \t, etc)
     * @param string string to test
     * @return if string is blank
     */
    public static boolean isBlank(@Nullable String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Tests if a string starts with a newline character
     *     @param string string to test
     *     @return if its first character is a newline
     */
    public static boolean startsWithNewline(final String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests if a string is numeric, i.e. contains only ASCII digit characters
     * @param string string to test
     * @return true if only digit chars, false if empty or null or contains non-digit chars
     */
    public static boolean isNumeric(String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests if a code point is "whitespace" as defined in the HTML spec. Used for output HTML.
     * @param c code point to test
     * @return true if code point is whitespace, false otherwise
     * @see #isActuallyWhitespace(int)
     */
    public static boolean isWhitespace(int c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests if a code point is "whitespace" as defined by what it looks like. Used for Element.text etc.
     * @param c code point to test
     * @return true if code point is whitespace, false otherwise
     */
    public static boolean isActuallyWhitespace(int c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isInvisibleChar(int c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Normalise the whitespace within this string; multiple spaces collapse to a single, and all whitespace characters
     * (e.g. newline, tab) convert to a simple space.
     * @param string content to normalise
     * @return normalised string
     */
    public static String normaliseWhitespace(String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * After normalizing the whitespace within a string, appends it to a string builder.
     * @param accum builder to append to
     * @param string string to normalize whitespace within
     * @param stripLeading set to true if you wish to remove any leading whitespace
     */
    public static void appendNormalisedWhitespace(StringBuilder accum, String string, boolean stripLeading) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean in(final String needle, final String... haystack) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean inSorted(String needle, String[] haystack) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Tests that a String contains only ASCII characters.
     *     @param string scanned string
     *     @return true if all characters are in range 0 - 127
     */
    public static boolean isAscii(String string) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static final Pattern extraDotSegmentsPattern = Pattern.compile("^/(?>(?>\\.\\.?/)+)");

    /**
     * Create a new absolute URL, from a provided existing absolute URL and a relative URL component.
     * @param base the existing absolute base URL
     * @param relUrl the relative URL to resolve. (If it's already absolute, it will be returned)
     * @return the resolved absolute URL
     * @throws MalformedURLException if an error occurred generating the URL
     */
    public static URL resolve(URL base, String relUrl) throws MalformedURLException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a new absolute URL, from a provided existing absolute URL and a relative URL component.
     * @param baseUrl the existing absolute base URL
     * @param relUrl the relative URL to resolve. (If it's already absolute, it will be returned)
     * @return an absolute URL if one was able to be generated, or the empty string if not
     */
    public static String resolve(String baseUrl, String relUrl) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static final Pattern validUriScheme = Pattern.compile("^[a-zA-Z][a-zA-Z0-9+-.]*:");

    // matches ascii 0 - 31, to strip from url
    private static final Pattern controlChars = Pattern.compile("[\\x00-\\x1f]*");

    private static String stripControlChars(final String input) {
        return controlChars.matcher(input).replaceAll("");
    }

    private static final int InitBuilderSize = 1024;

    private static final int MaxBuilderSize = 8 * 1024;

    private static final SoftPool<StringBuilder> BuilderPool = new SoftPool<>(() -> new StringBuilder(InitBuilderSize));

    /**
     * Maintains cached StringBuilders in a flyweight pattern, to minimize new StringBuilder GCs. The StringBuilder is
     * prevented from growing too large.
     * <p>
     * Care must be taken to release the builder once its work has been completed, with {@link #releaseBuilder}
     * @return an empty StringBuilder
     */
    public static StringBuilder borrowBuilder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Release a borrowed builder. Care must be taken not to use the builder after it has been returned, as its
     * contents may be changed by this method, or by a concurrent thread.
     * @param sb the StringBuilder to release.
     * @return the string value of the released String Builder (as an incentive to release it!).
     */
    public static String releaseBuilder(StringBuilder sb) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Releases a borrowed builder, but does not call .toString() on it. Useful in case you already have that string.
     *     @param sb the StringBuilder to release.
     *     @see #releaseBuilder(StringBuilder)
     */
    public static void releaseBuilderVoid(StringBuilder sb) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return a {@link Collector} similar to the one returned by {@link Collectors#joining(CharSequence)},
     * but backed by jsoup's {@link StringJoiner}, which allows for more efficient garbage collection.
     *
     * @param delimiter The delimiter for separating the strings.
     * @return A {@code Collector} which concatenates CharSequence elements, separated by the specified delimiter
     */
    public static Collector<CharSequence, ?, String> joining(String delimiter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isAsciiLetter(char c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isDigit(char c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static boolean isHexDigit(char c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
