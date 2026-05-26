package org.jsoup.nodes;

import org.jsoup.helper.Validate;
import org.jsoup.internal.QuietAppendable;
import org.jspecify.annotations.Nullable;
import java.util.List;

/**
 * A node that does not hold any children. E.g.: {@link TextNode}, {@link DataNode}, {@link Comment}.
 */
public abstract class LeafNode extends Node {

    // either a string, tracked string, or attributes object
    Object value;

    public LeafNode() {
        value = "";
    }

    protected LeafNode(String coreValue) {
        Validate.notNull(coreValue);
        value = coreValue;
    }

    @Override
    protected final boolean hasAttributes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public final Attributes attributes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void ensureAttributes() {
        if (!hasAttributes()) {
            String coreValue = coreValue();
            Attributes attributes = new Attributes();
            Range.Spans rangeSpans = spans();
            value = attributes;
            attributes.put(nodeName(), coreValue);
            if (rangeSpans != null)
                attributes.putSpans(rangeSpans);
        }
    }

    String coreValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @Nullable
    public Element parent() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String nodeValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void coreValue(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String attr(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Node attr(String key, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean hasAttr(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Node removeAttr(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String absUrl(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String baseUri() {
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
    public Node empty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected List<Node> ensureChildNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void outerHtmlTail(QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected LeafNode doClone(Node parent) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    Range.@Nullable Spans spans() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    Range.Spans ensureSpans() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Holds a compact leaf value plus ranges without expanding to Attributes.
     */
    private static final class TrackedValue {

        String coreValue;

        final Range.Spans spans;

        /**
         *         Creates a tracked leaf value around the core text.
         */
        TrackedValue(String coreValue) {
            this(coreValue, new Range.Spans());
        }

        /**
         *         Creates a tracked leaf value around copied range spans.
         */
        TrackedValue(String coreValue, Range.Spans spans) {
            this.coreValue = coreValue;
            this.spans = spans;
        }

        /**
         *         Returns a copy so cloned leaf nodes can mutate range spans independently.
         */
        TrackedValue copy() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
