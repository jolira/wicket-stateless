/**
 * 
 */
package com.google.code.joliratools;

import static com.google.code.joliratools.StatelessEncoder.mergeParameters;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;

abstract class StatelessAjaxEventBehavior extends AjaxEventBehavior {
	
    private static final long serialVersionUID = 2387070289758596955L;

    StatelessAjaxEventBehavior(final String event) {
        super(event);
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
    public CharSequence getCallbackUrl() {
        final Url url = Url.parse(super.getCallbackUrl().toString());
        final PageParameters params = getPageParameters();

        return mergeParameters(url, params).toString();
    }

    protected abstract PageParameters getPageParameters();

    /**
     * @return always {@literal true}
     */
    @Override
    public boolean getStatelessHint(final Component component) {
        return true;
    }
}