package cn.edu.ustb.basicOperate

import java.io.{File, PrintWriter}
import scala.io.{BufferedSource, Source}

object FileIO {
  def main(args: Array[String]): Unit = {
    //1. 从文件中读取数据
    //函数式编程：Source.fromFile("datas\\data1.txt").foreach(print)
    val source: BufferedSource = Source.fromFile("datas\\data1.txt")
    source.foreach(print)
    source.close()

    //2. 将数据写入文件
    // Scala中没有专门提供写入文件的具体方法和工具
    val writer = new PrintWriter(new File("datas\\test.txt"))
    writer.write("hello scala from java writer")
    writer.close()
  }
}
