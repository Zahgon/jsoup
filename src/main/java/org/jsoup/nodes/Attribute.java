package org.jsoup.nodes;

import org.jsoup.helper.Validate;
import org.jsoup.internal.Normalizer;
import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.SharedConstants;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.jspecify.annotations.Nullable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A single key + value attribute. (Only used for presentation.)
 */
public class Attribute implements Map.Entry<String, String>, Cloneable {

    private static final String[] booleanAttributes = { "allowfullscreen", "async", "autofocus", "checked", "compact", "declare", "default", "defer", "disabled", "formnovalidate", "hidden", "inert", "ismap", "itemscope", "multiple", "muted", "nohref", "noresize", "noshade", "novalidate", "nowrap", "open", "readonly", "required", "reversed", "seamless", "selected", "sortable", "truespeed", "typemustmatch" };

    private String key;

    @Nullable
    private String val;

    // used to update the holding Attributes when the key / value is changed via this interface
    @Nullable
    Attributes parent;

    /**
     * Create a new attribute from unencoded (raw) key and value.
     * @param key attribute key; case is preserved.
     * @param value attribute value (may be null)
     * @see #createFromEncoded
     */
    public Attribute(String key, @Nullable String value) {
        this(key, value, null);
    }

    /**
     * Create a new attribute from unencoded (raw) key and value.
     * @param key attribute key; case is preserved.
     * @param val attribute value (may be null)
     * @param parent the containing Attributes (this Attribute is not automatically added to said Attributes)
     * @see #createFromEncoded
     */
    public Attribute(String key, @Nullable String val, @Nullable Attributes parent) {
        Validate.notNull(key);
        key = key.trim();
        // trimming could potentially make empty, so validate here
        Validate.notEmpty(key);
        this.key = key;
        this.val = val;
        this.parent = parent;
    }

