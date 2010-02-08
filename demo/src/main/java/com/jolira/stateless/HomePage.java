package com.jolira.stateless;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

import com.google.code.joliratools.StatelessAjaxFallbackLink;

/**
 * For testing only
 */
public class HomePage extends WebPage {
    private static final String COUNTER_PARAM = "counter";

    /**
     * Constructor that is invoked when page is invoked without a session.
     * 
     * @param parameters
     *            Page parameters
     */
    public HomePage(final PageParameters parameters) {
        final String _counter = getParameter(parameters, COUNTER_PARAM);
        final int counter = _counter != null ? Integer.parseInt(_counter) : 0;
        final Label c2 = new Label("c2", Integer.toString(counter));
        final PageParameters updated = updateParams(counter);
        final Link<?> c2Link = new StatelessAjaxFallbackLink<Void>("c2-link",
                null, updated) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(final AjaxRequestTarget target) {
                if (target != null) {
                    setPageParameters(updated);
                    target.addComponent(c2);
                }
            }
        };

        c2.setMarkupId("c2"); // Required to make stateless Ajax work
        c2.setOutputMarkupId(true);
        add(c2Link);
        add(c2);

        final String _a = getParameter(parameters, "a");
        final String _b = getParameter(parameters, "b");
        final Form<String> form = new StatelessForm<String>("inputForm");
        final TextField<String> a = new TextField<String>("a",
                new Model<String>(_a));
        final TextField<String> b = new TextField<String>("b",
                new Model<String>(_b));

        form.add(a);
        form.add(b);
        add(form);
    }

    private String getParameter(final PageParameters parameters,
            final String key) {
        final String[] value = (String[]) parameters.get(key);

        if (value == null || value.length < 1) {
            return null;
        }

        return value[0];
    }

    protected final PageParameters updateParams(final int counter) {
        final PageParameters updatedParameters = new PageParameters();

        updatedParameters.put(COUNTER_PARAM, Integer.toString(counter + 1));
        return updatedParameters;
    }
}
