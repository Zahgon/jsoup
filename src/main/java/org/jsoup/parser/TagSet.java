package org.jsoup.parser;

import org.jsoup.helper.Validate;
import org.jsoup.internal.SharedConstants;
import org.jspecify.annotations.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import static org.jsoup.parser.Parser.NamespaceHtml;
import static org.jsoup.parser.Parser.NamespaceMathml;
import static org.jsoup.parser.Parser.NamespaceSvg;

/**
 * A TagSet controls the {@link Tag} configuration for a Document's parse, and its serialization. It contains the initial
 * defaults, and after the parse, any additionally discovered tags.
 *
 * @see Parser#tagSet(TagSet)
 * @since 1.20.1
 */
public class TagSet {

    static final TagSet HtmlTagSet = initHtmlDefault();

    // namespace -> tag name -> Tag
    private final Map<String, Map<String, Tag>> tags = new HashMap<>();

    // internal fallback for lazy tag copies
    @Nullable
    private final TagSet source;

    // optional onNewTag tag customizer
    @Nullable
    private ArrayList<Consumer<Tag>> customizers;

    /**
     *     Returns a mutable copy of the default HTML tag set.
     */
    public static TagSet Html() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private TagSet(@Nullable TagSet source, @Nullable ArrayList<Consumer<Tag>> customizers) {
        this.source = source;
        this.customizers = customizers;
    }

    public TagSet() {
        this(null, null);
    }

    /**
     *     Creates a new TagSet by copying the current tags and customizers from the provided source TagSet. Changes made to
     *     one TagSet will not affect the other.
     *     @param template the TagSet to copy
     */
    public TagSet(TagSet template) {
        this(template.source, copyCustomizers(template));
        // copy tags eagerly; any lazy pull-through should come only from the root source (which would be the HTML defaults), not the template itself.
        // that way the template tagset is not mutated when we do read through
        if (template.tags.isEmpty())
            return;
        for (Map.Entry<String, Map<String, Tag>> namespaceEntry : template.tags.entrySet()) {
            Map<String, Tag> nsTags = new HashMap<>(namespaceEntry.getValue().size());
            for (Map.Entry<String, Tag> tagEntry : namespaceEntry.getValue().entrySet()) {
                nsTags.put(tagEntry.getKey(), tagEntry.getValue().clone());
            }
            tags.put(namespaceEntry.getKey(), nsTags);
        }
    }

    @Nullable
    private static ArrayList<Consumer<Tag>> copyCustomizers(TagSet base) {
        if (base.customizers == null)
            return null;
        return new ArrayList<>(base.customizers);
    }

    /**
     *     Insert a tag into this TagSet. If the tag already exists, it is replaced.
     *     <p>Tags explicitly added like this are considered to be known tags (vs those that are dynamically created via
     *     .valueOf() if not already in the set.</p>
     *
     *     @param tag the tag to add
     *     @return this TagSet
     */
    public TagSet add(Tag tag) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Adds the tag, but does not set defined. Used in .valueOf
     */
    private void doAdd(Tag tag) {
        if (customizers != null) {
            for (Consumer<Tag> customizer : customizers) {
                customizer.accept(tag);
            }
        }
        tag.setParserOptions();
        tags.computeIfAbsent(tag.namespace, ns -> new HashMap<>()).put(tag.tagName, tag);
    }

    /**
     *     Get an existing Tag from this TagSet by tagName and namespace. The tag name is not normalized, to support mixed
     *     instances.
     *
     *     @param tagName the case-sensitive tag name
     *     @param namespace the namespace
     *     @return the tag, or null if not found
     */
    @Nullable
    public Tag get(String tagName, String namespace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Tag.valueOf with the normalName via the token.normalName, to save redundant lower-casing passes.
     *     Provide a null normalName unless we already have one; will be normalized if required from tagName.
     */
    Tag valueOf(String tagName, @Nullable String normalName, String namespace, boolean preserveTagCase) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get a Tag by name from this TagSet. If not previously defined (unknown), returns a new tag.
     *     <p>New tags will be added to this TagSet.</p>
     *
     *     @param tagName Name of tag, e.g. "p".
     *     @param namespace the namespace for the tag.
     *     @param settings used to control tag name sensitivity
     *     @return The tag, either defined or new generic.
     */
    public Tag valueOf(String tagName, String namespace, ParseSettings settings) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Get a Tag by name from this TagSet. If not previously defined (unknown), returns a new tag.
     *     <p>New tags will be added to this TagSet.</p>
     *
     *     @param tagName Name of tag, e.g. "p". <b>Case-sensitive</b>.
     *     @param namespace the namespace for the tag.
     *     @return The tag, either defined or new generic.
     *     @see #valueOf(String tagName, String namespace, ParseSettings settings)
     */
    public Tag valueOf(String tagName, String namespace) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Register a callback to customize each {@link Tag} as it's added to this TagSet.
     *     <p>Customizers are invoked once per Tag, when they are added (explicitly or via the valueOf methods).</p>
     *
     *     <p>For example, to allow all unknown tags to be self-closing during when parsing as HTML:</p>
     *     <pre><code>
     *     Parser parser = Parser.htmlParser();
     *     parser.tagSet().onNewTag(tag -> {
     *     if (!tag.isKnownTag())
     *        tag.set(Tag.SelfClose);
     *     });
     *
     *     Document doc = Jsoup.parse(html, parser);
     *     </code></pre>
     *
     *     @param customizer a {@code Consumer<Tag>} that will be called for each newly added or cloned Tag; callers can
     *     inspect and modify the Tag's state (e.g. set options)
     *     @return this TagSet, to allow method chaining
     *     @since 1.21.0
     */
    public TagSet onNewTag(Consumer<Tag> customizer) {
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

    // Default HTML initialization
    /**
     *     Initialize the default HTML tag set.
     */
    static TagSet initHtmlDefault() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private TagSet setupTags(String namespace, String[] tagNames, Consumer<Tag> tagModifier) {
        for (String tagName : tagNames) {
            Tag tag = get(tagName, namespace);
            if (tag == null) {
                // normal name is already normal here
                tag = new Tag(tagName, tagName, namespace);
                // clear defaults
                tag.options = 0;
                add(tag);
            }
            tagModifier.accept(tag);
        }
        return this;
    }
}
