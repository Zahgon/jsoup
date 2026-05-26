package org.jsoup.nodes;

import org.jsoup.internal.LineMap;
import org.jsoup.internal.StringUtil;
import java.util.Arrays;
import java.util.Objects;

/**
 * A Range tracks the source offsets where a Node starts or ends. Line and column coordinates are derived from the
 * line map retained during parsing. To track these positions, enable {@link org.jsoup.parser.Parser#setTrackPosition(boolean)}
 * before parsing.
 * @see Node#sourceRange()
 * @since 1.15.2
 */
public class Range {

    // sentinels
    private static final LineMap UnsetLineMap = new LineMap();

    private static final int[] UnsetAttrRanges = new int[0];

    private static final Position UntrackedPos = new Position(-1, -1, -1);

    private static final Range Untracked = new Range();

    private final LineMap lineMap;

    private final int startPos;

    private final int endPos;

    /**
     *     Creates the untracked source range sentinel.
     */
    private Range() {
        lineMap = UnsetLineMap;
        startPos = -1;
        endPos = -1;
    }

    /**
     *     Creates a new Range from source offsets.
     */
    private Range(LineMap lineMap, int startPos, int endPos) {
        this.lineMap = lineMap;
        if (startPos < 0 || endPos < 0)
            throw new IllegalArgumentException("Range positions must be non-negative");
        this.startPos = startPos;
        this.endPos = endPos;
    }

    /**
     *     Deprecated parser-internal source range setup method, retained for source compatibility. The line and column values
     *     in the supplied Positions are not retained; they are derived from source offsets. If either supplied Position is
     *     untracked, this Range will also be untracked.
     *
     *     @param start the start position
     *     @param end   the end position
     *     @deprecated Use parser position tracking instead. Will be removed in jsoup 1.24.1.
     */
    @Deprecated
    public Range(Position start, Position end) {
        Objects.requireNonNull(start);
        Objects.requireNonNull(end);
        if (start.pos < -1 || end.pos < -1)
            throw new IllegalArgumentException("Range positions must be non-negative, or -1 for untracked");
        if (start.pos == -1 || end.pos == -1) {
            lineMap = UnsetLineMap;
            startPos = -1;
            endPos = -1;
        } else {
            lineMap = new LineMap();
            startPos = start.pos;
            endPos = end.pos;
        }
    }

