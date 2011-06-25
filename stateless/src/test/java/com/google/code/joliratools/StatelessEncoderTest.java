/**
 * 
 */
package com.google.code.joliratools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.wicket.request.Url;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Test;

/**
 * @author jfk
 * 
 */
public class StatelessEncoderTest {

    /**
     * Test method for
     * {@link StatelessEncoder#mergeParameters(CharSequence, PageParameters)}.
     */
    @Test
    public void testAppendnULLParameters() {
        final Url encoded = StatelessEncoder.mergeParameters(null,
                null);

        assertNull(encoded);
    }

    /**
     * Test method for
     * {@link StatelessEncoder#mergeParameters(CharSequence, PageParameters)}.
     */
    @Test
    public void testAppendParameters() {
        final PageParameters params = new PageParameters();

        params.add("test1", "val1");
        params.add("test2", new String[] { "val2", "val3" });

        StatelessEncoder.mergeParameters(null, null);
        final Url originalUrl = Url.parse("");
        final Url encoded = StatelessEncoder.mergeParameters(originalUrl, params);

        // XXX compares Map content vs. its toString(). Improve the assertion!
        assertEquals(Url.parse("?test1=val1&test2=val2&test2=val3"), encoded);
    }
    
    @Test
    public void overrideOriginalParameter() {
    	
    	Url originalUrl = Url.parse("./home?0-1.ILinkListener-c2--link&test2=original");

        final PageParameters params = new PageParameters();
        params.add("test1", "value1");
        params.add("test2", new String[] { "val2", "val3" });

    	Url mergedParameters = StatelessEncoder.mergeParameters(originalUrl, params);
    	
    	assertEquals(Url.parse("./home?0-1.ILinkListener-c2--link&test1=value1&test2=val2&test2=val3"), mergedParameters);
    }
}
