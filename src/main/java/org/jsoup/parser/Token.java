package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.NodeInternals;
import org.jsoup.nodes.Range;
import org.jspecify.annotations.Nullable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Parse tokens for the Tokeniser.
 */
abstract class Token {

    static final int UnsetPos = -1;

    // used in switches in TreeBuilder vs .getClass()
    final TokenType type;

    // position in CharacterReader this token was read from
    int startPos, endPos = UnsetPos;

    private Token(TokenType type) {
        this.type = type;
    }

    String tokenType() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Reset the data represent by this token, for reuse. Prevents the need to create transfer objects for every
     * piece of data, which immediately get GCed.
     */
    Token reset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int startPos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void startPos(int pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    int endPos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void endPos(int pos) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static final class Doctype extends Token {

        final TokenData name = new TokenData();

        @Nullable
        String pubSysKey = null;

        final TokenData publicIdentifier = new TokenData();

        final TokenData systemIdentifier = new TokenData();

        final TokenData internalSubset = new TokenData();

        boolean sawInternalSubset = false;

        boolean forceQuirks = false;

        Doctype() {
            super(TokenType.Doctype);
        }

        @Override
        Token reset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        String getName() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Nullable
        String getPubSysKey() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        String getPublicIdentifier() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public String getSystemIdentifier() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        String getInternalSubset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        boolean hasInternalSubset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public boolean isForceQuirks() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    static abstract class Tag extends Token {

        protected TokenData tagName = new TokenData();

        // lc version of tag name, for case-insensitive tree build
        @Nullable
        protected String normalName;

        boolean selfClosing = false;

        // start tags get attributes on construction. End tags get attributes on first new attribute (but only for parser convenience, not used).
        @Nullable
        Attributes attributes;

        final private TokenData attrName = new TokenData();

        final private TokenData attrValue = new TokenData();

        // distinguish boolean attribute from empty string value
        private boolean hasEmptyAttrValue = false;

        // attribute source range tracking
        final TreeBuilder treeBuilder;

        final boolean trackSource;

        private static final int AttrRangeWidth = 4;

        int attrNameStart, attrNameEnd, attrValStart, attrValEnd;

        @Nullable
        private String @Nullable [] attrRangeNames;

        private int @Nullable [] attrRangePositions;

        private int attrRangeCount;

        Tag(TokenType type, TreeBuilder treeBuilder) {
            super(type);
            this.treeBuilder = treeBuilder;
            this.trackSource = treeBuilder.trackSourceRange;
        }

        @Override
        Tag reset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private void resetPendingAttr() {
            attrName.reset();
            attrValue.reset();
            hasEmptyAttrValue = false;
            if (trackSource)
                attrNameStart = attrNameEnd = attrValStart = attrValEnd = UnsetPos;
        }

        /* Limits runaway crafted HTML from spewing attributes and getting a little sluggish in ensureCapacity.
        Real-world HTML will P99 around 8 attributes, so plenty of headroom. Implemented here and not in the Attributes
        object so that API users can add more if ever required. */
        private static final int MaxAttributes = 512;

        final void newAttribute() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private void trackAttributeRange(String name) {
            if (treeBuilder.trackSourceRange && isStartTag()) {
                // if there's no value (e.g. boolean), make it an implicit range at current
                if (!attrValue.hasData())
                    attrValStart = attrValEnd = attrNameEnd;
                addAttributeRange(name, attrNameStart, attrNameEnd, attrValStart, attrValEnd);
            }
        }

        /**
         *         Stages an attribute range until the attributes are normalized and deduplicated.
         */
        private void addAttributeRange(String name, int nameStart, int nameEnd, int valueStart, int valueEnd) {
            ensureAttributeRangeCapacity(attrRangeCount + 1);
            assert attrRangeNames != null;
            assert attrRangePositions != null;
            attrRangeNames[attrRangeCount] = name;
            int rangeIndex = attrRangeIndex(attrRangeCount);
            attrRangePositions[rangeIndex] = nameStart;
            attrRangePositions[rangeIndex + 1] = nameEnd;
            attrRangePositions[rangeIndex + 2] = valueStart;
            attrRangePositions[rangeIndex + 3] = valueEnd;
            attrRangeCount++;
        }

        /**
         *         Grows parser-local attribute range staging arrays.
         */
        private void ensureAttributeRangeCapacity(int minSize) {
            if (attrRangeNames != null && attrRangeNames.length >= minSize)
                return;
            int size = attrRangeNames == null ? 3 : attrRangeNames.length * 2;
            if (size < minSize)
                size = minSize;
            attrRangeNames = attrRangeNames == null ? new String[size] : Arrays.copyOf(attrRangeNames, size);
            attrRangePositions = attrRangePositions == null ? new int[size * AttrRangeWidth] : Arrays.copyOf(attrRangePositions, size * AttrRangeWidth);
        }

        /**
         *         Attaches staged attribute ranges after parser normalization and deduplication have settled attribute slots.
         */
        final void finaliseAttributeRanges(ParseSettings settings) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Maps a staged attribute range to its first source offset slot.
         */
        private static int attrRangeIndex(int index) {
            return index * AttrRangeWidth;
        }

        final boolean hasAttributes() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        final boolean hasAttributeIgnoreCase(String key) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        final void finaliseTag() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Preserves case
         */
        final String name() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Lower case
         */
        final String normalName() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        final String toStringName() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        final Tag name(String name) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        final boolean isSelfClosing() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        // these appenders are rarely hit in not null state-- caused by null chars.
        final void appendTagName(String append) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        final void appendTagName(char append) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        final void appendAttributeName(String append, int startPos, int endPos) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        final void appendAttributeName(char append, int startPos, int endPos) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        final void appendAttributeValue(String append, int startPos, int endPos) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        final void appendAttributeValue(char append, int startPos, int endPos) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        final void appendAttributeValue(int[] appendCodepoints, int startPos, int endPos) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        final void setEmptyAttributeValue() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private void attrNamePos(int startPos, int endPos) {
            if (trackSource) {
                // latches to first
                attrNameStart = attrNameStart > UnsetPos ? attrNameStart : startPos;
                attrNameEnd = endPos;
            }
        }

        private void attrValPos(int startPos, int endPos) {
            if (trackSource) {
                // latches to first
                attrValStart = attrValStart > UnsetPos ? attrValStart : startPos;
                attrValEnd = endPos;
            }
        }

        @Override
        abstract public String toString();
    }

    final static class StartTag extends Tag {

        // TreeBuilder is provided so if tracking, can get line / column positions for Range; and can dedupe as we go
        StartTag(TreeBuilder treeBuilder) {
            super(TokenType.StartTag, treeBuilder);
        }

        @Override
        Tag reset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        StartTag nameAttr(String name, Attributes attributes) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    final static class EndTag extends Tag {

        EndTag(TreeBuilder treeBuilder) {
            super(TokenType.EndTag, treeBuilder);
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    final static class Comment extends Token {

        private final TokenData data = new TokenData();

        boolean bogus = false;

        @Override
        Token reset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        Comment() {
            super(TokenType.Comment);
        }

        String getData() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        Comment append(String append) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        Comment append(char append) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    static class Character extends Token {

        final TokenData data = new TokenData();

        Character() {
            super(TokenType.Character);
        }

        /**
         * Deep copy
         */
        Character(Character source) {
            super(TokenType.Character);
            this.startPos = source.startPos;
            this.endPos = source.endPos;
            this.data.set(source.data.value());
        }

        @Override
        Token reset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        Character data(String str) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        Character append(String str) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        String getData() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Normalize null chars in the data. If replace is true, replaces with the replacement char; if false, removes.
         */
        public void normalizeNulls(boolean replace) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private static final String nullString = String.valueOf(TokeniserState.nullChar);
    }

    final static class CData extends Character {

        CData(String data) {
            super();
            this.data(data);
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     *     XmlDeclaration - extends Tag for pseudo attribute support
     */
    final static class XmlDecl extends Tag {

        // <!..>, or <?...?> if false (a processing instruction)
        boolean isDeclaration = true;

        public XmlDecl(TreeBuilder treeBuilder) {
            super(TokenType.XmlDecl, treeBuilder);
        }

        @Override
        XmlDecl reset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    final static class EOF extends Token {

        EOF() {
            super(Token.TokenType.EOF);
        }

        @Override
        Token reset() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    final boolean isDoctype() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final Doctype asDoctype() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final boolean isStartTag() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final StartTag asStartTag() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final boolean isEndTag() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final EndTag asEndTag() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final boolean isComment() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final Comment asComment() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final boolean isCharacter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final boolean isCData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final Character asCharacter() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final XmlDecl asXmlDecl() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final boolean isEOF() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public enum TokenType {

        Doctype,
        StartTag,
        EndTag,
        Comment,
        // note no CData - treated in builder as an extension of Character
        Character,
        XmlDecl,
        EOF
    }
}
