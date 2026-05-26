package org.jsoup.nodes;

import org.jsoup.helper.Validate;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.SharedConstants;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.ParseSettings;
import org.jspecify.annotations.Nullable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import static org.jsoup.internal.Normalizer.lowerCase;
import static org.jsoup.nodes.Range.AttributeRange.UntrackedAttr;

/**
 * The attributes of an Element.
 * <p>
 * During parsing, attributes in with the same name in an element are deduplicated, according to the configured parser's
 * attribute case-sensitive setting. It is possible to have duplicate attributes subsequently if
 * {@link #add(String, String)} vs {@link #put(String, String)} is used.
 * </p>
 * <p>
 * Attribute name and value comparisons are generally <b>case sensitive</b>. By default for HTML, attribute names are
 * normalized to lower-case on parsing. That means you should use lower-case strings when referring to attributes by
 * name.
 * </p>
 *
 * @author Jonathan Hedley, jonathan@hedley.net
 */
public class Attributes implements Iterable<Attribute>, Cloneable {

    // The Attributes object is only created on the first use of an attribute; the Element will just have a null
    // Attribute slot otherwise
    // Indicates an internal key. Can't be set via HTML. (It could be set via accessor, but not too worried about that. Suppressed from list, iter, size.)
    static final char InternalPrefix = '/';

    // data attributes
    protected static final String dataPrefix = "data-";

    private static final String EmptyString = "";

    // manages the key/val arrays
    // sampling found mean count when attrs present = 1.49; 1.08 overall. 2.6:1 don't have any attrs.
    private static final int InitialCapacity = 3;

    private static final int GrowthFactor = 2;

    static final int NotFound = -1;

    // the number of instance fields is kept as low as possible giving an object size of 24 bytes
    // number of slots used (not total capacity, which is keys.length). Package visible for actual size (incl internal)
    int size = 0;

    // keys is not null, but contents may be. Same for vals
    @Nullable
    String[] keys = new String[InitialCapacity];

    // Genericish: all non-internal attribute values must be Strings and are cast on access.
    @Nullable
    Object[] vals = new Object[InitialCapacity];

    // todo - make keys iterable without creating Attribute objects
    // check there's room for more
    private void checkCapacity(int minNewSize) {
        Validate.isTrue(minNewSize >= size);
        int curCap = keys.length;
        if (curCap >= minNewSize)
            return;
        int newCap = curCap >= InitialCapacity ? size * GrowthFactor : InitialCapacity;
        if (minNewSize > newCap)
            newCap = minNewSize;
        keys = Arrays.copyOf(keys, newCap);
        vals = Arrays.copyOf(vals, newCap);
    }

