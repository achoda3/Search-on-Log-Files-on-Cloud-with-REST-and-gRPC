import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.PrivateMethodTester
import org.scalatest.matchers.should.Matchers

class Test1 extends AnyFlatSpec with Matchers with PrivateMethodTester {
  val Client = new ClientHttp
  it should "generate a number" in {
    val result = Client.get("19:00:18.167", "00:00:00.001")
    val array = result.split("body")
    val array2 = array(1).split("\"")
    val number = array2(3).dropRight(1).toInt
    val negOne = -1
    number should be >= negOne
  }
  it should "generate the invalid time Interval number" in {
    val result = Client.get("19:00:18.167", "00:00:00.001")
    val array = result.split("body")
    val array2 = array(1).split("\"")
    val number = array2(3).dropRight(1).toInt
    val negOne = -1
    number should be equals (negOne)
  }
  it should "generate the valid time Interval number" in {
    val result = Client.get("19:00:13.167", "00:00:01.000")
    val array = result.split("body")
    val array2 = array(1).split("\"")
    val number = array2(3).dropRight(1).toInt
    val One = 1
    number should be equals (One)
  }
  it should "generate the invalid time Interval number 2" in {
    val result = Client.get("19:00:25.167", "00:00:04.001")
    val array = result.split("body")
    val array2 = array(1).split("\"")
    val number = array2(3).dropRight(1).toInt
    val negOne = -1
    number should be equals (negOne)
  }
  it should "generate the invalid time Interval number 3" in {
    val result = Client.get("00:00:00.167", "00:99:04.001")
    val array = result.split("body")
    val array2 = array(1).split("\"")
    val number = array2(3).dropRight(1).toInt
    val negOne = -1
    number should be equals (negOne)
  }
  it should "generate the valid time Interval number 2" in {
    val result = Client.get("19:00:20.167", "00:01:01.000")
    val array = result.split("body")
    val array2 = array(1).split("\"")
    val number = array2(3).dropRight(1).toInt
    val One = 1
    number should be equals (One)
  }
}
