package org.jsoup.helper;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Progress;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.internal.ControllableInputStream;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.parser.StreamParser;
import org.jspecify.annotations.Nullable;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import static org.jsoup.Connection.Method.HEAD;
import static org.jsoup.helper.DataUtil.UTF_8;
import static org.jsoup.internal.Normalizer.lowerCase;
import static org.jsoup.internal.SharedConstants.DefaultBufferSize;

/**
 * Implementation of {@link Connection}.
 * @see org.jsoup.Jsoup#connect(String)
 */
@SuppressWarnings("CharsetObjectCanBeUsed")
public class HttpConnection implements Connection {

    public static final String CONTENT_ENCODING = "Content-Encoding";

    /**
     * Many users would get caught by not setting a user-agent and therefore getting different responses on their desktop
     * vs in jsoup, which would otherwise default to {@code Java}. So by default, use a desktop UA.
     */
    public static final String DEFAULT_UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/137.0.0.0 Safari/537.36";

    private static final String USER_AGENT = "User-Agent";

    public static final String CONTENT_TYPE = "Content-Type";

    public static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded";

    // http/1.1 temporary redirect, not in Java's set.
    private static final int HTTP_TEMP_REDIR = 307;

    static final String DefaultUploadType = "application/octet-stream";

    private static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

    private HttpConnection.Request req;

    private Connection.@Nullable Response res;

    // The HttpClient for this Connection, if via the HttpClientExecutor
    @Nullable
    Object client;

    // The previous Authenticator used by this Connection, if via the HttpClientExecutor
    @Nullable
    RequestAuthenticator lastAuth;

    /**
     *     Create a new Connection, with the request URL specified.
     *     @param url the URL to fetch from
     *     @return a new Connection object
     */
    public static Connection connect(String url) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Create a new Connection, with the request URL specified.
     *     @param url the URL to fetch from
     *     @return a new Connection object
     */
    public static Connection connect(URL url) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Create a new, empty HttpConnection.
     */
    public HttpConnection() {
        req = new Request();
        req.connection = this;
    }

    /**
     *     Create a new Request by deep-copying an existing Request. Note that the data and body of the original are not
     *     copied. All other settings (proxy, parser, cookies, etc) are copied.
     *     @param copy the request to copy
     */
    HttpConnection(Request copy) {
        req = new Request(copy);
    }

