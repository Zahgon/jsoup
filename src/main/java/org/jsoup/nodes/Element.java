package org.jsoup.nodes;

import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.helper.Regex;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.jsoup.parser.TokenQueue;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeFilter;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.Nodes;
import org.jsoup.select.Selector;
import org.jspecify.annotations.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.jsoup.internal.Normalizer.normalize;
import static org.jsoup.nodes.Document.OutputSettings.Syntax.xml;
import static org.jsoup.nodes.TextNode.lastCharIsWhitespace;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.TokenQueue.escapeCssIdentifier;
import static org.jsoup.select.Selector.evaluatorOf;

/**
 * An HTML Element consists of a tag name, attributes, and child nodes (including text nodes and other elements).
 * <p>
 * From an Element, you can extract data, traverse the node graph, and manipulate the HTML.
 */
public class Element extends Node implements Iterable<Element> {

    private static final List<Element> EmptyChildren = Collections.emptyList();

    private static final NodeList EmptyNodeList = new NodeList(0);

    static final String BaseUriKey = Attributes.internalKey("baseUri");

    Tag tag;

    NodeList childNodes;

    // field is nullable but all methods for attributes are non-null
    @Nullable
    Attributes attributes;

    /**
     * Create a new, standalone element, in the specified namespace.
     * @param tag tag name
     * @param namespace namespace for this element
     */
    public Element(String tag, String namespace) {
        this(Tag.valueOf(tag, namespace, ParseSettings.preserveCase), null);
    }

    /**
     * Create a new, standalone element, in the HTML namespace.
     * @param tag tag name
     * @see #Element(String tag, String namespace)
     */
    public Element(String tag) {
        this(tag, Parser.NamespaceHtml);
    }

    /**
     * Create a new, standalone Element. (Standalone in that it has no parent.)
     *
     * @param tag tag of this element
     * @param baseUri the base URI (optional, may be null to inherit from parent, or "" to clear parent's)
     * @param attributes initial attributes (optional, may be null)
     * @see #appendChild(Node)
     * @see #appendElement(String)
     */
    public Element(Tag tag, @Nullable String baseUri, @Nullable Attributes attributes) {
        Validate.notNull(tag);
        childNodes = EmptyNodeList;
        this.attributes = attributes;
        this.tag = tag;
        if (!StringUtil.isBlank(baseUri))
            this.setBaseUri(baseUri);
    }

    /**
     * Create a new Element from a Tag and a base URI.
     *
     * @param tag element tag
     * @param baseUri the base URI of this element. Optional, and will inherit from its parent, if any.
     * @see Tag#valueOf(String, ParseSettings)
     */
    public Element(Tag tag, @Nullable String baseUri) {
        this(tag, baseUri, null);
    }

