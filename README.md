wicket-stateless
================

Sometimes it is good to be stateless. As the core of Wicket is focused on managing statefull behavior there is sometimes a lack of support for stateless pages. The goal of this package is to add a few components that provide more comprehensive stateless features for Wicket.

These components currently include a ``StatelessLink`` and a ``StatelessAjaxFallbackLink``. Please let me know if you need more components.

An Example
----------

This example is based on the Wicket Link example. The full source code for this very simple example is available at http://jolira-tools.googlecode.com/svn/wicket-stateless/trunk/demo/src/main/java/com/jolira/stateless/.

WicketApplication.java
----------------------

``
public class WicketApplication extends WebApplication {
    @Override
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

    @Override
    protected IRequestCycleProcessor newRequestCycleProcessor() {
      return new StatelessWebRequestCycleProcessor();
    }
}
``

== ``HomePage.html`` ==

```html
<html
  xmlns:wicket="http://wicket.apache.org/dtds.data/wicket-xhtml1.4-strict.dtd">
<head>
<title>Stateless Wicket</title>
<style type="text/css">
span.wicket-ajax-indicator {
	margin: 0;
	padding: 0;
	padding-left: 2px;
}
</style>
</head>
<body>
counter 2:
<span wicket:id="c2"></span>
<a href="#" wicket:id="c2-link">increment</a>
( statless ajax fallback link. this link uses ajax when it is available,
and a regular wicket roundtrip when ajax or javascript are not available
and the page should be stateless. )
<br />

<form wicket:id="inputForm" method="get">
<label for="a">a:</label>
<input wicket:id="a" id="a" type="text" size="15"/><br/>
<label for="b">b:</label>
<input wicket:id="b" id="b" type="text" size="15"/><br/>
<input type="submit" value="Execute" />
</form>

<select wicket:id="c" />

</body>
</html>
```

== ``HomePage.java`` ==

```java
public class HomePage extends WebPage {
    private static final String COUNTER_PARAM = "counter";

    public HomePage(final PageParameters parameters) {
        final String _counter = getParameter(parameters, COUNTER_PARAM);
        final int counter = _counter != null ? Integer.parseInt(_counter) : 0;
        final Label c2 = new Label("c2", Integer.toString(counter));
        final PageParameters updated = updateParams(counter);
        final Link<?> c2Link = new StatelessAjaxFallbackLink<Void>("c2-link",
                null, updated) {
            private static final long serialVersionUID = 1L;

            @Override
            public void onClick(final AjaxRequestTarget target) {
                if (target != null) {
                    target.addComponent(c2);
                }
            }
        };

        c2.setMarkupId(c2.getId()); // Required to make stateless Ajax work
        c2.setOutputMarkupId(true);
        add(c2Link);
        add(c2);

        final String _a = getParameter(parameters, "a");
        final String _b = getParameter(parameters, "b");
        final Form<String> form = new StatelessForm<String>("inputForm") {
            private static final long serialVersionUID = -1804691502079814185L;

            @Override
            protected void onSubmit() {
                info("clicked sumbit");
            }

        };
        final TextField<String> a = new TextField<String>("a",
                new Model<String>(_a));
        final TextField<String> b = new TextField<String>("b",
                new Model<String>(_b));
        final DropDownChoice<String> c = new DropDownChoice<String>("c",
                new Model<String>("2"), Arrays.asList(new String[] { "1", "2",
                        "3" }));

        c.add(new StatelessAjaxFormComponentUpdatingBehavior("onchange") {
            private static final long serialVersionUID = 3837958099817895568L;

            @Override
            protected PageParameters getPageParameters() {
                return new PageParameters();
            }

            @Override
            protected void onUpdate(final AjaxRequestTarget target) {
                final String value = c.getModelObject();
                System.out.println("xxxxxxxxxxxxxxxxxx: " + value);
                setResponsePage(HomePage.class);
            }
        });
        c.setMarkupId("c");
        form.add(a);
        form.add(b);
        add(form);

        add(c);
    }

    private String getParameter(final PageParameters parameters,
            final String key) {
        final String[] value = (String[]) parameters.get(key);

        if (value == null || value.length < 1) {
            return null;
        }

        return value[0];
    }

    protected final PageParameters updateParams(final int counter) {
        final PageParameters updatedParameters = new PageParameters();

        updatedParameters.put(COUNTER_PARAM, Integer.toString(counter + 1));
        return updatedParameters;
    }
}
```


== Maven ==

This component is available in [http://repo2.maven.org/maven2/com/jolira/ Maven Central]. Simply add the following lines of configuration to our `pom.xml`:

```xml
<dependencies>
  <dependency>
    <groupId>com.jolira</groupId>
    <artifactId>wicket-stateless</artifactId>
    <version>1.0.8</version>
  </dependency>
</dependencies>
```

== Consider Using [http://code.google.com/p/jolira-tools/wiki/guicier gucier] ==

Working with stateless pages creates the need for passing state in different ways. Application such as http://mobile.walmart.com pass this state using [http://www.wicketframework.org/apidocs/wicket/PageParameters.html PageParameters].

In order to simplify your code you may want to consider using [http://code.google.com/p/jolira-tools/wiki/guicier gucier], which allows users to pass [http://www.wicketframework.org/apidocs/wicket/PageParameters.html PageParameters] in a type-safe manner (and additionally supports [http://code.google.com/p/google-guice/wiki/Injections Guice constructor injection]).