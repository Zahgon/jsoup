package org.jsoup.nodes;

import org.jsoup.helper.Validate;
import org.jspecify.annotations.Nullable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterate through a Node and its tree of descendants, in document order, and returns nodes of the specified type. This
 * iterator supports structural changes to the tree during the traversal, such as {@link Node#remove()},
 * {@link Node#replaceWith(Node)}, {@link Node#wrap(String)}, etc.
 * <p>See also the {@link org.jsoup.select.NodeTraversor NodeTraversor} if {@code head} and {@code tail} callbacks are
 * desired for each node.</p>
 * @since 1.17.1
 */
public class NodeIterator<T extends Node> implements Iterator<T> {

    // root / starting node
    private Node root;

    // the next node to return
    @Nullable
    private T next;

    // the current (last emitted) node
    private Node current;

    // the previously emitted node; used to recover from structural changes
    private Node previous;

    // the current node's parent; used to detect structural changes
    @Nullable
    private Node currentParent;

    // the desired node class type
    private final Class<T> type;

    /**
     *     Create a NoteIterator that will iterate the supplied node, and all of its descendants. The returned {@link #next}
     *     type will be filtered to the input type.
     * @param start initial node
     * @param type node type to filter for
     */
    public NodeIterator(Node start, Class<T> type) {
        Validate.notNull(start);
        Validate.notNull(type);
        this.type = type;
        restart(start);
    }

    /**
     *     Create a NoteIterator that will iterate the supplied node, and all of its descendants. All node types will be
     *     returned.
     * @param start initial node
     */
    public static NodeIterator<Node> from(Node start) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Restart this Iterator from the specified start node. Will act as if it were newly constructed. Useful for e.g. to
     *     save some GC if the iterator is used in a tight loop.
     * @param start the new start node.
     */
    public void restart(Node start) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean hasNext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public T next() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     If next is not null, looks for and sets next. If next is null after this, we have reached the end.
     */
    private void maybeFindNext() {
        if (next != null)
            return;
        //  change detected (removed or replaced), redo from previous
        if (currentParent != null && !current.hasParent())
            current = previous;
        next = findNextNode();
    }

    @Nullable
    private T findNextNode() {
        Node node = current;
        while (true) {
            if (node.childNodeSize() > 0)
                // descend children
                node = node.childNode(0);
            else if (root.equals(node))
                // complete when all children of root are fully visited
                node = null;
            else if (node.nextSibling() != null)
                // in a descendant with no more children; traverse
                node = node.nextSibling();
            else {
                while (true) {
                    // pop out of descendants
                    node = node.parent();
                    if (node == null || root.equals(node))
                        // got back to root; complete
                        return null;
                    if (node.nextSibling() != null) {
                        // traverse
                        node = node.nextSibling();
                        break;
                    }
                }
            }
            if (node == null)
                // reached the end
                return null;
            if (type.isInstance(node))
                return type.cast(node);
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
