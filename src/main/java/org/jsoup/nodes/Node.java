package org.jsoup.nodes;

import org.jsoup.helper.Validate;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.ParseSettings;
import org.jsoup.select.NodeFilter;
import org.jsoup.select.NodeVisitor;
import org.jspecify.annotations.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * The base, abstract Node model. {@link Element}, {@link Document}, {@link Comment}, {@link TextNode}, et al.,
 * are instances of Node.
 *
 * @author Jonathan Hedley, jonathan@hedley.net
 */
public abstract class Node implements Cloneable {

    static final List<Node> EmptyNodes = Collections.emptyList();

    static final String EmptyString = "";

    // Nodes don't always have parents
    @Nullable
    Element parentNode;

    int siblingIndex;

    /**
     * Default constructor. Doesn't set up base uri, children, or attributes; use with caution.
     */
    protected Node() {
    }

    /**
     *     Get the node name of this node. Use for debugging purposes and not logic switching (for that, use instanceof).
     *     @return node name
     */
    public abstract String nodeName();

    /**
     *     Get the normalized name of this node. For node types other than Element, this is the same as {@link #nodeName()}.
     *     For an Element, will be the lower-cased tag name.
     *     @return normalized node name
     *     @since 1.15.4.
     */
    public String normalName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the node's value. For a TextNode, the whole text; for a Comment, the comment data; for an Element,
     *     wholeOwnText. Returns "" if there is no value.
     *     @return the node's value
     */
    public String nodeValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Test if this node has the specified normalized name, in any namespace.
     * @param normalName a normalized element name (e.g. {@code div}).
     * @return true if the element's normal name matches exactly
     * @since 1.17.2
     */
    public boolean nameIs(String normalName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Test if this node's parent has the specified normalized name.
     * @param normalName a normalized name (e.g. {@code div}).
     * @return true if the parent element's normal name matches exactly
     * @since 1.17.2
     */
    public boolean parentNameIs(String normalName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Test if this node's parent is an Element with the specified normalized name and namespace.
     * @param normalName a normalized element name (e.g. {@code div}).
     * @param namespace the namespace
     * @return true if the parent element's normal name matches exactly, and that element is in the specified namespace
     * @since 1.17.2
     */
    public boolean parentElementIs(String normalName, String namespace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if this Node has an actual Attributes object.
     */
    protected abstract boolean hasAttributes();

    /**
     *     Checks if this node has a parent. Nodes won't have parents if (e.g.) they are newly created and not added as a child
     *     to an existing node, or if they are a {@link #shallowClone()}. In such cases, {@link #parent()} will return {@code null}.
     *     @return if this node has a parent.
     */
    public boolean hasParent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get an attribute's value by its key. <b>Case insensitive</b>
     * <p>
     * To get an absolute URL from an attribute that may be a relative URL, prefix the key with <code><b>abs:</b></code>,
     * which is a shortcut to the {@link #absUrl} method.
     * </p>
     * E.g.:
     * <blockquote><code>String url = a.attr("abs:href");</code></blockquote>
     *
     * @param attributeKey The attribute key.
     * @return The attribute, or empty string if not present (to avoid nulls).
     * @see #attributes()
     * @see #hasAttr(String)
     * @see #absUrl(String)
     */
    public String attr(String attributeKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get each of the Element's attributes.
     * @return attributes (which implements Iterable, with the same order as presented in the original HTML).
     */
    public abstract Attributes attributes();

    /**
     *     Get the number of attributes that this Node has.
     *     @return the number of attributes
     *     @since 1.14.2
     */
    public int attributesSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set an attribute (key=value). If the attribute already exists, it is replaced. The attribute key comparison is
     * <b>case insensitive</b>. The key will be set with case sensitivity as set in the parser settings.
     * @param attributeKey The attribute key.
     * @param attributeValue The attribute value.
     * @return this (for chaining)
     */
    public Node attr(String attributeKey, String attributeValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Test if this Node has an attribute. <b>Case insensitive</b>.
     * @param attributeKey The attribute key to check.
     * @return true if the attribute exists, false if not.
     */
    public boolean hasAttr(String attributeKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Remove an attribute from this node.
     * @param attributeKey The attribute to remove.
     * @return this (for chaining)
     */
    public Node removeAttr(String attributeKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Clear (remove) each of the attributes in this node.
     * @return this, for chaining
     */
    public Node clearAttributes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the base URI that applies to this node. Will return an empty string if not defined. Used to make relative links
     *     absolute.
     *
     *     @return base URI
     *     @see #absUrl
     */
    public abstract String baseUri();

    /**
     * Set the baseUri for just this node (not its descendants), if this Node tracks base URIs.
     * @param baseUri new URI
     */
    protected abstract void doSetBaseUri(String baseUri);

    /**
     *     Update the base URI of this node and all of its descendants.
     *     @param baseUri base URI to set
     */
    public void setBaseUri(final String baseUri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get an absolute URL from a URL attribute that may be relative (such as an <code>&lt;a href&gt;</code> or
     * <code>&lt;img src&gt;</code>).
     * <p>
     * E.g.: <code>String absUrl = linkEl.absUrl("href");</code>
     * </p>
     * <p>
     * If the attribute value is already absolute (i.e. it starts with a protocol, like
     * <code>http://</code> or <code>https://</code> etc), and it successfully parses as a URL, the attribute is
     * returned directly. Otherwise, it is treated as a URL relative to the element's {@link #baseUri}, and made
     * absolute using that.
     * </p>
     * <p>
     * As an alternate, you can use the {@link #attr} method with the <code>abs:</code> prefix, e.g.:
     * <code>String absUrl = linkEl.attr("abs:href");</code>
     * </p>
     *
     * @param attributeKey The attribute key
     * @return An absolute URL if one could be made, or an empty string (not null) if the attribute was missing or
     * could not be made successfully into a URL.
     * @see #attr
     * @see java.net.URL#URL(java.net.URL, String)
     */
    public String absUrl(String attributeKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected abstract List<Node> ensureChildNodes();

    /**
     *     Get a child node by its 0-based index.
     *     @param index index of child node
     *     @return the child node at this index.
     *     @throws IndexOutOfBoundsException if the index is out of bounds.
     */
    public Node childNode(int index) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get this node's children. Presented as an unmodifiable list: new children can not be added, but the child nodes
     *     themselves can be manipulated.
     *     @return list of children. If no children, returns an empty list.
     */
    public List<Node> childNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a deep copy of this node's children. Changes made to these nodes will not be reflected in the original
     * nodes
     * @return a deep copy of this node's children
     */
    public List<Node> childNodesCopy() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the number of child nodes that this node holds.
     * @return the number of child nodes that this node holds.
     */
    public abstract int childNodeSize();

    protected Node[] childNodesAsArray() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Delete all this node's children.
     * @return this node, for chaining
     */
    public abstract Node empty();

    /**
     *     Gets this node's parent node. This is always an Element.
     *     @return parent node; or null if no parent.
     *     @see #hasParent()
     *     @see #parentElement();
     */
    @Nullable
    public Node parent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets this node's parent Element.
     *     @return parent element; or null if this node has no parent.
     *     @see #hasParent()
     *     @since 1.21.1
     */
    @Nullable
    public Element parentElement() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets this node's parent node. Not overridable by extending classes, so useful if you really just need the Node type.
     *     @return parent node; or null if no parent.
     */
    @Nullable
    public final Node parentNode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get this node's root node; that is, its topmost ancestor. If this node is the top ancestor, returns {@code this}.
     * @return topmost ancestor.
     */
    public Node root() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the Document associated with this Node.
     * @return the Document associated with this Node, or null if there is no such Document.
     */
    @Nullable
    public Document ownerDocument() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Remove (delete) this node from the DOM tree. If this node has children, they are also removed. If this node is
     * an orphan, nothing happens.
     */
    public void remove() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Insert the specified HTML into the DOM before this node (as a preceding sibling).
     * @param html HTML to add before this node
     * @return this node, for chaining
     * @see #after(String)
     */
    public Node before(String html) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Insert the specified node into the DOM before this node (as a preceding sibling).
     * @param node to add before this node
     * @return this node, for chaining
     * @see #after(Node)
     */
    public Node before(Node node) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Insert the specified HTML into the DOM after this node (as a following sibling).
     * @param html HTML to add after this node
     * @return this node, for chaining
     * @see #before(String)
     */
    public Node after(String html) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Insert the specified node into the DOM after this node (as a following sibling).
     * @param node to add after this node
     * @return this node, for chaining
     * @see #before(Node)
     */
    public Node after(Node node) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void addSiblingHtml(int index, String html) {
        Validate.notNull(html);
        Validate.notNull(parentNode);
        Element context = parentNode instanceof Element ? (Element) parentNode : null;
        List<Node> nodes = NodeUtils.parser(this).parseFragmentInput(html, context, baseUri());
        parentNode.addChildren(index, nodes.toArray(new Node[0]));
    }

    /**
     *     Wrap the supplied HTML around this node.
     *
     *     @param html HTML to wrap around this node, e.g. {@code <div class="head"></div>}. Can be arbitrarily deep. If
     *     the input HTML does not parse to a result starting with an Element, this will be a no-op.
     *     @return this node, for chaining.
     */
    public Node wrap(String html) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Removes this node from the DOM, and moves its children up into the node's parent. This has the effect of dropping
     * the node but keeping its children.
     * <p>
     * For example, with the input html:
     * </p>
     * <p>{@code <div>One <span>Two <b>Three</b></span></div>}</p>
     * Calling {@code element.unwrap()} on the {@code span} element will result in the html:
     * <p>{@code <div>One Two <b>Three</b></div>}</p>
     * and the {@code "Two "} {@link TextNode} being returned.
     *
     * @return the first child of this node, after the node has been unwrapped. @{code Null} if the node had no children.
     * @see #remove()
     * @see #wrap(String)
     */
    @Nullable
    public Node unwrap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static Element getDeepChild(Element el) {
        Element child = el.firstElementChild();
        while (child != null) {
            el = child;
            child = child.firstElementChild();
        }
        return el;
    }

    /**
     * Replace this node in the DOM with the supplied node.
     * @param in the node that will replace the existing node.
     */
    public void replaceWith(Node in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void setParentNode(Node parentNode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void replaceChild(Node out, Node in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void removeChild(Node out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void addChildren(Node... children) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void addChildren(int index, Node... children) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void reparentChild(Node child) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Retrieves this node's sibling nodes. Similar to {@link #childNodes() node.parent.childNodes()}, but does not
     *     include this node (a node is not a sibling of itself).
     *     @return node siblings. If the node has no parent, returns an empty list.
     */
    public List<Node> siblingNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get this node's next sibling.
     *     @return next sibling, or {@code null} if this is the last sibling
     */
    @Nullable
    public Node nextSibling() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get this node's previous sibling.
     *     @return the previous sibling, or @{code null} if this is the first sibling
     */
    @Nullable
    public Node previousSibling() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the list index of this node in its node sibling list. E.g. if this is the first node
     * sibling, returns 0.
     * @return position in node sibling list
     * @see org.jsoup.nodes.Element#elementSiblingIndex()
     */
    public int siblingIndex() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void setSiblingIndex(int siblingIndex) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets the first child node of this node, or {@code null} if there is none. This could be any Node type, such as an
     *     Element, TextNode, Comment, etc. Use {@link Element#firstElementChild()} to get the first Element child.
     *     @return the first child node, or null if there are no children.
     *     @see Element#firstElementChild()
     *     @see #lastChild()
     *     @since 1.15.2
     */
    @Nullable
    public Node firstChild() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets the last child node of this node, or {@code null} if there is none.
     *     @return the last child node, or null if there are no children.
     *     @see Element#lastElementChild()
     *     @see #firstChild()
     *     @since 1.15.2
     */
    @Nullable
    public Node lastChild() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets the first sibling of this node. That may be this node.
     *
     *     @return the first sibling node
     *     @since 1.21.1
     */
    public Node firstSibling() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets the last sibling of this node. That may be this node.
     *
     *     @return the last sibling (aka the parent's last child)
     *     @since 1.21.1
     */
    public Node lastSibling() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets the next sibling Element of this node. E.g., if a {@code div} contains two {@code p}s, the
     *     {@code nextElementSibling} of the first {@code p} is the second {@code p}.
     *     <p>This is similar to {@link #nextSibling()}, but specifically finds only Elements.</p>
     *
     *     @return the next element, or null if there is no next element
     *     @see #previousElementSibling()
     */
    @Nullable
    public Element nextElementSibling() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets the previous Element sibling of this node.
     *
     *     @return the previous element, or null if there is no previous element
     *     @see #nextElementSibling()
     */
    @Nullable
    public Element previousElementSibling() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform a depth-first traversal through this node and its descendants.
     * @param nodeVisitor the visitor callbacks to perform on each node
     * @return this node, for chaining
     */
    public Node traverse(NodeVisitor nodeVisitor) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Perform the supplied action on this Node and each of its descendants, during a depth-first traversal. Nodes may be
     *     inspected, changed, added, replaced, or removed.
     *     @param action the function to perform on the node
     *     @return this Node, for chaining
     *     @see Element#forEach(Consumer)
     */
    public Node forEachNode(Consumer<? super Node> action) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Perform a depth-first controllable traversal through this node and its descendants.
     * @param nodeFilter the filter callbacks to perform on each node
     * @return this node, for chaining
     */
    public Node filter(NodeFilter nodeFilter) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Returns a Stream of this Node and all of its descendant Nodes. The stream has document order.
     *     @return a stream of all nodes.
     *     @see Element#stream()
     *     @since 1.17.1
     */
    public Stream<Node> nodeStream() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Returns a Stream of this and descendant nodes, containing only nodes of the specified type. The stream has document
     *     order.
     *     @return a stream of nodes filtered by type.
     *     @see Element#stream()
     *     @since 1.17.1
     */
    public <T extends Node> Stream<T> nodeStream(Class<T> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the outer HTML of this node. For example, on a {@code p} element, may return {@code <p>Para</p>}.
     *     @return outer HTML
     *     @see Element#html()
     *     @see Element#text()
     */
    public String outerHtml() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void outerHtml(Appendable accum) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected void outerHtml(QuietAppendable accum) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the outer HTML of this node.
     *
     *     @param accum accumulator to place HTML into
     *     @param out
     */
    abstract void outerHtmlHead(final QuietAppendable accum, final Document.OutputSettings out);

    abstract void outerHtmlTail(final QuietAppendable accum, final Document.OutputSettings out);

    /**
     *     Write this node and its children to the given {@link Appendable}.
     *
     *     @param appendable the {@link Appendable} to write to.
     *     @return the supplied {@link Appendable}, for chaining.
     *     @throws org.jsoup.SerializationException if the appendable throws an IOException.
     */
    public <T extends Appendable> T html(T appendable) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the source range (start and end positions) in the original input source from which this node was parsed.
     *     Position tracking must be enabled prior to parsing the content. For an Element, this will be the positions of the
     *     start tag.
     *     @return the range for the start of the node, or {@code untracked} if its range was not tracked.
     *     @see org.jsoup.parser.Parser#setTrackPosition(boolean)
     *     @see Range#isImplicit()
     *     @see Element#endSourceRange()
     *     @see Attributes#sourceRange(String name)
     *     @since 1.15.2
     */
    public Range sourceRange() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets the range spans, if source tracking was used.
     */
    Range.@Nullable Spans spans() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets or creates range spans for this node.
     */
    Range.Spans ensureSpans() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets this node's outer HTML.
     * @return outer HTML.
     * @see #outerHtml()
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @deprecated internal method moved into Printer; will be removed in jsoup 1.24.1.
     */
    @Deprecated
    protected void indent(Appendable accum, int depth, Document.OutputSettings out) throws IOException {
        accum.append('\n').append(StringUtil.padding(depth * out.indentAmount(), out.maxPaddingWidth()));
    }

    /**
     * Check if this node is the same instance of another (object identity test).
     * <p>For a node value equality check, see {@link #hasSameValue(Object)}</p>
     * @param o other object to compare to
     * @return true if the content of this node is the same as the other
     * @see Node#hasSameValue(Object)
     */
    @Override
    public boolean equals(@Nullable Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Provides a hashCode for this Node, based on its object identity. Changes to the Node's content will not impact the
     *     result.
     *     @return an object identity based hashcode for this Node
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if this node has the same content as another node. A node is considered the same if its name, attributes and content match the
     * other node; particularly its position in the tree does not influence its similarity.
     * @param o other object to compare to
     * @return true if the content of this node is the same as the other
     */
    public boolean hasSameValue(@Nullable Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Create a stand-alone, deep copy of this node, and all of its children. The cloned node will have no siblings.
     *     <p><ul>
     *     <li>If this node is a {@link LeafNode}, the clone will have no parent.</li>
     *     <li>If this node is an {@link Element}, the clone will have a simple owning {@link Document} to retain the
     *     configured output settings and parser.</li>
     *     </ul></p>
     *     <p>The cloned node may be adopted into another Document or node structure using
     *     {@link Element#appendChild(Node)}.</p>
     *
     *     @return a stand-alone cloned node, including clones of any children
     *     @see #shallowClone()
     */
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    // because it does call super.clone in doClone - analysis just isn't following
    @Override
    public Node clone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a stand-alone, shallow copy of this node. None of its children (if any) will be cloned, and it will have
     * no parent or sibling nodes.
     * @return a single independent copy of this node
     * @see #clone()
     */
    public Node shallowClone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /*
     * Return a clone of the node using the given parent (which can be null).
     * Not a deep copy of children.
     */
    protected Node doClone(@Nullable Node parent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
