package cn.edu.ustb.basicOperate

import scala.io.StdIn

object StdInTest {
  def main(args: Array[String]): Unit = {
    //输入信息
    println("请输入您的姓名：")
    val name: String = StdIn.readLine()
    println("请输入您的年龄：")
    val age: Int = StdIn.readInt()

    //打印信息
    println(s"欢迎登录！您的姓名：$name, 您的年龄：$age")
  }
}
