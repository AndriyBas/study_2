/**
 * Created by andriybas on 10/18/14.
 */


object SolutionFirstPath {

  def main(args: Array[String]): Unit = {
    import scala.io.ReadStdin._

    val n: Int = readInt()

    val a: Array[Array[Int]] = Array.fill(n + 1, n + 1)(0)

    for {
      i <- 1 to n
      line = readLine()
    } a(i) = (0 +: line.split(' ').map(_.toInt)).toArray

    val sum = Array.fill(n + 1, n + 1)(0)

    //    for (i <- 1 to n)
    //      sum(0)(i) = a(0)(i)

    for {
      i <- 1 to n
      j <- 0 to n
    } sum(i)(j) = sum(i - 1)(j) + a(i)(j)

    val res = Array.fill(n + 1, n + 1)(0)

    for(i <- 1 to n) {
      res(i)(1) = a(i)(1)
    }

    for (j <- 2 to n; i <- 1 to n) {
      var minCurrent = Int.MaxValue
      for (z <- 1 to n) {
        val d = Math.abs(sum(z)(j) - sum(i)(j)) + a(Math.min(i, z))(j)
        val current = res(z)(j - 1) + d
        if (minCurrent > current)
          minCurrent = current
      }
      res(i)(j) = minCurrent
    }

    var ans = res(1)(n)
    for(i <- 2 to n)
      if(res(i)(n) < ans)
        ans = res(i)(n)

    println(ans)
  }

}
