package org.jsoup.select;

import org.jsoup.helper.Validate;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.ParseSettings;
import org.jsoup.helper.Regex;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import static org.jsoup.internal.Normalizer.lowerCase;
import static org.jsoup.internal.Normalizer.normalize;
import static org.jsoup.internal.StringUtil.normaliseWhitespace;

/**
 * An Evaluator tests if an element (or a node) meets the selector's requirements. Obtain an evaluator for a given CSS selector
 * with {@link Selector#evaluatorOf(String css)}. If you are executing the same selector on many elements (or documents), it
 * can be more efficient to compile and reuse an Evaluator than to reparse the selector on each invocation of select().
 * <p>Evaluators are thread-safe and may be used concurrently across multiple documents.</p>
 */
public abstract class Evaluator {

    protected Evaluator() {
    }

    /**
     *     Provides a Predicate for this Evaluator, matching the test Element.
     * @param root the root Element, for match evaluation
     * @return a predicate that accepts an Element to test for matches with this Evaluator
     * @since 1.17.1
     */
    public Predicate<Element> asPredicate(Element root) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Predicate<Node> asNodePredicate(Element root) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Test if the element meets the evaluator's requirements.
     *
     * @param root    Root of the matching subtree
     * @param element tested element
     * @return Returns <tt>true</tt> if the requirements are met or
     * <tt>false</tt> otherwise
     */
    public abstract boolean matches(Element root, Element element);

