package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.internal.LineMap;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeInternals;
import org.jsoup.select.NodeVisitor;
import org.jspecify.annotations.Nullable;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import static org.jsoup.parser.Parser.NamespaceHtml;

/**
 * @author Jonathan Hedley
 */
abstract class TreeBuilder {

    protected Parser parser;

    CharacterReader reader;

    Tokeniser tokeniser;

    // current doc we are building into
    Document doc;

    // the stack of open elements
    ArrayList<Element> stack;

    // current base uri, for creating new elements
    String baseUri;

    // currentToken is used for error and source position tracking. Null at start of fragment parse
    Token currentToken;

    ParseSettings settings;

    // the tags we're using in this parse
    TagSet tagSet;

    // optional listener for node add / removes
    @Nullable
    NodeVisitor nodeListener;

    // start tag to process
    private Token.StartTag start;

    private final Token.EndTag end = new Token.EndTag(this);

    abstract ParseSettings defaultSettings();

    // optionally tracks source ranges of nodes and attributes
    boolean trackSourceRange;

    // shared line map for retained source ranges
    @Nullable
    LineMap lineMap;

    void initialiseParse(Reader input, String baseUri, Parser parser) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void completeParse() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Document parse(Reader input, String baseUri, Parser parser) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    List<Node> parseFragment(Reader inputFragment, @Nullable Element context, String baseUri, Parser parser) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void initialiseParseFragment(@Nullable Element context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    abstract List<Node> completeParseFragment();

    /**
     * Set the node listener, which will then get callbacks for node insert and removals.
     */
    void nodeListener(NodeVisitor nodeListener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Create a new copy of this TreeBuilder
     *     @return copy, ready for a new parse
     */
    abstract TreeBuilder newInstance();

    void runParser() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean stepParser() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    abstract boolean process(Token token);

    boolean processStartTag(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean processStartTag(String name, Attributes attrs) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean processEndTag(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Removes the last Element from the stack, hits onNodeClosed, and then returns it.
     * @return
     */
    Element pop() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Adds the specified Element to the end of the stack, and hits onNodeInserted.
     * @param element
     */
    final void push(Element element) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Ensures the stack respects {@link Parser#getMaxDepth()} by closing the deepest open elements until there is room for
     *     a new insertion.
     */
    final void enforceStackDepthLimit() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Hook for the HTML Tree Builder that needs to clean up when an element is removed due to the depth limit
     */
    void onStackPrunedForDepth(Element element) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Default maximum depth for parsers using this tree builder.
     */
    int defaultMaxDepth() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the current element (last on the stack). If all items have been removed, returns the document instead
     *     (which might not actually be on the stack; use stack.size() == 0 to test if required.
     *     @return the last element on the stack, if any; or the root document
     */
    Element currentElement() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Checks if the Current Element's normal name equals the supplied name, in the HTML namespace.
     *     @param normalName name to check
     *     @return true if there is a current element on the stack, and its name equals the supplied
     */
    boolean currentElementIs(String normalName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Checks if the Current Element's normal name equals the supplied name, in the specified namespace.
     *     @param normalName name to check
     *     @param namespace the namespace
     *     @return true if there is a current element on the stack, and its name equals the supplied
     */
    boolean currentElementIs(String normalName, String namespace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If the parser is tracking errors, add an error at the current position.
     * @param msg error message
     */
    void error(String msg) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If the parser is tracking errors, add an error at the current position.
     * @param msg error message template
     * @param args template arguments
     */
    void error(String msg, Object... args) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Tag tagFor(String tagName, String normalName, String namespace, ParseSettings settings) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Tag tagFor(Token.Tag token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets the default namespace for this TreeBuilder
     * @return the default namespace
     */
    String defaultNamespace() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    TagSet defaultTagSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Called by implementing TreeBuilders when a node has been inserted. This implementation includes optionally tracking
     *     the source range of the node.  @param node the node that was just inserted
     */
    void onNodeInserted(Node node) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Called by implementing TreeBuilders when a node is explicitly closed. This implementation includes optionally
     *     tracking the closing source range of the node.  @param node the node being closed
     */
    void onNodeClosed(Node node) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void trackNodePosition(Node node, boolean isStart) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Internal method, used by parser tokens to attach source ranges to Nodes and Attributes.
     */
    LineMap lineMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
