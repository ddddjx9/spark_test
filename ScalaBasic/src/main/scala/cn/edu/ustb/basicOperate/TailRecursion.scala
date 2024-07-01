package cn.edu.ustb.basicOperate

import scala.annotation.tailrec

object TailRecursion {
  def main(args: Array[String]): Unit = {
    val result: Long = sum(5050, 0)
    println(result)
  }

  @tailrec
  def sum(n: Long, accumulator: Long): Long = {
    if (n == 1) {
      return 1 + accumulator
    }
    //不用写return
    sum(n - 1, n + accumulator)
  }
}


