package org.jsoup.select;

import org.jsoup.helper.Validate;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.NodeFilter.FilterResult;

/**
 * A depth-first node traversor. Use to walk through all nodes under and including the specified root node, in document
 * order. The {@link NodeVisitor#head(Node, int)} and {@link NodeVisitor#tail(Node, int)} methods will be called for
 * each node.
 * <p>During the <code>head()</code> visit, DOM structural changes around the node currently being visited are
 * supported, including {@link Node#replaceWith(Node)} and {@link Node#remove()}. See
 * {@link NodeVisitor#head(Node, int) head()} for the traversal contract after mutation. Other non-structural node
 * changes are also supported.</p>
 * <p>DOM structural changes to the current node are not supported during the <code>tail()</code> visit.</p>
 */
public class NodeTraversor {

    // cursor state
    private static final byte VisitHead = 0;

    private static final byte AfterHead = 1;

    private static final byte VisitTail = 2;

    /**
     *     Run a depth-first traverse of the root and all of its descendants.
     *     @param visitor Node visitor.
     *     @param root the initial node point to traverse.
     *     @see NodeVisitor#traverse(Node root)
     */
    public static void traverse(NodeVisitor visitor, Node root) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Run a depth-first traversal of each Element.
     *     @param visitor Node visitor.
     *     @param elements Elements to traverse.
     */
    public static void traverse(NodeVisitor visitor, Elements elements) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Run a depth-first controllable traversal of the root and all of its descendants.
     *     @param filter NodeFilter visitor.
     *     @param root the root node point to traverse.
     *     @return The filter result of the root node, or {@link FilterResult#STOP}.
     *
     *     @see NodeFilter
     */
    public static FilterResult filter(NodeFilter filter, Node root) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Run a depth-first controllable traversal of each Element.
     *     @param filter NodeFilter visitor.
     *     @see NodeFilter
     */
    public static void filter(NodeFilter filter, Elements elements) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
