package org.jsoup.internal;

import org.jsoup.helper.Validate;
import org.jspecify.annotations.Nullable;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import static org.jsoup.internal.SharedConstants.DefaultBufferSize;

/**
 * A simple implementation of a buffered input stream, in which we can control the byte[] buffer to recycle it. Not safe for
 * use between threads; no sync or locks. The buffer is borrowed on initial demand in fill.
 * @since 1.18.2
 */
class SimpleBufferedInput extends FilterInputStream {

    static final int BufferSize = DefaultBufferSize;

    static final SoftPool<byte[]> BufferPool = new SoftPool<>(() -> new byte[BufferSize]);

    // how many bytes we are allowed to pull from the underlying stream
    private int capRemaining = Integer.MAX_VALUE;

    // the byte buffer; recycled via SoftPool. Created in fill if required
    private byte @Nullable [] byteBuf;

    private int bufPos;

    private int bufLength;

    // mark set by ControllableInputStream; -1 when unset
    private int bufMark = -1;

    // true when the underlying inputstream has been read fully
    private boolean inReadFully = false;

    SimpleBufferedInput(@Nullable InputStream in) {
        super(in);
        // effectively an empty stream
        if (in == null)
            inReadFully = true;
    }

    @Override
    public int read() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int read(byte[] dest, int offset, int desiredLen) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void fill() throws IOException {
        if (inReadFully)
            return;
        if (byteBuf == null) {
            // get one on first demand
            byteBuf = BufferPool.borrow();
        }
        compact();
        bufLength = bufPos;
        int toRead = Math.min(byteBuf.length - bufPos, capRemaining);
        if (toRead <= 0)
            return;
        int read = in.read(byteBuf, bufPos, toRead);
        if (read > 0) {
            bufLength = read + bufPos;
            capRemaining -= read;
            while (byteBuf.length - bufLength > 0 && capRemaining > 0) {
                // read in more if we have space, without blocking
                try {
                    if (in.available() < 1)
                        break;
                } catch (IOException e) {
                    // available() is advisory; keep the bytes we've already buffered
                    break;
                }
                toRead = Math.min(byteBuf.length - bufLength, capRemaining);
                if (toRead <= 0)
                    break;
                read = in.read(byteBuf, bufLength, toRead);
                if (read <= 0)
                    break;
                bufLength += read;
                capRemaining -= read;
            }
        }
        if (read == -1)
            inReadFully = true;
    }

    byte[] getBuf() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Check if the underlying InputStream has been read fully. There may still content in this buffer to be consumed.
     *     @return true if the underlying inputstream has been read fully.
     */
    boolean baseReadFully() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void resetFullyRead() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int available() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void capRemaining(int newRemaining) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void setMark() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void rewindToMark() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void clearMark() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void compact() {
        if (byteBuf == null || bufPos == 0)
            return;
        int keepFrom = bufMark >= 0 ? bufMark : bufPos;
        if (keepFrom <= 0)
            return;
        int remaining = bufLength - keepFrom;
        if (remaining > 0) {
            System.arraycopy(byteBuf, keepFrom, byteBuf, 0, remaining);
        }
        bufLength = remaining;
        bufPos -= keepFrom;
        if (bufMark >= 0) {
            bufMark -= keepFrom;
        }
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
