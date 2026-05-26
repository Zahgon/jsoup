package org.jsoup.helper;

import org.jsoup.Connection;
import org.jspecify.annotations.Nullable;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static org.jsoup.helper.HttpConnection.Response;

/**
 * Execute HTTP requests using the HttpURLConnection implementation. The HttpClient is used by default if available; set system property
 * {@code jsoup.useHttpClient} to {@code false} to explicitly prefer the HttpUrlConnection.
 */
class UrlConnectionExecutor extends RequestExecutor {

    @Nullable
    HttpURLConnection conn;

    UrlConnectionExecutor(HttpConnection.Request req, HttpConnection.@Nullable Response prevRes) {
        super(req, prevRes);
    }

    @Override
    HttpConnection.Response execute() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    InputStream responseBody() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void safeClose() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // set up connection defaults, and details from request
    private static HttpURLConnection createConnection(HttpConnection.Request req) throws IOException {
        Proxy proxy = req.proxy();
        final HttpURLConnection conn = (HttpURLConnection) (proxy == null ? req.url().openConnection() : req.url().openConnection(proxy));
        conn.setRequestMethod(req.method().name());
        // don't rely on native redirection support
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(req.timeout());
        // gets reduced after connection is made and status is read
        conn.setReadTimeout(req.timeout() / 2);
        if (conn instanceof HttpsURLConnection) {
            HttpsURLConnection scon = (HttpsURLConnection) conn;
            if (req.sslContext != null)
                scon.setSSLSocketFactory(req.sslContext.getSocketFactory());
            else if (req.sslSocketFactory() != null)
                scon.setSSLSocketFactory(req.sslSocketFactory());
        }
        if (req.authenticator != null)
            // removed in finally
            AuthenticationHandler.handler.enable(req.authenticator, conn);
        if (req.method().hasBody())
            conn.setDoOutput(true);
        // from the Request key/val cookies and the Cookie Store
        CookieUtil.applyCookiesToRequest(req, conn::addRequestProperty);
        for (Map.Entry<String, List<String>> header : req.multiHeaders().entrySet()) {
            for (String value : header.getValue()) {
                conn.addRequestProperty(header.getKey(), value);
            }
        }
        return conn;
    }

    private static LinkedHashMap<String, List<String>> createHeaderMap(HttpURLConnection conn) {
        // the default sun impl of conn.getHeaderFields() returns header values out of order
        final LinkedHashMap<String, List<String>> headers = new LinkedHashMap<>();
        int i = 0;
        while (true) {
            final String key = conn.getHeaderFieldKey(i);
            final String val = conn.getHeaderField(i);
            if (key == null && val == null)
                break;
            i++;
            if (key == null || val == null)
                // skip http1.1 line
                continue;
            final List<String> vals = headers.computeIfAbsent(key, k -> new java.util.ArrayList<>());
            vals.add(val);
        }
        return headers;
    }
}
