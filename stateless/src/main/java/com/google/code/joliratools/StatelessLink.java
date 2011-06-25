/**
 * 
 */
package com.google.code.joliratools;

import static com.google.code.joliratools.StatelessEncoder.mergeParameters;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author jfk
 * 
 */
public abstract class StatelessLink<T> extends Link<T> {
	
    private static final long serialVersionUID = 397549666360107292L;
    
    private final PageParameters parameters;

    public StatelessLink(final String id) {
        this(id, null, null);
    }

    public StatelessLink(final String id, final IModel<T> model) {
        this(id, model, null);
    }

    public StatelessLink(final String id, final IModel<T> model, final PageParameters params) {
    	
        super(id, model);
        
        setMarkupId(id);

        this.parameters = params;
    }

    protected final PageParameters getPageParameters() {
        return parameters;
    }

    /**
     * Hints that this component is stateless.
     * 
     * @return always {@literal true}
     * @see Link#getStatelessHint()
     */
    @Override
    protected boolean getStatelessHint() {
        return true;
    }

    /**
     * Adds the parameters to the {@link CharSequence} generated by the
     * superclass.
     * 
     * @see Link#getURL()
     */
    @Override
    protected CharSequence getURL() {
        final Url url = Url.parse(super.getURL().toString());

        Url mergedUrl = mergeParameters(url, parameters);
        return mergedUrl.toString();
    }
}
