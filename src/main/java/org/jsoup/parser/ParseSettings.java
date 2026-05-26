package org.jsoup.parser;

import org.jsoup.nodes.Attributes;
import org.jspecify.annotations.Nullable;
import static org.jsoup.internal.Normalizer.lowerCase;
import static org.jsoup.internal.Normalizer.normalize;

/**
 * Controls parser case settings, to optionally preserve tag and/or attribute name case.
 */
public class ParseSettings {

    /**
     * HTML default settings: both tag and attribute names are lower-cased during parsing.
     */
    public static final ParseSettings htmlDefault;

    /**
     * Preserve both tag and attribute case.
     */
    public static final ParseSettings preserveCase;

    static {
        htmlDefault = new ParseSettings(false, false);
        preserveCase = new ParseSettings(true, true);
    }

    private final boolean preserveTagCase;

    private final boolean preserveAttributeCase;

    /**
     * Returns true if preserving tag name case.
     */
    public boolean preserveTagCase() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns true if preserving attribute case.
     */
    public boolean preserveAttributeCase() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Define parse settings.
     * @param tag preserve tag case?
     * @param attribute preserve attribute name case?
     */
    public ParseSettings(boolean tag, boolean attribute) {
        preserveTagCase = tag;
        preserveAttributeCase = attribute;
    }

    ParseSettings(ParseSettings copy) {
        this(copy.preserveTagCase, copy.preserveAttributeCase);
    }

    /**
     * Normalizes a tag name according to the case preservation setting.
     */
    public String normalizeTag(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Normalizes an attribute according to the case preservation setting.
     */
    public String normalizeAttribute(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void normalizeAttributes(Attributes attributes) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the normal name that a Tag will have (trimmed and lower-cased)
     */
    static String normalName(String name) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
