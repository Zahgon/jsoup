package org.jsoup.nodes;

import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.parser.Tag;
import org.jsoup.select.NodeVisitor;
import org.jspecify.annotations.Nullable;

/**
 * Base Printer
 */
class Printer implements NodeVisitor {

    final Node root;

    final QuietAppendable accum;

    final OutputSettings settings;

    Printer(Node root, QuietAppendable accum, OutputSettings settings) {
        this.root = root;
        this.accum = accum;
        this.settings = settings;
    }

    void addHead(Element el, int depth) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void addTail(Element el, int depth) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void addText(TextNode textNode, int textOptions, int depth) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void addNode(LeafNode node, int depth) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void indent(int depth) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void head(Node node, int depth) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void tail(Node node, int depth) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Pretty Printer
     */
    static class Pretty extends Printer {

        boolean preserveWhitespace = false;

        Pretty(Node root, QuietAppendable accum, OutputSettings settings) {
            super(root, accum, settings);
            // check if there is a pre on stack
            for (Node node = root; node != null; node = node.parentNode()) {
                if (tagIs(Tag.PreserveWhitespace, node)) {
                    preserveWhitespace = true;
                    break;
                }
            }
        }

        @Override
        void addHead(Element el, int depth) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        void addTail(Element el, int depth) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        void addNode(LeafNode node, int depth) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        void addText(TextNode node, int textOptions, int depth) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        int textTrim(TextNode node, int options) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean shouldIndent(@Nullable Node node) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean isBlockEl(@Nullable Node node) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Returns true if any of the Element's child nodes should indent. Checks the last 5 nodes only (to minimize
         *         scans).
         */
        static boolean hasChildBlocks(Element el) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        static private final int maxScan = 5;

        static boolean hasNonTextNodes(Element el) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Nullable
        static Node previousNonblank(Node node) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Nullable
        static Node nextNonBlank(@Nullable Node node) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        static boolean isBlankText(@Nullable Node node) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        static boolean tagIs(int option, @Nullable Node node) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     * Outline Printer
     */
    static class Outline extends Pretty {

        Outline(Node root, QuietAppendable accum, OutputSettings settings) {
            super(root, accum, settings);
        }

        @Override
        boolean isBlockEl(@Nullable Node node) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        boolean shouldIndent(@Nullable Node node) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    static Printer printerFor(Node root, QuietAppendable accum) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
