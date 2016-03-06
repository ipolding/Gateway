package services

import javax.inject._

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.ec2.AmazonEC2Client
import com.amazonaws.services.ec2.model.DescribeInstancesRequest
import play.api.{Configuration, Logger}
import play.api.mvc._

import scala.concurrent.ExecutionContext

/**
 * This class looks up addresses of services.
 */
@Singleton
class ServiceDiscovery @Inject() (configuration : Configuration) (implicit ec: ExecutionContext) {

  private var services = scala.collection.mutable.Map[String, String]()

  private val accessKey = configuration.getString("key.aws.access").get
  private val secretKey = configuration.getString("key.aws.secret").get

  private val awsCredentials = new BasicAWSCredentials(accessKey, secretKey)
  private val ec2Client = {

    val client = new AmazonEC2Client(awsCredentials)
    client.setRegion(Region.getRegion(Regions.EU_WEST_1))
    client
  }

  def getHost(request : Request[AnyContent]) : String = {
    val host: String = request.host
    Logger.info(s"Trying to resolve host for ${host}")
    val maybeAddress = services get host
    if (maybeAddress.isEmpty) {
      val dns = lookUpDNS(host)
        services += (host -> dns)
        services(host)
    } else {
      maybeAddress.get
    }
  }

  private def lookUpDNS(host : String) : String = {

    import com.amazonaws.services.ec2.model.Filter

    import scala.collection.JavaConversions._

    val request = new DescribeInstancesRequest()
    val purposeFilter = new Filter("tag-value")

    Logger.info(s"Searching for tag-value=$host")
    val instances = ec2Client.describeInstances(
                                  request.withFilters(purposeFilter.withValues(host)))
    val topResult = instances.getReservations.headOption

    if (topResult.isEmpty) {
        Logger.error(s"Couldn't find any reservations for tag-value=$host")
        throw new EC2InstanceNotFound(s"Couldn't find any reservations for tag-value=$host")
    } else {
      topResult.get.getInstances.head.getPublicDnsName
    }

  }

}

class EC2InstanceNotFound(s: String) extends RuntimeException
