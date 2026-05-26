package org.jsoup.nodes;

import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.StringUtil;

/**
 * An XML Declaration. Includes support for treating the declaration contents as pseudo attributes.
 */
public class XmlDeclaration extends LeafNode {

    /**
     *     First char is `!` if isDeclaration, like in {@code  <!ENTITY ...>}.
     *     Otherwise, is `?`, a processing instruction, like {@code <?xml .... ?>} (and note trailing `?`).
     */
    private final boolean isDeclaration;

    /**
     * Create a new XML declaration
     * @param name of declaration
     * @param isDeclaration {@code true} if a declaration (first char is `!`), otherwise a processing instruction (first char is `?`).
     */
    public XmlDeclaration(String name, boolean isDeclaration) {
        super(name);
        this.isDeclaration = isDeclaration;
    }

    @Override
    public String nodeName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the name of this declaration.
     * @return name of this declaration.
     */
    public String name() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the unencoded XML declaration.
     * @return XML declaration
     */
    public String getWholeDeclaration() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void getWholeDeclaration(QuietAppendable accum, Document.OutputSettings out) {
        for (Attribute attribute : attributes()) {
            String key = attribute.getKey();
            String val = attribute.getValue();
            if (!key.equals(nodeName())) {
                // skips coreValue (name)
                accum.append(' ');
                // basically like Attribute, but skip empty vals in XML
                accum.append(key);
                if (!val.isEmpty()) {
                    accum.append("=\"");
                    Entities.escape(accum, val, out, Entities.ForAttribute);
                    accum.append('"');
                }
            }
        }
    }

    @Override
    void outerHtmlHead(QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void outerHtmlTail(QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public XmlDeclaration clone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
