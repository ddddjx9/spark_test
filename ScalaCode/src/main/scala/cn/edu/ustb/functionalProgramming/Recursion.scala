package cn.edu.ustb.functionalProgramming

import scala.annotation.tailrec

/**
 * 尾递归
 */
object Recursion {
  def main(args: Array[String]): Unit = {
    @tailrec
    def factorial(x: Int, acc: Int): Int = {
      if (x == 1) {
        //如果该行代码不是最后一行代码，但是还想要返回，就必须加上return
        return acc
      }
      factorial(x - 1, acc * x)
    }

    println(factorial(4, 1))
  }
}
