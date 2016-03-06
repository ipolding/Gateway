package services

import java.time.{Clock, Instant}
import javax.inject._
import com.amazonaws.regions.Region


import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.ec2.model.{Reservation, DescribeInstancesRequest}
import com.amazonaws.services.ec2.{AmazonEC2AsyncClient, AmazonEC2Client}
import play.api.Logger
import play.api.inject.ApplicationLifecycle

import scala.concurrent.{ExecutionContext, Future, Promise}

/**
 * This class looks up addresses of services.
 */
@Singleton
class ServiceDiscovery @Inject() (implicit ec: ExecutionContext) /*@Inject()*/ {

  private var services = scala.collection.mutable.Map[String, String]()

  private val accessKey = "AKIAJSUMXFR75I4HW7UA"
  private val secretKey = "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"

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
    val topResult = instances.getReservations.head
    topResult.getInstances.head.getPublicDnsName

  }


}