    /**
     *     Get the attribute's key (aka name).
     *     @return the attribute key
     */
    @Override
    public String getKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Set the attribute key; case is preserved.
     *     @param key the new key; must not be null
     */
    public void setKey(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the attribute value. Will return an empty string if the value is not set.
     *     @return the attribute value
     */
    @Override
    public String getValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Check if this Attribute has a value. Set boolean attributes have no value.
     * @return if this is a boolean attribute / attribute without a value
     */
    public boolean hasDeclaredValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Set the attribute value.
     *     @param val the new attribute value; may be null (to set an enabled boolean attribute)
     *     @return the previous value (if was null; an empty string)
     */
    @Override
    public String setValue(@Nullable String val) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get this attribute's key prefix, if it has one; else the empty string.
     *     <p>For example, the attribute {@code og:title} has prefix {@code og}, and local {@code title}.</p>
     *
     *     @return the tag's prefix
     *     @since 1.20.1
     */
    public String prefix() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get this attribute's local name. The local name is the name without the prefix (if any).
     *     <p>For example, the attribute key {@code og:title} has local name {@code title}.</p>
     *
     *     @return the tag's local name
     *     @since 1.20.1
     */
    public String localName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get this attribute's namespace URI, if the attribute was prefixed with a defined namespace name. Otherwise, returns
     *     the empty string. These will only be defined if using the XML parser.
     *     @return the tag's namespace URI, or empty string if not defined
     *     @since 1.20.1
     */
    public String namespace() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the HTML representation of this attribute; e.g. {@code href="index.html"}.
     *     @return HTML
     */
    public String html() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get the source ranges (start to end positions) in the original input source from which this attribute's <b>name</b>
     *     and <b>value</b> were parsed.
     *     <p>Position tracking must be enabled prior to parsing the content.</p>
     *     @return the ranges for the attribute's name and value, or {@code untracked} if the attribute does not exist or its range
     *     was not tracked.
     *     @see org.jsoup.parser.Parser#setTrackPosition(boolean)
     *     @see Attributes#sourceRange(String)
     *     @see Node#sourceRange()
     *     @see Element#endSourceRange()
     *     @since 1.17.1
     */
    public Range.AttributeRange sourceRange() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void html(QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static void html(String key, @Nullable String val, QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * @deprecated internal method; use {@link #html(String, String, QuietAppendable, Document.OutputSettings)} with {@link org.jsoup.internal.QuietAppendable#wrap(Appendable)} instead. Will be removed in jsoup 1.24.1.
     */
    @Deprecated
    protected void html(Appendable accum, Document.OutputSettings out) throws IOException {
        html(key, val, accum, out);
    }

    /**
     * @deprecated internal method; use {@link #html(String, String, QuietAppendable, Document.OutputSettings)} with {@link org.jsoup.internal.QuietAppendable#wrap(Appendable)} instead. Will be removed in jsoup 1.24.1.
     */
    @Deprecated
    protected static void html(String key, @Nullable String val, Appendable accum, Document.OutputSettings out) throws IOException {
        html(key, val, QuietAppendable.wrap(accum), out);
    }

    static void htmlNoValidate(String key, @Nullable String val, QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static final Pattern xmlKeyReplace = Pattern.compile("[^-a-zA-Z0-9_:.]+");

    private static final Pattern htmlKeyReplace = Pattern.compile("[\\x00-\\x1f\\x7f-\\x9f \"'/=]+");

    /**
     * Get a valid attribute key for the given syntax. If the key is not valid, it will be coerced into a valid key.
     * @param key the original attribute key
     * @param syntax HTML or XML
     * @return the original key if it's valid; a key with invalid characters replaced with "_" otherwise; or null if a valid key could not be created.
     */
    @Nullable
    public static String getValidKey(String key, Syntax syntax) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // perf critical in html() so using manual scan vs regex:
    // note that we aren't using anything in supplemental space, so OK to iter charAt
    private static boolean isValidXmlKey(String key) {
        // =~ [a-zA-Z_:][-a-zA-Z0-9_:.]*
        final int length = key.length();
        if (length == 0)
            return false;
        char c = key.charAt(0);
        if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_' || c == ':'))
            return false;
        for (int i = 1; i < length; i++) {
            c = key.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '-' || c == '_' || c == ':' || c == '.'))
                return false;
        }
        return true;
    }

    private static boolean isValidHtmlKey(String key) {
        // =~ [\x00-\x1f\x7f-\x9f "'/=]+
        final int length = key.length();
        if (length == 0)
            return false;
        for (int i = 0; i < length; i++) {
            char c = key.charAt(i);
            if ((c <= 0x1f) || (c >= 0x7f && c <= 0x9f) || c == ' ' || c == '"' || c == '\'' || c == '/' || c == '=')
                return false;
        }
        return true;
    }

    /**
     *     Get the string representation of this attribute, implemented as {@link #html()}.
     *     @return string
     */
    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a new Attribute from an unencoded key and a HTML attribute encoded value.
     * @param unencodedKey assumes the key is not encoded, as can be only run of simple \w chars.
     * @param encodedValue HTML attribute encoded value
     * @return attribute
     */
    public static Attribute createFromEncoded(String unencodedKey, String encodedValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected boolean isDataAttribute() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    protected static boolean isDataAttribute(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Collapsible if it's a boolean attribute and value is empty or same as name
     *
     * @param out output settings
     * @return  Returns whether collapsible or not
     * @deprecated internal method; use {@link #shouldCollapseAttribute(String, String, Document.OutputSettings)} instead. Will be removed in jsoup 1.24.1.
     */
    @Deprecated
    protected final boolean shouldCollapseAttribute(Document.OutputSettings out) {
        return shouldCollapseAttribute(key, val, out);
    }

    // collapse unknown foo=null, known checked=null, checked="", checked=checked; write out others
    protected static boolean shouldCollapseAttribute(final String key, @Nullable final String val, final Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks if this attribute name is defined as a boolean attribute in HTML5
     */
    public static boolean isBooleanAttribute(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean equals(@Nullable Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Attribute clone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
