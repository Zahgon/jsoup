package org.jsoup.examples;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * Example program to list links from a URL.
 * <p>To invoke from the command line, assuming you've downloaded the jsoup-examples
 * jar to your current directory:</p>
 * <p><code>java -cp jsoup-examples.jar org.jsoup.examples.ListLinks url</code></p>
 * where <i>url</i> is the URL to fetch.
 */
public class ListLinks {

    public static void main(String[] args) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width - 1) + ".";
        else
            return s;
    }
}
