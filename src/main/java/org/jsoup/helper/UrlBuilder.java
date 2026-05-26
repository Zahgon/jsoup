package org.jsoup.helper;

import org.jsoup.Connection;
import org.jsoup.internal.StringUtil;
import org.jspecify.annotations.Nullable;
import java.io.UnsupportedEncodingException;
import java.net.IDN;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import static org.jsoup.helper.DataUtil.UTF_8;

/**
 * A utility class to normalize input URLs. jsoup internal; API subject to change.
 * <p>Normalization includes puny-coding the host, and encoding non-ascii path components. Any non-ascii characters in
 * the query string (or the fragment/anchor) are escaped, but any existing escapes in those components are preserved.</p>
 */
final class UrlBuilder {

    URL u;

    @Nullable
    StringBuilder q;

    UrlBuilder(URL inputUrl) {
        this.u = inputUrl;
        if (u.getQuery() != null)
            q = StringUtil.borrowBuilder().append(u.getQuery());
    }

    URL build() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void appendKeyVal(Connection.KeyVal kv) throws UnsupportedEncodingException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static String decodePart(String encoded) {
        try {
            return URLDecoder.decode(encoded, UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // wtf!
            throw new RuntimeException(e);
        }
    }

    private static final String unsafeCharacters = "<>\"{}|\\^[]`";

    private static void appendToAscii(String s, boolean spaceAsPlus, StringBuilder sb) throws UnsupportedEncodingException {
        for (int i = 0; i < s.length(); i++) {
            int c = s.codePointAt(i);
            if (c == ' ') {
                sb.append(spaceAsPlus ? '+' : "%20");
            } else if (c == '%') {
                // if already a valid escape, pass; otherwise, escape
                if (i < s.length() - 2 && isHex(s.charAt(i + 1)) && isHex(s.charAt(i + 2))) {
                    sb.append('%').append(s.charAt(i + 1)).append(s.charAt(i + 2));
                    // skip the next two characters
                    i += 2;
                } else {
                    sb.append("%25");
                }
            } else if (c > 127 || unsafeCharacters.indexOf(c) != -1) {
                // past ascii, or otherwise unsafe
                sb.append(URLEncoder.encode(new String(Character.toChars(c)), UTF_8.name()));
                // advance past supplemental
                if (Character.charCount(c) == 2)
                    i++;
            } else {
                sb.append((char) c);
            }
        }
    }

    private static boolean isHex(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }
}