    static String encodeMimeName(String val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection newRequest() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a new Connection that just wraps the provided Request and Response
     */
    private HttpConnection(Request req, Response res) {
        this.req = req;
        this.res = res;
    }

    @Override
    public Connection url(URL url) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection url(String url) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection proxy(@Nullable Proxy proxy) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection proxy(String host, int port) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection userAgent(String userAgent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection timeout(int millis) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection maxBodySize(int bytes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection followRedirects(boolean followRedirects) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection referrer(String referrer) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection method(Method method) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection ignoreHttpErrors(boolean ignoreHttpErrors) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection ignoreContentType(boolean ignoreContentType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection data(String key, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @Deprecated
    public Connection sslSocketFactory(SSLSocketFactory sslSocketFactory) {
        req.sslSocketFactory(sslSocketFactory);
        return this;
    }

    @Override
    public Connection sslContext(SSLContext sslContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection data(String key, String filename, InputStream inputStream) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection data(String key, String filename, InputStream inputStream, String contentType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection data(Map<String, String> data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection data(String... keyvals) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection data(Collection<Connection.KeyVal> data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection.@Nullable KeyVal data(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection requestBody(String body) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection requestBodyStream(InputStream stream) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection header(String name, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection headers(Map<String, String> headers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection cookie(String name, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection cookies(Map<String, String> cookies) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection cookieStore(CookieStore cookieStore) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CookieStore cookieStore() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection parser(Parser parser) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Document get() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Document post() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection.Response execute() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection.Request request() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection request(Connection.Request request) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection.Response response() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection response(Connection.Response response) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection postDataCharset(String charset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection auth(@Nullable RequestAuthenticator authenticator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Connection onResponseProgress(Progress<Connection.Response> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("unchecked")
    private static abstract class Base<T extends Connection.Base<T>> implements Connection.Base<T> {

        // only used if you created a new Request()
        private static final URL UnsetUrl;

        static {
            try {
                UnsetUrl = new URL("http://undefined/");
            } catch (MalformedURLException e) {
                throw new IllegalStateException(e);
            }
        }

        URL url = UnsetUrl;

        Method method = Method.GET;

        Map<String, List<String>> headers;

        Map<String, String> cookies;

        private Base() {
            headers = new LinkedHashMap<>();
            cookies = new LinkedHashMap<>();
        }

        private Base(Base<T> copy) {
            // unmodifiable object
            url = copy.url;
            method = copy.method;
            headers = new LinkedHashMap<>();
            for (Map.Entry<String, List<String>> entry : copy.headers.entrySet()) {
                headers.put(entry.getKey(), new ArrayList<>(entry.getValue()));
            }
            // just holds strings
            cookies = new LinkedHashMap<>();
            // just holds strings
            cookies.putAll(copy.cookies);
        }

        @Override
        public URL url() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public T url(URL url) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Method method() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public T method(Method method) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @Nullable
        public String header(String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public T addHeader(String name, @Nullable String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public List<String> headers(String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public T header(String name, String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean hasHeader(String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Test if the request has a header with this value (case-insensitive).
         */
        @Override
        public boolean hasHeaderWithValue(String name, String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public T removeHeader(String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Map<String, String> headers() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Map<String, List<String>> multiHeaders() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private List<String> getHeadersCaseInsensitive(String name) {
            Validate.notNull(name);
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (name.equalsIgnoreCase(entry.getKey()))
                    return entry.getValue();
            }
            return Collections.emptyList();
        }

        private Map.@Nullable Entry<String, List<String>> scanHeaders(String name) {
            String lc = lowerCase(name);
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                if (lowerCase(entry.getKey()).equals(lc))
                    return entry;
            }
            return null;
        }

        @Override
        public String cookie(String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public T cookie(String name, String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean hasCookie(String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public T removeCookie(String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Map<String, String> cookies() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static class Request extends HttpConnection.Base<Connection.Request> implements Connection.Request {

        static {
            System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
            // make sure that we can send Sec-Fetch-Site headers etc.
        }

        HttpConnection connection;

        @Nullable
        private Proxy proxy;

        private int timeoutMilliseconds;

        private int maxBodySizeBytes;

        private boolean followRedirects;

        private final Collection<Connection.KeyVal> data;

        // String or InputStream
        @Nullable
        private Object body = null;

        @Nullable
        String mimeBoundary;

        private boolean ignoreHttpErrors = false;

        private boolean ignoreContentType = false;

        private Parser parser;

        // called parser(...) vs initialized in ctor
        private boolean parserDefined = false;

        private String postDataCharset = DataUtil.defaultCharsetName;

        @Nullable
        private SSLSocketFactory sslSocketFactory;

        @Nullable
        SSLContext sslContext;

        private CookieManager cookieManager;

        @Nullable
        RequestAuthenticator authenticator;

        @Nullable
        private Progress<Connection.Response> responseProgress;

        // detects and warns if same request used concurrently
        private final ReentrantLock executing = new ReentrantLock();

        Request() {
            super();
            // 30 seconds
            timeoutMilliseconds = 30000;
            // 2MB
            maxBodySizeBytes = 1024 * 1024 * 2;
            followRedirects = true;
            data = new ArrayList<>();
            method = Method.GET;
            addHeader("Accept-Encoding", "gzip");
            addHeader(USER_AGENT, DEFAULT_UA);
            parser = Parser.htmlParser();
            // creates a default InMemoryCookieStore
            cookieManager = new CookieManager();
        }

        Request(Request copy) {
            super(copy);
            connection = copy.connection;
            proxy = copy.proxy;
            postDataCharset = copy.postDataCharset;
            timeoutMilliseconds = copy.timeoutMilliseconds;
            maxBodySizeBytes = copy.maxBodySizeBytes;
            followRedirects = copy.followRedirects;
            // data not copied
            data = new ArrayList<>();
            //body not copied
            ignoreHttpErrors = copy.ignoreHttpErrors;
            ignoreContentType = copy.ignoreContentType;
            // parsers and their tree-builders maintain state, so need a fresh copy
            parser = copy.parser.newInstance();
            parserDefined = copy.parserDefined;
            // these are all synchronized so safe to share
            sslSocketFactory = copy.sslSocketFactory;
            sslContext = copy.sslContext;
            cookieManager = copy.cookieManager;
            authenticator = copy.authenticator;
            responseProgress = copy.responseProgress;
        }

        @Override
        @Nullable
        public Proxy proxy() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Request proxy(@Nullable Proxy proxy) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Request proxy(String host, int port) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int timeout() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Request timeout(int millis) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int maxBodySize() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Connection.Request maxBodySize(int bytes) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean followRedirects() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Connection.Request followRedirects(boolean followRedirects) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean ignoreHttpErrors() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @Nullable
        public SSLSocketFactory sslSocketFactory() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @Deprecated
        public void sslSocketFactory(SSLSocketFactory sslSocketFactory) {
            this.sslSocketFactory = sslSocketFactory;
        }

        @Override
        @Nullable
        public SSLContext sslContext() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Connection.Request sslContext(SSLContext sslContext) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Connection.Request ignoreHttpErrors(boolean ignoreHttpErrors) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean ignoreContentType() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Connection.Request ignoreContentType(boolean ignoreContentType) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Request data(Connection.KeyVal keyval) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Collection<Connection.KeyVal> data() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Connection.Request requestBody(@Nullable String body) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @Nullable
        public String requestBody() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Connection.Request requestBodyStream(InputStream stream) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Request parser(Parser parser) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Parser parser() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Connection.Request postDataCharset(String charset) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String postDataCharset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        CookieManager cookieManager() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Connection.Request auth(@Nullable RequestAuthenticator authenticator) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @Nullable
        public RequestAuthenticator auth() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static class Response extends HttpConnection.Base<Connection.Response> implements Connection.Response {

        private static final int MAX_REDIRECTS = 20;

        private static final String LOCATION = "Location";

        int statusCode;

        String statusMessage = "";

        @Nullable
        private ByteBuffer byteData;

        @Nullable
        private ControllableInputStream bodyStream;

        @Nullable
        RequestExecutor executor;

        @Nullable
        private String charset;

        @Nullable
        String contentType;

        int contentLength;

        private boolean executed = false;

        private boolean inputStreamRead = false;

        private int numRedirects = 0;

        private final HttpConnection.Request req;

        /*
         * Matches XML content types (like text/xml, image/svg+xml, application/xhtml+xml;charset=UTF8, etc)
         */
        private static final Pattern xmlContentTypeRxp = Pattern.compile("(\\w+)/\\w*\\+?xml.*");

        /**
         *         <b>Internal only! </b>Creates a dummy HttpConnection.Response, useful for testing. All actual responses
         *         are created from the HttpURLConnection and fields defined.
         */
        Response() {
            super();
            statusCode = 400;
            statusMessage = "Request not made";
            req = new Request();
            contentType = null;
        }

        static Response execute(HttpConnection.Request req) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        static Response execute(HttpConnection.Request req, @Nullable Response prevRes) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int statusCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String statusMessage() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @Nullable
        public String charset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Response charset(String charset) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @Nullable
        public String contentType() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Called from parse() or streamParser(), validates and prepares the input stream, and aligns common settings.
         */
        private ControllableInputStream prepareParse() {
            Validate.isTrue(executed, "Request must be executed (with .execute(), .get(), or .post() before parsing response");
            ControllableInputStream stream = bodyStream;
            if (byteData != null) {
                // bytes have been read in to the buffer, parse that
                ByteArrayInputStream bytes = new ByteArrayInputStream(byteData.array(), 0, byteData.limit());
                // no max
                stream = ControllableInputStream.wrap(bytes, 0);
                // ok to reparse if in bytes
                inputStreamRead = false;
            }
            Validate.isFalse(inputStreamRead, "Input stream already read and parsed, cannot re-read.");
            Validate.notNull(stream);
            inputStreamRead = true;
            return stream;
        }

        @Override
        public Document parse() throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public StreamParser streamParser() throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Reads the bodyStream into byteData. A no-op if already executed.
         */
        @Override
        public Connection.Response readFully() throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Reads the body, but throws an UncheckedIOException if an IOException occurs.
         *         @throws UncheckedIOException if an IOException occurs
         */
        private void readByteDataUnchecked() {
            try {
                readFully();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        @Override
        public String readBody() throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String body() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public byte[] bodyAsBytes() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @Deprecated
        public Connection.Response bufferUp() {
            readByteDataUnchecked();
            return this;
        }

        @Override
        public BufferedInputStream bodyStream() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Call on completion of stream read, to close the body (or error) stream. The connection.disconnect allows
         * keep-alives to work (as the underlying connection is actually held open, despite the name).
         */
        private void safeClose() {
            if (bodyStream != null) {
                try {
                    bodyStream.close();
                } catch (IOException e) {
                    // no-op
                } finally {
                    bodyStream = null;
                }
            }
            // disconnect
            if (executor != null)
                executor.safeClose();
        }

        Response(HttpConnection.Request request) {
            this.req = request;
        }

        // set up url, method, header, cookies
        void prepareResponse(Map<String, List<String>> resHeaders, HttpConnection.@Nullable Response previousResponse) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void processResponseHeaders(Map<String, List<String>> resHeaders) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Servers may encode response headers in UTF-8 instead of RFC defined 8859. The JVM decodes the headers (before we see them) as 8859, which can lead to mojibake data.
         *         <p>This method attempts to detect that and re-decode the string as UTF-8.</p>
         *         <p>However on Android, the headers will be decoded as UTF8, so we can detect and pass those directly.</p>
         * @param val a header value string that may have been incorrectly decoded as 8859.
         * @return a potentially re-decoded string.
         */
        @Nullable
        static String fixHeaderEncoding(@Nullable String val) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private static boolean looksLikeUtf8(byte[] input) {
            int i = 0;
            // BOM:
            if (input.length >= 3 && (input[0] & 0xFF) == 0xEF && (input[1] & 0xFF) == 0xBB && (input[2] & 0xFF) == 0xBF) {
                i = 3;
            }
            int end;
            boolean foundNonAscii = false;
            for (int j = input.length; i < j; ++i) {
                int o = input[i];
                if ((o & 0x80) == 0) {
                    // ASCII
                    continue;
                }
                foundNonAscii = true;
                // UTF-8 leading:
                if ((o & 0xE0) == 0xC0) {
                    end = i + 1;
                } else if ((o & 0xF0) == 0xE0) {
                    end = i + 2;
                } else if ((o & 0xF8) == 0xF0) {
                    end = i + 3;
                } else {
                    return false;
                }
                if (end >= input.length)
                    return false;
                while (i < end) {
                    i++;
                    o = input[i];
                    if ((o & 0xC0) != 0x80) {
                        return false;
                    }
                }
            }
            return foundNonAscii;
        }

        private static void setOutputContentType(final HttpConnection.Request req) {
            final String contentType = req.header(CONTENT_TYPE);
            String bound = null;
            if (contentType != null) {
                // no-op; don't add content type as already set (e.g. for requestBody())
                // todo - if content type already set, we could add charset
                // if user has set content type to multipart/form-data, auto add boundary.
                if (contentType.contains(MULTIPART_FORM_DATA) && !contentType.contains("boundary")) {
                    bound = DataUtil.mimeBoundary();
                    req.header(CONTENT_TYPE, MULTIPART_FORM_DATA + "; boundary=" + bound);
                }
            } else if (needsMultipart(req)) {
                bound = DataUtil.mimeBoundary();
                req.header(CONTENT_TYPE, MULTIPART_FORM_DATA + "; boundary=" + bound);
            } else {
                req.header(CONTENT_TYPE, FORM_URL_ENCODED + "; charset=" + req.postDataCharset());
            }
            req.mimeBoundary = bound;
        }

        static void writePost(final HttpConnection.Request req, final OutputStream outputStream) throws IOException {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private static void implWritePost(final HttpConnection.Request req, final BufferedWriter w, final OutputStream outputStream) throws IOException {
            final Collection<Connection.KeyVal> data = req.data();
            final String boundary = req.mimeBoundary;
            if (boundary != null) {
                // a multipart post
                for (Connection.KeyVal keyVal : data) {
                    w.write("--");
                    w.write(boundary);
                    w.write("\r\n");
                    w.write("Content-Disposition: form-data; name=\"");
                    // encodes " to %22
                    w.write(encodeMimeName(keyVal.key()));
                    w.write("\"");
                    final InputStream input = keyVal.inputStream();
                    if (input != null) {
                        w.write("; filename=\"");
                        w.write(encodeMimeName(keyVal.value()));
                        w.write("\"\r\nContent-Type: ");
                        String contentType = keyVal.contentType();
                        w.write(contentType != null ? contentType : DefaultUploadType);
                        w.write("\r\n\r\n");
                        w.flush();
                        DataUtil.crossStreams(input, outputStream);
                        outputStream.flush();
                    } else {
                        w.write("\r\n\r\n");
                        w.write(keyVal.value());
                    }
                    w.write("\r\n");
                }
                w.write("--");
                w.write(boundary);
                w.write("--");
            } else if (req.body != null) {
                // a single body (bytes or plain text);  data will be in query string
                if (req.body instanceof String) {
                    w.write((String) req.body);
                } else if (req.body instanceof InputStream) {
                    DataUtil.crossStreams((InputStream) req.body, outputStream);
                    outputStream.flush();
                } else {
                    throw new IllegalStateException();
                }
            } else {
                // regular form data (application/x-www-form-urlencoded)
                boolean first = true;
                for (Connection.KeyVal keyVal : data) {
                    if (!first)
                        w.append('&');
                    else
                        first = false;
                    w.write(URLEncoder.encode(keyVal.key(), req.postDataCharset()));
                    w.write('=');
                    w.write(URLEncoder.encode(keyVal.value(), req.postDataCharset()));
                }
            }
        }

        // for get url reqs, serialise the data map into the url
        private static void serialiseRequestUrl(Connection.Request req) throws IOException {
            UrlBuilder in = new UrlBuilder(req.url());
            for (Connection.KeyVal keyVal : req.data()) {
                Validate.isFalse(keyVal.hasInputStream(), "InputStream data not supported in URL query string.");
                in.appendKeyVal(keyVal);
            }
            req.url(in.build());
            // moved into url as get params
            req.data().clear();
        }
    }

    private static boolean needsMultipart(Connection.Request req) {
        // multipart mode, for files. add the header if we see something with an inputstream, and return a non-null boundary
        for (Connection.KeyVal keyVal : req.data()) {
            if (keyVal.hasInputStream())
                return true;
        }
        return false;
    }

    public static class KeyVal implements Connection.KeyVal {

        private String key;

        private String value;

        @Nullable
        private InputStream stream;

        @Nullable
        private String contentType;

        public static KeyVal create(String key, String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public static KeyVal create(String key, String filename, InputStream stream) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private KeyVal(String key, String value) {
            Validate.notEmptyParam(key, "key");
            Validate.notNullParam(value, "value");
            this.key = key;
            this.value = value;
        }

        @Override
        public KeyVal key(String key) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String key() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public KeyVal value(String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String value() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public KeyVal inputStream(InputStream inputStream) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @Nullable
        public InputStream inputStream() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean hasInputStream() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public Connection.KeyVal contentType(String contentType) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        @Nullable
        public String contentType() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
