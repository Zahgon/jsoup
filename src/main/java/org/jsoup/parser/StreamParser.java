package org.jsoup.parser;

import org.jsoup.Connection;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.Selector;
import org.jspecify.annotations.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UncheckedIOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * A StreamParser provides a progressive parse of its input. As each Element is completed, it is emitted via a Stream or
 * Iterator interface. Elements returned will be complete with all their children, and an (empty) next sibling, if
 * applicable.
 * <p>To conserve memory, you can {@link Node#remove() remove()} Elements (or their children) from the DOM during the
 * parse. This provides a mechanism to parse an input document that would otherwise be too large to fit into memory, yet
 * still providing a DOM interface to the document and its elements.</p>
 * <p>
 * Additionally, the parser provides a {@link #selectFirst(String query)} / {@link #selectNext(String query)}, which will
 * run the parser until a hit is found, at which point the parse is suspended. It can be resumed via another
 * {@code select()} call, or via the {@link #stream()} or {@link #iterator()} methods.
 * </p>
 * <p>Once the input has been fully read, the input Reader will be closed. Or, if the whole document does not need to be
 * read, call {@link #stop()} and {@link #close()}.</p>
 * <p>The {@link #document()} method will return the Document being parsed into, which will be only partially complete
 * until the input is fully consumed.</p>
 * <p>A StreamParser can be reused via a new {@link #parse(Reader, String)}, but is not thread-safe for concurrent inputs.
 * New parsers should be used in each thread.</p>
 * <p>If created via {@link Connection.Response#streamParser()}, or another Reader that is I/O backed, the iterator and
 * stream consumers will throw an {@link java.io.UncheckedIOException} if the underlying Reader errors during read.</p>
 * <p>For examples, see the jsoup
 * <a href="https://jsoup.org/cookbook/input/streamparser-dom-sax">StreamParser cookbook.</a></p>
 * <p>
 * Selectors that depend on knowing all siblings (e.g. {@code :last-child}, {@code :last-of-type}, {@code :nth-last-child},
 * {@code :only-child} and their negations) cannot be correctly evaluated while streaming, because the parser does not know
 * if a later sibling will appear. For those cases, run {@link #complete()} first to finish the parse (which is effectively
 * the same as using {@code Jsoup.parse(...)} unless you have already removed nodes during streaming).
 * </p>
 * @since 1.18.1
 */
public class StreamParser implements Closeable {

    final private Parser parser;

    final private TreeBuilder treeBuilder;

    final private ElementIterator it = new ElementIterator();

    @Nullable
    private Document document;

    private boolean stopped = false;

    /**
     *     Construct a new StreamParser, using the supplied base Parser.
     *     @param parser the configured base parser
     */
    public StreamParser(Parser parser) {
        this.parser = parser;
        treeBuilder = parser.getTreeBuilder();
        treeBuilder.nodeListener(it);
    }

    /**
     *     Provide the input for a Document parse. The input is not read until a consuming operation is called.
     *     @param input the input to be read.
     *     @param baseUri the URL of this input, for absolute link resolution
     *     @return this parser, for chaining
     */
    public StreamParser parse(Reader input, String baseUri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Provide the input for a Document parse. The input is not read until a consuming operation is called.
     *     @param input the input to be read
     *     @param baseUri the URL of this input, for absolute link resolution
     *     @return this parser
     */
    public StreamParser parse(String input, String baseUri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Provide the input for a fragment parse. The input is not read until a consuming operation is called.
     *     @param input the input to be read
     *     @param context the optional fragment context element
     *     @param baseUri the URL of this input, for absolute link resolution
     *     @return this parser
     *     @see #completeFragment()
     */
    public StreamParser parseFragment(Reader input, @Nullable Element context, String baseUri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Provide the input for a fragment parse. The input is not read until a consuming operation is called.
     *     @param input the input to be read
     *     @param context the optional fragment context element
     *     @param baseUri the URL of this input, for absolute link resolution
     *     @return this parser
     *     @see #completeFragment()
     */
    public StreamParser parseFragment(String input, @Nullable Element context, String baseUri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Creates a {@link Stream} of {@link Element}s, with the input being parsed as each element is consumed. Each
     *     Element returned will be complete (that is, all of its children will be included, and if it has a next sibling, that
     *     (empty) sibling will exist at {@link Element#nextElementSibling()}). The stream will be emitted in document order as
     *     each element is closed. That means that child elements will be returned prior to their parents.
     *     <p>The stream will start from the current position of the backing iterator and the parse.</p>
     *     <p>When consuming the stream, if the Reader that the Parser is reading throws an I/O exception (for example a
     *     SocketTimeoutException), that will be emitted as an {@link UncheckedIOException}</p>
     *     @return a stream of Element objects
     *     @throws UncheckedIOException if the underlying Reader excepts during a read (in stream consuming methods)
     */
    public Stream<Element> stream() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Returns an {@link Iterator} of {@link Element}s, with the input being parsed as each element is consumed. Each
     *     Element returned will be complete (that is, all of its children will be included, and if it has a next sibling, that
     *     (empty) sibling will exist at {@link Element#nextElementSibling()}). The elements will be emitted in document order as
     *     each element is closed. That means that child elements will be returned prior to their parents.
     *     <p>The iterator will start from the current position of the parse.</p>
     *     <p>The iterator is backed by this StreamParser, and the resources it holds.</p>
     *     @return a stream of Element objects
     */
    public Iterator<Element> iterator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Flags that the parse should be stopped; the backing iterator will not return any more Elements.
     *     @return this parser
     */
    public StreamParser stop() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Closes the input and releases resources including the underlying parser and reader.
     *     <p>The parser will also be closed when the input is fully read.</p>
     *     <p>The parser can be reused with another call to {@link #parse(Reader, String)}.</p>
     */
    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the current {@link Document} as it is being parsed. It will be only partially complete until the input is fully
     *     read. Structural changes (e.g. insert, remove) may be made to the Document contents.
     *     @return the (partial) Document
     */
    public Document document() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Runs the parser until the input is fully read, and returns the completed Document.
     *     @return the completed Document
     *     @throws IOException if an I/O error occurs
     */
    public Document complete() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     When initialized as a fragment parse, runs the parser until the input is fully read, and returns the completed
     *     fragment child nodes.
     *     @return the completed child nodes
     *     @throws IOException if an I/O error occurs
     *     @see #parseFragment(Reader, Element, String)
     */
    public List<Node> completeFragment() throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Finds the first Element that matches the provided query. If the parsed Document does not already have a match, the
     *     input will be parsed until the first match is found, or the input is completely read.
     *     @param query the {@link org.jsoup.select.Selector} query.
     *     @return the first matching {@link Element}, or {@code null} if there's no match
     *     @throws IOException if an I/O error occurs
     *     @see #selectFirst(Evaluator)
     */
    @Nullable
    public Element selectFirst(String query) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Just like {@link #selectFirst(String)}, but if there is no match, throws an {@link IllegalArgumentException}. This
     *     is useful if you want to simply abort processing on a failed match.
     *     @param query the {@link org.jsoup.select.Selector} query.
     *     @return the first matching element
     *     @throws IllegalArgumentException if no match is found
     *     @throws IOException if an I/O error occurs
     */
    public Element expectFirst(String query) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Finds the first Element that matches the provided query. If the parsed Document does not already have a match, the
     *     input will be parsed until the first match is found, or the input is completely read.
     *     <p>By providing a compiled evaluator vs a CSS selector, this method may be more efficient when executing the same
     *     query against multiple documents.</p>
     *     @param eval the {@link org.jsoup.select.Selector} evaluator.
     *     @return the first matching {@link Element}, or {@code null} if there's no match
     *     @throws IOException if an I/O error occurs
     *     @see Selector#evaluatorOf(String css)
     */
    @Nullable
    public Element selectFirst(Evaluator eval) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Finds the next Element that matches the provided query. The input will be parsed until the next match is found, or
     *     the input is completely read.
     *     @param query the {@link org.jsoup.select.Selector} query.
     *     @return the next matching {@link Element}, or {@code null} if there's no match
     *     @throws IOException if an I/O error occurs
     *     @see #selectNext(Evaluator)
     */
    @Nullable
    public Element selectNext(String query) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Just like {@link #selectFirst(String)}, but if there is no match, throws an {@link IllegalArgumentException}. This
     *     is useful if you want to simply abort processing on a failed match.
     *     @param query the {@link org.jsoup.select.Selector} query.
     *     @return the first matching element
     *     @throws IllegalArgumentException if no match is found
     *     @throws IOException if an I/O error occurs
     */
    public Element expectNext(String query) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Finds the next Element that matches the provided query. The input will be parsed until the next match is found, or
     *     the input is completely read.
     *     <p>By providing a compiled evaluator vs a CSS selector, this method may be more efficient when executing the same
     *     query against multiple documents.</p>
     *     @param eval the {@link org.jsoup.select.Selector} evaluator.
     *     @return the next matching {@link Element}, or {@code null} if there's no match
     *     @throws IOException if an I/O error occurs
     *     @see Selector#evaluatorOf(String css)
     */
    @Nullable
    public Element selectNext(Evaluator eval) throws IOException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final class ElementIterator implements Iterator<Element>, NodeVisitor {

        // listeners add to a next emit queue, as a single token read step may yield multiple elements
        final private Queue<Element> emitQueue = new LinkedList<>();

        // most recently emitted
        @Nullable
        private Element current;

        // element waiting to be picked up
        @Nullable
        private Element next;

        // The last tailed element (</html>), on hold for final pop
        @Nullable
        private Element tail;

        void reset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        // Iterator Interface:
        /**
         *         {@inheritDoc}
         *         @throws UncheckedIOException if the underlying Reader errors during a read
         */
        @Override
        public boolean hasNext() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         {@inheritDoc}
         *         @throws UncheckedIOException if the underlying Reader errors during a read
         */
        @Override
        public Element next() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private void maybeFindNext() {
            if (stopped || next != null)
                return;
            // drain the current queue before stepping to get more
            if (!emitQueue.isEmpty()) {
                next = emitQueue.remove();
                return;
            }
            // step the parser, which will hit the node listeners to add to the queue:
            while (treeBuilder.stepParser()) {
                if (!emitQueue.isEmpty()) {
                    next = emitQueue.remove();
                    return;
                }
            }
            stop();
            close();
            // send the final element out:
            if (tail != null) {
                next = tail;
                tail = null;
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        // NodeVisitor Interface:
        @Override
        public void head(Node node, int depth) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void tail(Node node, int depth) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
