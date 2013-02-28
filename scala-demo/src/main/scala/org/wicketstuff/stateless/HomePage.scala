package org.wicketstuff.stateless

import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label

class HomePage(parameters: PageParameters)
  extends WebPage(parameters) {

  add(new Label("message", "Hello Scala 2"))

}