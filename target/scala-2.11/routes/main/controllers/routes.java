
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/ian/Development/Projects/Gateway/conf/routes
// @DATE:Sun Mar 06 12:37:14 GMT 2016

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseProxyController ProxyController = new controllers.ReverseProxyController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseProxyController ProxyController = new controllers.javascript.ReverseProxyController(RoutesPrefix.byNamePrefix());
  }

}
