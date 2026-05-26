package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeInternals;
import org.jsoup.nodes.Range;
import org.jspecify.annotations.Nullable;
import java.util.ArrayList;
import static org.jsoup.internal.StringUtil.inSorted;
import static org.jsoup.parser.HtmlTreeBuilder.isSpecial;
import static org.jsoup.parser.HtmlTreeBuilderState.Constants.*;

/**
 * The Tree Builder's current state. Each state embodies the processing for the state, and transitions to other states.
 */
enum HtmlTreeBuilderState {

    Initial {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    BeforeHtml {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.processStartTag("html");
            tb.transition(BeforeHead);
            return tb.process(t);
        }
    }
    ,
    BeforeHead {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    InHead {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean anythingElse(Token t, TreeBuilder tb) {
            tb.processEndTag("head");
            return tb.process(t);
        }
    }
    ,
    InHeadNoscript {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            // note that this deviates from spec, which is to pop out of noscript and reprocess in head:
            // https://html.spec.whatwg.org/multipage/parsing.html#parsing-main-inheadnoscript
            // allows content to be inserted as data
            tb.error(this);
            tb.insertCharacterNode(new Token.Character().data(t.toString()));
            return true;
        }
    }
    ,
    AfterHead {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.processStartTag("body");
            tb.framesetOk(true);
            return tb.process(t);
        }
    }
    ,
    InBody {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean inBodyStartTag(Token t, HtmlTreeBuilder tb) {
            final Token.StartTag startTag = t.asStartTag();
            final String name = startTag.normalName();
            final ArrayList<Element> stack;
            Element el;
            switch(name) {
                case "a":
                    if (tb.getActiveFormattingElement("a") != null) {
                        tb.error(this);
                        tb.processEndTag("a");
                        // still on stack?
                        Element remainingA = tb.getFromStack("a");
                        if (remainingA != null) {
                            tb.removeFromActiveFormattingElements(remainingA);
                            tb.removeFromStack(remainingA);
                        }
                    }
                    tb.reconstructFormattingElements();
                    el = tb.insertElementFor(startTag);
                    tb.pushActiveFormattingElements(el);
                    break;
                case "span":
                    // same as final else, but short circuits lots of checks
                    tb.reconstructFormattingElements();
                    tb.insertElementFor(startTag);
                    break;
                case "li":
                    tb.framesetOk(false);
                    stack = tb.getStack();
                    for (int i = stack.size() - 1; i > 0; i--) {
                        el = stack.get(i);
                        if (el.nameIs("li")) {
                            tb.processEndTag("li");
                            break;
                        }
                        if (isSpecial(el) && !inSorted(el.normalName(), Constants.InBodyStartLiBreakers))
                            break;
                    }
                    if (tb.inButtonScope("p")) {
                        tb.processEndTag("p");
                    }
                    tb.insertElementFor(startTag);
                    break;
                case "html":
                    tb.error(this);
                    // ignore
                    if (tb.onStack("template"))
                        return false;
                    // otherwise, merge attributes onto real html (if present)
                    stack = tb.getStack();
                    if (stack.size() > 0) {
                        Element html = tb.getStack().get(0);
                        mergeAttributes(startTag, html);
                    }
                    break;
                case "body":
                    tb.error(this);
                    stack = tb.getStack();
                    if (stack.size() < 2 || (stack.size() > 2 && !stack.get(1).nameIs("body")) || tb.onStack("template")) {
                        // only in fragment case
                        // ignore
                        return false;
                    } else {
                        tb.framesetOk(false);
                        // will be on stack if this is a nested body. won't be if closed (which is a variance from spec, which leaves it on)
                        Element body = tb.getFromStack("body");
                        if (body != null)
                            mergeAttributes(startTag, body);
                    }
                    break;
                case "frameset":
                    tb.error(this);
                    stack = tb.getStack();
                    if (stack.size() < 2 || (stack.size() > 2 && !stack.get(1).nameIs("body"))) {
                        // only in fragment case
                        // ignore
                        return false;
                    } else if (!tb.framesetOk()) {
                        // ignore frameset
                        return false;
                    } else {
                        Element second = stack.get(1);
                        if (second.parent() != null)
                            second.remove();
                        // pop up to html element
                        while (stack.size() > 1) stack.remove(stack.size() - 1);
                        tb.insertElementFor(startTag);
                        tb.transition(InFrameset);
                    }
                    break;
                case "form":
                    if (tb.getFormElement() != null && !tb.onStack("template")) {
                        tb.error(this);
                        return false;
                    }
                    if (tb.inButtonScope("p")) {
                        tb.closeElement("p");
                    }
                    // won't associate to any template
                    tb.insertFormElement(startTag, true, true);
                    break;
                case "plaintext":
                    if (tb.inButtonScope("p")) {
                        tb.processEndTag("p");
                    }
                    tb.insertElementFor(startTag);
                    // once in, never gets out
                    tb.tokeniser.transition(TokeniserState.PLAINTEXT);
                    break;
                case "button":
                    if (tb.inButtonScope("button")) {
                        // close and reprocess
                        tb.error(this);
                        tb.processEndTag("button");
                        tb.process(startTag);
                    } else {
                        tb.reconstructFormattingElements();
                        tb.insertElementFor(startTag);
                        tb.framesetOk(false);
                    }
                    break;
                case "nobr":
                    tb.reconstructFormattingElements();
                    if (tb.inScope("nobr")) {
                        tb.error(this);
                        tb.processEndTag("nobr");
                        tb.reconstructFormattingElements();
                    }
                    el = tb.insertElementFor(startTag);
                    tb.pushActiveFormattingElements(el);
                    break;
                case "table":
                    if (tb.getDocument().quirksMode() != Document.QuirksMode.quirks && tb.inButtonScope("p")) {
                        tb.processEndTag("p");
                    }
                    tb.insertElementFor(startTag);
                    tb.framesetOk(false);
                    tb.transition(InTable);
                    break;
                case "input":
                    tb.reconstructFormattingElements();
                    el = tb.insertEmptyElementFor(startTag);
                    if (!el.attr("type").equalsIgnoreCase("hidden"))
                        tb.framesetOk(false);
                    break;
                case "hr":
                    if (tb.inButtonScope("p")) {
                        tb.processEndTag("p");
                    }
                    tb.insertEmptyElementFor(startTag);
                    tb.framesetOk(false);
                    break;
                case "image":
                    if (tb.getFromStack("svg") == null)
                        // change <image> to <img>, unless in svg
                        return tb.process(startTag.name("img"));
                    else
                        tb.insertElementFor(startTag);
                    break;
                case "textarea":
                    tb.framesetOk(false);
                    HandleTextState(startTag, tb, tb.tagFor(startTag).textState());
                    break;
                case "xmp":
                    if (tb.inButtonScope("p")) {
                        tb.processEndTag("p");
                    }
                    tb.reconstructFormattingElements();
                    tb.framesetOk(false);
                    HandleTextState(startTag, tb, tb.tagFor(startTag).textState());
                    break;
                case "iframe":
                    tb.framesetOk(false);
                    HandleTextState(startTag, tb, tb.tagFor(startTag).textState());
                    break;
                case "noembed":
                    // also handle noscript if script enabled
                    HandleTextState(startTag, tb, tb.tagFor(startTag).textState());
                    break;
                case "select":
                    tb.reconstructFormattingElements();
                    tb.insertElementFor(startTag);
                    tb.framesetOk(false);
                    // don't change states if not added to the stack
                    if (startTag.selfClosing)
                        break;
                    HtmlTreeBuilderState state = tb.state();
                    if (state.equals(InTable) || state.equals(InCaption) || state.equals(InTableBody) || state.equals(InRow) || state.equals(InCell))
                        tb.transition(InSelectInTable);
                    else
                        tb.transition(InSelect);
                    break;
                case "math":
                    tb.reconstructFormattingElements();
                    tb.insertForeignElementFor(startTag, Parser.NamespaceMathml);
                    break;
                case "svg":
                    tb.reconstructFormattingElements();
                    tb.insertForeignElementFor(startTag, Parser.NamespaceSvg);
                    break;
                // static final String[] Headings = new String[]{"h1", "h2", "h3", "h4", "h5", "h6"};
                case "h1":
                case "h2":
                case "h3":
                case "h4":
                case "h5":
                case "h6":
                    if (tb.inButtonScope("p")) {
                        tb.processEndTag("p");
                    }
                    if (inSorted(tb.currentElement().normalName(), Constants.Headings)) {
                        tb.error(this);
                        tb.pop();
                    }
                    tb.insertElementFor(startTag);
                    break;
                // static final String[] InBodyStartPreListing = new String[]{"listing", "pre"};
                case "pre":
                case "listing":
                    if (tb.inButtonScope("p")) {
                        tb.processEndTag("p");
                    }
                    tb.insertElementFor(startTag);
                    // ignore LF if next token
                    tb.reader.matchConsume("\n");
                    tb.framesetOk(false);
                    break;
                // static final String[] DdDt = new String[]{"dd", "dt"};
                case "dd":
                case "dt":
                    tb.framesetOk(false);
                    stack = tb.getStack();
                    final int bottom = stack.size() - 1;
                    final int upper = bottom >= MaxStackScan ? bottom - MaxStackScan : 0;
                    for (int i = bottom; i >= upper; i--) {
                        el = stack.get(i);
                        if (inSorted(el.normalName(), Constants.DdDt)) {
                            tb.processEndTag(el.normalName());
                            break;
                        }
                        if (isSpecial(el) && !inSorted(el.normalName(), Constants.InBodyStartLiBreakers))
                            break;
                    }
                    if (tb.inButtonScope("p")) {
                        tb.processEndTag("p");
                    }
                    tb.insertElementFor(startTag);
                    break;
                case "optgroup":
                case "option":
                    if (tb.currentElementIs("option"))
                        tb.processEndTag("option");
                    tb.reconstructFormattingElements();
                    tb.insertElementFor(startTag);
                    break;
                case "rb":
                case "rtc":
                    if (tb.inScope("ruby")) {
                        tb.generateImpliedEndTags();
                        if (!tb.currentElementIs("ruby"))
                            tb.error(this);
                    }
                    tb.insertElementFor(startTag);
                    break;
                case "rp":
                case "rt":
                    if (tb.inScope("ruby")) {
                        tb.generateImpliedEndTags("rtc");
                        if (!tb.currentElementIs("rtc") && !tb.currentElementIs("ruby"))
                            tb.error(this);
                    }
                    tb.insertElementFor(startTag);
                    break;
                // InBodyStartEmptyFormatters:
                case "area":
                case "br":
                case "embed":
                case "img":
                case "keygen":
                case "wbr":
                    tb.reconstructFormattingElements();
                    tb.insertEmptyElementFor(startTag);
                    tb.framesetOk(false);
                    break;
                // Formatters:
                case "b":
                case "big":
                case "code":
                case "em":
                case "font":
                case "i":
                case "s":
                case "small":
                case "strike":
                case "strong":
                case "tt":
                case "u":
                    tb.reconstructFormattingElements();
                    el = tb.insertElementFor(startTag);
                    tb.pushActiveFormattingElements(el);
                    break;
                default:
                    Tag tag = tb.tagFor(startTag);
                    TokeniserState textState = tag.textState();
                    if (textState != null) {
                        // custom rcdata or rawtext (if we were in head, will have auto-transitioned here)
                        HandleTextState(startTag, tb, textState);
                    } else if (!tag.isKnownTag()) {
                        // no other special rules for custom tags
                        tb.insertElementFor(startTag);
                    } else if (inSorted(name, Constants.InBodyStartPClosers)) {
                        if (tb.inButtonScope("p"))
                            tb.processEndTag("p");
                        tb.insertElementFor(startTag);
                    } else if (inSorted(name, Constants.InBodyStartToHead)) {
                        return tb.process(t, InHead);
                    } else if (inSorted(name, Constants.InBodyStartApplets)) {
                        tb.reconstructFormattingElements();
                        tb.insertElementFor(startTag);
                        tb.insertMarkerToFormattingElements();
                        tb.framesetOk(false);
                    } else if (inSorted(name, Constants.InBodyStartMedia)) {
                        tb.insertEmptyElementFor(startTag);
                    } else if (inSorted(name, Constants.InBodyStartDrop)) {
                        tb.error(this);
                        return false;
                    } else {
                        tb.reconstructFormattingElements();
                        tb.insertElementFor(startTag);
                    }
            }
            return true;
        }

        // used for DD / DT scan, prevents runaway
        private static final int MaxStackScan = 24;

        private boolean inBodyEndTag(Token t, HtmlTreeBuilder tb) {
            final Token.EndTag endTag = t.asEndTag();
            final String name = endTag.normalName();
            switch(name) {
                case "template":
                    tb.process(t, InHead);
                    break;
                // *sigh*
                case "sarcasm":
                case "span":
                    // same as final fall through, but saves short circuit
                    return anyOtherEndTag(t, tb);
                case "li":
                    if (!tb.inListItemScope(name)) {
                        tb.error(this);
                        return false;
                    } else {
                        tb.generateImpliedEndTags(name);
                        if (!tb.currentElementIs(name))
                            tb.error(this);
                        tb.popStackToClose(name);
                    }
                    break;
                case "body":
                    if (!tb.inScope("body")) {
                        tb.error(this);
                        return false;
                    } else {
                        if (tb.onStackNot(InBodyEndOtherErrors))
                            tb.error(this);
                        // track source position of close; body is left on stack, in case of trailers
                        tb.trackNodePosition(tb.getFromStack("body"), false);
                        tb.transition(AfterBody);
                    }
                    break;
                case "html":
                    if (!tb.onStack("body")) {
                        tb.error(this);
                        // ignore
                        return false;
                    } else {
                        if (tb.onStackNot(InBodyEndOtherErrors))
                            tb.error(this);
                        tb.transition(AfterBody);
                        // re-process
                        return tb.process(t);
                    }
                case "form":
                    if (!tb.onStack("template")) {
                        Element currentForm = tb.getFormElement();
                        tb.setFormElement(null);
                        if (currentForm == null || !tb.inScope(name)) {
                            tb.error(this);
                            return false;
                        }
                        tb.generateImpliedEndTags();
                        if (!tb.currentElementIs(name))
                            tb.error(this);
                        // remove currentForm from stack. will shift anything under up.
                        tb.removeFromStack(currentForm);
                    } else {
                        // template on stack
                        if (!tb.inScope(name)) {
                            tb.error(this);
                            return false;
                        }
                        tb.generateImpliedEndTags();
                        if (!tb.currentElementIs(name))
                            tb.error(this);
                        tb.popStackToClose(name);
                    }
                    break;
                case "p":
                    if (!tb.inButtonScope(name)) {
                        tb.error(this);
                        // if no p to close, creates an empty <p></p>
                        tb.processStartTag(name);
                        return tb.process(endTag);
                    } else {
                        tb.generateImpliedEndTags(name);
                        if (!tb.currentElementIs(name))
                            tb.error(this);
                        tb.popStackToClose(name);
                    }
                    break;
                case "dd":
                case "dt":
                    if (!tb.inScope(name)) {
                        tb.error(this);
                        return false;
                    } else {
                        tb.generateImpliedEndTags(name);
                        if (!tb.currentElementIs(name))
                            tb.error(this);
                        tb.popStackToClose(name);
                    }
                    break;
                case "h1":
                case "h2":
                case "h3":
                case "h4":
                case "h5":
                case "h6":
                    if (!tb.hasHeadingInScope()) {
                        tb.error(this);
                        return false;
                    } else {
                        tb.generateImpliedEndTags(name);
                        if (!tb.currentElementIs(name))
                            tb.error(this);
                        tb.popStackToClose(Constants.Headings);
                    }
                    break;
                case "br":
                    tb.error(this);
                    tb.processStartTag("br");
                    return false;
                default:
                    // todo - move rest to switch if desired
                    if (inSorted(name, Constants.InBodyEndAdoptionFormatters)) {
                        return inBodyEndTagAdoption(t, tb);
                    } else if (inSorted(name, Constants.InBodyEndClosers)) {
                        if (!tb.inScope(name)) {
                            // nothing to close
                            tb.error(this);
                            return false;
                        } else {
                            tb.generateImpliedEndTags();
                            if (!tb.currentElementIs(name))
                                tb.error(this);
                            tb.popStackToClose(name);
                        }
                    } else if (inSorted(name, Constants.InBodyStartApplets)) {
                        if (!tb.inScope("name")) {
                            if (!tb.inScope(name)) {
                                tb.error(this);
                                return false;
                            }
                            tb.generateImpliedEndTags();
                            if (!tb.currentElementIs(name))
                                tb.error(this);
                            tb.popStackToClose(name);
                            tb.clearFormattingElementsToLastMarker();
                        }
                    } else {
                        return anyOtherEndTag(t, tb);
                    }
            }
            return true;
        }

        boolean anyOtherEndTag(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean inBodyEndTagAdoption(Token t, HtmlTreeBuilder tb) {
            // https://html.spec.whatwg.org/multipage/parsing.html#adoption-agency-algorithm
            // JH: Including the spec notes here to simplify tracking / correcting. It's a bit gnarly and there may still be some nuances I haven't caught. But test cases and comparisons to browsers check out.
            // The adoption agency algorithm, which takes as its only argument a token token for which the algorithm is being run, consists of the following steps:
            final Token.EndTag endTag = t.asEndTag();
            // 1. Let subject be token's tag name.
            final String subject = endTag.normalName;
            // 2. If the [current node] is an [HTML element] whose tag name is subject, and the [current node] is not in the [list of active formatting elements], then pop the [current node] off the [stack of open elements] and return.
            if (tb.currentElement().normalName().equals(subject) && !tb.isInActiveFormattingElements(tb.currentElement())) {
                tb.pop();
                return true;
            }
            // 3. Let outerLoopCounter be 0.
            int outer = 0;
            while (true) {
                // 4. While true:
                if (outer >= 8) {
                    // 1. If outerLoopCounter is greater than or equal to 8, then return.
                    return true;
                }
                // 2. Increment outerLoopCounter by 1.
                outer++;
                // 3. Let formattingElement be the last element in the [list of active formatting elements] that:
                //  - is between the end of the list and the last [marker] in the list, if any, or the start of the list otherwise, and
                //  - has the tag name subject.
                //  If there is no such element, then return and instead act as described in the "any other end tag" entry above.
                Element formatEl = null;
                for (int i = tb.formattingElements.size() - 1; i >= 0; i--) {
                    Element next = tb.formattingElements.get(i);
                    if (// marker
                    next == null)
                        break;
                    if (next.normalName().equals(subject)) {
                        formatEl = next;
                        break;
                    }
                }
                if (formatEl == null) {
                    return anyOtherEndTag(t, tb);
                }
                // 4. If formattingElement is not in the [stack of open elements], then this is a [parse error]; remove the element from the list, and return.
                if (!tb.onStack(formatEl)) {
                    tb.error(this);
                    tb.removeFromActiveFormattingElements(formatEl);
                    return true;
                }
                //  5. If formattingElement is in the [stack of open elements], but the element is not [in scope], then this is a [parse error]; return.
                if (!tb.inScope(formatEl.normalName())) {
                    tb.error(this);
                    return false;
                } else if (tb.currentElement() != formatEl) {
                    //  6. If formattingElement is not the [current node], this is a [parse error].
                    tb.error(this);
                }
                //  7. Let furthestBlock be the topmost node in the [stack of open elements] that is lower in the stack than formattingElement, and is an element in the [special]category. There might not be one.
                Element furthestBlock = null;
                ArrayList<Element> stack = tb.getStack();
                int fei = stack.lastIndexOf(formatEl);
                if (fei != -1) {
                    // look down the stack
                    for (int i = fei + 1; i < stack.size(); i++) {
                        Element el = stack.get(i);
                        if (isSpecial(el)) {
                            furthestBlock = el;
                            break;
                        }
                    }
                }
                //  8. If there is no furthestBlock, then the UA must first pop all the nodes from the bottom of the [stack of open elements], from the [current node] up to and including formattingElement, then remove formattingElement from the [list of active formatting elements], and finally return.
                if (furthestBlock == null) {
                    while (tb.currentElement() != formatEl) {
                        tb.pop();
                    }
                    tb.pop();
                    tb.removeFromActiveFormattingElements(formatEl);
                    return true;
                }
                // 9. Let commonAncestor be the element immediately above formattingElement in the [stack of open elements].
                Element commonAncestor = tb.aboveOnStack(formatEl);
                // Would be a WTF
                if (commonAncestor == null) {
                    tb.error(this);
                    return true;
                }
                // 10. Let a bookmark note the position of formattingElement in the [list of active formatting elements] relative to the elements on either side of it in the list.
                // JH - I think this means its index? Or do we need a linked list?
                int bookmark = tb.positionOfElement(formatEl);
                //  11. Let node and lastNode be furthestBlock.
                Element el = furthestBlock;
                Element lastEl = furthestBlock;
                // 12. Let innerLoopCounter be 0.
                int inner = 0;
                while (true) {
                    // 13. While true:
                    // 1. Increment innerLoopCounter by 1.
                    inner++;
                    // 2. Let node be the element immediately above node in the [stack of open elements], or if node is no longer in the [stack of open elements] , the element that was immediately above node in the [stack of open elements] before node was removed.
                    if (!tb.onStack(el)) {
                        // if node was removed from stack, use the element that was above it
                        // JH - is there a situation where it's not the parent?
                        el = el.parent();
                    } else {
                        el = tb.aboveOnStack(el);
                    }
                    if (el == null || el.nameIs("body")) {
                        // shouldn't be able to hit
                        tb.error(this);
                        break;
                    }
                    //  3. If node is formattingElement, then [break].
                    if (el == formatEl) {
                        break;
                    }
                    //  4. If innerLoopCounter is greater than 3 and node is in the [list of active formatting elements], then remove node from the [list of active formatting elements].
                    if (inner > 3 && tb.isInActiveFormattingElements(el)) {
                        tb.removeFromActiveFormattingElements(el);
                        break;
                    }
                    // 5. If node is not in the [list of active formatting elements], then remove node from the [stack of open elements] and [continue].
                    if (!tb.isInActiveFormattingElements(el)) {
                        tb.removeFromStack(el);
                        continue;
                    }
                    //  6. [Create an element for the token] for which the element node was created, in the [HTML namespace], with commonAncestor as the intended parent; replace the entry for node in the [list of active formatting elements] with an entry for the new element, replace the entry for node in the [stack of open elements] with an entry for the new element, and let node be the new element.
                    if (!tb.onStack(el)) {
                        // stale formatting element; cannot adopt/replace
                        tb.error(this);
                        tb.removeFromActiveFormattingElements(el);
                        // exit inner loop; proceed with step 14 using current lastEl
                        break;
                    }
                    Element replacement = new Element(tb.tagFor(el.nodeName(), el.normalName(), tb.defaultNamespace(), ParseSettings.preserveCase), tb.getBaseUri());
                    tb.replaceActiveFormattingElement(el, replacement);
                    tb.replaceOnStack(el, replacement);
                    el = replacement;
                    //  7. If lastNode is furthestBlock, then move the aforementioned bookmark to be immediately after the new node in the [list of active formatting elements].
                    if (lastEl == furthestBlock) {
                        bookmark = tb.positionOfElement(el) + 1;
                    }
                    // 8. [Append] lastNode to node.
                    el.appendChild(lastEl);
                    // 9. Set lastNode to node.
                    lastEl = el;
                }
                // end inner loop # 13
                // 14. Insert whatever lastNode ended up being in the previous step at the [appropriate place for inserting a node], but using commonAncestor as the _override target_.
                // todo - impl https://html.spec.whatwg.org/multipage/parsing.html#appropriate-place-for-inserting-a-node fostering
                // just use commonAncestor as target:
                commonAncestor.appendChild(lastEl);
                // 15. [Create an element for the token] for which formattingElement was created, in the [HTML namespace], with furthestBlock as the intended parent.
                Element adoptor = new Element(formatEl.tag(), tb.getBaseUri());
                // also attributes
                adoptor.attributes().addAll(formatEl.attributes());
                // 16. Take all of the child nodes of furthestBlock and append them to the element created in the last step.
                for (Node child : furthestBlock.childNodes()) {
                    adoptor.appendChild(child);
                }
                // 17. Append that new element to furthestBlock.
                furthestBlock.appendChild(adoptor);
                // 18. Remove formattingElement from the [list of active formatting elements], and insert the new element into the [list of active formatting elements] at the position of the aforementioned bookmark.
                tb.removeFromActiveFormattingElements(formatEl);
                tb.pushWithBookmark(adoptor, bookmark);
                // 19. Remove formattingElement from the [stack of open elements], and insert the new element into the [stack of open elements] immediately below the position of furthestBlock in that stack.
                tb.removeFromStack(formatEl);
                tb.insertOnStackAfter(furthestBlock, adoptor);
            }
            // end of outer loop # 4
        }
    }
    ,
    Text {

        // in script, style etc. normally treated as data tags
        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    InTable {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    InTableText {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    InCaption {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    InColumnGroup {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            if (!tb.currentElementIs("colgroup")) {
                tb.error(this);
                return false;
            }
            tb.pop();
            tb.transition(InTable);
            tb.process(t);
            return true;
        }
    }
    ,
    InTableBody {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean exitTableBody(Token t, HtmlTreeBuilder tb) {
            if (!(tb.inTableScope("tbody") || tb.inTableScope("thead") || tb.inTableScope("tfoot"))) {
                // frag case
                tb.error(this);
                return false;
            }
            tb.clearStackToTableBodyContext();
            // tbody, tfoot, thead
            tb.processEndTag(tb.currentElement().normalName());
            return tb.process(t);
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            return tb.process(t, InTable);
        }
    }
    ,
    InRow {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            return tb.process(t, InTable);
        }
    }
    ,
    InCell {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            return tb.process(t, InBody);
        }

        private void closeCell(HtmlTreeBuilder tb) {
            if (tb.inTableScope("td"))
                tb.processEndTag("td");
            else
                // only here if th or td in scope
                tb.processEndTag("th");
        }
    }
    ,
    InSelect {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.error(this);
            return false;
        }
    }
    ,
    InSelectInTable {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    InTemplate {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AfterBody {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    InFrameset {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AfterFrameset {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AfterAfterBody {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    AfterAfterFrameset {

        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ,
    ForeignContent {

        // https://html.spec.whatwg.org/multipage/parsing.html#parsing-main-inforeign
        @Override
        boolean process(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean processAsHtml(Token t, HtmlTreeBuilder tb) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
    ;

    private static void mergeAttributes(Token.StartTag source, Element dest) {
        if (!source.hasAttributes())
            return;
        source.finaliseAttributeRanges(source.treeBuilder.settings);
        for (Attribute attr : source.attributes) {
            // only iterates public attributes
            Attributes destAttrs = dest.attributes();
            if (!destAttrs.hasKey(attr.getKey())) {
                // grab before its parent changes
                Range.AttributeRange range = attr.sourceRange();
                destAttrs.put(attr);
                NodeInternals.attributeRange(destAttrs, attr.getKey(), range);
            }
        }
    }

    private static final String nullString = String.valueOf('\u0000');

    abstract boolean process(Token t, HtmlTreeBuilder tb);

    private static boolean isWhitespace(Token t) {
        if (t.isCharacter()) {
            String data = t.asCharacter().getData();
            return StringUtil.isBlank(data);
        }
        return false;
    }

    private static void HandleTextState(Token.StartTag startTag, HtmlTreeBuilder tb, @Nullable TokeniserState state) {
        if (state != null)
            tb.tokeniser.transition(state);
        tb.markInsertionMode();
        tb.transition(Text);
        tb.insertElementFor(startTag);
    }

    // lists of tags to search through
    static final class Constants {

        static final String[] InHeadEmpty = new String[] { "base", "basefont", "bgsound", "command", "link" };

        static final String[] InHeadRaw = new String[] { "noframes", "style" };

        static final String[] InHeadEnd = new String[] { "body", "br", "html" };

        static final String[] AfterHeadBody = new String[] { "body", "br", "html" };

        static final String[] BeforeHtmlToHead = new String[] { "body", "br", "head", "html" };

        static final String[] InHeadNoScriptHead = new String[] { "basefont", "bgsound", "link", "meta", "noframes", "style" };

        static final String[] InBodyStartToHead = new String[] { "base", "basefont", "bgsound", "command", "link", "meta", "noframes", "script", "style", "template", "title" };

        static final String[] InBodyStartPClosers = new String[] { "address", "article", "aside", "blockquote", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "menu", "nav", "ol", "p", "section", "summary", "ul" };

        static final String[] Headings = new String[] { "h1", "h2", "h3", "h4", "h5", "h6" };

        static final String[] InBodyStartLiBreakers = new String[] { "address", "div", "p" };

        static final String[] DdDt = new String[] { "dd", "dt" };

        static final String[] InBodyStartApplets = new String[] { "applet", "marquee", "object" };

        static final String[] InBodyStartMedia = new String[] { "param", "source", "track" };

        static final String[] InBodyStartInputAttribs = new String[] { "action", "name", "prompt" };

        static final String[] InBodyStartDrop = new String[] { "caption", "col", "colgroup", "frame", "head", "tbody", "td", "tfoot", "th", "thead", "tr" };

        static final String[] InBodyEndClosers = new String[] { "address", "article", "aside", "blockquote", "button", "center", "details", "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "listing", "menu", "nav", "ol", "pre", "section", "summary", "ul" };

        static final String[] InBodyEndOtherErrors = new String[] { "body", "dd", "dt", "html", "li", "optgroup", "option", "p", "rb", "rp", "rt", "rtc", "tbody", "td", "tfoot", "th", "thead", "tr" };

        static final String[] InBodyEndAdoptionFormatters = new String[] { "a", "b", "big", "code", "em", "font", "i", "nobr", "s", "small", "strike", "strong", "tt", "u" };

        static final String[] InTableToBody = new String[] { "tbody", "tfoot", "thead" };

        static final String[] InTableAddBody = new String[] { "td", "th", "tr" };

        static final String[] InTableToHead = new String[] { "script", "style", "template" };

        static final String[] InCellNames = new String[] { "td", "th" };

        static final String[] InCellBody = new String[] { "body", "caption", "col", "colgroup", "html" };

        static final String[] InCellTable = new String[] { "table", "tbody", "tfoot", "thead", "tr" };

        static final String[] InCellCol = new String[] { "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr" };

        static final String[] InTableEndErr = new String[] { "body", "caption", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr" };

        static final String[] InTableFoster = new String[] { "table", "tbody", "tfoot", "thead", "tr" };

        static final String[] InTableBodyExit = new String[] { "caption", "col", "colgroup", "tbody", "tfoot", "thead" };

        static final String[] InTableBodyEndIgnore = new String[] { "body", "caption", "col", "colgroup", "html", "td", "th", "tr" };

        static final String[] InRowMissing = new String[] { "caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr" };

        static final String[] InRowIgnore = new String[] { "body", "caption", "col", "colgroup", "html", "td", "th" };

        static final String[] InSelectEnd = new String[] { "input", "keygen", "textarea" };

        static final String[] InSelectTableEnd = new String[] { "caption", "table", "tbody", "td", "tfoot", "th", "thead", "tr" };

        static final String[] InTableEndIgnore = new String[] { "tbody", "tfoot", "thead" };

        static final String[] InHeadNoscriptIgnore = new String[] { "head", "noscript" };

        static final String[] InCaptionIgnore = new String[] { "body", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr" };

        static final String[] InTemplateToHead = new String[] { "base", "basefont", "bgsound", "link", "meta", "noframes", "script", "style", "template", "title" };

        static final String[] InTemplateToTable = new String[] { "caption", "colgroup", "tbody", "tfoot", "thead" };

        static final String[] InForeignToHtml = new String[] { "b", "big", "blockquote", "body", "br", "center", "code", "dd", "div", "dl", "dt", "em", "embed", "h1", "h2", "h3", "h4", "h5", "h6", "head", "hr", "i", "img", "li", "listing", "menu", "meta", "nobr", "ol", "p", "pre", "ruby", "s", "small", "span", "strike", "strong", "sub", "sup", "table", "tt", "u", "ul", "var" };
    }
}
