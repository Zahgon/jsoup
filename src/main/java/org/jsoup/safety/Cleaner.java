package org.jsoup.safety;

import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeInternals;
import org.jsoup.nodes.Range;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.ParseErrorList;
import org.jsoup.parser.Parser;
import org.jsoup.select.NodeVisitor;
import java.util.List;
import static org.jsoup.internal.SharedConstants.DummyUri;

/**
 * The {@link Safelist}-based HTML cleaner. Use to ensure that end-user provided HTML contains only the elements and attributes
 * that you are expecting; no junk, and no cross-site scripting attacks!
 * <p>
 * The HTML cleaner parses the input as HTML and then runs it through a safelist, so the output HTML can only contain
 * HTML that is allowed by the safelist.
 * </p>
 * <p>
 * It is assumed that the input HTML is a body fragment; the clean methods only pull from the source's body, and the
 * canned safelists only allow body-contained tags.
 * </p>
 * <p>
 * Rather than interacting directly with a Cleaner object, generally see the {@code clean} methods in {@link org.jsoup.Jsoup}.
 * </p>
 * <p>
 * A Cleaner may be reused across multiple documents and shared across concurrent threads once its {@link Safelist} has
 * been configured. The cleaner uses the supplied safelist directly, so later safelist changes affect later cleaning
 * calls. If you need a variant of an existing configuration, use {@link Safelist#Safelist(Safelist)} to make a copy.
 * </p>
 */
public class Cleaner {

    private final Safelist safelist;

    /**
     *     Create a new cleaner, that sanitizes documents using the supplied safelist.
     *     @param safelist safe-list to clean with
     */
    public Cleaner(Safelist safelist) {
        Validate.notNull(safelist);
        this.safelist = safelist;
    }

    /**
     *     Creates a new, clean document, from the original dirty document, containing only elements allowed by the safelist.
     *     The original document is not modified. Only elements from the dirty document's <code>body</code> are used. The
     *     OutputSettings of the original document are cloned into the clean document.
     *     @param dirtyDocument Untrusted base document to clean.
     *     @return cleaned document.
     */
    public Document clean(Document dirtyDocument) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Determines if the input document's <b>body</b> is valid, against the safelist. It is considered valid if all the
     *     tags and attributes in the input HTML are allowed by the safelist, and that there is no content in the
     *     <code>head</code>.
     *     <p>
     *     This method is intended to be used in a user interface as a validator for user input. Note that regardless of the
     *     output of this method, the input document <b>must always</b> be normalized using a method such as
     *     {@link #clean(Document)}, and the result of that method used to store or serialize the document before later reuse
     *     such as presentation to end users. This ensures that enforced attributes are set correctly, and that any
     *     differences between how a given browser and how jsoup parses the input HTML are normalized.
     *     </p>
     *     <p>Example:
     *     <pre>{@code
     *     Document inputDoc = Jsoup.parse(inputHtml);
     *     Cleaner cleaner = new Cleaner(Safelist.relaxed());
     *     boolean isValid = cleaner.isValid(inputDoc);
     *     Document normalizedDoc = cleaner.clean(inputDoc);
     *     }</pre>
     *     </p>
     *     @param dirtyDocument document to test
     *     @return true if no tags or attributes need to be removed; false if they do
     */
    public boolean isValid(Document dirtyDocument) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Determines if the input document's <b>body HTML</b> is valid, against the safelist. It is considered valid if all
     *     the tags and attributes in the input HTML are allowed by the safelist.
     *     <p>
     *     This method is intended to be used in a user interface as a validator for user input. Note that regardless of the
     *     output of this method, the input document <b>must always</b> be normalized using a method such as
     *     {@link #clean(Document)}, and the result of that method used to store or serialize the document before later reuse
     *     such as presentation to end users. This ensures that enforced attributes are set correctly, and that any
     *     differences between how a given browser and how jsoup parses the input HTML are normalized.
     *     </p>
     *     <p>Example:
     *     <pre>{@code
     *     Document inputDoc = Jsoup.parse(inputHtml);
     *     Cleaner cleaner = new Cleaner(Safelist.relaxed());
     *     boolean isValid = cleaner.isValidBodyHtml(inputHtml);
     *     Document normalizedDoc = cleaner.clean(inputDoc);
     *     }</pre>
     *     </p>
     *     @param bodyHtml HTML fragment to test
     *     @return true if no tags or attributes need to be removed; false if they do
     */
    public boolean isValidBodyHtml(String bodyHtml) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Iterates the input and copies trusted nodes (tags, attributes, text) into the destination.
     */
    private final class CleaningVisitor implements NodeVisitor {

        private int numDiscarded = 0;

        private final Element root;

        // current element to append nodes to
        private Element destination;

        private CleaningVisitor(Element root, Element destination) {
            this.root = root;
            this.destination = destination;
        }

        @Override
        public void head(Node source, int depth) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public void tail(Node source, int depth) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    private int copySafeNodes(Element source, Element dest) {
        CleaningVisitor cleaningVisitor = new CleaningVisitor(source, dest);
        cleaningVisitor.traverse(source);
        return cleaningVisitor.numDiscarded;
    }

    private ElementMeta createSafeElement(Element sourceEl) {
        // reuses tag, clones attributes and preserves any user data
        Element dest = sourceEl.shallowClone();
        String sourceTag = sourceEl.tagName();
        Attributes destAttrs = dest.attributes();
        // clear all non-internal attributes, ready for safe copy
        dest.clearAttributes();
        int numDiscarded = 0;
        Attributes sourceAttrs = sourceEl.attributes();
        for (Attribute sourceAttr : sourceAttrs) {
            if (safelist.isSafeAttribute(sourceTag, sourceEl, sourceAttr)) {
                // will keep this attr
                String key = sourceAttr.getKey();
                String value = sourceAttr.getValue();
                if (safelist.shouldAbsUrl(sourceTag, key)) {
                    // configured to make absolute urls for this key (href)
                    value = sourceEl.absUrl(key);
                    if (// could not be made abs; leave as-is to allow custom unknown protocols
                    value.isEmpty())
                        value = sourceAttr.getValue();
                }
                Range.AttributeRange range = sourceAttrs.sourceRange(key);
                destAttrs.put(key, value);
                NodeInternals.attributeRange(destAttrs, key, range);
            } else
                numDiscarded++;
        }
        Attributes enforcedAttrs = safelist.getEnforcedAttributes(sourceTag);
        // special case for <a href rel=nofollow>, only apply to external links:
        if (sourceEl.nameIs("a") && enforcedAttrs.get("rel").equals("nofollow")) {
            String href = sourceEl.absUrl("href");
            String sourceBase = sourceEl.baseUri();
            if (!href.isEmpty() && !sourceBase.isEmpty() && href.startsWith(sourceBase)) {
                // same site, so don't set the nofollow
                enforcedAttrs.remove("rel");
            }
        }
        // apply enforced attributes case-insensitively, so a preserved-case source attr is canonicalized to the enforced key
        for (Attribute enforcedAttr : enforcedAttrs) {
            destAttrs.removeIgnoreCase(enforcedAttr.getKey());
            destAttrs.put(enforcedAttr.getKey(), enforcedAttr.getValue());
        }
        // re-attach, if removed in clear
        dest.attributes().addAll(destAttrs);
        return new ElementMeta(dest, numDiscarded);
    }

    private static class ElementMeta {

        Element el;

        int numAttribsDiscarded;

        ElementMeta(Element el, int numAttribsDiscarded) {
            this.el = el;
            this.numAttribsDiscarded = numAttribsDiscarded;
        }
    }
}
