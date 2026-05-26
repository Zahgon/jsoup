package org.jsoup.helper;

import org.jspecify.annotations.Nullable;
import java.lang.reflect.Constructor;
import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Handles per request Authenticator-based authentication. Loads the class `org.jsoup.helper.RequestAuthHandler` if
 * per-request Authenticators are supported (Java 9+), or installs a system-wide Authenticator that delegates to a request
 * ThreadLocal.
 */
class AuthenticationHandler extends Authenticator {

    // max authentication attempts per request. allows for multiple auths (e.g. proxy and server) in one request, but saves otherwise 20 requests if credentials are incorrect.
    static final int MaxAttempts = 3;

    static AuthShim handler;

    static {
        try {
            Class<? extends AuthShim> perRequestClass = Class.forName("org.jsoup.helper.RequestAuthHandler").asSubclass(AuthShim.class);
            Constructor<? extends AuthShim> constructor = perRequestClass.getConstructor();
            handler = constructor.newInstance();
        } catch (ClassNotFoundException e) {
            handler = new GlobalHandler();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Nullable
    RequestAuthenticator auth;

    int attemptCount = 0;

    AuthenticationHandler() {
    }

    AuthenticationHandler(RequestAuthenticator auth) {
        this.auth = auth;
    }

    /**
     *     Authentication callback, called by HttpURLConnection - either as system-wide default (Java 8) or per HttpURLConnection (Java 9+)
     * @return credentials, or null if not attempting to auth.
     */
    @Nullable
    @Override
    public final PasswordAuthentication getPasswordAuthentication() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    interface AuthShim {

        void enable(RequestAuthenticator auth, Object connOrHttp);

        void remove();

        @Nullable
        AuthenticationHandler get(AuthenticationHandler helper);
    }

    /**
     *     On Java 8 we install a system-wide Authenticator, which pulls the delegating Auth from a ThreadLocal pool.
     */
    static class GlobalHandler implements AuthShim {

        static ThreadLocal<AuthenticationHandler> authenticators = new ThreadLocal<>();

        static {
            Authenticator.setDefault(new AuthenticationHandler());
        }

        @Override
        public void enable(RequestAuthenticator auth, Object ignored) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public AuthenticationHandler get(AuthenticationHandler helper) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
