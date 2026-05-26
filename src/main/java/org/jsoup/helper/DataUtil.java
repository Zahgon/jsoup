package org.jsoup.helper;

import org.jsoup.Connection;
import org.jsoup.internal.ControllableInputStream;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.SimpleStreamReader;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.Parser;
import org.jsoup.parser.StreamParser;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;
import org.jspecify.annotations.Nullable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import static org.jsoup.internal.SharedConstants.DefaultBufferSize;

/**
 * Internal static utilities for handling data.
 */
@SuppressWarnings("CharsetObjectCanBeUsed")
public final class DataUtil {

    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:[\"'])?([^\\s,;\"']*)");

    // Don't use StandardCharsets, as those only appear in Android API 19, and we target 10.
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    // used if not found in header or meta charset
    static final String defaultCharsetName = UTF_8.name();

    private static final int firstReadBufferSize = 1024 * 5;

    private static final char[] mimeBoundaryChars = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    static final int boundaryLength = 32;

    private DataUtil() {
    }

    /**
     * Loads and parses a file to a Document, with the HtmlParser. Files that are compressed with gzip (and end in {@code .gz} or {@code .z})
     * are supported in addition to uncompressed files.
     *
     * @param file file to load
     * @param charsetName (optional) character set of input; specify {@code null} to attempt to autodetect. A BOM in
     *     the file will always override this setting.
     * @param baseUri base URI of document, to resolve relative links against
     * @return Document
     * @throws IOException on IO error
     */
    public static Document load(File file, @Nullable String charsetName, String baseUri) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Loads and parses a file to a Document. Files that are compressed with gzip (and end in {@code .gz} or {@code .z})
     * are supported in addition to uncompressed files.
     *
     * @param file file to load
     * @param charsetName (optional) character set of input; specify {@code null} to attempt to autodetect. A BOM in
     *     the file will always override this setting.
     * @param baseUri base URI of document, to resolve relative links against
     * @param parser alternate {@link Parser#xmlParser() parser} to use.
     *
     * @return Document
     * @throws IOException on IO error
     * @since 1.14.2
     */
    public static Document load(File file, @Nullable String charsetName, String baseUri, Parser parser) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Loads and parses a file to a Document, with the HtmlParser. Files that are compressed with gzip (and end in {@code .gz} or {@code .z})
     * are supported in addition to uncompressed files.
     *
     * @param path file to load
     * @param charsetName (optional) character set of input; specify {@code null} to attempt to autodetect. A BOM in
     *     the file will always override this setting.
     * @param baseUri base URI of document, to resolve relative links against
     * @return Document
     * @throws IOException on IO error
     */
    public static Document load(Path path, @Nullable String charsetName, String baseUri) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Loads and parses a file to a Document. Files that are compressed with gzip (and end in {@code .gz} or {@code .z})
     * are supported in addition to uncompressed files.
     *
     * @param path file to load
     * @param charsetName (optional) character set of input; specify {@code null} to attempt to autodetect. A BOM in
     * the file will always override this setting.
     * @param baseUri base URI of document, to resolve relative links against
     * @param parser alternate {@link Parser#xmlParser() parser} to use.
     *
     * @return Document
     * @throws IOException on IO error
     * @since 1.17.2
     */
    public static Document load(Path path, @Nullable String charsetName, String baseUri, Parser parser) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a {@link StreamParser} that will parse the supplied file progressively.
     * Files that are compressed with gzip (and end in {@code .gz} or {@code .z})
     * are supported in addition to uncompressed files.
     *
     * @param path file to load
     * @param charset (optional) character set of input; specify {@code null} to attempt to autodetect from metadata.
     * A BOM in the file will always override this setting.
     * @param baseUri base URI of document, to resolve relative links against
     * @param parser underlying HTML or XML parser to use.
     *
     * @return Document
     * @throws IOException on IO error
     * @since 1.18.2
     * @see Connection.Response#streamParser()
     */
    public static StreamParser streamParser(Path path, @Nullable Charset charset, String baseUri, Parser parser) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Open an input stream from a file; if it's a gzip file, returns a GZIPInputStream to unzip it.
     */
    private static ControllableInputStream openStream(Path path) throws IOException {
        final SeekableByteChannel byteChannel = Files.newByteChannel(path);
        InputStream stream = Channels.newInputStream(byteChannel);
        String name = Normalizer.lowerCase(path.getFileName().toString());
        if (name.endsWith(".gz") || name.endsWith(".z")) {
            try {
                // gzip magic bytes
                final boolean zipped = (stream.read() == 0x1f && stream.read() == 0x8b);
                // reset to start of file
                byteChannel.position(0);
                if (zipped)
                    stream = new GZIPInputStream(stream);
            } catch (IOException e) {
                // error during our first read; close the stream and cascade close byteChannel
                stream.close();
                throw e;
            }
        }
        return ControllableInputStream.wrap(stream, 0);
    }

    /**
     * Parses a Document from an input steam.
     * @param in input stream to parse. The stream will be closed after reading.
     * @param charsetName character set of input (optional)
     * @param baseUri base URI of document, to resolve relative links against
     * @return Document
     * @throws IOException on IO error
     */
    public static Document load(InputStream in, @Nullable String charsetName, String baseUri) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parses a Document from an input steam, using the provided Parser.
     * @param in input stream to parse. The stream will be closed after reading.
     * @param charsetName character set of input (optional)
     * @param baseUri base URI of document, to resolve relative links against
     * @param parser alternate {@link Parser#xmlParser() parser} to use.
     * @return Document
     * @throws IOException on IO error
     */
    public static Document load(InputStream in, @Nullable String charsetName, String baseUri, Parser parser) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Writes the input stream to the output stream. Doesn't close them.
     * @param in input stream to read from
     * @param out output stream to write to
     * @throws IOException on IO error
     */
    static void crossStreams(final InputStream in, final OutputStream out) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * A struct to return a detected charset, and a document (if fully read).
     */
    static class CharsetDoc {

        Charset charset;

        InputStream input;

        @Nullable
        Document doc;

        CharsetDoc(Charset charset, @Nullable Document doc, InputStream input) {
            this.charset = charset;
            this.input = input;
            this.doc = doc;
        }
    }

    static Document parseInputStream(@Nullable ControllableInputStream input, @Nullable String charsetName, String baseUri, Parser parser) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static final Evaluator metaCharset = Selector.evaluatorOf("meta[http-equiv=content-type], meta[charset]");

    /**
     * Detects charset for a regular parse, and may reuse a fully sniffed document.
     */
    static CharsetDoc detectCharset(ControllableInputStream input, @Nullable String charsetName, String baseUri, Parser parser) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Detects charset for a stream parse, and leaves the input readable for subsequent parsing.
     */
    static CharsetDoc detectCharsetForStreamParser(ControllableInputStream input, @Nullable String charsetName, String baseUri, Parser parser) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Shared charset detection worker; regular parse can reuse a fully sniffed doc, stream parse cannot.
     */
    private static CharsetDoc detectCharset(ControllableInputStream input, @Nullable String charsetName, String baseUri, Parser parser, boolean reuseDocIfFullyRead) throws IOException {
        Document doc = null;
        // read the start of the stream and look for a BOM or meta charset:
        // look for BOM - overrides any other header or input
        // resets / consumes appropriately
        String bomCharset = detectCharsetFromBom(input);
        if (bomCharset != null)
            charsetName = bomCharset;
        if (charsetName == null) {
            // read ahead and determine from meta. safe first parse as UTF-8
            int origMax = input.max();
            input.max(firstReadBufferSize);
            // clear any pre-read (e.g., BOM) state before capped sniff
            input.resetFullyRead();
            input.mark(firstReadBufferSize);
            // ignores closes during parse, in case we need to rewind
            input.allowClose(false);
            try (Reader reader = new SimpleStreamReader(input, UTF_8)) {
                // input is currently capped to firstReadBufferSize
                doc = parser.parseInput(reader, baseUri);
                input.reset();
                // reset for a full read if required
                input.max(origMax);
            } catch (UncheckedIOException e) {
                throw e.getCause();
            } finally {
                input.allowClose(true);
            }
            // look for <meta http-equiv="Content-Type" content="text/html;charset=gb2312"> or HTML5 <meta charset="gb2312">
            Elements metaElements = doc.select(metaCharset);
            // if not found, will keep utf-8 as best attempt
            String foundCharset = null;
            for (Element meta : metaElements) {
                if (meta.hasAttr("http-equiv"))
                    foundCharset = getCharsetFromContentType(meta.attr("content"));
                if (foundCharset == null && meta.hasAttr("charset"))
                    foundCharset = meta.attr("charset");
                if (foundCharset != null)
                    break;
            }
            // look for <?xml encoding='ISO-8859-1'?>
            if (foundCharset == null && doc.childNodeSize() > 0) {
                Node first = doc.childNode(0);
                XmlDeclaration decl = null;
                if (first instanceof XmlDeclaration)
                    decl = (XmlDeclaration) first;
                else if (first instanceof Comment) {
                    Comment comment = (Comment) first;
                    if (comment.isXmlDeclaration())
                        decl = comment.asXmlDeclaration();
                }
                if (decl != null && decl.name().equalsIgnoreCase("xml")) {
                    foundCharset = decl.attr("encoding");
                }
            }
            foundCharset = validateCharset(foundCharset);
            if (foundCharset != null && !foundCharset.equalsIgnoreCase(defaultCharsetName)) {
                // need to re-decode. (case-insensitive check here to match how validate works)
                foundCharset = foundCharset.trim().replaceAll("[\"']", "");
                charsetName = foundCharset;
                doc = null;
            } else if (reuseDocIfFullyRead && input.baseReadFully()) {
                // keep the current parse if the caller can use a fully read doc
                // the parser tried to close it
                input.close();
            } else {
                doc = null;
            }
        } else {
            // specified by content type header (or by user on file load)
            Validate.notEmpty(charsetName, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
        }
        // finally: prepare the return struct
        if (charsetName == null)
            charsetName = defaultCharsetName;
        Charset charset = charsetName.equals(defaultCharsetName) ? UTF_8 : Charset.forName(charsetName);
        return new CharsetDoc(charset, doc, input);
    }

    static Document parseInputStream(CharsetDoc charsetDoc, String baseUri, Parser parser) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Read the input stream into a byte buffer. To deal with slow input streams, you may interrupt the thread this
     * method is executing on. The data read until being interrupted will be available.
     * @param inStream the input stream to read from
     * @param maxSize the maximum size in bytes to read from the stream. Set to 0 to be unlimited.
     * @return the filled byte buffer
     * @throws IOException if an exception occurs whilst reading from the input stream.
     */
    public static ByteBuffer readToByteBuffer(InputStream inStream, int maxSize) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static ByteBuffer emptyByteBuffer() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Parse out a charset from a content type header. If the charset is not supported, returns null (so the default
     * will kick in.)
     * @param contentType e.g. "text/html; charset=EUC-JP"
     * @return "EUC-JP", or null if not found. Charset is trimmed and uppercased.
     */
    @Nullable
    static String getCharsetFromContentType(@Nullable String contentType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    private static String validateCharset(@Nullable String cs) {
        if (cs == null || cs.length() == 0)
            return null;
        cs = cs.trim().replaceAll("[\"']", "");
        try {
            if (Charset.isSupported(cs))
                return cs;
            cs = cs.toUpperCase(Locale.ENGLISH);
            if (Charset.isSupported(cs))
                return cs;
        } catch (IllegalCharsetNameException e) {
            // if all this charset matching fails.... we just take the default
        }
        return null;
    }

    /**
     * Creates a random string, suitable for use as a mime boundary
     */
    static String mimeBoundary() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    private static String detectCharsetFromBom(ControllableInputStream input) throws IOException {
        byte[] bom = new byte[4];
        input.mark(bom.length);
        //noinspection ResultOfMethodCallIgnored
        input.read(bom, 0, 4);
        input.reset();
        // 16 and 32 decoders consume the BOM to determine be/le; utf-8 should be consumed here
        if (// BE
        bom[0] == 0x00 && bom[1] == 0x00 && bom[2] == (byte) 0xFE && bom[3] == (byte) 0xFF || bom[0] == (byte) 0xFF && bom[1] == (byte) 0xFE && bom[2] == 0x00 && bom[3] == 0x00) {
            // LE
            // and I hope it's on your system
            return "UTF-32";
        } else if (// BE
        bom[0] == (byte) 0xFE && bom[1] == (byte) 0xFF || bom[0] == (byte) 0xFF && bom[1] == (byte) 0xFE) {
            // in all Javas
            return "UTF-16";
        } else if (bom[0] == (byte) 0xEF && bom[1] == (byte) 0xBB && bom[2] == (byte) 0xBF) {
            // consume the UTF-8 BOM
            input.read(bom, 0, 3);
            // in all Javas
            return "UTF-8";
        }
        return null;
    }
}
