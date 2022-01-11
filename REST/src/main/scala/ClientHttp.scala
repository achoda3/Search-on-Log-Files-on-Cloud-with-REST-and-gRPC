import scala.io.StdIn.readLine
class ClientHttp {
  @throws(classOf[java.io.IOException])
  @throws(classOf[java.net.SocketTimeoutException])
  def get(time: String,
          deltaTime: String,
          connectTimeout: Int = 5000,
          readTimeout: Int = 5000,
          requestMethod: String = "GET") =
  {
    import java.net.{URL, HttpURLConnection}
    val connection = (new URL("https://jelmxfp5v2.execute-api.us-east-2.amazonaws.com/default/pythonSample?time=%22"+time+"%22&&dt=%22"+deltaTime+"%22")).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)
    val inputStream = connection.getInputStream
    val content = io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close
    content
  }
  def main(args: Array[String]): Unit = {
    //print("Enter Time")
    val time = args(0)
    val delta = args(1)
    val result = get(time, delta)
    val array = result.split("body")
    val array2 = array(1).split("\"")
    val number = array2(3).dropRight(1).toInt
    if(number > -1){
      print("Time interval found!")
    } else {
      println("Time interval not found!")
    }

  }
}