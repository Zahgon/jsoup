package org.jsoup.nodes;

import org.jsoup.internal.QuietAppendable;

/**
 * A data node, for contents of style, script tags etc, where contents should not show in text().
 *
 * @author Jonathan Hedley, jonathan@hedley.net
 */
public class DataNode extends LeafNode {

    /**
     *     Create a new DataNode.
     *     @param data data contents
     */
    public DataNode(String data) {
        super(data);
    }

    @Override
    public String nodeName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the data contents of this node. Will be unescaped and with original new lines, space etc.
     *     @return data
     */
    public String getWholeData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the data contents of this node.
     * @param data un-encoded data
     * @return this node, for chaining
     */
    public DataNode setWholeData(String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void outerHtmlHead(QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public DataNode clone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
