package org.jsoup.internal;

import org.jsoup.helper.Validate;
import org.jspecify.annotations.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import static org.jsoup.internal.SimpleBufferedInput.BufferPool;

/**
 * A simple decoding InputStreamReader that recycles internal buffers.
 */
public class SimpleStreamReader extends Reader {

    private final InputStream in;

    private final CharsetDecoder decoder;

    // null after close
    @Nullable
    private ByteBuffer byteBuf;

    public SimpleStreamReader(InputStream in, Charset charset) {
        this.in = in;
        this.decoder = charset.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        // shared w/ SimpleBufferedInput, ControllableInput
        byte[] buf = BufferPool.borrow();
        byteBuf = ByteBuffer.wrap(buf);
        // limit(0)
        byteBuf.flip();
    }

    @Override
    public int read(char[] charArray, int off, int len) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean hasAvailableBytes() {
        try {
            return in.available() > 0;
        } catch (IOException e) {
            // available() is advisory; a real read can still consume buffered bytes or reach EOF
            return false;
        }
    }

    private int bufferUp() throws IOException {
        // already validated ^
        assert byteBuf != null;
        byteBuf.compact();
        try {
            int pos = byteBuf.position();
            int remaining = (byteBuf.limit() - pos);
            int read = in.read(byteBuf.array(), byteBuf.arrayOffset() + pos, remaining);
            if (read < 0)
                return read;
            if (read == 0)
                throw new IOException("Underlying input stream returned zero bytes");
            byteBuf.position(pos + read);
        } finally {
            byteBuf.flip();
        }
        return byteBuf.remaining();
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
