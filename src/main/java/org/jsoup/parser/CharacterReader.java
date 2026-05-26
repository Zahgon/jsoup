package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.internal.LineMap;
import org.jsoup.internal.SoftPool;
import org.jsoup.internal.StringUtil;
import org.jspecify.annotations.Nullable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;

/**
 * CharacterReader consumes tokens off a string. Used internally by jsoup. API subject to changes.
 * <p>If the underlying reader throws an IOException during any operation, the CharacterReader will throw an
 * {@link UncheckedIOException}. That won't happen with String / StringReader inputs.</p>
 */
public final class CharacterReader implements AutoCloseable {

    static final char EOF = (char) -1;

    private static final int MaxStringCacheLen = 12;

    private static final int StringCacheSize = 512;

    // holds reused strings in this doc, to lessen garbage
    private String[] stringCache;

    // reuse cache between iterations
    private static final SoftPool<String[]> StringPool = new SoftPool<>(() -> new String[StringCacheSize]);

    // visible for testing
    static final int BufferSize = 1024 * 2;

    // when bufPos characters read, refill; visible for testing
    static final int RefillPoint = BufferSize / 2;

    // the maximum we can rewind. No HTML entities can be larger than this.
    private static final int RewindLimit = 1024;

    // underlying Reader, will be backed by a buffered+controlled input stream, or StringReader
    private Reader reader;

    // character buffer we consume from; filled from Reader
    private char[] charBuf;

    // position in charBuf that's been consumed to
    private int bufPos;

    // the num of characters actually buffered in charBuf, <= charBuf.length
    private int bufLength;

    // how far into the charBuf we read before re-filling. 0.5 of charBuf.length after bufferUp
    private int fillPoint = 0;

    // how many characters total have been consumed from this CharacterReader (less the current bufPos)
    private int consumed;

    // if not -1, the marked rewind position
    private int bufMark = -1;

    // if the underlying stream has been completely read, no value in further buffering
    private boolean readFully;

    // recycled char buffer
    private static final SoftPool<char[]> BufferPool = new SoftPool<>(() -> new char[BufferSize]);

    // optionally maps source offsets to line and column positions
    @Nullable
    private LineMap lineMap = null;

    public CharacterReader(Reader input, int sz) {
        // sz is no longer used
        this(input);
    }

    public CharacterReader(Reader input) {
        Validate.notNull(input);
        reader = input;
        charBuf = BufferPool.borrow();
        stringCache = StringPool.borrow();
        bufferUp();
    }

