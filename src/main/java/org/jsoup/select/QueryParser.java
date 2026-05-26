package org.jsoup.select;

import org.jsoup.helper.Regex;
import org.jsoup.internal.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.TokenQueue;
import org.jspecify.annotations.Nullable;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.jsoup.select.StructuralEvaluator.ImmediateParentRun;
import static org.jsoup.internal.Normalizer.normalize;

/**
 * Parses a CSS selector into an Evaluator tree.
 */
public class QueryParser implements AutoCloseable {

    // ' ' is also a combinator, but found implicitly
    private final static char[] Combinators = { '>', '+', '~' };

    private final static String[] AttributeEvals = new String[] { "=", "!=", "^=", "$=", "*=", "~=" };

    private final static char[] SequenceEnders = { ',', ')' };

    private final TokenQueue tq;

    private final String query;

    // ::comment:contains should act on node value, vs element text
    private boolean inNodeContext;

    /**
     * Create a new QueryParser.
     * @param query CSS query
     */
    private QueryParser(String query) {
        Validate.notEmpty(query);
        query = query.trim();
        this.query = query;
        this.tq = new TokenQueue(query);
    }

    /**
     *     Parse a CSS query into an Evaluator. If you are evaluating the same query repeatedly, it may be more efficient to
     *     parse it once and reuse the Evaluator.
     *
     *     @param query CSS query
     *     @return Evaluator
     *     @see Selector selector query syntax
     *     @throws Selector.SelectorParseException if the CSS query is invalid
     */
    public static Evaluator parse(String query) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Parse the query. We use this simplified expression of the grammar:
     *     <pre>
     *     SelectorGroup   ::= Selector (',' Selector)*
     *     Selector        ::= [ Combinator ] SimpleSequence ( Combinator SimpleSequence )*
     *     SimpleSequence  ::= [ TypeSelector ] ( ID | Class | Attribute | Pseudo )*
     *     Pseudo           ::= ':' Name [ '(' SelectorGroup ')' ]
     *     Combinator      ::= S+         // descendant (whitespace)
     *     | '>'       // child
     *     | '+'       // adjacent sibling
     *     | '~'       // general sibling
     *     </pre>
     *
     *     See <a href="https://www.w3.org/TR/selectors-4/#grammar">selectors-4</a> for the real thing
     */
    Evaluator parse() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Evaluator parseSelectorGroup() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Evaluator parseSelector() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    Evaluator parseSimpleSequence() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static Evaluator combinator(Evaluator left, char combinator, Evaluator right) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Nullable
    Evaluator parseSubclass() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Merge two evals into an Or.
     */
    static Evaluator or(Evaluator left, Evaluator right) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Merge two evals into an And.
     */
    static Evaluator and(@Nullable Evaluator left, Evaluator right) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Evaluator parsePseudoSelector() {
        final String pseudo = tq.consumeCssIdentifier();
        switch(pseudo) {
            case "lt":
                return new Evaluator.IndexLessThan(consumeIndex());
            case "gt":
                return new Evaluator.IndexGreaterThan(consumeIndex());
            case "eq":
                return new Evaluator.IndexEquals(consumeIndex());
            case "has":
                return has();
            case "is":
                return is();
            case "contains":
                return contains(false);
            case "containsOwn":
                return contains(true);
            case "containsWholeText":
                return containsWholeText(false);
            case "containsWholeOwnText":
                return containsWholeText(true);
            case "containsData":
                return containsData();
            case "matches":
                return matches(false);
            case "matchesOwn":
                return matches(true);
            case "matchesWholeText":
                return matchesWholeText(false);
            case "matchesWholeOwnText":
                return matchesWholeText(true);
            case "not":
                return not();
            case "nth-child":
                return cssNthChild(false, false);
            case "nth-last-child":
                return cssNthChild(true, false);
            case "nth-of-type":
                return cssNthChild(false, true);
            case "nth-last-of-type":
                return cssNthChild(true, true);
            case "first-child":
                return new Evaluator.IsFirstChild();
            case "last-child":
                return new Evaluator.IsLastChild();
            case "first-of-type":
                return new Evaluator.IsFirstOfType();
            case "last-of-type":
                return new Evaluator.IsLastOfType();
            case "only-child":
                return new Evaluator.IsOnlyChild();
            case "only-of-type":
                return new Evaluator.IsOnlyOfType();
            case "empty":
                return new Evaluator.IsEmpty();
            case "blank":
                return new NodeEvaluator.BlankValue();
            case "root":
                return new Evaluator.IsRoot();
            case "matchText":
                {
                    // :matchText remains supported until its scheduled removal.
                    @SuppressWarnings("deprecation")
                    Evaluator.MatchText matchText = new Evaluator.MatchText();
                    return matchText;
                }
            default:
                throw new Selector.SelectorParseException("Could not parse query '%s': unexpected token at '%s'", query, tq.remainder());
        }
    }

