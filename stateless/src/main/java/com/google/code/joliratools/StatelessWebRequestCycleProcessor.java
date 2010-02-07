/**
 * 
 */
package com.google.code.joliratools;

import org.apache.wicket.protocol.http.WebRequestCycleProcessor;
import org.apache.wicket.protocol.http.request.WebRequestCodingStrategy;
import org.apache.wicket.request.IRequestCodingStrategy;


/**
 * Use a {@link StatelessWebRequestCodingStrategy} instead of the
 * {@link WebRequestCodingStrategy} to support stateless targets.
 * 
 * @author jfk
 * 
 */
public class StatelessWebRequestCycleProcessor extends WebRequestCycleProcessor {
    /**
     * Create a {@link StatelessWebRequestCodingStrategy}.
     * 
     * @see WebRequestCycleProcessor#newRequestCodingStrategy()
     */
    @Override
    protected IRequestCodingStrategy newRequestCodingStrategy() {
        return new StatelessWebRequestCodingStrategy();
    }

}
