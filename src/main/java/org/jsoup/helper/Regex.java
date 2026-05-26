package org.jsoup.helper;

import org.jsoup.internal.SharedConstants;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * A regular expression abstraction. Allows jsoup to optionally use the re2j regular expression engine (linear time)
 * instead of the JDK's backtracking regex implementation.
 *
 * <p>If the {@code com.google.re2j} library is found on the classpath, by default it will be used. You can override this
 * by setting {@code -Djsoup.useRe2j=false} to explicitly disable, and use the JDK regex engine.</p>
 *
 * <p>(Currently this a simplified implementation for jsoup's specific use; can extend as required.)</p>
 */
public class Regex {

    private static final boolean hasRe2j = hasRe2j();

    private final Pattern jdkPattern;

    Regex(Pattern jdkPattern) {
        this.jdkPattern = jdkPattern;
    }

    /**
     *     Compile a regex, using re2j if enabled and available; otherwise JDK regex.
     *
     *     @param regex the regex to compile
     *     @return the compiled regex
     *     @throws ValidationException if the regex is invalid
     */
    public static Regex compile(String regex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Wraps an existing JDK Pattern (for API compat); doesn't switch
     */
    public static Regex fromPattern(Pattern pattern) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Checks if re2j is available (on classpath) and enabled (via system property).
     *     @return true if re2j is available and enabled
     */
    public static boolean usingRe2j() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static boolean wantsRe2j() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static void wantsRe2j(boolean use) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static boolean hasRe2j() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Matcher matcher(CharSequence input) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public interface Matcher {

        boolean find();
    }

    private static final class JdkMatcher implements Matcher {

        private final java.util.regex.Matcher delegate;

        JdkMatcher(java.util.regex.Matcher delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean find() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
