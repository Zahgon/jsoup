package org.jsoup.parser;

import org.jsoup.internal.StringUtil;
import org.jsoup.helper.Validate;

/**
 * A character reader with helpers focusing on parsing CSS selectors. Used internally by jsoup. API subject to changes.
 */
public class TokenQueue implements AutoCloseable {

    // escape char for chomp balanced.
    private static final char Esc = '\\';

    private static final char Hyphen_Minus = '-';

    private static final char Unicode_Null = '\u0000';

    private static final char Replacement = '\uFFFD';

    private final CharacterReader reader;

    /**
     *     Create a new TokenQueue.
     *     @param data string of data to back queue.
     */
    public TokenQueue(String data) {
        reader = new CharacterReader(data);
    }

    /**
     *     Is the queue empty?
     *     @return true if no data left in queue.
     */
    public boolean isEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Consume one character off queue.
     *     @return first character on queue.
     */
    public char consume() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Drops the next character off the queue.
     */
    public void advance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    char current() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Tests if the next characters on the queue match the sequence, case-insensitively.
     *     @param seq String to check queue for.
     *     @return true if the next characters match.
     */
    public boolean matches(String seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests if the next character on the queue matches the character, case-sensitively.
     */
    public boolean matches(char c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Tests if the next characters match any of the sequences, case-<b>sensitively</b>.
     *     @param seq list of chars to case-sensitively check for
     *     @return true of any matched, false if none did
     */
    public boolean matchesAny(char... seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     If the queue case-insensitively matches the supplied string, consume it off the queue.
     *     @param seq String to search for, and if found, remove from queue.
     *     @return true if found and removed, false if not found.
     */
    public boolean matchChomp(String seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If the queue matches the supplied (case-sensitive) character, consume it off the queue.
     */
    public boolean matchChomp(char c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Tests if queue starts with a whitespace character.
     *     @return if starts with whitespace
     */
    public boolean matchesWhitespace() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Test if the queue matches a tag word character (letter or digit).
     *     @return if matches a word character
     */
    public boolean matchesWord() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Consumes the supplied sequence of the queue, case-insensitively. If the queue does not start with the supplied
     *     sequence, will throw an illegal state exception -- but you should be running match() against that condition.
     *
     *     @param seq sequence to remove from head of queue.
     */
    public void consume(String seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Pulls a string off the queue, up to but exclusive of the match sequence, or to the queue running out.
     *     @param seq String to end on (and not include in return, but leave on queue). <b>Case-sensitive.</b>
     *     @return The matched data consumed from queue.
     */
    public String consumeTo(String seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Consumes to the first sequence provided, or to the end of the queue. Leaves the terminator on the queue.
     *     @param seq any number of terminators to consume to. <b>Case-insensitive.</b>
     *     @return consumed string
     */
    public String consumeToAny(String... seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Pulls a balanced string off the queue. E.g. if queue is "(one (two) three) four", (,) will return "one (two) three",
     *     and leave " four" on the queue. Unbalanced openers and closers can be quoted (with ' or ") or escaped (with \).
     *     Those escapes will be left in the returned string, which is suitable for regexes (where we need to preserve the
     *     escape), but unsuitable for contains text strings; use unescape for that.
     *
     *     @param open opener
     *     @param close closer
     *     @return data matched from the queue
     */
    public String chompBalanced(char open, char close) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Unescape a \ escaped string.
     * @param in backslash escaped string
     * @return unescaped string
     */
    public static String unescape(String in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Given a CSS identifier (such as a tag, ID, or class), escape any CSS special characters that would otherwise not be
     *     valid in a selector.
     *
     *     @see <a href="https://www.w3.org/TR/cssom-1/#serialize-an-identifier">CSS Object Model, serialize an identifier</a>
     */
    public static String escapeCssIdentifier(String in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static void appendEscaped(StringBuilder out, char c) {
        out.append(Esc).append(c);
    }

    private static void appendEscapedCodepoint(StringBuilder out, char c) {
        out.append(Esc).append(Integer.toHexString(c)).append(' ');
    }

    /**
     * Pulls the next run of whitespace characters of the queue.
     * @return Whether consuming whitespace or not
     */
    public boolean consumeWhitespace() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Consume a CSS element selector (tag name, but | instead of : for namespaces (or *| for wildcard namespace), to not conflict with :pseudo selects).
     *
     * @return tag name
     */
    public String consumeElementSelector() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static final char[] ElementSelectorChars = { '*', '|', '_', '-' };

    /**
     *     Consume a CSS identifier (ID or class) off the queue.
     *     <p>Note: For backwards compatibility this method supports improperly formatted CSS identifiers, e.g. {@code 1} instead
     *     of {@code \31}.</p>
     *
     *     @return The unescaped identifier.
     *     @throws IllegalArgumentException if an invalid escape sequence was found. Afterward, the state of the TokenQueue
     *     is undefined.
     *     @see <a href="https://www.w3.org/TR/css-syntax-3/#consume-name">CSS Syntax Module Level 3, Consume an ident sequence</a>
     *     @see <a href="https://www.w3.org/TR/css-syntax-3/#typedef-ident-token">CSS Syntax Module Level 3, ident-token</a>
     */
    public String consumeCssIdentifier() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void consumeCssEscapeSequenceInto(StringBuilder out) {
        if (isEmpty()) {
            out.append(Replacement);
            return;
        }
        char firstEscaped = consume();
        if (!StringUtil.isHexDigit(firstEscaped)) {
            out.append(firstEscaped);
        } else {
            // put back the first hex digit
            reader.unconsume();
            // consume up to 6 hex digits
            String hexString = reader.consumeMatching(StringUtil::isHexDigit, 6);
            int codePoint;
            try {
                codePoint = Integer.parseInt(hexString, 16);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid escape sequence: " + hexString, e);
            }
            if (isValidCodePoint(codePoint)) {
                out.appendCodePoint(codePoint);
            } else {
                out.append(Replacement);
            }
            if (!isEmpty()) {
                char c = current();
                if (c == '\r') {
                    // Since there's currently no input preprocessing, check for CRLF here.
                    // https://www.w3.org/TR/css-syntax-3/#input-preprocessing
                    advance();
                    if (!isEmpty() && current() == '\n')
                        advance();
                } else if (c == ' ' || c == '\t' || isNewline(c)) {
                    advance();
                }
            }
        }
    }

    // statics below specifically for CSS identifiers:
    // https://www.w3.org/TR/css-syntax-3/#non-ascii-code-point
    private static boolean isNonAscii(char c) {
        return c >= '\u0080';
    }

    // https://www.w3.org/TR/css-syntax-3/#ident-start-code-point
    private static boolean isIdentStart(char c) {
        return c == '_' || StringUtil.isAsciiLetter(c) || isNonAscii(c);
    }

    // https://www.w3.org/TR/css-syntax-3/#ident-code-point
    private static boolean isIdent(char c) {
        return c == Hyphen_Minus || StringUtil.isDigit(c) || isIdentStart(c);
    }

    // https://www.w3.org/TR/css-syntax-3/#newline
    // Note: currently there's no preprocessing happening.
    private static boolean isNewline(char c) {
        return c == '\n' || c == '\r' || c == '\f';
    }

    // https://www.w3.org/TR/css-syntax-3/#consume-an-escaped-code-point
    private static boolean isValidCodePoint(int codePoint) {
        return codePoint != 0 && Character.isValidCodePoint(codePoint) && !Character.isSurrogate((char) codePoint);
    }

    private static final char[] CssIdentifierChars = { '-', '_' };

    private String consumeEscapedCssIdentifier(char... matches) {
        StringBuilder sb = StringUtil.borrowBuilder();
        while (!isEmpty()) {
            char c = current();
            if (c == Esc) {
                advance();
                if (!isEmpty())
                    sb.append(consume());
                else
                    break;
            } else if (matchesCssIdentifier(matches)) {
                sb.append(c);
                advance();
            } else {
                break;
            }
        }
        return StringUtil.releaseBuilder(sb);
    }

    private boolean matchesCssIdentifier(char... matches) {
        return matchesWord() || reader.matchesAny(matches);
    }

    /**
     *     Consume and return whatever is left on the queue.
     *     @return remainder of queue.
     */
    public String remainder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
