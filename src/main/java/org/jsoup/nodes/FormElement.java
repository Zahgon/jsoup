package org.jsoup.nodes;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.HttpConnection;
import org.jsoup.helper.Validate;
import org.jsoup.internal.SharedConstants;
import org.jsoup.internal.StringUtil;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.Selector;
import org.jspecify.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * An HTML Form Element provides ready access to the form fields/controls that are associated with it. It also allows a
 * form to easily be submitted.
 */
public class FormElement extends Element {

    private final Elements linkedEls = new Elements();

    // contains form submittable elements that were linked during the parse (and due to parse rules, may no longer be a child of this form)
    private static final Evaluator submittable = Selector.evaluatorOf(StringUtil.join(SharedConstants.FormSubmitTags, ", "));

    /**
     * Create a new, standalone form element.
     *
     * @param tag        tag of this element
     * @param baseUri    the base URI
     * @param attributes initial attributes
     */
    public FormElement(Tag tag, @Nullable String baseUri, @Nullable Attributes attributes) {
        super(tag, baseUri, attributes);
    }

    /**
     * Get the list of form control elements associated with this form.
     * @return form controls associated with this element.
     */
    public Elements elements() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a form control element to this form.
     * @param element form control to add
     * @return this form element, for chaining
     */
    public FormElement addElement(Element element) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void removeChild(Node out) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     *     Prepare to submit this form. A Connection object is created with the request set up from the form values. This
     *     Connection will inherit the settings and the cookies (etc) of the connection/session used to request this Document
     *     (if any), as available in {@link Document#connection()}
     *     <p>You can then set up other options (like user-agent, timeout, cookies), then execute it.</p>
     *
     *     @return a connection prepared from the values of this form, in the same session as the one used to request it
     *     @throws IllegalArgumentException if the form's absolute action URL cannot be determined. Make sure you pass the
     *     document's base URI when parsing.
     */
    public Connection submit() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get the data that this form submits. The returned list is a copy of the data, and changes to the contents of the
     * list will not be reflected in the DOM.
     * @return a list of key vals
     */
    public List<Connection.KeyVal> formData() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FormElement clone() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
