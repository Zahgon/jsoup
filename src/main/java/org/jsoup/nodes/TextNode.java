package org.jsoup.nodes;

import org.jsoup.helper.Validate;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.StringUtil;

/**
 * A text node.
 *
 * @author Jonathan Hedley, jonathan@hedley.net
 */
public class TextNode extends LeafNode {

    /**
     *     Create a new TextNode representing the supplied (unencoded) text).
     *
     *     @param text raw text
     *     @see #createFromEncoded(String)
     */
    public TextNode(String text) {
        super(text);
    }

    @Override
    public String nodeName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the text content of this text node.
     * @return Unencoded, normalised text.
     * @see TextNode#getWholeText()
     */
    public String text() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the text content of this text node.
     * @param text unencoded text
     * @return this, for chaining
     */
    public TextNode text(String text) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the (unencoded) text of this text node, including any newlines and spaces present in the original.
     *     @return text
     */
    public String getWholeText() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Test if this text node is blank -- that is, empty or only whitespace (including newlines).
     *     @return true if this document is empty or only whitespace, false if it contains any text content.
     */
    public boolean isBlank() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Split this text node into two nodes at the specified string offset. After splitting, this node will contain the
     * original text up to the offset, and will have a new text node sibling containing the text after the offset.
     * @param offset string offset point to split node at.
     * @return the newly created text node containing the text after the offset.
     */
    public TextNode splitText(int offset) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void outerHtmlHead(QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public TextNode clone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a new TextNode from HTML encoded (aka escaped) data.
     * @param encodedText Text containing encoded HTML (e.g. {@code &lt;})
     * @return TextNode containing unencoded data (e.g. {@code <})
     */
    public static TextNode createFromEncoded(String encodedText) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static String normaliseWhitespace(String text) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static String stripLeadingWhitespace(String text) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static boolean lastCharIsWhitespace(StringBuilder sb) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
