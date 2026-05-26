package org.jsoup.nodes;

import org.jsoup.internal.QuietAppendable;
import org.jsoup.internal.StringUtil;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.jspecify.annotations.Nullable;

/**
 * A {@code <!DOCTYPE>} node.
 */
public class DocumentType extends LeafNode {

    // todo needs a bit of a chunky cleanup. this level of detail isn't needed
    public static final String PUBLIC_KEY = "PUBLIC";

    public static final String SYSTEM_KEY = "SYSTEM";

    private static final String NameKey = "name";

    // PUBLIC or SYSTEM
    private static final String PubSysKey = "pubSysKey";

    private static final String PublicId = "publicId";

    private static final String SystemId = "systemId";

    private static final String InternalSubsetKey = Attributes.internalKey("doctypeInternalSubset");

    /**
     * Create a new doctype element.
     * @param name the doctype's name
     * @param publicId the doctype's public ID
     * @param systemId the doctype's system ID
     */
    public DocumentType(String name, String publicId, String systemId) {
        super(name);
        Validate.notNull(publicId);
        Validate.notNull(systemId);
        attributes().add(NameKey, name).add(PublicId, publicId).add(SystemId, systemId);
        updatePubSyskey();
    }

    public void setPubSysKey(@Nullable String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Sets the raw XML internal subset for serialization.
     *     @param value the internal subset contents
     */
    public void setInternalSubset(String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void updatePubSyskey() {
        if (has(PublicId)) {
            attributes().add(PubSysKey, PUBLIC_KEY);
        } else if (has(SystemId))
            attributes().add(PubSysKey, SYSTEM_KEY);
    }

    /**
     * Get this doctype's name (when set, or empty string)
     * @return doctype name
     */
    public String name() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get this doctype's Public ID (when set, or empty string)
     * @return doctype Public ID
     */
    public String publicId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get this doctype's System ID (when set, or empty string)
     * @return doctype System ID
     */
    public String systemId() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String nodeName() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    void outerHtmlHead(QuietAppendable accum, Document.OutputSettings out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean has(final String attribute) {
        return !StringUtil.isBlank(attr(attribute));
    }
}
