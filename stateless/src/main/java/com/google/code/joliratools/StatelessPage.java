/**
 * Copyright (c) 2011 jolira. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the GNU Public
 * License 2.0 which is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

package com.google.code.joliratools;

/**
 * Stateless components need access to Component#onBeforeRender() to re-render
 * Repeaters that may not be available.
 * 
 * @author jfk
 * @date Jan 27, 2011 12:56:41 PM
 * @since 1.0
 * 
 */
public interface StatelessPage {
    /**
     * The public version of Component#onBeforeRender()
     */
    public void onBeforeRender();
}
