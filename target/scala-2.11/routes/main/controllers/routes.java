
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/ian/Development/Projects/Gateway/gateway/conf/routes
// @DATE:Sun Mar 06 01:51:52 GMT 2016

package controllers;

import router.RoutesPrefix;

public class routes {
  
  public static final controllers.ReverseAsyncController AsyncController = new controllers.ReverseAsyncController(RoutesPrefix.byNamePrefix());

  public static class javascript {
    
    public static final controllers.javascript.ReverseAsyncController AsyncController = new controllers.javascript.ReverseAsyncController(RoutesPrefix.byNamePrefix());
  }

}