    /**
     *     Internal test to check if a nodelist object has been created.
     */
    protected boolean hasChildNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected List<Node> ensureChildNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected boolean hasAttributes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Attributes attributes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String baseUri() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    static String searchUpForAttribute(final Element start, final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void doSetBaseUri(String baseUri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int childNodeSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String nodeName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the name of the tag for this element. E.g. {@code div}. If you are using {@link ParseSettings#preserveCase
     * case preserving parsing}, this will return the source's original case.
     *
     * @return the tag name
     */
    public String tagName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the normalized name of this Element's tag. This will always be the lower-cased version of the tag, regardless
     * of the tag case preserving setting of the parser. For e.g., {@code <DIV>} and {@code <div>} both have a
     * normal name of {@code div}.
     * @return normal name
     */
    @Override
    public String normalName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Test if this Element has the specified normalized name, and is in the specified namespace.
     * @param normalName a normalized element name (e.g. {@code div}).
     * @param namespace the namespace
     * @return true if the element's normal name matches exactly, and is in the specified namespace
     * @since 1.17.2
     */
    public boolean elementIs(String normalName, String namespace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Change (rename) the tag of this element. For example, convert a {@code <span>} to a {@code <div>} with
     * {@code el.tagName("div");}.
     *
     * @param tagName new tag name for this element
     * @return this element, for chaining
     * @see Elements#tagName(String)
     */
    public Element tagName(String tagName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Change (rename) the tag of this element. For example, convert a {@code <span>} to a {@code <div>} with
     * {@code el.tagName("div");}.
     *
     * @param tagName new tag name for this element
     * @param namespace the new namespace for this element
     * @return this element, for chaining
     * @see Elements#tagName(String)
     */
    public Element tagName(String tagName, String namespace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the Tag for this element.
     *
     * @return the tag object
     */
    public Tag tag() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Change the Tag of this element.
     *     @param tag the new tag
     *     @return this element, for chaining
     *     @since 1.20.1
     */
    public Element tag(Tag tag) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Test if this element is a block-level element. (E.g. {@code <div> == true} or an inline element
     * {@code <span> == false}).
     *
     * @return true if block, false if not (and thus inline)
     */
    public boolean isBlock() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the {@code id} attribute of this element.
     *
     * @return The id attribute, if present, or an empty string if not.
     */
    public String id() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Set the {@code id} attribute of this element.
     *     @param id the ID value to use
     *     @return this Element, for chaining
     */
    public Element id(String id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set an attribute value on this element. If this element already has an attribute with the
     * key, its value is updated; otherwise, a new attribute is added.
     *
     * @return this element
     */
    @Override
    public Element attr(String attributeKey, String attributeValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set a boolean attribute value on this element. Setting to <code>true</code> sets the attribute value to "" and
     * marks the attribute as boolean so no value is written out. Setting to <code>false</code> removes the attribute
     * with the same key if it exists.
     *
     * @param attributeKey the attribute key
     * @param attributeValue the attribute value
     *
     * @return this element
     */
    public Element attr(String attributeKey, boolean attributeValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get an Attribute by key. Changes made via {@link Attribute#setKey(String)}, {@link Attribute#setValue(String)} etc
     *     will cascade back to this Element.
     *     @param key the (case-sensitive) attribute key
     *     @return the Attribute for this key, or null if not present.
     *     @since 1.17.2
     */
    @Nullable
    public Attribute attribute(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get this element's HTML5 custom data attributes. Each attribute in the element that has a key
     * starting with "data-" is included the dataset.
     * <p>
     * E.g., the element {@code <div data-package="jsoup" data-language="Java" class="group">...} has the dataset
     * {@code package=jsoup, language=java}.
     * <p>
     * This map is a filtered view of the element's attribute map. Changes to one map (add, remove, update) are reflected
     * in the other map.
     * <p>
     * You can find elements that have data attributes using the {@code [^data-]} attribute key prefix selector.
     * @return a map of {@code key=value} custom data attributes.
     */
    public Map<String, String> dataset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @Nullable
    public final Element parent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get this element's parent and ancestors, up to the document root.
     * @return this element's stack of parents, starting with the closest first.
     */
    public Elements parents() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get a child element of this element, by its 0-based index number.
     * <p>
     * Note that an element can have both mixed Nodes and Elements as children. This method inspects
     * a filtered list of children that are elements, and the index is based on that filtered list.
     * </p>
     *
     * @param index the index number of the element to retrieve
     * @return the child element, if it exists, otherwise throws an {@code IndexOutOfBoundsException}
     * @see #childNode(int)
     */
    public Element child(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the number of child nodes of this element that are elements.
     * <p>
     * This method works on the same filtered list like {@link #child(int)}. Use {@link #childNodes()} and {@link
     * #childNodeSize()} to get the unfiltered Nodes (e.g. includes TextNodes etc.)
     * </p>
     *
     * @return the number of child nodes that are elements
     * @see #children()
     * @see #child(int)
     */
    public int childrenSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get this element's child elements.
     * <p>
     * This is effectively a filter on {@link #childNodes()} to get Element nodes.
     * </p>
     * @return child elements. If this element has no children, returns an empty list.
     * @see #childNodes()
     */
    public Elements children() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Maintains a shadow copy of this element's child elements. If the nodelist is changed, this cache is invalidated.
     * @return a list of child elements
     */
    List<Element> childElementsList() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static final String childElsKey = "jsoup.childEls";

    private static final String childElsMod = "jsoup.childElsMod";

    /**
     * returns the cached child els, if they exist, and the modcount of our childnodes matches the stashed modcount
     */
    @SuppressWarnings("unchecked")
    @Nullable
    List<Element> cachedChildren() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * caches the child els into the Attribute user data.
     */
    private void stashChildren(List<Element> els) {
        Map<String, Object> userData = attributes().userData();
        WeakReference<List<Element>> ref = new WeakReference<>(els);
        userData.put(childElsKey, ref);
        userData.put(childElsMod, childNodes.modCount());
    }

    /**
     *     Returns a Stream of this Element and all of its descendant Elements. The stream has document order.
     *     @return a stream of this element and its descendants.
     *     @see #nodeStream()
     *     @since 1.17.1
     */
    public Stream<Element> stream() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private <T> List<T> filterNodes(Class<T> clazz) {
        return childNodes.stream().filter(clazz::isInstance).map(clazz::cast).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    /**
     * Get this element's child text nodes. The list is unmodifiable but the text nodes may be manipulated.
     * <p>
     * This is effectively a filter on {@link #childNodes()} to get Text nodes.
     * @return child text nodes. If this element has no text nodes, returns an
     * empty list.
     * </p>
     * For example, with the input HTML: {@code <p>One <span>Two</span> Three <br> Four</p>} with the {@code p} element selected:
     * <ul>
     *     <li>{@code p.text()} = {@code "One Two Three Four"}</li>
     *     <li>{@code p.ownText()} = {@code "One Three Four"}</li>
     *     <li>{@code p.children()} = {@code Elements[<span>, <br>]}</li>
     *     <li>{@code p.childNodes()} = {@code List<Node>["One ", <span>, " Three ", <br>, " Four"]}</li>
     *     <li>{@code p.textNodes()} = {@code List<TextNode>["One ", " Three ", " Four"]}</li>
     * </ul>
     */
    public List<TextNode> textNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get this element's child data nodes. The list is unmodifiable but the data nodes may be manipulated.
     * <p>
     * This is effectively a filter on {@link #childNodes()} to get Data nodes.
     * </p>
     * @return child data nodes. If this element has no data nodes, returns an
     * empty list.
     * @see #data()
     */
    public List<DataNode> dataNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that match the {@link Selector} CSS query, with this element as the starting context. Matched elements
     * may include this element, or any of its descendents.
     * <p>If the query starts with a combinator (e.g. {@code *} or {@code >}), that will combine to this element.</p>
     * <p>This method is generally more powerful to use than the DOM-type {@code getElementBy*} methods, because
     * multiple filters can be combined, e.g.:</p>
     * <ul>
     * <li>{@code el.select("a[href]")} - finds links ({@code a} tags with {@code href} attributes)</li>
     * <li>{@code el.select("a[href*=example.com]")} - finds links pointing to example.com (loosely)</li>
     * <li>{@code el.select("* div")} - finds all divs that descend from this element (and excludes this element)</li>
     * <li>{@code el.select("> div")} - finds all divs that are direct children of this element (and excludes this element)</li>
     * </ul>
     * <p>See the query syntax documentation in {@link org.jsoup.select.Selector}.</p>
     * <p>Also known as {@code querySelectorAll()} in the Web DOM.</p>
     *
     * @param cssQuery a {@link Selector} CSS-like query
     * @return an {@link Elements} list containing elements that match the query (empty if none match)
     * @see Selector selector query syntax
     * @see #select(Evaluator)
     * @throws Selector.SelectorParseException (unchecked) on an invalid CSS query.
     */
    public Elements select(String cssQuery) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that match the supplied Evaluator. This has the same functionality as {@link #select(String)}, but
     * may be useful if you are running the same query many times (on many documents) and want to save the overhead of
     * repeatedly parsing the CSS query.
     * @param evaluator an element evaluator
     * @return an {@link Elements} list containing elements that match the query (empty if none match)
     * @see Selector#evaluatorOf(String css)
     */
    public Elements select(Evaluator evaluator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Selects elements from the given root that match the specified {@link Selector} CSS query, with this element as the
     *     starting context, and returns them as a lazy Stream. Matched elements may include this element, or any of its
     *     children.
     *     <p>
     *     Unlike {@link #select(String query)}, which returns a complete list of all matching elements, this method returns a
     *     {@link Stream} that processes elements lazily as they are needed. The stream operates in a "pull" model — elements
     *     are fetched from the root as the stream is traversed. You can use standard {@code Stream} operations such as
     *     {@code filter}, {@code map}, or {@code findFirst} to process elements on demand.
     *     </p>
     *
     *     @param cssQuery a {@link Selector} CSS-like query
     *     @return a {@link Stream} containing elements that match the query (empty if none match)
     *     @throws Selector.SelectorParseException (unchecked) on an invalid CSS query.
     *     @see Selector selector query syntax
     *     @see #selectStream(Evaluator eval)
     *     @since 1.19.1
     */
    public Stream<Element> selectStream(String cssQuery) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Find a Stream of elements that match the supplied Evaluator.
     *
     *     @param evaluator an element Evaluator
     *     @return a {@link Stream} containing elements that match the query (empty if none match)
     *     @see Selector#evaluatorOf(String css)
     *     @since 1.19.1
     */
    public Stream<Element> selectStream(Evaluator evaluator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find the first Element that matches the {@link Selector} CSS query, with this element as the starting context.
     * <p>This is effectively the same as calling {@code element.select(query).first()}, but is more efficient as query
     * execution stops on the first hit.</p>
     * <p>Also known as {@code querySelector()} in the Web DOM.</p>
     * @param cssQuery cssQuery a {@link Selector} CSS-like query
     * @return the first matching element, or <b>{@code null}</b> if there is no match.
     * @see #expectFirst(String)
     */
    @Nullable
    public Element selectFirst(String cssQuery) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Finds the first Element that matches the supplied Evaluator, with this element as the starting context, or
     * {@code null} if none match.
     *
     * @param evaluator an element evaluator
     * @return the first matching element (walking down the tree, starting from this element), or {@code null} if none
     * match.
     */
    @Nullable
    public Element selectFirst(Evaluator evaluator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Just like {@link #selectFirst(String)}, but if there is no match, throws an {@link IllegalArgumentException}. This
     *     is useful if you want to simply abort processing on a failed match.
     *     @param cssQuery a {@link Selector} CSS-like query
     *     @return the first matching element
     *     @throws IllegalArgumentException if no match is found
     *     @since 1.15.2
     */
    public Element expectFirst(String cssQuery) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Find nodes that match the supplied {@link Evaluator}, with this element as the starting context. Matched
     *     nodes may include this element, or any of its descendents.
     *
     *     @param evaluator an evaluator
     *     @return a list of nodes that match the query (empty if none match)
     *     @since 1.21.1
     */
    public Nodes<Node> selectNodes(Evaluator evaluator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Find nodes that match the supplied {@link Selector} CSS query, with this element as the starting context. Matched
     *     nodes may include this element, or any of its descendents.
     *     <p>To select leaf nodes, the query should specify the node type, e.g. {@code ::text},
     *     {@code ::comment}, {@code ::data}, {@code ::leafnode}.</p>
     *
     *     @param cssQuery a {@link Selector} CSS query
     *     @return a list of nodes that match the query (empty if none match)
     *     @since 1.21.1
     */
    public Nodes<Node> selectNodes(String cssQuery) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Find nodes that match the supplied Evaluator, with this element as the starting context. Matched
     *     nodes may include this element, or any of its descendents.
     *
     *     @param evaluator an evaluator
     *     @param type the type of node to collect (e.g. {@link Element}, {@link LeafNode}, {@link TextNode} etc)
     *     @param <T> the type of node to collect
     *     @return a list of nodes that match the query (empty if none match)
     *     @since 1.21.1
     */
    public <T extends Node> Nodes<T> selectNodes(Evaluator evaluator, Class<T> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Find nodes that match the supplied {@link Selector} CSS query, with this element as the starting context. Matched
     *     nodes may include this element, or any of its descendents.
     *     <p>To select specific node types, use {@code ::text}, {@code ::comment}, {@code ::leafnode}, etc. For example, to
     *     select all text nodes under {@code p} elements: </p>
     *     <pre>    Nodes&lt;TextNode&gt; textNodes = doc.selectNodes("p ::text", TextNode.class);</pre>
     *
     *     @param cssQuery a {@link Selector} CSS query
     *     @param type the type of node to collect (e.g. {@link Element}, {@link LeafNode}, {@link TextNode} etc)
     *     @param <T> the type of node to collect
     *     @return a list of nodes that match the query (empty if none match)
     *     @since 1.21.1
     */
    public <T extends Node> Nodes<T> selectNodes(String cssQuery, Class<T> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Find the first Node that matches the {@link Selector} CSS query, with this element as the starting context.
     *     <p>This is effectively the same as calling {@code element.selectNodes(query).first()}, but is more efficient as
     *     query
     *     execution stops on the first hit.</p>
     *     <p>Also known as {@code querySelector()} in the Web DOM.</p>
     *
     *     @param cssQuery cssQuery a {@link Selector} CSS-like query
     *     @return the first matching node, or <b>{@code null}</b> if there is no match.
     *     @since 1.21.1
     *     @see #expectFirst(String)
     */
    @Nullable
    public <T extends Node> T selectFirstNode(String cssQuery, Class<T> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Finds the first Node that matches the supplied Evaluator, with this element as the starting context, or
     *     {@code null} if none match.
     *
     *     @param evaluator an element evaluator
     *     @return the first matching node (walking down the tree, starting from this element), or {@code null} if none
     *     match.
     *     @since 1.21.1
     */
    @Nullable
    public <T extends Node> T selectFirstNode(Evaluator evaluator, Class<T> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Just like {@link #selectFirstNode(String, Class)}, but if there is no match, throws an
     *     {@link IllegalArgumentException}. This is useful if you want to simply abort processing on a failed match.
     *
     *     @param cssQuery a {@link Selector} CSS-like query
     *     @return the first matching node
     *     @throws IllegalArgumentException if no match is found
     *     @since 1.21.1
     */
    public <T extends Node> T expectFirstNode(String cssQuery, Class<T> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks if this element matches the given {@link Selector} CSS query. Also knows as {@code matches()} in the Web
     * DOM.
     *
     * @param cssQuery a {@link Selector} CSS query
     * @return if this element matches the query
     */
    public boolean is(String cssQuery) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if this element matches the given evaluator.
     * @param evaluator an element evaluator
     * @return if this element matches
     */
    public boolean is(Evaluator evaluator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find the closest element up the tree of parents that matches the specified CSS query. Will return itself, an
     * ancestor, or {@code null} if there is no such matching element.
     * @param cssQuery a {@link Selector} CSS query
     * @return the closest ancestor element (possibly itself) that matches the provided evaluator. {@code null} if not
     * found.
     */
    @Nullable
    public Element closest(String cssQuery) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find the closest element up the tree of parents that matches the specified evaluator. Will return itself, an
     * ancestor, or {@code null} if there is no such matching element.
     * @param evaluator a query evaluator
     * @return the closest ancestor element (possibly itself) that matches the provided evaluator. {@code null} if not
     * found.
     */
    @Nullable
    public Element closest(Evaluator evaluator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Find Elements that match the supplied {@index XPath} expression.
     *     <p>Note that for convenience of writing the Xpath expression, namespaces are disabled, and queries can be
     *     expressed using the element's local name only.</p>
     *     <p>By default, XPath 1.0 expressions are supported. If you would to use XPath 2.0 or higher, you can provide an
     *     alternate XPathFactory implementation:</p>
     *     <ol>
     *     <li>Add the implementation to your classpath. E.g. to use <a href="https://www.saxonica.com/products/products.xml">Saxon-HE</a>, add <a href="https://mvnrepository.com/artifact/net.sf.saxon/Saxon-HE">net.sf.saxon:Saxon-HE</a> to your build.</li>
     *     <li>Set the system property <code>javax.xml.xpath.XPathFactory:jsoup</code> to the implementing classname. E.g.:<br>
     *     <code>System.setProperty(W3CDom.XPathFactoryProperty, "net.sf.saxon.xpath.XPathFactoryImpl");</code>
     *     </li>
     *     </ol>
     *
     *     @param xpath XPath expression
     *     @return matching elements, or an empty list if none match.
     *     @see #selectXpath(String, Class)
     *     @since 1.14.3
     */
    public Elements selectXpath(String xpath) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Find Nodes that match the supplied XPath expression.
     *     <p>For example, to select TextNodes under {@code p} elements: </p>
     *     <pre>List&lt;TextNode&gt; textNodes = doc.selectXpath("//body//p//text()", TextNode.class);</pre>
     *     <p>Note that in the jsoup DOM, Attribute objects are not Nodes. To directly select attribute values, do something
     *     like:</p>
     *     <pre>List&lt;String&gt; hrefs = doc.selectXpath("//a").eachAttr("href");</pre>
     *     @param xpath XPath expression
     *     @param nodeType the jsoup node type to return
     *     @see #selectXpath(String)
     *     @return a list of matching nodes
     *     @since 1.14.3
     */
    public <T extends Node> List<T> selectXpath(String xpath, Class<T> nodeType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Insert a node to the end of this Element's children. The incoming node will be re-parented.
     *
     * @param child node to add.
     * @return this Element, for chaining
     * @see #prependChild(Node)
     * @see #insertChildren(int, Collection)
     */
    public Element appendChild(Node child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Insert the given nodes to the end of this Element's children.
     *
     *     @param children nodes to add
     *     @return this Element, for chaining
     *     @see #insertChildren(int, Collection)
     */
    public Element appendChildren(Collection<? extends Node> children) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add this element to the supplied parent element, as its next child.
     *
     * @param parent element to which this element will be appended
     * @return this element, so that you can continue modifying the element
     */
    public Element appendTo(Element parent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a node to the start of this element's children.
     *
     * @param child node to add.
     * @return this element, so that you can add more child nodes or elements.
     */
    public Element prependChild(Node child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Insert the given nodes to the start of this Element's children.
     *
     *     @param children nodes to add
     *     @return this Element, for chaining
     *     @see #insertChildren(int, Collection)
     */
    public Element prependChildren(Collection<? extends Node> children) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the given child nodes into this element at the specified index. Current nodes will be shifted to the
     * right. The inserted nodes will be moved from their current parent. To prevent moving, copy the nodes first.
     *
     * @param index 0-based index to insert children at. Specify {@code 0} to insert at the start, {@code -1} at the
     * end
     * @param children child nodes to insert
     * @return this element, for chaining.
     */
    public Element insertChildren(int index, Collection<? extends Node> children) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the given child nodes into this element at the specified index. Current nodes will be shifted to the
     * right. The inserted nodes will be moved from their current parent. To prevent moving, copy the nodes first.
     *
     * @param index 0-based index to insert children at. Specify {@code 0} to insert at the start, {@code -1} at the
     * end
     * @param children child nodes to insert
     * @return this element, for chaining.
     */
    public Element insertChildren(int index, Node... children) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a new element by tag name, and add it as this Element's last child.
     *
     * @param tagName the name of the tag (e.g. {@code div}).
     * @return the new element, to allow you to add content to it, e.g.:
     *  {@code parent.appendElement("h1").attr("id", "header").text("Welcome");}
     */
    public Element appendElement(String tagName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a new element by tag name and namespace, add it as this Element's last child.
     *
     * @param tagName the name of the tag (e.g. {@code div}).
     * @param namespace the namespace of the tag (e.g. {@link Parser#NamespaceHtml})
     * @return the new element, in the specified namespace
     */
    public Element appendElement(String tagName, String namespace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a new element by tag name, and add it as this Element's first child.
     *
     * @param tagName the name of the tag (e.g. {@code div}).
     * @return the new element, to allow you to add content to it, e.g.:
     *  {@code parent.prependElement("h1").attr("id", "header").text("Welcome");}
     */
    public Element prependElement(String tagName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a new element by tag name and namespace, and add it as this Element's first child.
     *
     * @param tagName the name of the tag (e.g. {@code div}).
     * @param namespace the namespace of the tag (e.g. {@link Parser#NamespaceHtml})
     * @return the new element, in the specified namespace
     */
    public Element prependElement(String tagName, String namespace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create and append a new TextNode to this element.
     *
     * @param text the (un-encoded) text to add
     * @return this element
     */
    public Element appendText(String text) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create and prepend a new TextNode to this element.
     *
     * @param text the decoded text to add
     * @return this element
     */
    public Element prependText(String text) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add inner HTML to this element. The supplied HTML will be parsed, and each node appended to the end of the children.
     * @param html HTML to add inside this element, after the existing HTML
     * @return this element
     * @see #html(String)
     */
    public Element append(String html) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add inner HTML into this element. The supplied HTML will be parsed, and each node prepended to the start of the element's children.
     * @param html HTML to add inside this element, before the existing HTML
     * @return this element
     * @see #html(String)
     */
    public Element prepend(String html) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Insert the specified HTML into the DOM before this element (as a preceding sibling).
     *
     * @param html HTML to add before this element
     * @return this element, for chaining
     * @see #after(String)
     */
    @Override
    public Element before(String html) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Insert the specified node into the DOM before this node (as a preceding sibling).
     * @param node to add before this element
     * @return this Element, for chaining
     * @see #after(Node)
     */
    @Override
    public Element before(Node node) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Insert the specified HTML into the DOM after this element (as a following sibling).
     *
     * @param html HTML to add after this element
     * @return this element, for chaining
     * @see #before(String)
     */
    @Override
    public Element after(String html) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Insert the specified node into the DOM after this node (as a following sibling).
     * @param node to add after this element
     * @return this element, for chaining
     * @see #before(Node)
     */
    @Override
    public Element after(Node node) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Remove all the element's child nodes. Any attributes are left as-is. Each child node has its parent set to
     * {@code null}.
     * @return this element
     */
    @Override
    public Element empty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Wrap the supplied HTML around this element.
     *
     * @param html HTML to wrap around this element, e.g. {@code <div class="head"></div>}. Can be arbitrarily deep.
     * @return this element, for chaining.
     */
    @Override
    public Element wrap(String html) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets an #id selector for this element, if it has a unique ID. Otherwise, returns an empty string.
     *
     *     @param ownerDoc the document that owns this element, if there is one
     */
    private String uniqueIdSelector(@Nullable Document ownerDoc) {
        String id = id();
        if (!id.isEmpty()) {
            // check if the ID is unique and matches this
            String idSel = "#" + escapeCssIdentifier(id);
            if (ownerDoc != null) {
                Elements els = ownerDoc.select(idSel);
                if (els.size() == 1 && els.get(0) == this)
                    return idSel;
            } else {
                return idSel;
            }
        }
        return EmptyString;
    }

    /**
     *     Get a CSS selector that will uniquely select this element.
     *     <p>
     *     If the element has an ID, returns #id; otherwise returns the parent (if any) CSS selector, followed by
     *     {@literal '>'}, followed by a unique selector for the element (tag.class.class:nth-child(n)).
     *     </p>
     *
     *     @return the CSS Path that can be used to retrieve the element in a selector.
     */
    public String cssSelector() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private String cssSelectorComponent() {
        // Escape tagname, and translate HTML namespace ns:tag to CSS namespace syntax ns|tag
        String tagName = escapeCssIdentifier(tagName()).replace("\\:", "|");
        StringBuilder selector = StringUtil.borrowBuilder().append(tagName);
        String classes = classNames().stream().map(TokenQueue::escapeCssIdentifier).collect(StringUtil.joining("."));
        if (!classes.isEmpty())
            selector.append('.').append(classes);
        if (// don't add Document to selector, as will always have a html node
        parent() == null || parent() instanceof Document)
            return StringUtil.releaseBuilder(selector);
        selector.insert(0, " > ");
        if (parent().select(selector.toString()).size() > 1)
            selector.append(String.format(":nth-child(%d)", elementSiblingIndex() + 1));
        return StringUtil.releaseBuilder(selector);
    }

    /**
     * Get sibling elements. If the element has no sibling elements, returns an empty list. An element is not a sibling
     * of itself, so will not be included in the returned list.
     * @return sibling elements
     */
    public Elements siblingElements() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get each of the sibling elements that come after this element.
     *
     * @return each of the element siblings after this element, or an empty list if there are no next sibling elements
     */
    public Elements nextElementSiblings() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get each of the element siblings before this element.
     *
     * @return the previous element siblings, or an empty list if there are none.
     */
    public Elements previousElementSiblings() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Elements nextElementSiblings(boolean next) {
        Elements els = new Elements();
        if (parentNode == null)
            return els;
        els.add(this);
        return next ? els.nextAll() : els.prevAll();
    }

    /**
     * Gets the first Element sibling of this element. That may be this element.
     * @return the first sibling that is an element (aka the parent's first element child)
     */
    public Element firstElementSibling() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the list index of this element in its element sibling list. I.e. if this is the first element
     * sibling, returns 0.
     * @return position in element sibling list
     */
    public int elementSiblingIndex() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the last element sibling of this element. That may be this element.
     * @return the last sibling that is an element (aka the parent's last element child)
     */
    public Element lastElementSibling() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static <E extends Element> int indexInList(Element search, List<E> elements) {
        final int size = elements.size();
        for (int i = 0; i < size; i++) {
            if (elements.get(i) == search)
                return i;
        }
        return 0;
    }

    /**
     *     Gets the first child of this Element that is an Element, or {@code null} if there is none.
     *     @return the first Element child node, or null.
     *     @see #firstChild()
     *     @see #lastElementChild()
     *     @since 1.15.2
     */
    @Nullable
    public Element firstElementChild() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets the last child of this Element that is an Element, or @{code null} if there is none.
     *     @return the last Element child node, or null.
     *     @see #lastChild()
     *     @see #firstElementChild()
     *     @since 1.15.2
     */
    @Nullable
    public Element lastElementChild() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // DOM type methods
    /**
     * Finds elements, including and recursively under this element, with the specified tag name.
     * @param tagName The tag name to search for (case insensitively).
     * @return a matching unmodifiable list of elements. Will be empty if this element and none of its children match.
     */
    public Elements getElementsByTag(String tagName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find an element by ID, including or under this element.
     * <p>
     * Note that this finds the first matching ID, starting with this element. If you search down from a different
     * starting point, it is possible to find a different element by ID. For unique element by ID within a Document,
     * use {@link Document#getElementById(String)}
     * @param id The ID to search for.
     * @return The first matching element by ID, starting with this element, or null if none found.
     */
    @Nullable
    public Element getElementById(String id) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that have this class, including or under this element. Case-insensitive.
     * <p>
     * Elements can have multiple classes (e.g. {@code <div class="header round first">}). This method
     * checks each class, so you can find the above with {@code el.getElementsByClass("header");}.
     *
     * @param className the name of the class to search for.
     * @return elements with the supplied class name, empty if none
     * @see #hasClass(String)
     * @see #classNames()
     */
    public Elements getElementsByClass(String className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that have a named attribute set. Case-insensitive.
     *
     * @param key name of the attribute, e.g. {@code href}
     * @return elements that have this attribute, empty if none
     */
    public Elements getElementsByAttribute(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that have an attribute name starting with the supplied prefix. Use {@code data-} to find elements
     * that have HTML5 datasets.
     * @param keyPrefix name prefix of the attribute e.g. {@code data-}
     * @return elements that have attribute names that start with the prefix, empty if none.
     */
    public Elements getElementsByAttributeStarting(String keyPrefix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that have an attribute with the specific value. Case-insensitive.
     *
     * @param key name of the attribute
     * @param value value of the attribute
     * @return elements that have this attribute with this value, empty if none
     */
    public Elements getElementsByAttributeValue(String key, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that either do not have this attribute, or have it with a different value. Case-insensitive.
     *
     * @param key name of the attribute
     * @param value value of the attribute
     * @return elements that do not have a matching attribute
     */
    public Elements getElementsByAttributeValueNot(String key, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that have attributes that start with the value prefix. Case-insensitive.
     *
     * @param key name of the attribute
     * @param valuePrefix start of attribute value
     * @return elements that have attributes that start with the value prefix
     */
    public Elements getElementsByAttributeValueStarting(String key, String valuePrefix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that have attributes that end with the value suffix. Case-insensitive.
     *
     * @param key name of the attribute
     * @param valueSuffix end of the attribute value
     * @return elements that have attributes that end with the value suffix
     */
    public Elements getElementsByAttributeValueEnding(String key, String valueSuffix) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that have attributes whose value contains the match string. Case-insensitive.
     *
     * @param key name of the attribute
     * @param match substring of value to search for
     * @return elements that have attributes containing this text
     */
    public Elements getElementsByAttributeValueContaining(String key, String match) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that have an attribute whose value matches the supplied regular expression.
     * @param key name of the attribute
     * @param pattern compiled regular expression to match against attribute values
     * @return elements that have attributes matching this regular expression
     */
    public Elements getElementsByAttributeValueMatching(String key, Pattern pattern) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that have attributes whose values match the supplied regular expression.
     * @param key name of the attribute
     * @param regex regular expression to match against attribute values. You can use <a href="http://java.sun.com/docs/books/tutorial/essential/regex/pattern.html#embedded">embedded flags</a> (such as {@code (?i)} and {@code (?m)}) to control regex options.
     * @return elements that have attributes matching this regular expression
     */
    public Elements getElementsByAttributeValueMatching(String key, String regex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements whose sibling index is less than the supplied index.
     * @param index 0-based index
     * @return elements less than index
     */
    public Elements getElementsByIndexLessThan(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements whose sibling index is greater than the supplied index.
     * @param index 0-based index
     * @return elements greater than index
     */
    public Elements getElementsByIndexGreaterThan(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements whose sibling index is equal to the supplied index.
     * @param index 0-based index
     * @return elements equal to index
     */
    public Elements getElementsByIndexEquals(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that contain the specified string. The search is case-insensitive. The text may appear directly
     * in the element, or in any of its descendants.
     * @param searchText to look for in the element's text
     * @return elements that contain the string, case-insensitive.
     * @see Element#text()
     */
    public Elements getElementsContainingText(String searchText) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements that directly contain the specified string. The search is case-insensitive. The text must appear directly
     * in the element, not in any of its descendants.
     * @param searchText to look for in the element's own text
     * @return elements that contain the string, case-insensitive.
     * @see Element#ownText()
     */
    public Elements getElementsContainingOwnText(String searchText) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements whose text matches the supplied regular expression.
     * @param pattern regular expression to match text against
     * @return elements matching the supplied regular expression.
     * @see Element#text()
     */
    public Elements getElementsMatchingText(Pattern pattern) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements whose text matches the supplied regular expression.
     * @param regex regular expression to match text against. You can use <a href="http://java.sun.com/docs/books/tutorial/essential/regex/pattern.html#embedded">embedded flags</a> (such as {@code (?i)} and {@code (?m)}) to control regex options.
     * @return elements matching the supplied regular expression.
     * @see Element#text()
     */
    public Elements getElementsMatchingText(String regex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements whose own text matches the supplied regular expression.
     * @param pattern regular expression to match text against
     * @return elements matching the supplied regular expression.
     * @see Element#ownText()
     */
    public Elements getElementsMatchingOwnText(Pattern pattern) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find elements whose own text matches the supplied regular expression.
     * @param regex regular expression to match text against. You can use <a href="http://java.sun.com/docs/books/tutorial/essential/regex/pattern.html#embedded">embedded flags</a> (such as {@code (?i)} and {@code (?m)}) to control regex options.
     * @return elements matching the supplied regular expression.
     * @see Element#ownText()
     */
    public Elements getElementsMatchingOwnText(String regex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Find all elements under this element (including self, and children of children).
     *
     * @return all elements
     */
    public Elements getAllElements() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets the <b>normalized, combined text</b> of this element and all its children. Whitespace is normalized and
     *     trimmed.
     *     <p>For example, given HTML {@code <p>Hello  <b>there</b> now! </p>}, {@code p.text()} returns {@code "Hello there
     *    now!"}
     *     <p>If you do not want normalized text, use {@link #wholeText()}. If you want just the text of this node (and not
     *     children), use {@link #ownText()}.
     *     <p>This method returns normalized, readable plain text for downstream uses such as data extraction,
     *     indexing, and accessibility-oriented processing. The contents of data nodes (such as
     *     {@code <script>} tags) are not considered text. Use {@link #data()} or {@link #html()} to retrieve
     *     that content.
     *
     *     @return decoded, normalized text, or empty string if none.
     *     @see #wholeText()
     *     @see #ownText()
     *     @see #textNodes()
     */
    public String text() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static class TextAccumulator implements NodeVisitor {

        private final StringBuilder accum;

        public TextAccumulator(StringBuilder accum) {
            this.accum = accum;
        }

        @Override
        public void head(Node node, int depth) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void tail(Node node, int depth) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * check if an element should separate preceding text during text()
         */
        private static boolean needsLeadingTextSeparator(Element element) {
            return element.isBlock() || element.nameIs("br") || element.tag.is(Tag.TextBoundary) && element.childNodeSize() > 0 && element.hasText();
        }

        /**
         * check if an element should separate following text during text()
         */
        private static boolean needsTrailingTextSeparator(Element element) {
            return element.tag.is(Tag.TextBoundary) || !element.tag.isInline() || hasBlockChild(element);
        }

        /**
         * check if an inline wrapper contains direct block children and should close with a separator
         */
        private static boolean hasBlockChild(Element element) {
            for (int i = 0; i < element.childNodeSize(); i++) {
                Node child = element.childNode(i);
                if (child instanceof Element && ((Element) child).isBlock())
                    return true;
            }
            return false;
        }
    }

    /**
     *     Get the decoded text of this element and its children, preserving source whitespace and newlines from text nodes.
     *     Unlike {@link #text()}, no separators are inferred around element boundaries; {@code <br>} elements are returned
     *     as newlines.
     *     @return decoded, non-normalized text
     *     @see #text()
     *     @see #wholeOwnText()
     */
    public String wholeText() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     An Element's nodeValue is its whole own text.
     */
    @Override
    public String nodeValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static String wholeTextOf(Stream<Node> stream) {
        return stream.map(node -> {
            if (node instanceof TextNode)
                return ((TextNode) node).getWholeText();
            if (node.nameIs("br"))
                return "\n";
            return "";
        }).collect(StringUtil.joining(""));
    }

    /**
     *     Get the non-normalized, decoded text of this element, <b>not including</b> any child elements, including any
     *     newlines and spaces present in the original source.
     *     @return decoded, non-normalized text that is a direct child of this Element
     *     @see #text()
     *     @see #wholeText()
     *     @see #ownText()
     *     @since 1.15.1
     */
    public String wholeOwnText() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the (normalized) text owned by this element only; does not get the combined text of all children.
     * <p>
     * For example, given HTML {@code <p>Hello <b>there</b> now!</p>}, {@code p.ownText()} returns {@code "Hello now!"},
     * whereas {@code p.text()} returns {@code "Hello there now!"}.
     * Note that the text within the {@code b} element is not returned, as it is not a direct child of the {@code p} element.
     *
     * @return decoded text, or empty string if none.
     * @see #text()
     * @see #textNodes()
     */
    public String ownText() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void ownText(StringBuilder accum) {
        for (int i = 0; i < childNodeSize(); i++) {
            Node child = childNodes.get(i);
            if (child instanceof TextNode) {
                TextNode textNode = (TextNode) child;
                appendNormalisedText(accum, textNode);
            } else if (child.nameIs("br") && !lastCharIsWhitespace(accum)) {
                accum.append(" ");
            }
        }
    }

    private static void appendNormalisedText(StringBuilder accum, TextNode textNode) {
        String text = textNode.getWholeText();
        if (preserveWhitespace(textNode.parentNode) || textNode instanceof CDataNode)
            accum.append(text);
        else
            StringUtil.appendNormalisedWhitespace(accum, text, lastCharIsWhitespace(accum));
    }

    static boolean preserveWhitespace(@Nullable Node node) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the text of this element. Any existing contents (text or elements) will be cleared.
     * <p>As a special case, for {@code <script>} and {@code <style>} tags, the input text will be treated as data,
     * not visible text.</p>
     * @param text decoded text
     * @return this element
     */
    public Element text(String text) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Checks if the current element or any of its child elements contain non-whitespace text.
     *     @return {@code true} if the element has non-blank text content, {@code false} otherwise.
     */
    public boolean hasText() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the combined data of this element. Data is e.g. the inside of a {@code <script>} tag. Note that data is NOT the
     *     plain text of the element. Use {@link #text()} to get normalized, readable text for extraction, indexing, or
     *     accessibility-oriented processing, and {@code data()} for the contents of scripts, comments, CSS styles, etc.
     *
     *     @return the data, or empty string if none
     *     @see #dataNodes()
     */
    public String data() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the literal value of this element's "class" attribute, which may include multiple class names, space
     * separated. (E.g. on <code>&lt;div class="header gray"&gt;</code> returns, "<code>header gray</code>")
     * @return The literal class attribute, or <b>empty string</b> if no class attribute set.
     */
    public String className() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get each of the element's class names. E.g. on element {@code <div class="header gray">},
     *     returns a set of two elements {@code "header", "gray"}.
     *     <p>Note that modifications to this set are not pushed to the backing {@code class} attribute; use
     *     {@link #classNames(Set)} to persist them.</p>
     *     <p>Use {@link #classList()} for a more efficient, read-only list that preserves duplicate class names.</p>
     *
     *     @return set of class names, empty if no class attribute
     *     @see #classNames(Set)
     *     @see #hasClass(String)
     *     @see #classList()
     */
    public Set<String> classNames() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get each of the element's class names, in attribute order. E.g. on element
     *     {@code <div class="header gray">}, returns a list of two elements {@code "header", "gray"}.
     *     <p>This immutable snapshot preserves duplicate class names, and is more memory efficient than
     *     {@link #classNames()} when a read-only result is sufficient, particularly for elements without class names.
     *     Use {@link #classNames()} for a mutable set of unique class names.</p>
     *
     *     @return immutable list of class names, empty if no class attribute
     *     @see #classNames()
     *     @see #hasClass(String)
     *     @since 1.23.1
     */
    public List<String> classList() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Find the next class token start.
     */
    private static int nextClassStart(String classAttr, int offset, int len) {
        while (offset < len && StringUtil.isWhitespace(classAttr.charAt(offset))) offset++;
        return offset;
    }

    /**
     *     Find the next class token end.
     */
    private static int nextClassEnd(String classAttr, int offset, int len) {
        while (offset < len && !StringUtil.isWhitespace(classAttr.charAt(offset))) offset++;
        return offset;
    }

    /**
     *     Returns the class token while preserving the original string for a single unpadded class.
     */
    private static String classToken(String classAttr, int start, int end) {
        return start == 0 && end == classAttr.length() ? classAttr : classAttr.substring(start, end);
    }

    /**
     *     Set the element's {@code class} attribute to the supplied class names.
     *     @param classNames set of classes
     *     @return this element, for chaining
     */
    public Element classNames(Set<String> classNames) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests if this element has a class. Case-insensitive.
     * @param className name of class to check for
     * @return true if it does, false if not
     */
    // performance sensitive
    public boolean hasClass(String className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Add a class name to this element's {@code class} attribute.
     *     @param className class name to add
     *     @return this element
     */
    public Element addClass(String className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Remove a class name from this element's {@code class} attribute.
     *     @param className class name to remove
     *     @return this element
     */
    public Element removeClass(String className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Toggle a class name on this element's {@code class} attribute: if present, remove it; otherwise add it.
     *     @param className class name to toggle
     *     @return this element
     */
    public Element toggleClass(String className) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the value of a form element (input, textarea, etc).
     * @return the value of the form element, or empty string if not set.
     */
    public String val() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the value of a form element (input, textarea, etc).
     * @param value value to set
     * @return this element (for chaining)
     */
    public Element val(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the source range (start and end positions) of the end (closing) tag for this Element. Position tracking must be
     *     enabled before parsing the content.
     *     @return the range of the closing tag for this element, or {@code untracked} if its range was not tracked.
     *     @see org.jsoup.parser.Parser#setTrackPosition(boolean)
     *     @see Node#sourceRange()
     *     @see Range#isImplicit()
     *     @since 1.15.2
     */
    public Range endSourceRange() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void outerHtmlHead(final QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void outerHtmlTail(QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /* If XML syntax, normalizes < to _ in tag name. */
    @Nullable
    private String safeTagName(Document.OutputSettings.Syntax syntax) {
        return syntax == xml ? Normalizer.xmlSafeTagName(tagName()) : tagName();
    }

    /**
     * Retrieves the element's inner HTML. E.g. on a {@code <div>} with one empty {@code <p>}, would return
     * {@code <p></p>}. (Whereas {@link #outerHtml()} would return {@code <div><p></p></div>}.)
     *
     * @return String of HTML.
     * @see #outerHtml()
     */
    public String html() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public <T extends Appendable> T html(T accum) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set this element's inner HTML. Clears the existing HTML first.
     * @param html HTML to parse and set into this element
     * @return this element
     * @see #append(String)
     */
    public Element html(String html) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Element clone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Element shallowClone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected Element doClone(@Nullable Node parent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // overrides of Node for call chaining
    @Override
    public Element clearAttributes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Element removeAttr(String attributeKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Element root() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Element traverse(NodeVisitor nodeVisitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Element forEachNode(Consumer<? super Node> action) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Perform the supplied action on this Element and each of its descendant Elements, during a depth-first traversal.
     *     Elements may be inspected, changed, added, replaced, or removed.
     *     @param action the function to perform on the element
     *     @see Node#forEachNode(Consumer)
     */
    @Override
    public void forEach(Consumer<? super Element> action) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Returns an Iterator that iterates this Element and each of its descendant Elements, in document order.
     *     @return an Iterator
     */
    @Override
    public Iterator<Element> iterator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Element filter(NodeFilter nodeFilter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static final class NodeList extends ArrayList<Node> {

        /**
         * Tracks if the children have valid sibling indices. We only need to reindex on siblingIndex() demand.
         */
        boolean validChildren = true;

        public NodeList(int size) {
            super(size);
        }

        /**
         * The modCount is used to invalidate the cached element children.
         */
        int modCount() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void incrementMod() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    void reindexChildren() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void invalidateChildren() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean hasValidChildren() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
