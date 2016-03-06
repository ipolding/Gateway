
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/ian/Development/Projects/Gateway/gateway/conf/routes
// @DATE:Sun Mar 06 01:51:52 GMT 2016

package router

import play.core.routing._
import play.core.routing.HandlerInvokerFactory._
import play.core.j._

import play.api.mvc._

import _root_.controllers.Assets.Asset

class Routes(
  override val errorHandler: play.api.http.HttpErrorHandler, 
  // @LINE:16
  AsyncController_0: controllers.AsyncController,
  val prefix: String
) extends GeneratedRouter {

   @javax.inject.Inject()
   def this(errorHandler: play.api.http.HttpErrorHandler,
    // @LINE:16
    AsyncController_0: controllers.AsyncController
  ) = this(errorHandler, AsyncController_0, "/")

  import ReverseRouteContext.empty

  def withPrefix(prefix: String): Routes = {
    router.RoutesPrefix.setPrefix(prefix)
    new Routes(errorHandler, AsyncController_0, prefix)
  }

  private[this] val defaultPrefix: String = {
    if (this.prefix.endsWith("/")) "" else "/"
  }

  def documentation = List(
    ("""GET""", this.prefix, """controllers.AsyncController.playlister(id:String = "")"""),
    ("""GET""", this.prefix + (if(this.prefix.endsWith("/")) "" else "/") + """""" + "$" + """id<.+>""", """controllers.AsyncController.playlister(id:String)"""),
    Nil
  ).foldLeft(List.empty[(String,String,String)]) { (s,e) => e.asInstanceOf[Any] match {
    case r @ (_,_,_) => s :+ r.asInstanceOf[(String,String,String)]
    case l => s ++ l.asInstanceOf[List[(String,String,String)]]
  }}


  // @LINE:16
  private[this] lazy val controllers_AsyncController_playlister0_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix)))
  )
  private[this] lazy val controllers_AsyncController_playlister0_invoker = createInvoker(
    AsyncController_0.playlister(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AsyncController",
      "playlister",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """"""
    )
  )

  // @LINE:17
  private[this] lazy val controllers_AsyncController_playlister1_route = Route("GET",
    PathPattern(List(StaticPart(this.prefix), StaticPart(this.defaultPrefix), DynamicPart("id", """.+""",false)))
  )
  private[this] lazy val controllers_AsyncController_playlister1_invoker = createInvoker(
    AsyncController_0.playlister(fakeValue[String]),
    HandlerDef(this.getClass.getClassLoader,
      "router",
      "controllers.AsyncController",
      "playlister",
      Seq(classOf[String]),
      "GET",
      """""",
      this.prefix + """""" + "$" + """id<.+>"""
    )
  )


  def routes: PartialFunction[RequestHeader, Handler] = {
  
    // @LINE:16
    case controllers_AsyncController_playlister0_route(params) =>
      call(Param[String]("id", Right(""))) { (id) =>
        controllers_AsyncController_playlister0_invoker.call(AsyncController_0.playlister(id))
      }
  
    // @LINE:17
    case controllers_AsyncController_playlister1_route(params) =>
      call(params.fromPath[String]("id", None)) { (id) =>
        controllers_AsyncController_playlister1_invoker.call(AsyncController_0.playlister(id))
      }
  }
}
