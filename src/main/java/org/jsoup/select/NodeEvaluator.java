package org.jsoup.select;

import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;
import org.jsoup.helper.Regex;
import static org.jsoup.internal.Normalizer.lowerCase;
import static org.jsoup.internal.StringUtil.normaliseWhitespace;

abstract class NodeEvaluator extends Evaluator {

    @Override
    public boolean matches(Element root, Element element) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    boolean matches(Element root, LeafNode leaf) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    abstract boolean evaluateMatch(Node node);

    @Override
    boolean wantsNodes() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static class InstanceType extends NodeEvaluator {

        final java.lang.Class<? extends Node> type;

        final String selector;

        InstanceType(java.lang.Class<? extends Node> type, String selector) {
            super();
            this.type = type;
            this.selector = "::" + selector;
        }

        @Override
        boolean evaluateMatch(Node node) {
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

    static class ContainsValue extends NodeEvaluator {

        private final String searchText;

        public ContainsValue(String searchText) {
            this.searchText = lowerCase(normaliseWhitespace(searchText));
        }

        @Override
        boolean evaluateMatch(Node node) {
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
     *     Matches nodes with no value or only whitespace.
     */
    static class BlankValue extends NodeEvaluator {

        @Override
        boolean evaluateMatch(Node node) {
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

    static class MatchesValue extends NodeEvaluator {

        private final Regex pattern;

        protected MatchesValue(Regex pattern) {
            this.pattern = pattern;
        }

        @Override
        boolean evaluateMatch(Node node) {
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
