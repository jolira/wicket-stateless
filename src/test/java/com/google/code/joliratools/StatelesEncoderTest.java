/**
 * 
 */
package com.google.code.joliratools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.apache.wicket.PageParameters;
import org.junit.Test;

/**
 * @author jfk
 * 
 */
public class StatelesEncoderTest {

    /**
     * Test method for
     * {@link StatelessEncoder#appendParameters(CharSequence, PageParameters)}.
     */
    @Test
    public void testAppendnULLParameters() {
        final CharSequence encoded = StatelessEncoder.appendParameters(null,
                null);

        assertNull(encoded);
    }

    /**
     * Test method for
     * {@link StatelessEncoder#appendParameters(CharSequence, PageParameters)}.
     */
    @Test
    public void testAppendParameters() {
        final PageParameters params = new PageParameters();

        params.put("test1", "val1");
        params.put("test2", new String[] { "val2", "val3" });
        params.put("test4", null);

        StatelessEncoder.appendParameters(null, null);
        final StringBuilder buf = new StringBuilder();
        final CharSequence encoded = StatelessEncoder.appendParameters(buf,
                params);

        assertEquals("?test2=val2&test2=val3&test1=val1", encoded.toString());
    }
}
