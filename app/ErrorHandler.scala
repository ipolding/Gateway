import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._
import scala.concurrent._
import services.EC2InstanceNotFound

class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful(
      Status(statusCode)("A client error occurred: " + message)
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    val result = if (exception.isInstanceOf[EC2InstanceNotFound]) {
      NotFound("Sorry! We can't find what you're looking for :(")
    } else {
      InternalServerError("A server error occurred: " + exception.getMessage)
    }

    Future.successful(
      result
    )
  }
}