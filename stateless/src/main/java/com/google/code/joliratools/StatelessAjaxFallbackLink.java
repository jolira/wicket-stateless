package com.google.code.joliratools;

/**
 * 
 */

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.calldecorator.CancelEventIfNoAjaxDecorator;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.ajax.markup.html.IAjaxLink;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

/**
 * Just like {@link AjaxFallbackLink}, but stateless.
 * 
 * @author jfk
 * 
 */
public abstract class StatelessAjaxFallbackLink<T> extends StatelessLink<T>
        implements IAjaxLink {
    private static final long serialVersionUID = -133600842398684777L;

    public StatelessAjaxFallbackLink(final String id) {
        this(id, null, null);
    }

    public StatelessAjaxFallbackLink(final String id, final IModel<T> model) {
        this(id, model, null);
    }

    public StatelessAjaxFallbackLink(final String id, final IModel<T> model,
            final PageParameters params) {
        super(id, model, params);

        add(new StatelessAjaxEventBehavior("onclick") {
            private static final long serialVersionUID = -8445395501430605953L;

            @Override
            protected IAjaxCallDecorator getAjaxCallDecorator() {
                return new CancelEventIfNoAjaxDecorator(
                        StatelessAjaxFallbackLink.this.getAjaxCallDecorator());
            }

            @Override
            protected PageParameters getPageParameters() {
                return StatelessAjaxFallbackLink.this.getPageParameters();
            }

            @Override
            @SuppressWarnings("synthetic-access")
            protected void onComponentTag(final ComponentTag tag) {
                // only render handler if link is enabled
                if (isLinkEnabled()) {
                    super.onComponentTag(tag);
                }
            }

            @Override
            protected void onEvent(final AjaxRequestTarget target) {
                onClick(target);
                target.addComponent(StatelessAjaxFallbackLink.this);
            }
        });
    }

    public StatelessAjaxFallbackLink(final String id,
            final PageParameters params) {
        this(id, null, params);
    }

    /**
     * 
     * @return call decorator to use or null if none
     */
    protected IAjaxCallDecorator getAjaxCallDecorator() {
        return null;
    }

    /**
     * @see Link#onClick()
     */
    @Override
    public final void onClick() {
        onClick(null);
    }

    /**
     * Callback for the onClick event. If ajax failed and this event was
     * generated via a normal link the target argument will be null
     * 
     * @param target
     *            ajax target if this linked was invoked using ajax, null
     *            otherwise
     */
    public abstract void onClick(final AjaxRequestTarget target);
}
