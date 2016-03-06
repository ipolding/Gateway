package controllers

import javax.inject._

import play.api.Logger
import play.api.libs.ws.WSClient
import play.api.mvc._
import services.ServiceDiscovery

import scala.concurrent.ExecutionContext

/**
 *
 * @param exec We need an `ExecutionContext` to execute our
 * asynchronous code.
  * @param sd need a `ServiceDiscoverer ` to find the URLs of servers ultimately responsible for the request.
  */
@Singleton
class ProxyController @Inject()(sd : ServiceDiscovery, ws : WSClient)(implicit exec: ExecutionContext) extends Controller {

  /**
  * Forwards requests to playlist to the playlister API
  *
  */
  def playlister(id : String) = Action.async {
    request =>
      Logger.info(s"processing $request")
      val port = if (request.path.contains("playlist")) {":9000"} else {""}

      var url = s"${sd.getHost(request)}${port}${request.path}"

      if (!request.rawQueryString.isEmpty){
        url = s"${sd.getHost(request)}${port}${request.path}?${request.rawQueryString}"
      }

      Logger.info(s"${request.uri} resolved to $url")
      val response = ws.url(s"http://$url").get()

      response.map(
        res => {
          val header: Option[String] = res.header("Content-Type")
          Logger.info(s"url=$url status = ${res.status}")
          Ok(res.body).withHeaders(("Content-Type", header.get))}

      )
  }

}