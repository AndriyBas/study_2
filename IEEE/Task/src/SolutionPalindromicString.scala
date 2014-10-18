/**
 * Created by andriybas on 10/18/14.
 */


object SolutionPalindromicString {

  def main(args: Array[String]): Unit = {
    import scala.io.ReadStdin._
    val s = readLine()
    if (s.isEmpty) {
      println(0)
    } else if (s.length == 1) {
      println(1)
    } else {
      val chNoBound: Array[Char] = s.toCharArray

      // add boundaries
      val s2: Array[Char] = Array.fill(chNoBound.length * 2 + 1)('|')
      for (i <- 0 until s2.length - 1 by 2) {
//        s2(i) = '|'
        s2(i + 1) = chNoBound(i / 2)
      }
//      s2(s2.length - 1) = '|'

      val p: Array[Int] = Array.fill(s2.length)(0)

      var c: Int = 0
      var r = 0
      var m = 0
      var n = 0

      for (i <- 1 until s2.length) {
        if (i > r) {
          p(i) = 0
          m = i - 1
          n = i + 1
        } else {
          val i2 = c * 2 - i
          if (p(i2) < (r - i)) {
            p(i) = p(i2)
            m = -1
          } else {
            p(i) = r - i
            n = r + 1
            m = i * 2 - n
          }
        }
        while (m >= 0 && n < s2.length && s2(m) == s2(n)) {
          p(i) += 1
          m -= 1
          n += 1
        }
        if ((i + p(i)) > r) {
          c = i
          r = i + p(i)
        }
      }
      var len = 0
      c = 0
      for (i <- 1 until s2.length) {
        if (len < p(i)) {
          len = p(i)
          c = i
        }
      }

      println(len)


//      val cs = java.util.Arrays.copyOfRange(s2, c - len, c + len + 1)
//
//      if (cs == null || cs.length < 3) {
//      } else {
//        println((cs.length - 1) / 2)
//      }

    }
  }
}