    // ::comment etc
    private Evaluator parseNodeSelector() {
        final String pseudo = tq.consumeCssIdentifier();
        // Enter node context
        inNodeContext = true;
        Evaluator left;
        switch(pseudo) {
            case "node":
                left = new NodeEvaluator.InstanceType(Node.class, pseudo);
                break;
            case "leafnode":
                left = new NodeEvaluator.InstanceType(LeafNode.class, pseudo);
                break;
            case "text":
                left = new NodeEvaluator.InstanceType(TextNode.class, pseudo);
                break;
            case "comment":
                left = new NodeEvaluator.InstanceType(Comment.class, pseudo);
                break;
            case "data":
                left = new NodeEvaluator.InstanceType(DataNode.class, pseudo);
                break;
            case "cdata":
                left = new NodeEvaluator.InstanceType(CDataNode.class, pseudo);
                break;
            default:
                throw new Selector.SelectorParseException("Could not parse query '%s': unknown node type '::%s'", query, pseudo);
        }
        // Handle following subclasses in node context (like ::comment:contains())
        Evaluator right;
        while ((right = parseSubclass()) != null) {
            left = and(left, right);
        }
        inNodeContext = false;
        return left;
    }

    private Evaluator byId() {
        String id = tq.consumeCssIdentifier();
        Validate.notEmpty(id);
        return new Evaluator.Id(id);
    }

    private Evaluator byClass() {
        String className = tq.consumeCssIdentifier();
        Validate.notEmpty(className);
        return new Evaluator.Class(className.trim());
    }

    private Evaluator byTag() {
        // todo - these aren't dealing perfectly with case sensitivity. For case sensitive parsers, we should also make
        // the tag in the selector case-sensitive (and also attribute names). But for now, normalize (lower-case) for
        // consistency - both the selector and the element tag
        String tagName = normalize(tq.consumeElementSelector());
        Validate.notEmpty(tagName);
        // namespaces:
        if (tagName.startsWith("*|")) {
            // namespaces: wildcard match equals(tagName) or ending in ":"+tagName
            // strip *|
            String plainTag = tagName.substring(2);
            return new CombiningEvaluator.Or(new Evaluator.Tag(plainTag), new Evaluator.TagEndsWith(":" + plainTag));
        } else if (tagName.endsWith("|*")) {
            // ns|*
            // strip |*, to ns:
            String ns = tagName.substring(0, tagName.length() - 2) + ":";
            return new Evaluator.TagStartsWith(ns);
        } else if (tagName.contains("|")) {
            // flip "abc|def" to "abc:def"
            tagName = tagName.replace("|", ":");
        }
        return new Evaluator.Tag(tagName);
    }

    private Evaluator byAttribute() {
        try (TokenQueue cq = new TokenQueue(tq.chompBalanced('[', ']'))) {
            return evaluatorForAttribute(cq);
        }
    }

    private Evaluator evaluatorForAttribute(TokenQueue cq) {
        // eq, not, start, end, contain, match, (no val)
        String key = cq.consumeToAny(AttributeEvals);
        key = normalize(key);
        Validate.notEmpty(key);
        Validate.isFalse(key.equals("abs:"), "Absolute attribute key must have a name");
        cq.consumeWhitespace();
        final Evaluator eval;
        if (cq.isEmpty()) {
            if (key.startsWith("^"))
                eval = new Evaluator.AttributeStarting(key.substring(1));
            else if (// any attribute
            key.equals("*"))
                eval = new Evaluator.AttributeStarting("");
            else
                eval = new Evaluator.Attribute(key);
        } else {
            if (cq.matchChomp('='))
                eval = new Evaluator.AttributeWithValue(key, cq.remainder());
            else if (cq.matchChomp("!="))
                eval = new Evaluator.AttributeWithValueNot(key, cq.remainder());
            else if (cq.matchChomp("^="))
                eval = new Evaluator.AttributeWithValueStarting(key, cq.remainder());
            else if (cq.matchChomp("$="))
                eval = new Evaluator.AttributeWithValueEnding(key, cq.remainder());
            else if (cq.matchChomp("*="))
                eval = new Evaluator.AttributeWithValueContaining(key, cq.remainder());
            else if (cq.matchChomp("~="))
                eval = new Evaluator.AttributeWithValueMatching(key, Regex.compile(cq.remainder()));
            else
                throw new Selector.SelectorParseException("Could not parse attribute query '%s': unexpected token at '%s'", query, cq.remainder());
        }
        return eval;
    }

    //pseudo selectors :first-child, :last-child, :nth-child, ...
    private static final Pattern NthStepOffset = Pattern.compile("(([+-])?(\\d+)?)n(\\s*([+-])?\\s*\\d+)?", Pattern.CASE_INSENSITIVE);

    private static final Pattern NthOffset = Pattern.compile("([+-])?(\\d+)");

