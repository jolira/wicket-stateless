package com.google.code.joliratools;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.wicket.PageParameters;
import org.apache.wicket.request.target.coding.WebRequestEncoder;
import org.apache.wicket.util.string.AppendingStringBuffer;

/**
 * Centralize algorithms that are shared.
 * 
 * @author jfk
 * 
 */
final class StatelessEncoder {
    static CharSequence appendParameters(final CharSequence url,
            final PageParameters params) {
        if (params == null) {
            return url;
        }

        final AppendingStringBuffer buf = new AppendingStringBuffer(url);
        final WebRequestEncoder encoder = new WebRequestEncoder(buf);
        final Map<String, String[]> rparams = params.toRequestParameters();
        final Set<Entry<String, String[]>> entrySet = rparams.entrySet();

        for (final Entry<String, String[]> entry : entrySet) {
            final String key = entry.getKey();
            final String[] value = entry.getValue();

            if (value != null) {
                for (final String val : value) {
                    encoder.addValue(key, val);
                }
            }
        }

        return buf;
    }

    private StatelessEncoder() {
        // nothing
    }
}