    public CharacterReader(String input) {
        this(new StringReader(input));
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void bufferUp() {
        if (readFully || bufPos < fillPoint || bufMark != -1)
            return;
        // structured so bufferUp may become an intrinsic candidate
        doBufferUp();
    }

    /**
     *     Reads into the buffer. Will throw an UncheckedIOException if the underling reader throws an IOException.
     *     @throws UncheckedIOException if the underlying reader throws an IOException
     */
    private void doBufferUp() {
        /*
        The flow:
        - if read fully, or if bufPos < fillPoint, or if marked - do not fill.
        - update readerPos (total amount consumed from this CharacterReader) += bufPos
        - shift charBuf contents such that bufPos = 0; set next read offset (bufLength) -= shift amount
        - loop read the Reader until we fill charBuf. bufLength += read.
        - readFully = true when read = -1
         */
        consumed += bufPos;
        bufLength -= bufPos;
        if (bufLength > 0)
            System.arraycopy(charBuf, bufPos, charBuf, 0, bufLength);
        bufPos = 0;
        while (bufLength < BufferSize) {
            try {
                int read = reader.read(charBuf, bufLength, charBuf.length - bufLength);
                if (read == -1) {
                    readFully = true;
                    break;
                }
                if (read == 0) {
                    // if we have a surrogate on the buffer boundary and trying to read 1; will have enough in our buffer to proceed
                    break;
                }
                bufLength += read;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
        fillPoint = Math.min(bufLength, RefillPoint);
        // if enabled, we index newline positions for line number tracking
        scanBufferForNewlines();
        // cache for last containsIgnoreCase(seq)
        lastIcSeq = null;
    }

    void mark() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void unmark() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void rewindToMark() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the position currently read to in the content. Starts at 0.
     * @return current position
     */
    public int pos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests if the buffer has been fully read.
     */
    boolean readFully() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Enables or disables line number tracking. By default, will be <b>off</b>.Tracking line numbers improves the
     *     legibility of parser error messages, for example. Tracking should be enabled before any content is read to be of
     *     use.
     *
     *     @param track set tracking on|off
     *     @since 1.14.3
     */
    public void trackNewlines(boolean track) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Check if the tracking of newlines is enabled.
     *     @return the current newline tracking state
     *     @since 1.14.3
     */
    public boolean isTrackNewlines() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the line map enabled by {@link #trackNewlines(boolean)}.
     */
    LineMap lineMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the current line number (that the reader has consumed to). Starts at line #1.
     *     @return the current line number, or 1 if line tracking is not enabled.
     *     @since 1.14.3
     *     @see #trackNewlines(boolean)
     */
    public int lineNumber() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int lineNumber(int pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the current column number (that the reader has consumed to). Starts at column #1.
     *     @return the current column number
     *     @since 1.14.3
     *     @see #trackNewlines(boolean)
     */
    public int columnNumber() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int columnNumber(int pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get a formatted string representing the current line and column positions. E.g. <code>5:10</code> indicating line
     *     number 5 and column number 10.
     *     @return line:col position
     *     @since 1.14.3
     *     @see #trackNewlines(boolean)
     */
    String posLineCol() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Scans the buffer for newline positions and records line starts.
     */
    private void scanBufferForNewlines() {
        if (!isTrackNewlines())
            return;
        for (int i = bufPos; i < bufLength; i++) {
            if (charBuf[i] == '\n') {
                int lineStart = 1 + consumed + i;
                lineMap().addLineStart(lineStart);
            }
        }
    }

    /**
     * Tests if all the content has been read.
     * @return true if nothing left to read.
     */
    public boolean isEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean isEmptyNoBufferUp() {
        return bufPos >= bufLength;
    }

    /**
     * Get the char at the current position.
     * @return char
     */
    public char current() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Consume one character off the queue.
     *     @return first character on queue, or EOF if the queue is empty.
     */
    public char consume() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Unconsume one character (bufPos--). MUST only be called directly after a consume(), and no chance of a bufferUp.
     */
    void unconsume() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Moves the current position by one.
     */
    public void advance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the number of characters between the current position and the next instance of the input char
     * @param c scan target
     * @return offset between current position and next instance of target. -1 if not found.
     */
    int nextIndexOf(char c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the number of characters between the current position and the next instance of the input sequence
     *
     * @param seq scan target
     * @return offset between current position and next instance of target. -1 if not found.
     */
    int nextIndexOf(CharSequence seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Reads characters up to the specific char.
     * @param c the delimiter
     * @return the chars read
     */
    public String consumeTo(char c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Reads the characters up to (but not including) the specified case-sensitive string.
     *     <p>If the sequence is not found in the buffer, will return the remainder of the current buffered amount, less the
     *     length of the sequence, such that this call may be repeated.
     *     @param seq the delimiter
     *     @return the chars read
     */
    public String consumeTo(String seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Read characters while the input predicate returns true.
     *     @return characters read
     */
    String consumeMatching(CharPredicate func) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Read characters while the input predicate returns true, up to a maximum length.
     *     @param func predicate to test
     *     @param maxLength maximum length to read. -1 indicates no maximum
     *     @return characters read
     */
    String consumeMatching(CharPredicate func, int maxLength) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Read characters until the first of any delimiters is found.
     *     @param chars delimiters to scan for
     *     @return characters read up to the matched delimiter.
     */
    public String consumeToAny(final char... chars) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Read characters until either delimiter is found.
     */
    String consumeToAny(char c1, char c2) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Read characters until any delimiter is found.
     */
    String consumeToAny(char c1, char c2, char c3) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String consumeToAnySorted(final char... chars) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String consumeData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String consumeAttributeQuoted(final boolean single) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String consumeRawData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String consumeTagName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String consumeToEnd() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String consumeLetterSequence() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String consumeLetterThenDigitSequence() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String consumeHexSequence() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String consumeDigitSequence() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Complete a scan by moving the reader and returning the matched range.
     */
    private String consumeRange(int start, int pos) {
        bufPos = pos;
        return pos > start ? cacheString(charBuf, stringCache, start, pos - start) : "";
    }

    boolean matches(char c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean matches(String seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Checks if the current buffer position matches the sequence case-insensitively.
     */
    boolean matchesIgnoreCase(String seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean rangeMatchesIgnoreCase(String seq, int start) {
        for (int offset = 0; offset < seq.length(); offset++) {
            char scan = seq.charAt(offset);
            char target = charBuf[start + offset];
            if (scan == target)
                continue;
            scan = Character.toUpperCase(scan);
            target = Character.toUpperCase(target);
            if (scan != target)
                return false;
        }
        return true;
    }

    /**
     *     Tests if the next character in the queue matches any of the characters in the sequence, case sensitively.
     *     @param seq list of characters to check for
     *     @return true if any matched, false if none did
     */
    boolean matchesAny(char... seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean matchesAnySorted(char[] seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Checks if the current pos matches an ascii alpha (A-Z a-z) per https://infra.spec.whatwg.org/#ascii-alpha
     *     @return if it matches or not
     */
    boolean matchesAsciiAlpha() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean matchesDigit() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean matchConsume(String seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean matchConsumeIgnoreCase(String seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // we maintain a cache of the previously scanned sequence, and return that if applicable on repeated scans.
    // that improves the situation where there is a sequence of <p<p<p<p<p<p<p...</title> and we're bashing on the <p
    // looking for the </title>. Resets in bufferUp()
    // scan cache
    @Nullable
    private String lastIcSeq;

    // nearest found indexOf
    private int lastIcIndex;

    /**
     * Used to check presence of </title>, </style> when we're in RCData and see a <xxx.
     */
    boolean containsIgnoreCase(String seq) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Caches short strings, as a flyweight pattern, to reduce GC load. Just for this doc, to prevent leaks.
     * <p />
     * Simplistic, and on hash collisions just falls back to creating a new string, vs a full HashMap with Entry list.
     * That saves both having to create objects as hash keys, and running through the entry list, at the expense of
     * some more duplicates.
     */
    private static String cacheString(final char[] charBuf, final String[] stringCache, final int start, final int count) {
        if (// don't cache strings that are too big
        count > MaxStringCacheLen)
            return new String(charBuf, start, count);
        if (count < 1)
            return "";
        // calculate hash:
        int hash = 0;
        int end = count + start;
        for (int i = start; i < end; i++) {
            hash = 31 * hash + charBuf[i];
        }
        // get from cache
        final int index = hash & StringCacheSize - 1;
        String cached = stringCache[index];
        if (// positive hit
        cached != null && rangeEquals(charBuf, start, count, cached))
            return cached;
        else {
            cached = new String(charBuf, start, count);
            // add or replace, assuming most recently used are most likely to recur next
            stringCache[index] = cached;
        }
        return cached;
    }

    /**
     * Check if the value of the provided range equals the string.
     */
    static boolean rangeEquals(final char[] charBuf, final int start, int count, final String cached) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // just used for testing
    boolean rangeEquals(final int start, final int count, final String cached) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @FunctionalInterface
    interface CharPredicate {

        boolean test(char c);
    }
}
