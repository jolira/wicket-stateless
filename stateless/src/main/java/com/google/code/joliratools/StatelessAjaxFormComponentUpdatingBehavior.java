/**
 * 
 */
package com.google.code.joliratools;

import static com.google.code.joliratools.StatelessEncoder.appendParameters;

import org.apache.wicket.Component;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.behavior.AbstractAjaxBehavior;

/**
 * @author jfk
 * 
 */
public abstract class StatelessAjaxFormComponentUpdatingBehavior extends
        AjaxFormComponentUpdatingBehavior {
    private static final long serialVersionUID = -286307141298283926L;

    /**
     * @param event
     */
    public StatelessAjaxFormComponentUpdatingBehavior(final String event) {
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
    public CharSequence getCallbackUrl(final boolean onlyTargetActivePage) {
        final CharSequence url = super.getCallbackUrl(onlyTargetActivePage);
        final PageParameters params = getPageParameters();

        return appendParameters(url, params);
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
