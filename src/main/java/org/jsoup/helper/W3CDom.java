package org.jsoup.helper;

import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.Parser;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.Selector;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.jspecify.annotations.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import static javax.xml.transform.OutputKeys.METHOD;
import static org.jsoup.nodes.Document.OutputSettings.Syntax;

/**
 * Helper class to transform a {@link org.jsoup.nodes.Document} to a {@link org.w3c.dom.Document org.w3c.dom.Document},
 * for integration with toolsets that use the W3C DOM.
 */
public class W3CDom {

    /**
     * For W3C Documents created by this class, this property is set on each node to link back to the original jsoup node.
     */
    public static final String SourceProperty = "jsoupSource";

    // tracks the jsoup context element on w3c doc
    private static final String ContextProperty = "jsoupContextSource";

    // the w3c node used as the creating context
    private static final String ContextNodeProperty = "jsoupContextNode";

    /**
     *     To get support for XPath versions &gt; 1, set this property to the classname of an alternate XPathFactory
     *     implementation. (For e.g. {@code net.sf.saxon.xpath.XPathFactoryImpl}).
     */
    public static final String XPathFactoryProperty = "javax.xml.xpath.XPathFactory:jsoup";

    protected DocumentBuilderFactory factory;

    // false when using selectXpath, for user's query convenience
    private boolean namespaceAware = true;

    public W3CDom() {
        factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
    }

