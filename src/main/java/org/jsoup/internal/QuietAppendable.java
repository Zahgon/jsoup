package org.jsoup.internal;

import org.jsoup.SerializationException;
import java.io.IOException;

/**
 * A jsoup internal class to wrap an Appendable and throw IOExceptions as SerializationExceptions.
 * <p>Only implements the appendable methods we actually use.</p>
 */
public abstract class QuietAppendable {

    public abstract QuietAppendable append(CharSequence csq);

    public abstract QuietAppendable append(char c);

    // via StringBuilder, not Appendable
    public abstract QuietAppendable append(char[] chars, int offset, int len);

    static final class BaseAppendable extends QuietAppendable {

        private final Appendable a;

        @FunctionalInterface
        private interface Action {

            void append() throws IOException;
        }

        private BaseAppendable(Appendable appendable) {
            this.a = appendable;
        }

        private BaseAppendable quiet(Action action) {
            try {
                action.append();
            } catch (IOException e) {
                throw new SerializationException(e);
            }
            return this;
        }

        @Override
        public BaseAppendable append(CharSequence csq) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public BaseAppendable append(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public QuietAppendable append(char[] chars, int offset, int len) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * A version that wraps a StringBuilder, and so doesn't need the exception wrap.
     */
    static final class StringBuilderAppendable extends QuietAppendable {

        private final StringBuilder sb;

        private StringBuilderAppendable(StringBuilder sb) {
            this.sb = sb;
        }

        @Override
        public StringBuilderAppendable append(CharSequence csq) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public StringBuilderAppendable append(char c) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public QuietAppendable append(char[] chars, int offset, int len) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static QuietAppendable wrap(Appendable a) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
