import sbt._

class StatelessProject(info: ProjectInfo) extends DefaultWebProject(info)
{
  val localRepoR = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"

  val projectsVersion = "1.5-SNAPSHOT"

  val wicketScalaD = "org.wicketstuff.scala" % "wicket-scala" % projectsVersion
  val wicketStatelessD = "org.wicketstuff.stateless" % "stateless" % projectsVersion
  val wicketD = "org.apache.wicket" % "wicket-core" % projectsVersion
  val slf4j_log4jD = "org.slf4j" % "slf4j-log4j12" % "1.6.1"
  val log4jD = "log4j" % "log4j" % "1.2.16"

  val junitD = "junit" % "junit" % "4.8.2" % "test->default"
  val jettyD = "org.eclipse.jetty.aggregate" % "jetty-all-server" % "7.3.0.v20110203" % "test->default"
  val specsD = "org.scala-tools.testing" % "specs_2.8.1" % "1.6.7.2" % "test->default"
  var scalatestD = "org.scalatest" % "scalatest" % "1.3" % "test->default"

  override def mainResources = super.mainResources +++ descendents(mainScalaSourcePath ##, -sourceExtensions)
}
