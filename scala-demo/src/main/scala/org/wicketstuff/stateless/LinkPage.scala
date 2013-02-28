package org.wicketstuff.stateless

import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.markup.html.WebPage
import com.google.code.joliratools.StatelessLink
import org.apache.wicket.model.Model

/**
 * A page that shows usage of StatelessLink
 */
class LinkPage(parameters: PageParameters)
  extends WebPage(parameters) {

  val link = new StatelessLink("link", Model.of("Empty"), parameters) {

    override def onClick {
      println("clicked!")
    }
  };
  add(link);

}