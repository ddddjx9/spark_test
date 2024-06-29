package cn.edu.ustb.helloWorld

import scala.io.StdIn

object BranchControlling {
  def main(args: Array[String]): Unit = {
    println("请输入您的年龄：")
    val age: Int = StdIn.readInt()

    val result: Any = if (age < 18) {
      println("未成年")
      "未成年"
    } else if (age < 30) {
      println("你还是一个不成熟的成年人呢（狗头jpg.）")
      age
    } else {
      println("买房了吗买车了吗可以躺平了吗")
      "可怜的中年人"
    }

    val res: String = if (age <= 18) "未成年" else "成年"

    println(result)
    println(res)
  }
}
