package services

import javax.inject._

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.ec2.AmazonEC2Client
import com.amazonaws.services.ec2.model.DescribeInstancesRequest
import play.api.{Configuration, Logger}

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

  def getHost(subdomain : String) : String = {
    val maybeAddress = services get subdomain
    Logger.info(s"Searching for $subdomain")
    if (maybeAddress.isEmpty) {
      val dns = lookUpDNS(subdomain)
        services += (subdomain -> dns)
        services(subdomain)
    } else {
      maybeAddress.get
    }
  }

  private def lookUpDNS(subdomain : String) : String = {

    import com.amazonaws.services.ec2.model.Filter

    import scala.collection.JavaConversions._

    val request = new DescribeInstancesRequest()
    val purposeFilter = new Filter("tag-value")

    val instances = ec2Client.describeInstances(
            request.withFilters(
                        purposeFilter.withValues(subdomain)))
    val topResult = instances.getReservations.headOption

    if (topResult.isEmpty) {
        throw new EC2InstanceNotFound(s"Couldn't find any reservations for tag-value:$subdomain")
    } else {
      topResult.get.getInstances.head.getPublicDnsName
    }

  }

}

class EC2InstanceNotFound(s: String) extends RuntimeException
