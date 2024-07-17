package cn.edu.ustb.basicOperate

import scala.io.StdIn

object OutputIsoscelesTriangle {
  def main(args: Array[String]): Unit = {
    //练习输出等腰三角形
    //从控制台读入想要输出的行数
    //    *
    //   * *
    //  * * *
    // * * * *
    println("请输入您想要打印出的等腰三角形的行数：")
    val n: Int = StdIn.readInt()
    for (i <- 0 until n) {
      //先输出空格
      for (_ <- n - i to 1 by -1) {
        print(" ")
      }
      //再输出星号
      for (_ <- 1 to 2 * i + 1) {
        print("*")
      }
      println()
    }

    println("---------------------------------")
    //在Scala中可以这样书写：
    for {i <- 1 to n
         stars = 2 * i - 1
         spaces = n - i
         } {
      println(" " * spaces + "*" * stars)
    }
  }
}
