package org.wicketstuff.stateless

import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import com.google.code.joliratools.StatelessAjaxEventBehavior
import org.apache.wicket.ajax.AjaxRequestTarget

/**
 * Demonstrates usage of StatelessAjaxEventBehavior
 */
class AjaxEventPage(parameters: PageParameters) extends WebPage(parameters) {

  List(1, 2).foreach(id => {
      val label = new Label("l" + id, "text")
      add(label)
      label.setMarkupId(label.getId)

      // clicking on a label updates the model object of the other label
      label.add(new StatelessAjaxEventBehavior("onclick") {

        override def onEvent(target: AjaxRequestTarget) {
          var opposite = 0;
          id match {
            case 1 => opposite = 2;
            case 2 => opposite = 1;
          }

          val l = AjaxEventPage.this.get("l" + opposite)
          l.setDefaultModelObject("text_" + Counter.++)
          target.add(l)
        }

        override def getPageParameters() = {
          AjaxEventPage.this.getPageParameters
        }
      })
    }
  )
}

/**
 * Holds the static counter
 */
object Counter {
  var c1 = 0;

  def ++() = { c1 = c1 + 1; c1 }
}