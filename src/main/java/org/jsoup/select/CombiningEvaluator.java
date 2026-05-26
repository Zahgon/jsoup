package org.jsoup.select;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;
import org.jspecify.annotations.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Base combining (and, or) evaluator.
 */
public abstract class CombiningEvaluator extends Evaluator {

    // maintain original order so that #toString() is sensible
    final ArrayList<Evaluator> evaluators;

    // cost ascending order
    final List<Evaluator> sortedEvaluators;

    int num = 0;

    int cost = 0;

    boolean wantsNodes;

    CombiningEvaluator() {
        super();
        evaluators = new ArrayList<>();
        sortedEvaluators = new ArrayList<>();
    }

    CombiningEvaluator(Collection<Evaluator> evaluators) {
        this();
        this.evaluators.addAll(evaluators);
        updateEvaluators();
    }

    public void add(Evaluator e) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void reset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected int cost() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    boolean wantsNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void updateEvaluators() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static final class And extends CombiningEvaluator {

        public And(Collection<Evaluator> evaluators) {
            super(evaluators);
        }

        And(Evaluator... evaluators) {
            this(Arrays.asList(evaluators));
        }

        @Override
        public boolean matches(Element root, Element el) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean matches(Element root, LeafNode leaf) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static final class Or extends CombiningEvaluator {

        /**
         * Create a new Or evaluator. The initial evaluators are ANDed together and used as the first clause of the OR.
         * @param evaluators initial OR clause (these are wrapped into an AND evaluator).
         */
        public Or(Collection<Evaluator> evaluators) {
            super();
            if (num > 1)
                this.evaluators.add(new And(evaluators));
            else
                // 0 or 1
                this.evaluators.addAll(evaluators);
            updateEvaluators();
        }

        Or(Evaluator... evaluators) {
            this(Arrays.asList(evaluators));
        }

        Or() {
            super();
        }

        @Override
        public boolean matches(Element root, Element element) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean matches(Element root, LeafNode leaf) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
