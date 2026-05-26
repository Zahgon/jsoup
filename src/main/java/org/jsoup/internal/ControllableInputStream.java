package org.jsoup.internal;

import org.jsoup.Progress;
import org.jsoup.helper.Validate;
import org.jspecify.annotations.Nullable;
import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import static org.jsoup.internal.SharedConstants.DefaultBufferSize;

/**
 * A jsoup internal class (so don't use it as there is no contract API) that enables controls on a buffered input stream,
 * namely a maximum read size, and the ability to Thread.interrupt() the read.
 */
// reimplemented from ConstrainableInputStream for JDK21 - extending BufferedInputStream will pin threads during read
public class ControllableInputStream extends FilterInputStream {

    // super.in, but typed as SimpleBufferedInput
    private final SimpleBufferedInput buff;

    // logical cap exposed to callers (0 == unlimited)
    private int maxSize;

    // start time for timeout checks, nanos
    private long startTime;

    // optional max time of request
    private long timeout = 0;

    // how many bytes may still be returned to caller under the current cap
    private int remaining;

    // logical readPos snapshot for InputStream.mark/reset (not a buffer cursor)
    private int markPos;

    // true if Thread.interrupted() was detected, used to latch interrupted state
    private boolean interrupted;

    // for cases where we want to re-read the input, can ignore .close() from the parser
    private boolean allowClose = true;

    // if we are tracking progress, will have the expected content length and progress callback state
    @Nullable
    private ProgressState<?> progress;

    // expected content length for progress; -1 == unknown
    private int contentLength = -1;

    // amount read; can be reset()
    private int readPos = 0;

    private ControllableInputStream(SimpleBufferedInput in, int maxSize) {
        super(in);
        Validate.isTrue(maxSize >= 0);
        buff = in;
        this.maxSize = maxSize;
        remaining = maxSize;
        markPos = -1;
        startTime = System.nanoTime();
    }

    /**
     * If this InputStream is not already a ControllableInputStream, let it be one.
     * @param in the input stream to (maybe) wrap. A {@code null} input will create an empty wrapped stream.
     * @param maxSize the maximum size to allow to be read. 0 == infinite.
     * @return a controllable input stream
     */
    public static ControllableInputStream wrap(@Nullable InputStream in, int maxSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If this InputStream is not already a ControllableInputStream, let it be one.
     * @param in the input stream to (maybe) wrap
     * @param bufferSize the buffer size to use when reading
     * @param maxSize the maximum size to allow to be read. 0 == infinite.
     * @return a controllable input stream
     */
    public static ControllableInputStream wrap(InputStream in, int bufferSize, int maxSize) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean markSupported() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Reads this inputstream to a ByteBuffer. The supplied max may be less than the inputstream's max, to support
     * reading just the first bytes.
     */
    public static ByteBuffer readToByteBuffer(InputStream in, int max) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // not synchronized in later JDKs
    @SuppressWarnings("NonSynchronizedMethodOverridesSynchronizedMethod")
    @Override
    public void reset() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // not synchronized in later JDKs
    @SuppressWarnings("NonSynchronizedMethodOverridesSynchronizedMethod")
    @Override
    public void mark(int readlimit) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Check if the underlying InputStream has been read fully. There may still content in buffers to be consumed, and
     *     read methods may return -1 if hit the read limit.
     *     @return true if the underlying inputstream has been read fully.
     */
    public boolean baseReadFully() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void resetFullyRead() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the max size of this stream (how far at most will be read from the underlying stream)
     * @return the max size
     */
    public int max() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void max(int newMax) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void allowClose(boolean allowClose) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ControllableInputStream timeout(long startTimeNanos, long timeoutMillis) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void emitProgress() {
        ProgressState<?> progress = this.progress;
        if (progress == null)
            return;
        // calculate percent complete if contentLength > 0 (and cap to 100.0 if totalRead > contentLength):
        float percent = contentLength > 0 ? Math.min(100f, readPos * 100f / contentLength) : 0;
        progress.emit(readPos, contentLength, percent);
        // detach once we reach 100%, so that any subsequent buffer hits don't report 100 again
        if (percent == 100.0f)
            this.progress = null;
    }

    public <ProgressContext> ControllableInputStream onProgress(int contentLength, Progress<ProgressContext> callback, ProgressContext context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean expired() {
        if (timeout == 0)
            return false;
        final long now = System.nanoTime();
        final long dur = now - startTime;
        return (dur > timeout);
    }

    public BufferedInputStream inputStream() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static class ProgressState<ProgressContext> {

        private final Progress<ProgressContext> callback;

        private final ProgressContext context;

        ProgressState(Progress<ProgressContext> callback, ProgressContext context) {
            this.callback = callback;
            this.context = context;
        }

        void emit(int processed, int total, float percent) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