    /**
     *     Get the start position of this range, with 1-based line and column coordinates.
     * @return the start position.
     */
    public Position start() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the starting source offset of this range.
     *     @return the 0-based start source offset.
     *     @since 1.17.1
     */
    public int startPos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the end position of this range, with 1-based line and column coordinates.
     * @return the end position.
     */
    public Position end() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the ending source offset of this range.
     *     @return the 0-based ending source offset.
     *     @since 1.17.1
     */
    public int endPos() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Test if this range has source offsets available.
     * @return true if this range has source offsets, false otherwise (and all fields will be {@code -1}).
     */
    public boolean isTracked() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Checks if the range represents a node that was implicitly created / closed.
     *     <p>For example, with HTML of {@code <p>One<p>Two}, both {@code p} elements will have an explicit
     *     {@link Element#sourceRange()} but an implicit {@link Element#endSourceRange()} marking the end position, as neither
     *     have closing {@code </p>} tags. The TextNodes will have explicit sourceRanges.
     *     <p>A range is considered implicit if its start and end positions are the same.
     *     @return true if the range is tracked and its start and end positions are the same, false otherwise.
     *     @since 1.17.1
     */
    public boolean isImplicit() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Creates a Position from a source offset and this Range's line map.
     */
    private Position position(int pos) {
        return new Position(pos, lineMap.lineNumber(pos), lineMap.columnNumber(pos));
    }

    /**
     *     Retrieves the start source range for a given Node.
     * @param node the node to retrieve the position for
     * @return the Range, or the Untracked (-1) position if tracking is disabled.
     */
    static Range ofStart(Node node) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Retrieves the end source range for a given Element.
     * @param element the element to retrieve the end tag position for
     * @return the Range, or the Untracked (-1) position if tracking is disabled.
     */
    static Range ofEnd(Element element) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets a String representation of this Range, in the format {@code line,column:pos-line,column:pos}.
     * @return a String
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     A Position describes a source offset and its line and column coordinates. Positions are available when position
     *     tracking is enabled with {@link org.jsoup.parser.Parser#setTrackPosition(boolean)} before parsing.
     *     @see Node#sourceRange()
     */
    public static class Position {

        private final int pos, lineNumber, columnNumber;

        /**
         *         Deprecated parser-internal position setup method, retained for source compatibility. Position objects are
         *         normally derived from a Range's retained source offsets.
         * @param pos position index
         * @param lineNumber line number
         * @param columnNumber column number
         *         @deprecated Use parser position tracking instead. Will be removed in jsoup 1.24.1.
         */
        @Deprecated
        public Position(int pos, int lineNumber, int columnNumber) {
            this.pos = pos;
            this.lineNumber = lineNumber;
            this.columnNumber = columnNumber;
        }

        /**
         *         Gets the position index (0-based) of the original input source that this Position was read at. This tracks the
         *         total number of characters read into the source at this position, regardless of the number of preceding lines.
         * @return the position, or {@code -1} if untracked.
         */
        public int pos() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Gets the line number (1-based) of the original input source that this Position was read at.
         * @return the line number, or {@code -1} if untracked.
         */
        public int lineNumber() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Gets the cursor number (1-based) of the original input source that this Position was read at. The cursor number
         *         resets to 1 on every new line.
         * @return the cursor number, or {@code -1} if untracked.
         */
        public int columnNumber() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Test if this position was tracked during parsing.
         * @return true if this was tracked during parsing, false otherwise (and all fields will be {@code -1}).
         */
        public boolean isTracked() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Gets a String presentation of this Position, in the format {@code line,column:pos}.
         * @return a String
         */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean equals(Object o) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int hashCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    public static class AttributeRange {

        static final AttributeRange UntrackedAttr = new AttributeRange();

        private final LineMap lineMap;

        private final int nameStartPos, nameEndPos, valueStartPos, valueEndPos;

        /**
         *         Creates the untracked attribute source range sentinel.
         */
        private AttributeRange() {
            lineMap = UnsetLineMap;
            nameStartPos = -1;
            nameEndPos = -1;
            valueStartPos = -1;
            valueEndPos = -1;
        }

        /**
         *         Creates a new AttributeRange from source offsets.
         */
        private AttributeRange(LineMap lineMap, int nameStartPos, int nameEndPos, int valueStartPos, int valueEndPos) {
            this.lineMap = lineMap;
            if (nameStartPos < 0 || nameEndPos < 0 || valueStartPos < 0 || valueEndPos < 0)
                throw new IllegalArgumentException("Attribute range positions must be non-negative");
            this.nameStartPos = nameStartPos;
            this.nameEndPos = nameEndPos;
            this.valueStartPos = valueStartPos;
            this.valueEndPos = valueEndPos;
        }

        /**
         *         Deprecated parser-internal source range setup method, retained for source compatibility. Source ranges are
         *         normally produced by enabling parser position tracking before parsing. If either supplied Range is untracked,
         *         this AttributeRange will also be untracked.
         *         @deprecated Use parser position tracking instead. Will be removed in jsoup 1.24.1.
         */
        @Deprecated
        public AttributeRange(Range nameRange, Range valueRange) {
            Objects.requireNonNull(nameRange);
            Objects.requireNonNull(valueRange);
            if (!nameRange.isTracked() || !valueRange.isTracked()) {
                lineMap = UnsetLineMap;
                nameStartPos = -1;
                nameEndPos = -1;
                valueStartPos = -1;
                valueEndPos = -1;
            } else {
                lineMap = nameRange.lineMap;
                nameStartPos = nameRange.startPos;
                nameEndPos = nameRange.endPos;
                valueStartPos = valueRange.startPos;
                valueEndPos = valueRange.endPos;
            }
        }

        /**
         * Get the source range for the attribute's name.
         */
        public Range nameRange() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Get the source range for the attribute's value.
         */
        public Range valueRange() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Tests if this attribute range has tracked name and value offsets.
         * @return true if the attribute's name and value ranges were tracked; false otherwise.
         *         @since 1.23.1
         */
        public boolean isTracked() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Get a String representation of this Attribute range, in the form
         *         {@code line,column:pos-line,column:pos=line,column:pos-line,column:pos} (name start - name end = val start - val end)
         */
        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean equals(Object o) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int hashCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }

    /**
     *     Internal range span storage attached to a Node or Attributes object.
     *     <p>Unset records use {@code -1}; once written, a node, end-tag, or attribute range record is complete.</p>
     */
    static final class Spans {

        private static final int AttrRangeWidth = 4;

        private LineMap lineMap = UnsetLineMap;

        private int nodeStartPos = -1;

        private int nodeEndPos = -1;

        private int endTagStartPos = -1;

        private int endTagEndPos = -1;

        private int[] attrRanges = UnsetAttrRanges;

        /**
         *         Gets the node start source range.
         */
        private Range sourceRange() {
            return range(nodeStartPos, nodeEndPos);
        }

        /**
         *         Gets the element end tag source range.
         */
        private Range endSourceRange() {
            return range(endTagStartPos, endTagEndPos);
        }

        /**
         *         Sets the node start source range.
         */
        void sourceRange(LineMap lineMap, int startPos, int endPos) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Sets the element end tag source range.
         */
        void endSourceRange(LineMap lineMap, int startPos, int endPos) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Gets the source ranges for an attribute slot.
         */
        Range.AttributeRange attributeRange(int index) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Sets the source ranges for an attribute slot.
         */
        void attributeRange(int index, Range.AttributeRange range) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Sets source range offsets for an attribute slot.
         */
        void attributeRange(int index, LineMap lineMap, int nameStart, int nameEnd, int valueStart, int valueEnd) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Retains the first line map and rejects mixed-source ranges.
         */
        private void useLineMap(LineMap lineMap) {
            if (this.lineMap == UnsetLineMap) {
                this.lineMap = lineMap;
            } else if (this.lineMap != lineMap) {
                throw new IllegalArgumentException("Source ranges must come from the same parse");
            }
        }

        /**
         *         Removes an attribute slot and shifts following source ranges.
         */
        void removeAttributeRange(int index) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Returns a copy whose source range arrays can mutate independently.
         */
        Spans copy() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         *         Grows attribute range storage to hold the requested slot count.
         */
        private void ensureAttributeCapacity(int minLength) {
            if (attrRanges.length >= minLength)
                return;
            int oldLength = attrRanges.length;
            attrRanges = Arrays.copyOf(attrRanges, minLength);
            Arrays.fill(attrRanges, oldLength, attrRanges.length, -1);
        }

        /**
         *         Creates a Range from stored offsets.
         */
        private Range range(int startPos, int endPos) {
            if (startPos == -1)
                return Range.Untracked;
            return new Range(lineMap, startPos, endPos);
        }

        /**
         *         Maps an attribute slot to its stored name range start slot.
         */
        private static int attrNameStartIndex(int index) {
            return index * AttrRangeWidth;
        }

        /**
         *         Maps an attribute slot to its stored value range start slot.
         */
        private static int attrValueStartIndex(int index) {
            return index * AttrRangeWidth + 2;
        }

        @Override
        public boolean equals(Object o) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int hashCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
