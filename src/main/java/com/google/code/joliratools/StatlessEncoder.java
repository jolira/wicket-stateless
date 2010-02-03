package com.google.code.joliratools;

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
final class StatlessEncoder {
    static CharSequence appendParameters(final CharSequence url,
            final PageParameters params) {
        if (params == null) {
            return url;
        }

        final AppendingStringBuffer buf = new AppendingStringBuffer(url);
        final WebRequestEncoder encoder = new WebRequestEncoder(buf);

        for (final Entry<String, Object> stringObjectEntry : params.entrySet()) {
            final String key = stringObjectEntry.getKey();
            final String value = stringObjectEntry.getValue().toString();

            encoder.addValue(key, value);
        }

        return buf;
    }

    private StatlessEncoder() {
        // nothing
    }
}
