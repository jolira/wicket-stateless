package org.wicketstuff.stateless

import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.RuntimeConfigurationType

class StatelessApplication extends WebApplication {

  override def getHomePage = classOf[HomePage]

  override def init : Unit = {
    super.init

    mountPage("link", classOf[LinkPage])
    mountPage("event", classOf[AjaxEventPage])
  }

  override def getConfigurationType = RuntimeConfigurationType.DEVELOPMENT
}