    private Evaluator cssNthChild(boolean last, boolean ofType) {
        // arg is like "odd", or "-n+2", within nth-child(odd)
        String arg = normalize(consumeParens());
        final int step, offset;
        if ("odd".equals(arg)) {
            step = 2;
            offset = 1;
        } else if ("even".equals(arg)) {
            step = 2;
            offset = 0;
        } else {
            Matcher stepOffsetM, stepM;
            if ((stepOffsetM = NthStepOffset.matcher(arg)).matches()) {
                if (// has digits, like 3n+2 or -3n+2
                stepOffsetM.group(3) != null)
                    step = Integer.parseInt(stepOffsetM.group(1).replaceFirst("^\\+", ""));
                else
                    // no digits, might be like n+2, or -n+2. if group(2) == "-", it’s -1;
                    step = "-".equals(stepOffsetM.group(2)) ? -1 : 1;
                offset = stepOffsetM.group(4) != null ? Integer.parseInt(stepOffsetM.group(4).replaceFirst("^\\+", "")) : 0;
            } else if ((stepM = NthOffset.matcher(arg)).matches()) {
                step = 0;
                offset = Integer.parseInt(stepM.group().replaceFirst("^\\+", ""));
            } else {
                throw new Selector.SelectorParseException("Could not parse nth-index '%s': unexpected format", arg);
            }
        }
        return ofType ? (last ? new Evaluator.IsNthLastOfType(step, offset) : new Evaluator.IsNthOfType(step, offset)) : (last ? new Evaluator.IsNthLastChild(step, offset) : new Evaluator.IsNthChild(step, offset));
    }

    private String consumeParens() {
        return tq.chompBalanced('(', ')');
    }

    private int consumeIndex() {
        String index = consumeParens().trim();
        Validate.isTrue(StringUtil.isNumeric(index), "Index must be numeric");
        return Integer.parseInt(index);
    }

    // pseudo selector :has(el)
    private Evaluator has() {
        return parseNested(StructuralEvaluator.Has::new, ":has() must have a selector");
    }

    // pseudo selector :is()
    private Evaluator is() {
        return parseNested(StructuralEvaluator.Is::new, ":is() must have a selector");
    }

    private Evaluator parseNested(Function<Evaluator, Evaluator> func, String err) {
        Validate.isTrue(tq.matchChomp('('), err);
        Evaluator eval = parseSelectorGroup();
        Validate.isTrue(tq.matchChomp(')'), err);
        return func.apply(eval);
    }

    // pseudo selector :contains(text), containsOwn(text)
    private Evaluator contains(boolean own) {
        String query = own ? ":containsOwn" : ":contains";
        String searchText = TokenQueue.unescape(consumeParens());
        Validate.notEmpty(searchText, query + "(text) query must not be empty");
        if (inNodeContext)
            return new NodeEvaluator.ContainsValue(searchText);
        return own ? new Evaluator.ContainsOwnText(searchText) : new Evaluator.ContainsText(searchText);
    }

    private Evaluator containsWholeText(boolean own) {
        String query = own ? ":containsWholeOwnText" : ":containsWholeText";
        String searchText = TokenQueue.unescape(consumeParens());
        Validate.notEmpty(searchText, query + "(text) query must not be empty");
        return own ? new Evaluator.ContainsWholeOwnText(searchText) : new Evaluator.ContainsWholeText(searchText);
    }

    // pseudo selector :containsData(data)
    private Evaluator containsData() {
        String searchText = TokenQueue.unescape(consumeParens());
        Validate.notEmpty(searchText, ":containsData(text) query must not be empty");
        return new Evaluator.ContainsData(searchText);
    }

    // :matches(regex), matchesOwn(regex)
    private Evaluator matches(boolean own) {
        String query = own ? ":matchesOwn" : ":matches";
        // don't unescape, as regex bits will be escaped
        String regex = consumeParens();
        Validate.notEmpty(regex, query + "(regex) query must not be empty");
        Regex pattern = Regex.compile(regex);
        if (inNodeContext)
            return new NodeEvaluator.MatchesValue(pattern);
        return own ? new Evaluator.MatchesOwn(pattern) : new Evaluator.Matches(pattern);
    }

    // :matches(regex), matchesOwn(regex)
    private Evaluator matchesWholeText(boolean own) {
        String query = own ? ":matchesWholeOwnText" : ":matchesWholeText";
        // don't unescape, as regex bits will be escaped
        String regex = consumeParens();
        Validate.notEmpty(regex, query + "(regex) query must not be empty");
        Regex pattern = Regex.compile(regex);
        return own ? new Evaluator.MatchesWholeOwnText(pattern) : new Evaluator.MatchesWholeText(pattern);
    }

    // :not(selector)
    private Evaluator not() {
        String subQuery = consumeParens();
        Validate.notEmpty(subQuery, ":not(selector) subselect must not be empty");
        return new StructuralEvaluator.Not(parse(subQuery));
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
