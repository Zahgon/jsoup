package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jspecify.annotations.Nullable;

/**
 * A value holder for Tokens, as the stream is Tokenized. Can hold a String or a StringBuilder.
 * <p>The goal is to minimize String copies -- the tokenizer tries to read the entirety of the token's data in one it, and
 * set that as the simple String value. But if it turns out we need to append, fall back to a StringBuilder, which we get
 * out of the pool (to reduce the GC load).</p>
 */
class TokenData {

    @Nullable
    private String value;

    @Nullable
    private StringBuilder builder;

    TokenData() {
    }

    void set(String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void append(String str) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void append(char c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void appendCodePoint(int codepoint) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void flipToBuilder() {
        builder = StringUtil.borrowBuilder();
        builder.append(value);
        value = null;
    }

    boolean hasData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void reset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String value() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
