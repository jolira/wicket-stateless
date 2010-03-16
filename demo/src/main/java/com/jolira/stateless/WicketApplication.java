package com.jolira.stateless;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestCycleProcessor;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;

import com.google.code.joliratools.StatelessWebRequestCycleProcessor;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see com.jolira.stateless.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected void init() {
        // See https://issues.apache.org/jira/browse/WICKET-2774
        // mountBookmarkablePage("home", HomePage.class);
        mount(new QueryStringUrlCodingStrategy("home", HomePage.class));
    }

    @Override
    protected IRequestCycleProcessor newRequestCycleProcessor() {
        return new StatelessWebRequestCycleProcessor();
    }

}