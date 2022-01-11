package com.example.helloworld

//#import
import scala.concurrent.Future

import akka.NotUsed
import akka.actor.typed.ActorSystem
import akka.stream.scaladsl.BroadcastHub
import akka.stream.scaladsl.Keep
import akka.stream.scaladsl.MergeHub
import akka.stream.scaladsl.Sink
import akka.stream.scaladsl.Source

//#import

//#service-request-reply
//#service-stream
class GreeterServiceImpl(system: ActorSystem[_]) extends GreeterService {
  private implicit val sys: ActorSystem[_] = system

  //#service-request-reply
  val (inboundHub: Sink[HelloRequest, NotUsed], outboundHub: Source[HelloReply, NotUsed]) =
    MergeHub.source[HelloRequest]
    .map(request => HelloReply(s"Hello, ${request.time}"))
      .toMat(BroadcastHub.sink[HelloReply])(Keep.both)
      .run()
  //#service-request-reply

  @throws(classOf[java.io.IOException])
  @throws(classOf[java.net.SocketTimeoutException])
  def get(url: String,
          connectTimeout: Int = 5000,
          readTimeout: Int = 5000,
          requestMethod: String = "GET") =
  {
    import java.net.{URL, HttpURLConnection}
    val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)
    val inputStream = connection.getInputStream
    val content = scala.io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close
    content
  }

  override def sayHello(request: HelloRequest): Future[HelloReply] = {
    val time = request.time
    val delta = request.deltaTime
    val result = get("https://jelmxfp5v2.execute-api.us-east-2.amazonaws.com/default/pythonSample?time=%22"+time+"%22&&dt=%22"+delta+"%22")
    val array = result.split("body")
    val array2 = array(1).split("\"")
    val number = array2(3).dropRight(1).toInt
    if(number > -1){
      Future.successful(HelloReply("Time interval found!"))
    } else {
      Future.successful(HelloReply("Time interval not found!"))
    }
  }

  //#service-request-reply
  override def sayHelloToAll(in: Source[HelloRequest, NotUsed]): Source[HelloReply, NotUsed] = {
    in.runWith(inboundHub)
    outboundHub
  }
  //#service-request-reply
}
//#service-stream
//#service-request-reply
