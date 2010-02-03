/**
 * 
 */
package com.google.code.joliratools;

import static org.apache.wicket.Component.PATH_SEPARATOR;

import java.util.Map;

import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.IPageFactory;
import org.apache.wicket.IRequestTarget;
import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.RequestListenerInterface;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.protocol.http.request.WebRequestCodingStrategy;
import org.apache.wicket.request.IRequestTargetMounter;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.request.target.coding.IRequestTargetUrlCodingStrategy;
import org.apache.wicket.request.target.component.listener.BehaviorRequestTarget;
import org.apache.wicket.request.target.component.listener.IListenerInterfaceRequestTarget;
import org.apache.wicket.settings.ISessionSettings;
import org.apache.wicket.util.string.Strings;

/**
 * @author jfk
 * 
 */
public class StatelessWebRequestCodingStrategy extends WebRequestCodingStrategy {
    private static final int PARAM_COUNT = 4;
    /**
     * Name of the stateless interface.
     */
    public static final String STATELESS_PARAMETER_NAME = "jolira:stateless";

    private static Component getComponent(final Page page,
            final String componentPath) {
        final String pageRelativeComponentPath = Strings
                .afterFirstPathComponent(componentPath, PATH_SEPARATOR);
        final Component component = page.get(pageRelativeComponentPath);

        // See {@link
        // BookmarkableListenerInterfaceRequestTarget#processEvents(RequestCycle)}
        // We make have to try to look for the component twice, if we hit the
        // same condition.
        if (component == null) {
            throw new WicketRuntimeException(
                    "unable to find component with path "
                            + pageRelativeComponentPath
                            + " on stateless page "
                            + page
                            + " it could be that the component is inside a repeater make your component return false in getStatelessHint()");
        }

        return component;
    }

    private static String getComponentPath(final String[] encoded) {
        final StringBuilder buf = new StringBuilder(16);

        for (int idx = 3; idx < encoded.length; idx++) {
            final int current = buf.length();

            if (current > 0) {
                buf.append(PATH_SEPARATOR);
            }

            buf.append(encoded[idx]);
        }

        return buf.toString();
    }

    private static String[] getEncodedParams(final Map<String, ?> _params) {
        final String[] encoded = (String[]) _params
                .get(STATELESS_PARAMETER_NAME);

        if (encoded == null) {
            throw new WicketRuntimeException("No value found for "
                    + STATELESS_PARAMETER_NAME);
        }

        final String[] split = encoded[0].split(Character
                .toString(PATH_SEPARATOR));

        if (split.length < PARAM_COUNT) {
            throw new WicketRuntimeException("Invalid value found for "
                    + STATELESS_PARAMETER_NAME);
        }
        return split;
    }

    private static RequestListenerInterface getInterface(
            final String interfaceName) {
        final RequestListenerInterface listenerInterface = RequestListenerInterface
                .forName(interfaceName);

        if (listenerInterface == null) {
            throw new WicketRuntimeException(
                    "unable to find listener interface " + interfaceName);
        }
        return listenerInterface;
    }

    private static Page getPage(final String pageName,
            final RequestCycle cycle, final PageParameters params) {
        final Session session = cycle.getSession();
        final Class<? extends Page> pageClass = getPageClass(session, pageName);

        return newPage(pageClass, cycle, params);
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends Page> getPageClass(final Session session,
            final String clsName) {
        try {
            return (Class<? extends Page>) session.getClassResolver()
                    .resolveClass(clsName);
        } catch (final ClassNotFoundException e) {
            throw new WicketRuntimeException(
                    "Unable to load Bookmarkable Page", e);
        }
    }

    private static <C extends Page> Page newPage(final Class<C> pageClass,
            final RequestCycle cycle, final PageParameters pageParameters) {
        final Application app = Application.get();
        final ISessionSettings settings = app.getSessionSettings();
        final IPageFactory pageFactory = settings.getPageFactory();

        final Map<String, String[]> requestMap = cycle.getRequest()
                .getParameterMap();
        final Map<String, String[]> reqParams = pageParameters
                .toRequestParameters();

        requestMap.putAll(reqParams);

        return pageFactory.newPage(pageClass, pageParameters);
    }

    protected IRequestTarget decode(final RequestParameters requestParameters) {
        final Map<String, ?> _params = requestParameters.getParameters();
        final String[] encoded = getEncodedParams(_params);
        final RequestCycle cycle = RequestCycle.get();
        final PageParameters params = new PageParameters(_params);
        final Page page = getPage(encoded[0], cycle, params);
        final String componentPath = getComponentPath(encoded);
        final Component component = getComponent(page,
                componentPath);
        final RequestListenerInterface listenerInterface = getInterface(encoded[2]);

        requestParameters.setBehaviorId(encoded[1]);

        return new BehaviorRequestTarget(page, component, listenerInterface,
                requestParameters);
    }

    /**
     * Encode stateless targets and delegate the rest of the calls.
     * 
     * @param requestCycle
     *            the current request cycle
     * @param requestTarget
     *            the target to encode
     * @return the encoded url
     */
    @Override
    protected CharSequence encode(final RequestCycle requestCycle,
            final IListenerInterfaceRequestTarget requestTarget) {
        final Component component = requestTarget.getTarget();
        final boolean stateless = component.isStateless();

        if (!stateless) {
            return super.encode(requestCycle, requestTarget);
        }

        // Start string buffer for url
        final StringBuilder url = new StringBuilder(64);

        url.append("./");
        url.append(STATELESS_PARAMETER_NAME);
        url.append("?");
        url.append(STATELESS_PARAMETER_NAME);
        url.append('=');

        // Get component and page for request target
        final Page page = component.getPage();
        final Class<? extends Page> pageClass = page.getClass();
        final String pageClassName = pageClass.getName();

        // Add the name of the page class
        url.append(pageClassName);
        url.append(PATH_SEPARATOR);

        // Add behaviourId
        final RequestParameters params = requestTarget.getRequestParameters();

        if (params != null && params.getBehaviorId() != null) {
            url.append(params.getBehaviorId());
        }

        url.append(Component.PATH_SEPARATOR);

        // Add listener interface
        final RequestListenerInterface rli = requestTarget
                .getRequestListenerInterface();
        final String listenerName = rli.getName();

        url.append(listenerName);
        url.append(Component.PATH_SEPARATOR);

        // component id
        final String componentPath = component.getPath();

        url.append(componentPath);
        url.append(PATH_SEPARATOR);

        return url;
    }

    /**
     * @see IRequestTargetMounter#urlCodingStrategyForPath(java.lang.String)
     */
    @Override
    public IRequestTargetUrlCodingStrategy urlCodingStrategyForPath(
            final String path) {
        if (path != null && path.endsWith(STATELESS_PARAMETER_NAME)) {
            return new IRequestTargetUrlCodingStrategy() {
                public IRequestTarget decode(
                        final RequestParameters requestParameters) {
                    return StatelessWebRequestCodingStrategy.this
                            .decode(requestParameters);
                }

                public CharSequence encode(final IRequestTarget requestTarget) {
                    throw new UnsupportedOperationException();
                }

                public String getMountPath() {
                    throw new UnsupportedOperationException();
                }

                public boolean matches(final IRequestTarget requestTarget) {
                    throw new UnsupportedOperationException();
                }

                public boolean matches(
                        @SuppressWarnings("hiding") final String path,
                        final boolean caseSensitive) {
                    throw new UnsupportedOperationException();
                }
            };
        }

        return super.urlCodingStrategyForPath(path);
    }
}
