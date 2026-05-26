package org.jsoup.nodes;

import org.jsoup.internal.QuietAppendable;

/**
 * A Character Data node, to support CDATA sections.
 */
public class CDataNode extends TextNode {

    public CDataNode(String text) {
        super(text);
    }

    @Override
    public String nodeName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the un-encoded, <b>non-normalized</b> text content of this CDataNode.
     * @return un-encoded, non-normalized text
     */
    @Override
    public String text() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void outerHtmlHead(QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public CDataNode clone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
