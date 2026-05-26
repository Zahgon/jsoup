package org.jsoup.examples;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * A simple example, used on the jsoup website.
 * <p>To invoke from the command line, assuming you've downloaded the jsoup-examples
 * jar to your current directory:</p>
 * <p><code>java -cp jsoup-examples.jar org.jsoup.examples.Wikipedia url</code></p>
 */
public class Wikipedia {

    public static void main(String[] args) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static void log(String msg, String... vals) {
        System.out.println(String.format(msg, (Object[]) vals));
    }
}