    int indexOfKey(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Finds a visible attribute's range index, skipping internal metadata slots.
     */
    int visibleIndexOfKey(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Maps an attribute array slot to the matching visible attribute index.
     */
    private int visibleIndex(int index) {
        int visible = 0;
        for (int i = 0; i < index; i++) {
            if (!isInternalKey(keys[i]))
                visible++;
        }
        return visible;
    }

    private int indexOfKeyIgnoreCase(String key) {
        Validate.notNull(key);
        for (int i = 0; i < size; i++) {
            if (key.equalsIgnoreCase(keys[i]))
                return i;
        }
        return NotFound;
    }

    // we track boolean attributes as null in values - they're just keys. so returns empty for consumers
    // casts to String, so only for non-internal attributes
    static String checkNotNull(@Nullable Object val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get an attribute value by key.
     *     @param key the (case-sensitive) attribute key
     *     @return the attribute value if set; or empty string if not set (or a boolean attribute).
     *     @see #hasKey(String)
     */
    public String get(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get an Attribute by key. The Attribute will remain connected to these Attributes, so changes made via
     *     {@link Attribute#setKey(String)}, {@link Attribute#setValue(String)} etc will cascade back to these Attributes and
     *     their owning Element.
     *     @param key the (case-sensitive) attribute key
     *     @return the Attribute for this key, or null if not present.
     *     @since 1.17.2
     */
    @Nullable
    public Attribute attribute(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get an attribute's value by case-insensitive key
     * @param key the attribute name
     * @return the first matching attribute value if set; or empty string if not set (ora boolean attribute).
     */
    public String getIgnoreCase(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Adds a new attribute. Will produce duplicates if the key already exists.
     * @see Attributes#put(String, String)
     */
    public Attributes add(String key, @Nullable String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void addObject(String key, @Nullable Object value) {
        checkCapacity(size + 1);
        keys[size] = key;
        vals[size] = value;
        size++;
    }

    /**
     * Set a new attribute, or replace an existing one by key.
     * @param key case sensitive attribute key (not null)
     * @param value attribute value (which can be null, to set a true boolean attribute)
     * @return these attributes, for chaining
     */
    public Attributes put(String key, @Nullable String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the map holding any user-data associated with these Attributes. Will be created empty on first use. Held as
     *     an internal attribute, not a field member, to reduce the memory footprint of Attributes when not used. Can hold
     *     arbitrary objects; use for connecting W3C nodes to Elements, etc.
     * @return the map holding user-data
     */
    @SuppressWarnings("unchecked")
    Map<String, Object> userData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Check if these attributes have any user data associated with them.
     */
    boolean hasUserData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get an arbitrary user-data object by key.
     * @param key case-sensitive key to the object.
     * @return the object associated to this key, or {@code null} if not found.
     * @see #userData(String key, Object val)
     * @since 1.17.1
     */
    @Nullable
    public Object userData(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Set an arbitrary user-data object by key. Will be treated as an internal attribute, so will not be emitted in HTML.
     * @param key case-sensitive key
     * @param value object value. Providing a {@code null} value has the effect of removing the key from the userData map.
     * @return these attributes
     * @see #userData(String key)
     * @since 1.17.1
     */
    public Attributes userData(String key, @Nullable Object value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets the range spans, if source tracking was used.
     */
    Range.@Nullable Spans spans() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Gets or creates the range spans for this attributes object.
     */
    Range.Spans ensureSpans() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Sets the range spans when expanding compact leaf storage.
     */
    void putSpans(Range.Spans rangeSpans) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void putIgnoreCase(String key, @Nullable String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set a new boolean attribute. Removes the attribute if the value is false.
     * @param key case <b>insensitive</b> attribute key
     * @param value attribute value
     * @return these attributes, for chaining
     */
    public Attributes put(String key, boolean value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Set a new attribute, or replace an existing one by key.
     *     @param attribute attribute with case-sensitive key
     *     @return these attributes, for chaining
     */
    public Attributes put(Attribute attribute) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // removes and shifts up
    @SuppressWarnings("AssignmentToNull")
    private void remove(int index) {
        Validate.isFalse(index >= size);
        Range.Spans rangeSpans = spans();
        // Source ranges are stored by visible attribute index; internal metadata slots have no matching range record.
        if (rangeSpans != null && !isInternalKey(keys[index]))
            rangeSpans.removeAttributeRange(visibleIndex(index));
        int shifted = size - index - 1;
        if (shifted > 0) {
            System.arraycopy(keys, index + 1, keys, index, shifted);
            System.arraycopy(vals, index + 1, vals, index, shifted);
        }
        size--;
        // release hold
        keys[size] = null;
        vals[size] = null;
    }

    /**
     *     Remove an attribute by key. <b>Case sensitive.</b>
     *     @param key attribute key to remove
     */
    public void remove(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Remove an attribute by key. <b>Case insensitive.</b>
     *     @param key attribute key to remove
     */
    public void removeIgnoreCase(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Tests if these attributes contain an attribute with this key.
     *     @param key case-sensitive key to check for
     *     @return true if key exists, false otherwise
     */
    public boolean hasKey(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Tests if these attributes contain an attribute with this key.
     *     @param key key to check for
     *     @return true if key exists, false otherwise
     */
    public boolean hasKeyIgnoreCase(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if these attributes contain an attribute with a value for this key.
     * @param key key to check for
     * @return true if key exists, and it has a value
     */
    public boolean hasDeclaredValueForKey(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if these attributes contain an attribute with a value for this key.
     * @param key case-insensitive key to check for
     * @return true if key exists, and it has a value
     */
    public boolean hasDeclaredValueForKeyIgnoreCase(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the number of attributes in this set, excluding any internal-only attributes (e.g. user data).
     *     <p>Internal attributes are excluded from the {@link #html()}, {@link #asList()}, and {@link #iterator()}
     *     methods.</p>
     *
     *     @return size
     */
    public int size() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Test if this Attributes list is empty.
     *     <p>This does not include internal attributes, such as user data.</p>
     */
    public boolean isEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Add all the attributes from the incoming set to this set.
     *     @param incoming attributes to add to these attributes.
     */
    public void addAll(Attributes incoming) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the source ranges (start to end position) in the original input source from which this attribute's <b>name</b>
     *     and <b>value</b> were parsed.
     *     <p>Position tracking must be enabled before parsing the content.</p>
     *     @param key the attribute name
     *     @return the ranges for the attribute's name and value, or {@code untracked} if the attribute does not exist or its range
     *     was not tracked.
     *     @see org.jsoup.parser.Parser#setTrackPosition(boolean)
     *     @see Attribute#sourceRange()
     *     @see Node#sourceRange()
     *     @see Element#endSourceRange()
     *     @since 1.17.1
     */
    public Range.AttributeRange sourceRange(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Deprecated parser-internal source range setup method, retained for source compatibility. Source ranges are normally
     *     produced by enabling parser position tracking before parsing.
     *     @param key the attribute name
     *     @param range the range for the attribute's name and value
     *     @return these attributes, for chaining
     *     @since 1.18.2
     *     @deprecated Use parser position tracking instead. Will be removed in jsoup 1.24.1.
     */
    @Deprecated
    public Attributes sourceRange(String key, Range.AttributeRange range) {
        Validate.notNull(key);
        Validate.notNull(range);
        NodeInternals.attributeRange(this, key, range);
        return this;
    }

    @Override
    public Iterator<Attribute> iterator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the attributes as a List, for iteration.
     *     @return a view of the attributes as an unmodifiable List.
     */
    public List<Attribute> asList() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieves a filtered view of attributes that are HTML5 custom data attributes; that is, attributes with keys
     * starting with {@code data-}.
     * @return map of custom data attributes.
     */
    public Map<String, String> dataset() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the HTML representation of these attributes.
     *     @return HTML
     */
    public String html() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    final void html(final QuietAppendable accum, final Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks if these attributes are equal to another set of attributes, by comparing the two sets. Note that the order
     * of the attributes does not impact this equality (as per the Map interface equals()).
     * @param o attributes to compare with
     * @return if both sets of attributes have the same content
     */
    @Override
    public boolean equals(@Nullable Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Calculates the hashcode of these attributes, by iterating all attributes and summing their hashcodes.
     * @return calculated hashcode
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    @SuppressWarnings("unchecked")
    public Attributes clone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Internal method. Lowercases all (non-internal) keys.
     */
    public void normalize() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Internal method. Removes duplicate attribute by name. Settings for case sensitivity of key names.
     * @param settings case sensitivity
     * @return number of removed dupes
     */
    public int deduplicate(ParseSettings settings) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static class Dataset extends AbstractMap<String, String> {

        private final Attributes attributes;

        private Dataset(Attributes attributes) {
            this.attributes = attributes;
        }

        @Override
        public Set<Entry<String, String>> entrySet() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String put(String key, String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        private class EntrySet extends AbstractSet<Map.Entry<String, String>> {

            @Override
            public Iterator<Map.Entry<String, String>> iterator() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public int size() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        }

        private class DatasetIterator implements Iterator<Map.Entry<String, String>> {

            private final Iterator<Attribute> attrIter = attributes.iterator();

            private Attribute attr;

            @Override
            public boolean hasNext() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public Entry<String, String> next() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("STUB: not implemented");
            }
        }
    }

    private static String dataKey(String key) {
        return dataPrefix + key;
    }

    static String internalKey(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static boolean isInternalKey(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
