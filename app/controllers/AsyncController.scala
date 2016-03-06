package controllers

import javax.inject._

import play.api.libs.ws.WSClient
import play.api.mvc._
import services.ServiceDiscovery

import scala.concurrent.ExecutionContext

/**
 *
 * @param exec We need an `ExecutionContext` to execute our
 * asynchronous code.
  *
  * @param sd need an `ServiceiDiscovery` to find the URLs of servers ultimately responsible for the request.
  */
@Singleton
class AsyncController @Inject()(sd : ServiceDiscovery, ws : WSClient)(implicit exec: ExecutionContext) extends Controller {

  /**
  * Forwards requests to playlist to the playlister API
  *
  */
  def playlister(id : String) = Action.async {
    request => 

      val url = s"${sd.getHost(request.host)}${request.path}?${request.rawQueryString}"
      val response = ws.url(s"http://$url").get()

      response.map(
        res => {
          val header: Option[String] = res.header("Content-Type")
          Ok(res.body).withHeaders(("Content-Type", header.get))}

      )
  }

}