    final boolean matches(Element root, Node node) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean matches(Element root, LeafNode leafNode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    boolean wantsNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Reset any internal state in this Evaluator before executing a new Collector evaluation.
     */
    protected void reset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     A relative evaluator cost function. During evaluation, Evaluators are sorted by ascending cost as an optimization.
     * @return the relative cost of this Evaluator
     */
    protected int cost() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Evaluator for tag name
     */
    public static final class Tag extends Evaluator {

        private final String tagName;

        public Tag(String tagName) {
            this.tagName = tagName;
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for tag name that starts with prefix; used for ns|*
     */
    public static final class TagStartsWith extends Evaluator {

        private final String tagName;

        public TagStartsWith(String tagName) {
            this.tagName = tagName;
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for tag name that ends with suffix; used for *|el
     */
    public static final class TagEndsWith extends Evaluator {

        private final String tagName;

        public TagEndsWith(String tagName) {
            this.tagName = tagName;
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for element id
     */
    public static final class Id extends Evaluator {

        private final String id;

        public Id(String id) {
            this.id = id;
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for element class
     */
    public static final class Class extends Evaluator {

        private final String className;

        public Class(String className) {
            this.className = className;
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for attribute name matching
     */
    public static final class Attribute extends Evaluator {

        private final String key;

        public Attribute(String key) {
            this.key = key;
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for attribute name prefix matching
     */
    public static final class AttributeStarting extends Evaluator {

        private final String keyPrefix;

        public AttributeStarting(String keyPrefix) {
            // OK to be empty - will find elements with any attributes
            Validate.notNull(keyPrefix);
            this.keyPrefix = lowerCase(keyPrefix);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for attribute name/value matching
     */
    public static final class AttributeWithValue extends AttributeKeyPair {

        public AttributeWithValue(String key, String value) {
            super(key, value);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for attribute name != value matching
     */
    public static final class AttributeWithValueNot extends AttributeKeyPair {

        public AttributeWithValueNot(String key, String value) {
            super(key, value);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for attribute name/value matching (value prefix)
     */
    public static final class AttributeWithValueStarting extends AttributeKeyPair {

        public AttributeWithValueStarting(String key, String value) {
            super(key, value);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for attribute name/value matching (value ending)
     */
    public static final class AttributeWithValueEnding extends AttributeKeyPair {

        public AttributeWithValueEnding(String key, String value) {
            super(key, value);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for attribute name/value matching (value containing)
     */
    public static final class AttributeWithValueContaining extends AttributeKeyPair {

        public AttributeWithValueContaining(String key, String value) {
            super(key, value);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for attribute name/value matching (value regex matching)
     */
    public static final class AttributeWithValueMatching extends Evaluator {

        final String key;

        final Regex pattern;

        public AttributeWithValueMatching(String key, Regex pattern) {
            this.key = normalize(key);
            this.pattern = pattern;
        }

        public AttributeWithValueMatching(String key, Pattern pattern) {
            // api compat
            this(key, Regex.fromPattern(pattern));
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Abstract evaluator for attribute name/value matching
     */
    public abstract static class AttributeKeyPair extends Evaluator {

        final String key;

        final String value;

        public AttributeKeyPair(String key, String value) {
            Validate.notEmpty(key);
            Validate.notNull(value);
            this.key = normalize(key);
            boolean quoted = value.startsWith("'") && value.endsWith("'") || value.startsWith("\"") && value.endsWith("\"");
            if (quoted) {
                Validate.isTrue(value.length() > 1, "Quoted value must have content");
                value = value.substring(1, value.length() - 1);
            }
            // case-insensitive match
            this.value = lowerCase(value);
        }

        /**
         *         @deprecated since 1.22.1, use {@link #AttributeKeyPair(String, String)}; the previous trimQuoted parameter is no longer used.
         *         This constructor will be removed in jsoup 1.24.1.
         */
        @Deprecated
        public AttributeKeyPair(String key, String value, boolean ignored) {
            this(key, value);
        }
    }

    /**
     * Evaluator for any / all element matching
     */
    public static final class AllElements extends Evaluator {

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching by sibling index number (e {@literal <} idx)
     */
    public static final class IndexLessThan extends IndexEvaluator {

        public IndexLessThan(int index) {
            super(index);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching by sibling index number (e {@literal >} idx)
     */
    public static final class IndexGreaterThan extends IndexEvaluator {

        public IndexGreaterThan(int index) {
            super(index);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching by sibling index number (e = idx)
     */
    public static final class IndexEquals extends IndexEvaluator {

        public IndexEquals(int index) {
            super(index);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching the last sibling (css :last-child)
     */
    public static final class IsLastChild extends Evaluator {

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static final class IsFirstOfType extends IsNthOfType {

        public IsFirstOfType() {
            super(0, 1);
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static final class IsLastOfType extends IsNthLastOfType {

        public IsLastOfType() {
            super(0, 1);
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static abstract class CssNthEvaluator extends Evaluator {

        /**
         * Step
         */
        protected final int a;

        /**
         * Offset
         */
        protected final int b;

        public CssNthEvaluator(int step, int offset) {
            this.a = step;
            this.b = offset;
        }

        public CssNthEvaluator(int offset) {
            this(0, offset);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        protected abstract String getPseudoClass();

        protected abstract int calculatePosition(Element root, Element element);
    }

    /**
     * css-compatible Evaluator for :eq (css :nth-child)
     *
     * @see IndexEquals
     */
    public static final class IsNthChild extends CssNthEvaluator {

        public IsNthChild(int step, int offset) {
            super(step, offset);
        }

        @Override
        protected int calculatePosition(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected String getPseudoClass() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * css pseudo class :nth-last-child)
     *
     * @see IndexEquals
     */
    public static final class IsNthLastChild extends CssNthEvaluator {

        public IsNthLastChild(int step, int offset) {
            super(step, offset);
        }

        @Override
        protected int calculatePosition(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected String getPseudoClass() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * css pseudo class nth-of-type
     */
    public static class IsNthOfType extends CssNthEvaluator {

        public IsNthOfType(int step, int offset) {
            super(step, offset);
        }

        @Override
        protected int calculatePosition(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected String getPseudoClass() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static class IsNthLastOfType extends CssNthEvaluator {

        public IsNthLastOfType(int step, int offset) {
            super(step, offset);
        }

        @Override
        protected int calculatePosition(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected String getPseudoClass() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching the first sibling (css :first-child)
     */
    public static final class IsFirstChild extends Evaluator {

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * css3 pseudo-class :root
     * @see <a href="http://www.w3.org/TR/selectors/#root-pseudo">:root selector</a>
     */
    public static final class IsRoot extends Evaluator {

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static final class IsOnlyChild extends Evaluator {

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static final class IsOnlyOfType extends Evaluator {

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static final class IsEmpty extends Evaluator {

        @Override
        public boolean matches(Element root, Element el) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Abstract evaluator for sibling index matching
     *
     * @author ant
     */
    public abstract static class IndexEvaluator extends Evaluator {

        final int index;

        public IndexEvaluator(int index) {
            this.index = index;
        }
    }

    /**
     * Evaluator for matching Element (and its descendants) text
     */
    public static final class ContainsText extends Evaluator {

        private final String searchText;

        public ContainsText(String searchText) {
            this.searchText = lowerCase(normaliseWhitespace(searchText));
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching Element (and its descendants) wholeText. Neither the input nor the element text is
     * normalized. <code>:containsWholeText()</code>
     * @since 1.15.1.
     */
    public static final class ContainsWholeText extends Evaluator {

        private final String searchText;

        public ContainsWholeText(String searchText) {
            this.searchText = searchText;
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching Element (but <b>not</b> its descendants) wholeText. Neither the input nor the element text is
     * normalized. <code>:containsWholeOwnText()</code>
     * @since 1.15.1.
     */
    public static final class ContainsWholeOwnText extends Evaluator {

        private final String searchText;

        public ContainsWholeOwnText(String searchText) {
            this.searchText = searchText;
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching Element (and its descendants) data
     */
    public static final class ContainsData extends Evaluator {

        private final String searchText;

        public ContainsData(String searchText) {
            this.searchText = lowerCase(searchText);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching Element's own text
     */
    public static final class ContainsOwnText extends Evaluator {

        private final String searchText;

        public ContainsOwnText(String searchText) {
            this.searchText = lowerCase(normaliseWhitespace(searchText));
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching Element (and its descendants) text with regex
     */
    public static final class Matches extends Evaluator {

        private final Regex pattern;

        public Matches(Regex pattern) {
            this.pattern = pattern;
        }

        public Matches(Pattern pattern) {
            this(Regex.fromPattern(pattern));
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching Element's own text with regex
     */
    public static final class MatchesOwn extends Evaluator {

        private final Regex pattern;

        public MatchesOwn(Regex pattern) {
            this.pattern = pattern;
        }

        public MatchesOwn(Pattern pattern) {
            this(Regex.fromPattern(pattern));
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching Element (and its descendants) whole text with regex.
     * @since 1.15.1.
     */
    public static final class MatchesWholeText extends Evaluator {

        private final Regex pattern;

        public MatchesWholeText(Regex pattern) {
            this.pattern = pattern;
        }

        public MatchesWholeText(Pattern pattern) {
            this.pattern = Regex.fromPattern(pattern);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Evaluator for matching Element's own whole text with regex.
     * @since 1.15.1.
     */
    public static final class MatchesWholeOwnText extends Evaluator {

        private final Regex pattern;

        public MatchesWholeOwnText(Regex pattern) {
            this.pattern = pattern;
        }

        public MatchesWholeOwnText(Pattern pattern) {
            this(Regex.fromPattern(pattern));
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     *     @deprecated This selector is deprecated and will be removed in jsoup 1.24.1. Migrate to <code>::textnode</code> using the <code>Element#selectNodes()</code> method instead.
     */
    @Deprecated
    // Uses PseudoTextElement for deprecated :matchText support until removal.
    @SuppressWarnings("deprecation")
    public static final class MatchText extends Evaluator {

        private static boolean loggedError = false;

        public MatchText() {
            // log a deprecated error on first use; users typically won't directly construct this Evaluator and so won't otherwise get deprecation warnings
            if (!loggedError) {
                loggedError = true;
                System.err.println("WARNING: :matchText selector is deprecated and will be removed in jsoup 1.24.1. Use Element#selectNodes(String, Class) with selector ::textnode and class TextNode instead.");
            }
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
