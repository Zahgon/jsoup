package org.jsoup.select;

import org.jsoup.internal.SoftPool;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeIterator;
import org.jsoup.nodes.TextNode;
import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Base structural evaluator.
 */
abstract class StructuralEvaluator extends Evaluator {

    final Evaluator evaluator;

    // if the evaluator requested nodes, not just elements
    boolean wantsNodes;

    public StructuralEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
        wantsNodes = evaluator.wantsNodes();
    }

    @Override
    boolean wantsNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Memoize inner matches, to save repeated re-evaluations of parent, sibling etc.
    // root + element: Boolean matches. ThreadLocal in case the Evaluator is compiled then reused across multi threads
    final ThreadLocal<Map<Node, Map<Node, Boolean>>> threadMemo = ThreadLocal.withInitial(WeakHashMap::new);

    boolean memoMatches(final Element root, final Node node) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void reset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean matches(Element root, Element element) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    boolean matches(Element root, LeafNode leafNode) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    abstract boolean evaluateMatch(Element root, Node node);

    static class Root extends Evaluator {

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

    static class Has extends StructuralEvaluator {

        static final SoftPool<NodeIterator<Node>> NodeIterPool = new SoftPool<>(() -> new NodeIterator<>(new TextNode(""), Node.class));

        // the element here is just a placeholder so this can be final - gets set in restart()
        // evaluating against siblings (or children)
        private final boolean checkSiblings;

        public Has(Evaluator evaluator) {
            super(evaluator);
            checkSiblings = evalWantsSiblings(evaluator);
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        boolean evaluateMatch(Element root, Node node) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /* Test if the :has sub-clause wants sibling elements (vs nested elements) - will be a Combining eval */
        private static boolean evalWantsSiblings(Evaluator eval) {
            if (eval instanceof CombiningEvaluator) {
                CombiningEvaluator ce = (CombiningEvaluator) eval;
                for (Evaluator innerEval : ce.evaluators) {
                    if (innerEval instanceof PreviousSibling || innerEval instanceof ImmediatePreviousSibling)
                        return true;
                }
            }
            return false;
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
     * Implements the :is(sub-query) pseudo-selector
     */
    static class Is extends StructuralEvaluator {

        public Is(Evaluator evaluator) {
            super(evaluator);
        }

        @Override
        boolean evaluateMatch(Element root, Node node) {
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

    static class Not extends StructuralEvaluator {

        public Not(Evaluator evaluator) {
            super(evaluator);
        }

        @Override
        boolean evaluateMatch(Element root, Node node) {
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
     *     Any Ancestor (i.e., ascending parent chain.).
     */
    static class Ancestor extends StructuralEvaluator {

        public Ancestor(Evaluator evaluator) {
            super(evaluator);
        }

        @Override
        boolean evaluateMatch(Element root, Node node) {
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
     *     Holds a list of evaluators for one > two > three immediate parent matches, and the final direct evaluator under
     *     test. To match, these are effectively ANDed together, starting from the last, matching up to the first.
     */
    static class ImmediateParentRun extends StructuralEvaluator {

        final ArrayList<Evaluator> evaluators = new ArrayList<>();

        int cost = 2;

        public ImmediateParentRun(Evaluator evaluator) {
            super(evaluator);
            evaluators.add(evaluator);
            cost += evaluator.cost();
        }

        void add(Evaluator evaluator) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        boolean evaluateMatch(Element root, Node node) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected int cost() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        protected void reset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    static class PreviousSibling extends StructuralEvaluator {

        public PreviousSibling(Evaluator evaluator) {
            super(evaluator);
        }

        // matches any previous sibling, so can be same in Element only or wantsNodes context
        @Override
        boolean evaluateMatch(Element root, Node node) {
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

    static class ImmediatePreviousSibling extends StructuralEvaluator {

        public ImmediatePreviousSibling(Evaluator evaluator) {
            super(evaluator);
        }

        @Override
        boolean evaluateMatch(Element root, Node node) {
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
