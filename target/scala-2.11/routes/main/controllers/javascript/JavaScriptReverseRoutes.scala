
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/ian/Development/Projects/Gateway/conf/routes
// @DATE:Sun Mar 06 12:37:14 GMT 2016

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset

// @LINE:16
package controllers.javascript {
  import ReverseRouteContext.empty

  // @LINE:16
  class ReverseProxyController(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:16
    def playlister: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.ProxyController.playlister",
      """
        function(id0) {
        
          if (id0 == """ + implicitly[JavascriptLiteral[String]].to("") + """) {
            return _wA({method:"GET", url:"""" + _prefix + """"})
          }
        
          if (true) {
            return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("id", id0)})
          }
        
        }
      """
    )
  
  }


}
