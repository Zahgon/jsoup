package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.internal.SharedConstants;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.select.Elements;
import org.jspecify.annotations.Nullable;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.jsoup.parser.Parser.NamespaceXml;

/**
 * Use the {@code XmlTreeBuilder} when you want to parse XML without any of the HTML DOM rules being applied to the
 * document.
 * <p>Usage example: {@code Document xmlDoc = Jsoup.parse(html, baseUrl, Parser.xmlParser());}</p>
 *
 * @author Jonathan Hedley
 */
public class XmlTreeBuilder extends TreeBuilder {

    static final String XmlnsKey = "xmlns";

    static final String XmlnsPrefix = "xmlns:";

    // stack of namespaces, prefix => urn
    private final ArrayDeque<HashMap<String, String>> namespacesStack = new ArrayDeque<>();

    @Override
    ParseSettings defaultSettings() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void initialiseParse(Reader input, String baseUri, Parser parser) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void initialiseParseFragment(@Nullable Element context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Document parse(Reader input, String baseUri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Document parse(String input, String baseUri) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    List<Node> completeParseFragment() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    XmlTreeBuilder newInstance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String defaultNamespace() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    TagSet defaultTagSet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    int defaultMaxDepth() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected boolean process(Token token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void insertElementFor(Token.StartTag startTag) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static void processNamespaces(Attributes attributes, HashMap<String, String> namespaces) {
        // process attributes for namespaces (xmlns, xmlns:)
        for (Attribute attr : attributes) {
            String key = attr.getKey();
            String value = attr.getValue();
            if (key.equals(XmlnsKey)) {
                // new default for this level
                namespaces.put("", value);
            } else if (key.startsWith(XmlnsPrefix)) {
                String nsPrefix = key.substring(XmlnsPrefix.length());
                namespaces.put(nsPrefix, value);
            }
        }
    }

    private static void applyNamespacesToAttributes(Attributes attributes, HashMap<String, String> namespaces) {
        // second pass, apply namespace to attributes. Collects them first then adds (as userData is an attribute)
        Map<String, String> attrPrefix = new HashMap<>();
        for (Attribute attr : attributes) {
            String prefix = attr.prefix();
            if (!prefix.isEmpty()) {
                if (prefix.equals(XmlnsKey))
                    continue;
                String ns = namespaces.get(prefix);
                if (ns != null)
                    attrPrefix.put(SharedConstants.XmlnsAttr + prefix, ns);
            }
        }
        for (Map.Entry<String, String> entry : attrPrefix.entrySet()) attributes.userData(entry.getKey(), entry.getValue());
    }

    private static String resolveNamespace(String tagName, HashMap<String, String> namespaces) {
        String ns = namespaces.get("");
        int pos = tagName.indexOf(':');
        if (pos > 0) {
            String prefix = tagName.substring(0, pos);
            if (namespaces.containsKey(prefix))
                ns = namespaces.get(prefix);
        }
        return ns;
    }

    void insertLeafNode(LeafNode node) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void insertCommentFor(Token.Comment commentToken) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void insertCharacterFor(Token.Character token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void insertDoctypeFor(Token.Doctype token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void insertXmlDeclarationFor(Token.XmlDecl token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    Element pop() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If the stack contains an element with this tag's name, pop up the stack to remove the first occurrence. If not
     * found, skips.
     *
     * @param endTag tag to close
     */
    protected void popStackToClose(Token.EndTag endTag) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // an arbitrary tension point between real XML and crafted pain
    private static final int maxQueueDepth = 256;
}
