package com.jolira.stateless;

import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.devutils.stateless.StatelessChecker;
import org.apache.wicket.protocol.http.WebApplication;

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
        mountPage("home", HomePage.class);
        
        getDebugSettings().setDevelopmentUtilitiesEnabled(true);
        getComponentPreOnBeforeRenderListeners().add(new StatelessChecker());
    }

	@Override
	public RuntimeConfigurationType getConfigurationType() {
		return RuntimeConfigurationType.DEVELOPMENT;
	}
    
    
/*
    @Override
    protected IRequestCycleProcessor newRequestCycleProcessor() {
        return new StatelessWebRequestCycleProcessor();
    }
*/
}