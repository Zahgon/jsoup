package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jspecify.annotations.Nullable;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import static org.jsoup.internal.StringUtil.inSorted;
import static org.jsoup.parser.HtmlTreeBuilderState.Constants.Headings;
import static org.jsoup.parser.HtmlTreeBuilderState.Constants.InTableFoster;
import static org.jsoup.parser.HtmlTreeBuilderState.ForeignContent;
import static org.jsoup.parser.Parser.*;

/**
 * HTML Tree Builder; creates a DOM from Tokens.
 */
public class HtmlTreeBuilder extends TreeBuilder {

    static final String[] TagMathMlTextIntegration = new String[] { "mi", "mn", "mo", "ms", "mtext" };

    static final String[] TagSvgHtmlIntegration = new String[] { "desc", "foreignObject", "title" };

    static final String[] TagFormListed = { "button", "fieldset", "input", "keygen", "object", "output", "select", "textarea" };

    /**
     * @deprecated Not used anymore; configure parser depth via {@link Parser#setMaxDepth(int)}. Will be removed in jsoup 1.24.1.
     */
    @Deprecated
    public static final int MaxScopeSearchDepth = 100;

    // the current state
    private HtmlTreeBuilderState state;

    // original / marked state
    private HtmlTreeBuilderState originalState;

    private boolean baseUriSetFromDoc;

    // the current head element
    @Nullable
    private Element headElement;

    // the current form element
    @Nullable
    private FormElement formElement;

    // fragment parse root; name only copy of context. could be null even if fragment parsing
    @Nullable
    private Element contextElement;

    // active (open) formatting elements
    ArrayList<Element> formattingElements;

    // stack of Template Insertion modes
    private ArrayList<HtmlTreeBuilderState> tmplInsertMode;

    // chars in table to be shifted out
    private List<Token.Character> pendingTableCharacters;

    // reused empty end tag
    private Token.EndTag emptyEnd;

    // if ok to go into frameset
    private boolean framesetOk;

    // if next inserts should be fostered
    private boolean fosterInserts;

    // if parsing a fragment of html
    private boolean fragmentParsing;

