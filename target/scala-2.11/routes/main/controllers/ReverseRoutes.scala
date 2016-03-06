
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/ian/Development/Projects/Gateway/conf/routes
// @DATE:Sun Mar 06 11:39:56 GMT 2016

import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset

// @LINE:16
package controllers {

  // @LINE:16
  class ReverseAsyncController(_prefix: => String) {
    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:16
    def playlister(id:String): Call = {
    
      (id: @unchecked) match {
      
        // @LINE:16
        case (id) if id == "" =>
          implicit val _rrc = new ReverseRouteContext(Map(("id", "")))
          Call("GET", _prefix)
      
        // @LINE:17
        case (id)  =>
          import ReverseRouteContext.empty
          Call("GET", _prefix + { _defaultPrefix } + implicitly[PathBindable[String]].unbind("id", id))
      
      }
    
    }
  
  }


}
