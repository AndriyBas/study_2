/**
 * Created by andriybas on 10/18/14.
 */


object Solution {

  def main(args: Array[String]): Unit = {
    import scala.io.ReadStdin._
    val s = readLine()

    println(lcs(s, s.reverse))
  }

  def lcs(a: String, b: String): Int = {
    val x: Array[Array[Int]] = Array.fill(a.length() + 1, b.length() + 1)(0)
    for (
      i <- 0 until a.length;
      j <- 0 until b.length
    ) {
      if (a.charAt(i) == b.charAt(j))
        x(i + 1)(j + 1) = x(i)(j) + 1
      else
        x(i + 1)(j + 1) = x(i + 1)(j).max(x(i)(j + 1))
    }

    x(a.length)(b.length)
  }
}