    @Override
    ParseSettings defaultSettings() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    HtmlTreeBuilder newInstance() {
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

    @Override
    List<Node> completeParseFragment() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected boolean process(Token token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean useCurrentOrForeignInsert(Token token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static boolean isMathmlTextIntegration(Element el) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static boolean isHtmlIntegration(Element el) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean process(Token token, HtmlTreeBuilderState state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void transition(HtmlTreeBuilderState state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    HtmlTreeBuilderState state() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void markInsertionMode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    HtmlTreeBuilderState originalState() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void framesetOk(boolean framesetOk) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean framesetOk() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Document getDocument() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    String getBaseUri() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void maybeSetBaseUri(Element base) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean isFragmentParsing() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void error(HtmlTreeBuilderState state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Element createElementFor(Token.StartTag startTag, String namespace, boolean forcePreserveCase) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts an HTML element for the given tag
     */
    Element insertElementFor(final Token.StartTag startTag) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Inserts a foreign element. Preserves the case of the tag name and of the attributes.
     */
    Element insertForeignElementFor(final Token.StartTag startTag, String namespace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Element insertEmptyElementFor(Token.StartTag startTag) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    FormElement insertFormElement(Token.StartTag startTag, boolean onStack, boolean checkTemplateStack) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the Element onto the stack. All element inserts must run through this method. Performs any general
     *     tests on the Element before insertion.
     * @param el the Element to insert and make the current element
     */
    private void doInsertElement(Element el) {
        enforceStackDepthLimit();
        if (formElement != null && el.tag().namespace.equals(NamespaceHtml) && StringUtil.inSorted(el.normalName(), TagFormListed))
            // connect form controls to their form element
            formElement.addElement(el);
        // in HTML, the xmlns attribute if set must match what the parser set the tag's namespace to
        if (parser.getErrors().canAddError() && el.hasAttr("xmlns") && !el.attr("xmlns").equals(el.tag().namespace()))
            error("Invalid xmlns attribute [%s] on tag [%s]", el.attr("xmlns"), el.tagName());
        if (isFosterInserts() && StringUtil.inSorted(currentElement().normalName(), InTableFoster))
            insertInFosterParent(el);
        else
            currentElement().appendChild(el);
        push(el);
    }

    void insertCommentNode(Token.Comment token) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the provided character token into the current element. Any nulls in the data will be removed.
     */
    void insertCharacterNode(Token.Character characterToken) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Inserts the provided character token into the current element. The tokenizer will have already raised precise character errors.
     *
     *     @param characterToken the character token to insert
     *     @param replace if true, replaces any null chars in the data with the replacement char (U+FFFD). If false, removes
     *     null chars.
     */
    void insertCharacterNode(Token.Character characterToken, boolean replace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Inserts the provided character token into the provided element.
     */
    void insertCharacterToElement(Token.Character characterToken, Element el) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    ArrayList<Element> getStack() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean onStack(Element el) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks if there is an HTML element with the given name on the stack.
     */
    boolean onStack(String elName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // an arbitrary tension point between real HTML and crafted pain
    private static final int maxQueueDepth = 256;

    private static boolean onStack(ArrayList<Element> queue, Element element) {
        final int bottom = queue.size() - 1;
        final int upper = bottom >= maxQueueDepth ? bottom - maxQueueDepth : 0;
        for (int pos = bottom; pos >= upper; pos--) {
            Element next = queue.get(pos);
            if (next == element) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the nearest (lowest) HTML element with the given name from the stack.
     */
    @Nullable
    Element getFromStack(String elName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean removeFromStack(Element el) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void onStackPrunedForDepth(Element element) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Pops the stack until the given HTML element is removed.
     */
    @Nullable
    Element popStackToClose(String elName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Pops the stack until an element with the supplied name is removed, irrespective of namespace.
     */
    @Nullable
    Element popStackToCloseAnyNamespace(String elName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Pops the stack until one of the given HTML elements is removed.
     */
    void popStackToClose(String... elNames) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void clearStackToTableContext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void clearStackToTableBodyContext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void clearStackToTableRowContext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Removes elements from the stack until one of the supplied HTML elements is removed.
     */
    private void clearStackToContext(String... nodeNames) {
        for (int pos = stack.size() - 1; pos >= 0; pos--) {
            Element next = stack.get(pos);
            if (NamespaceHtml.equals(next.tag().namespace()) && (StringUtil.in(next.normalName(), nodeNames) || next.nameIs("html")))
                break;
            else
                pop();
        }
    }

    /**
     *     Gets the Element immediately above the supplied element on the stack. Which due to adoption, may not necessarily be
     *     its parent.
     *
     *     @param el
     *     @return the Element immediately above the supplied element, or null if there is no such element.
     */
    @Nullable
    Element aboveOnStack(Element el) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void insertOnStackAfter(Element after, Element in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void replaceOnStack(Element out, Element in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static void replaceInQueue(ArrayList<Element> queue, Element out, Element in) {
        int i = queue.lastIndexOf(out);
        Validate.isTrue(i != -1);
        queue.set(i, in);
    }

    /**
     * Reset the insertion mode, by searching up the stack for an appropriate insertion mode. The stack search depth
     * is limited to {@link #maxQueueDepth}.
     * @return true if the insertion mode was actually changed.
     */
    boolean resetInsertionMode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Places the body back onto the stack and moves to InBody, for cases in AfterBody / AfterAfterBody when more content comes
     */
    void resetBody() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Test if the target element is in the requested scope.
     */
    private boolean inSpecificScope(String targetName, int boundaryOptions) {
        // https://html.spec.whatwg.org/multipage/parsing.html#has-an-element-in-the-specific-scope
        for (int pos = stack.size() - 1; pos >= 0; pos--) {
            Element el = stack.get(pos);
            Tag tag = el.tag();
            if (NamespaceHtml.equals(tag.namespace()) && el.normalName().equals(targetName))
                return true;
            if (tag.hasParserOption(boundaryOptions))
                return false;
        }
        return false;
    }

    /**
     *     Test if any heading element is in scope.
     */
    boolean hasHeadingInScope() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean inScope(String targetName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean inListItemScope(String targetName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean inButtonScope(String targetName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean inTableScope(String targetName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean inSelectScope(String targetName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Tests if there is some element on the stack that is not in the provided set.
     */
    boolean onStackNot(String[] allowedTags) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void setHeadElement(Element headElement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Element getHeadElement() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean isFosterInserts() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void setFosterInserts(boolean fosterInserts) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    FormElement getFormElement() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void setFormElement(FormElement formElement) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void resetPendingTableCharacters() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    List<Token.Character> getPendingTableCharacters() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void addPendingTableCharacters(Token.Character c) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     13.2.6.3 Closing elements that have implied end tags
     *     When the steps below require the UA to generate implied end tags, then, while the current node is a dd element, a dt element, an li element, an optgroup element, an option element, a p element, an rb element, an rp element, an rt element, or an rtc element, the UA must pop the current node off the stack of open elements.
     *
     *     If a step requires the UA to generate implied end tags but lists an element to exclude from the process, then the UA must perform the above steps as if that element was not in the above list.
     *
     *     When the steps below require the UA to generate all implied end tags thoroughly, then, while the current node is a caption element, a colgroup element, a dd element, a dt element, an li element, an optgroup element, an option element, a p element, an rb element, an rp element, an rt element, an rtc element, a tbody element, a td element, a tfoot element, a th element, a thead element, or a tr element, the UA must pop the current node off the stack of open elements.
     *
     *     @param excludeTag If a step requires the UA to generate implied end tags but lists an element to exclude from the
     *     process, then the UA must perform the above steps as if that element was not in the above list.
     */
    void generateImpliedEndTags(String excludeTag) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void generateImpliedEndTags() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Pops HTML elements off the stack according to the implied end tag rules
     *     @param thorough if we are thorough (includes table elements etc) or not
     */
    void generateImpliedEndTags(boolean thorough) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void closeElement(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static boolean isSpecial(Element el) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Element lastFormattingElement() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int positionOfElement(Element el) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Element removeLastFormattingElement() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // active formatting elements
    void pushActiveFormattingElements(Element in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void pushWithBookmark(Element in, int bookmark) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void checkActiveFormattingElements(Element in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static boolean isSameFormattingElement(Element a, Element b) {
        // same if: same namespace, tag, and attributes. Element.equals only checks tag, might in future check children
        return a.normalName().equals(b.normalName()) && // a.namespace().equals(b.namespace()) &&
        a.attributes().equals(b.attributes());
        // todo: namespaces
    }

    void reconstructFormattingElements() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // limit how many elements get recreated
    private static final int maxUsedFormattingElements = 12;

    void clearFormattingElementsToLastMarker() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void removeFromActiveFormattingElements(Element el) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean isInActiveFormattingElements(Element el) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    Element getActiveFormattingElement(String nodeName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void replaceActiveFormattingElement(Element out, Element in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void insertMarkerToFormattingElements() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void insertInFosterParent(Node in) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Template Insertion Mode stack
    void pushTemplateMode(HtmlTreeBuilderState state) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    HtmlTreeBuilderState popTemplateMode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int templateModeSize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    HtmlTreeBuilderState currentTemplateMode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
