package org.jsoup.helper;

import org.jsoup.internal.SharedConstants;
import org.jspecify.annotations.Nullable;
import static org.jsoup.helper.HttpConnection.Request;
import static org.jsoup.helper.HttpConnection.Response;
import java.net.Proxy;
import java.lang.reflect.Constructor;

/**
 * Handles requests using either HttpClient (available in JVM 11+) or HttpURLConnection. During initialization, the
 * HttpClientExecutor class is used if it can be instantiated, unless the system property
 * {@link SharedConstants#UseHttpClient} is explicitly set to {@code false}.
 */
class RequestDispatch {

    @Nullable
    static Constructor<? extends RequestExecutor> clientConstructor;

    static {
        try {
            Class<? extends RequestExecutor> httpClass = Class.forName("org.jsoup.helper.HttpClientExecutor").asSubclass(RequestExecutor.class);
            clientConstructor = httpClass.getConstructor(Request.class, Response.class);
        } catch (Exception ignored) {
            // either not on Java11+, or on Android; will provide UrlConnectionExecutor
        }
    }

    static RequestExecutor get(Request request, @Nullable Response previousResponse) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
