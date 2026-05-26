package org.jsoup.nodes;

import org.jsoup.internal.QuietAppendable;
import org.jsoup.parser.Parser;
import org.jspecify.annotations.Nullable;
import java.util.List;

/**
 * A comment node.
 *
 * @author Jonathan Hedley, jonathan@hedley.net
 */
public class Comment extends LeafNode {

    /**
     *     Create a new comment node.
     *     @param data The contents of the comment
     */
    public Comment(String data) {
        super(data);
    }

    @Override
    public String nodeName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the contents of the comment.
     *     @return comment content
     */
    public String getData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Comment setData(String data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void outerHtmlHead(QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Comment clone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if this comment looks like an XML Declaration. This is the case when the HTML parser sees an XML
     * declaration or processing instruction. Other than doctypes, those aren't part of HTML, and will be parsed as a
     * bogus comment.
     * @return true if it looks like, maybe, it's an XML Declaration.
     * @see #asXmlDeclaration()
     */
    public boolean isXmlDeclaration() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static boolean isXmlDeclarationData(String data) {
        return (data.length() > 1 && (data.startsWith("!") || data.startsWith("?")));
    }

    /**
     * Attempt to cast this comment to an XML Declaration node.
     * @return an XML declaration if it could be parsed as one, null otherwise.
     * @see #isXmlDeclaration()
     */
    @Nullable
    public XmlDeclaration asXmlDeclaration() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
