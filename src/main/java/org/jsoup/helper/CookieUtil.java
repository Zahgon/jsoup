package org.jsoup.helper;

import org.jsoup.Connection;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.CharacterReader;
import org.jspecify.annotations.Nullable;
import java.io.IOException;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Helper functions to support the Cookie Manager / Cookie Storage in HttpConnection.
 *
 * @since 1.14.1
 */
class CookieUtil {

    // cookie manager get() wants request headers but doesn't use them, so we just pass a dummy object here
    private static final Map<String, List<String>> EmptyRequestHeaders = Collections.unmodifiableMap(new HashMap<>());

    private static final String Sep = "; ";

    private static final String CookieName = "Cookie";

    private static final String Cookie2Name = "Cookie2";

    /**
     *     Pre-request, get any applicable headers out of the Request cookies and the Cookie Store, and add them to the request
     *     headers. If the Cookie Store duplicates any Request cookies (same name and value), they will be discarded.
     */
    static void applyCookiesToRequest(HttpConnection.Request req, BiConsumer<String, String> setter) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static LinkedHashSet<String> requestCookieSet(Connection.Request req) {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        // req cookies are the wildcard key/val cookies (no domain, path, etc)
        for (Map.Entry<String, String> cookie : req.cookies().entrySet()) {
            set.add(cookie.getKey() + "=" + cookie.getValue());
        }
        return set;
    }

    static URI asUri(URL url) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Store the Result cookies into the cookie manager, and place relevant cookies into the Response object.
     */
    static void storeCookies(HttpConnection.Request req, HttpConnection.Response res, URL url, Map<String, List<String>> resHeaders) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static void parseCookie(@Nullable String value, HttpConnection.Response res) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