    /**
     *     Returns if this W3C DOM is namespace aware. By default, this will be {@code true}, but is disabled for simplicity
     *     when using XPath selectors in {@link org.jsoup.nodes.Element#selectXpath(String)}.
     *     @return the current namespace aware setting.
     */
    public boolean namespaceAware() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Update the namespace aware setting. This impacts the factory that is used to create W3C nodes from jsoup nodes.
     *     <p>For HTML documents, controls if the document will be in the default {@code http://www.w3.org/1999/xhtml}
     *     namespace if otherwise unset.</p>.
     *     @param namespaceAware the updated setting
     *     @return this W3CDom, for chaining.
     */
    public W3CDom namespaceAware(boolean namespaceAware) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converts a jsoup DOM to a W3C DOM.
     *
     * @param in jsoup Document
     * @return W3C Document
     */
    public static Document convert(org.jsoup.nodes.Document in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Serialize a W3C document to a String. Provide Properties to define output settings including if HTML or XML. If
     * you don't provide the properties ({@code null}), the output will be auto-detected based on the content of the
     * document.
     *
     * @param doc Document
     * @param properties (optional/nullable) the output properties to use. See {@link
     *     Transformer#setOutputProperties(Properties)} and {@link OutputKeys}
     * @return Document as string
     * @see #OutputHtml
     * @see #OutputXml
     * @see OutputKeys#ENCODING
     * @see OutputKeys#OMIT_XML_DECLARATION
     * @see OutputKeys#STANDALONE
     * @see OutputKeys#DOCTYPE_PUBLIC
     * @see OutputKeys#CDATA_SECTION_ELEMENTS
     * @see OutputKeys#INDENT
     * @see OutputKeys#MEDIA_TYPE
     */
    public static String asString(Document doc, @Nullable Map<String, String> properties) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static Properties propertiesFromMap(Map<String, String> map) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Canned default for HTML output.
     */
    public static HashMap<String, String> OutputHtml() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Canned default for XML output.
     */
    public static HashMap<String, String> OutputXml() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static HashMap<String, String> methodMap(String method) {
        HashMap<String, String> map = new HashMap<>();
        map.put(METHOD, method);
        return map;
    }

    /**
     * Convert a jsoup Document to a W3C Document. The created nodes will link back to the original
     * jsoup nodes in the user property {@link #SourceProperty} (but after conversion, changes on one side will not
     * flow to the other).
     *
     * @param in jsoup doc
     * @return a W3C DOM Document representing the jsoup Document or Element contents.
     */
    public Document fromJsoup(org.jsoup.nodes.Document in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Convert a jsoup DOM to a W3C Document. The created nodes will link back to the original
     * jsoup nodes in the user property {@link #SourceProperty} (but after conversion, changes on one side will not
     * flow to the other). The input Element is used as a context node, but the whole surrounding jsoup Document is
     * converted. (If you just want a subtree converted, use {@link #convert(org.jsoup.nodes.Element, Document)}.)
     *
     * @param in jsoup element or doc
     * @return a W3C DOM Document representing the jsoup Document or Element contents.
     * @see #sourceNodes(NodeList, Class)
     * @see #contextNode(Document)
     */
    public Document fromJsoup(org.jsoup.nodes.Element in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converts a jsoup document into the provided W3C Document. If required, you can set options on the output
     * document before converting.
     *
     * @param in jsoup doc
     * @param out w3c doc
     * @see org.jsoup.helper.W3CDom#fromJsoup(org.jsoup.nodes.Element)
     */
    public void convert(org.jsoup.nodes.Document in, Document out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Converts a jsoup element into the provided W3C Document. If required, you can set options on the output
     * document before converting.
     *
     * @param in jsoup element
     * @param out w3c doc
     * @see org.jsoup.helper.W3CDom#fromJsoup(org.jsoup.nodes.Element)
     */
    public void convert(org.jsoup.nodes.Element in, Document out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Evaluate an XPath query against the supplied document, and return the results.
     *     @param xpath an XPath query
     *     @param doc the document to evaluate against
     *     @return the matches nodes
     */
    public NodeList selectXpath(String xpath, Document doc) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Evaluate an XPath query against the supplied context node, and return the results.
     *     @param xpath an XPath query
     *     @param contextNode the context node to evaluate against
     *     @return the matches nodes
     */
    public NodeList selectXpath(String xpath, Node contextNode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Retrieves the original jsoup DOM nodes from a nodelist created by this convertor.
     *     @param nodeList the W3C nodes to get the original jsoup nodes from
     *     @param nodeType the jsoup node type to retrieve (e.g. Element, DataNode, etc)
     *     @param <T> node type
     *     @return a list of the original nodes
     */
    public <T extends org.jsoup.nodes.Node> List<T> sourceNodes(NodeList nodeList, Class<T> nodeType) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     For a Document created by {@link #fromJsoup(org.jsoup.nodes.Element)}, retrieves the W3C context node.
     *     @param wDoc Document created by this class
     *     @return the corresponding W3C Node to the jsoup Element that was used as the creating context.
     */
    public Node contextNode(Document wDoc) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Serialize a W3C document that was created by {@link #fromJsoup(org.jsoup.nodes.Element)} to a String.
     * The output format will be XML or HTML depending on the content of the doc.
     *
     * @param doc Document
     * @return Document as string
     * @see W3CDom#asString(Document, Map)
     */
    public String asString(Document doc) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Implements the conversion by walking the input.
     */
    protected static class W3CBuilder implements NodeVisitor {

        private final Document doc;

        private boolean namespaceAware = true;

        private Node dest;

        // the syntax (to coerce attributes to). From the input doc if available.
        private Syntax syntax = Syntax.xml;

        /*@Nullable*/
        // todo - unsure why this can't be marked nullable?
        private final org.jsoup.nodes.Element contextElement;

        public W3CBuilder(Document doc) {
            this.doc = doc;
            dest = doc;
            // Track the context jsoup Element, so we can save the corresponding w3c element
            contextElement = (org.jsoup.nodes.Element) doc.getUserData(ContextProperty);
        }

        @Override
        public void head(org.jsoup.nodes.Node source, int depth) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Nullable
        private static String w3cNamespace(org.jsoup.nodes.Element sourceEl) {
            // In W3C DOM, plain XML elements have no namespace; XML namespace is reserved for the {@code xml} prefix
            String namespace = sourceEl.tag().namespace();
            if (Parser.NamespaceXml.equals(namespace) && sourceEl.tag().prefix().isEmpty())
                return null;
            return namespace;
        }

        private void append(Node append, org.jsoup.nodes.Node source) {
            append.setUserData(SourceProperty, source, null);
            dest.appendChild(append);
        }

        @Override
        public void tail(org.jsoup.nodes.Node source, int depth) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private void copyAttributes(org.jsoup.nodes.Element jEl, Element wEl) {
            for (Attribute attribute : jEl.attributes()) {
                try {
                    setAttribute(jEl, wEl, attribute, syntax);
                } catch (DOMException e) {
                    if (syntax != Syntax.xml)
                        setAttribute(jEl, wEl, attribute, Syntax.xml);
                }
            }
        }

        private void setAttribute(org.jsoup.nodes.Element jEl, Element wEl, Attribute attribute, Syntax syntax) throws DOMException {
            String key = Attribute.getValidKey(attribute.getKey(), syntax);
            if (key != null) {
                String namespace = attribute.namespace();
                if (namespaceAware && !namespace.isEmpty())
                    wEl.setAttributeNS(namespace, key, attribute.getValue());
                else
                    wEl.setAttribute(key, attribute.getValue());
                maybeAddUndeclaredNs(namespace, key, jEl, wEl);
            }
        }

        /**
         *         Add a namespace declaration for an attribute with a prefix if it is not already present. Ensures that attributes
         *         with prefixes have the corresponding namespace declared, E.g. attribute "v-bind:foo" gets another attribute
         *         "xmlns:v-bind='undefined'. So that the asString() transformation pass is valid.
         *         If the parser was HTML we don't have a discovered namespace but we are trying to coerce it, so walk up the
         *         element stack and find it.
         */
        private void maybeAddUndeclaredNs(String namespace, String attrKey, org.jsoup.nodes.Element jEl, Element wEl) {
            if (!namespaceAware || !namespace.isEmpty())
                return;
            int pos = attrKey.indexOf(':');
            if (pos != -1) {
                // prefixed but no namespace defined during parse, add a fake so that w3c serialization doesn't blow up
                String prefix = attrKey.substring(0, pos);
                if (prefix.equals("xmlns"))
                    return;
                org.jsoup.nodes.Document doc = jEl.ownerDocument();
                if (doc != null && doc.parser().getTreeBuilder() instanceof HtmlTreeBuilder) {
                    // try walking up the stack and seeing if there is a namespace declared for this prefix (and that we didn't parse because HTML)
                    for (org.jsoup.nodes.Element el = jEl; el != null; el = el.parent()) {
                        String ns = el.attr("xmlns:" + prefix);
                        if (!ns.isEmpty()) {
                            namespace = ns;
                            // found it, set it
                            wEl.setAttributeNS(namespace, attrKey, jEl.attr(attrKey));
                            return;
                        }
                    }
                }
                // otherwise, put in a fake one
                wEl.setAttribute("xmlns:" + prefix, undefinedNs);
            }
        }

        private static final String undefinedNs = "undefined";
    }
}
