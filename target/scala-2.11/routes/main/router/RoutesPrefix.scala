
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/ian/Development/Projects/Gateway/gateway/conf/routes
// @DATE:Sun Mar 06 01:51:52 GMT 2016


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
