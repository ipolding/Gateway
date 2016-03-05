package services

import java.time.{Clock, Instant}
import javax.inject._
import com.amazonaws.regions.Region

import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.ec2.model.DescribeInstancesRequest
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

  //
//  Secret Access Key:
//    QHpi67R1zjJnnBO2rf3OFJhKGAL+aD/1s+vnBZfh

  private val accessKey = "AKIAJSUMXFR75I4HW7UA"
  private val secretKey = "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"



  private val awsCredentials = new BasicAWSCredentials(accessKey, secretKey)
  private val ec2Client = {

    val client = new AmazonEC2Client(awsCredentials)
    client.setRegion(Region.getRegion(Regions.EU_WEST_1))
    client
  }



  def service(url : String) : Future[String] = {
    val maybeAddress = services get url

    if (maybeAddress.isEmpty) {
      lookUpUrl(url)
        .map{
          address =>
            services += (url -> address)
            services(url)
        }
    } else {
      Future(maybeAddress.get)
    }
  }

  private def lookUpUrl(url : String) : Future[String] = {
    val req = new DescribeInstancesRequest()
                .withInstanceIds("i-25ef0ea9")
    val instances = ec2Client.describeInstances(req)
    print(instances)
    Future("url")
  }


}
