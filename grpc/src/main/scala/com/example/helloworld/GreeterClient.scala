package com.example.helloworld

//#import
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Failure
import scala.util.Success
import akka.Done
import akka.NotUsed
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.grpc.GrpcClientSettings
import akka.stream.scaladsl.Source

//#import

//#client-request-reply
object GreeterClient {

  def main(args: Array[String]): Unit = {
    implicit val sys: ActorSystem[_] = ActorSystem(Behaviors.empty, "GreeterClient")
    implicit val ec: ExecutionContext = sys.executionContext

    val client = GreeterServiceClient(GrpcClientSettings.fromConfig("helloworld.GreeterService"))

    val names =
      if (args.isEmpty) List("19:00:13.167", "00:00:01.000")
      else args.toList

    singleRequestReply(names(0), names(1))

    def singleRequestReply(time: String, deltaTime: String): Unit = {
      println(s"Performing request: $time , $deltaTime")
      val reply = client.sayHello(HelloRequest(time, deltaTime))
      reply.onComplete {
        case Success(msg) =>
          println(msg)
        case Failure(e) =>
          println(s"Error: $e")
      }
    }

  }

}
//#client-request-reply
