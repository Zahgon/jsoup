package org.jsoup;

import java.io.IOException;

/**
 * Signals that a HTTP response returned a mime type that is not supported.
 */
public class UnsupportedMimeTypeException extends IOException {

    private final String mimeType;

    private final String url;

    public UnsupportedMimeTypeException(String message, String mimeType, String url) {
        super(message);
        this.mimeType = mimeType;
        this.url = url;
    }

    public String getMimeType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public String getUrl() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
