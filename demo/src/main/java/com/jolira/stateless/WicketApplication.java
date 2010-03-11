package com.jolira.stateless;

import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.IRequestCycleProcessor;

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
        mountBookmarkablePage("home", HomePage.class);
    }

    @Override
    protected IRequestCycleProcessor newRequestCycleProcessor() {
      return new StatelessWebRequestCycleProcessor();
    }

}