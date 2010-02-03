/**
 * 
 */
package com.google.code.joliratools;

import java.util.Map.Entry;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.target.coding.WebRequestEncoder;
import org.apache.wicket.util.string.AppendingStringBuffer;

abstract class StatelessAjaxEventBehavior extends AjaxEventBehavior {
    private static final long serialVersionUID = 2387070289758596955L;
    private final PageParameters params;

    StatelessAjaxEventBehavior(final String event, final PageParameters params) {
        super(event);

        this.params = params;
    }

    /**
     * Adding parameters the generated URL. This preferably would be handled by
     * the {@link StatelessWebRequestCodingStrategy}, but the frameworks
     * currently is lacking a way to pass the parameters to that class.
     * 
     * 
     * @see AbstractAjaxBehavior#getCallbackUrl(boolean)
     */
    @Override
    public CharSequence getCallbackUrl(final boolean onlyTargetActivePage) {
        final CharSequence url = super.getCallbackUrl(onlyTargetActivePage);

        if (params == null) {
            return url;
        }

        final AppendingStringBuffer buf = new AppendingStringBuffer(url);
        final WebRequestEncoder encoder = new WebRequestEncoder(buf);

        for (final Entry<String, Object> stringObjectEntry : params.entrySet()) {
            final String key = stringObjectEntry.getKey();
            final String value = stringObjectEntry.getValue().toString();

            encoder.addValue(key, value);
        }

        return buf;
    }

    @Override
    public boolean getStatelessHint(final Component component) {
        return true;
    }
}