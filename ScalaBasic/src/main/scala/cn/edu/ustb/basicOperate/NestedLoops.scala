package cn.edu.ustb.basicOperate

/**
 * 演示嵌套循环
 */
object NestedLoops {
  def main(args: Array[String]): Unit = {
    //打印九九乘法表
    for (i <- 1 to 9; j <- 1 to i) {
      print(s"$j*$i = ${i * j} \t")
      if (j == i) {
        //说明输出完毕
        println()
      }
    }

    println("***********************************")
    for (i <- 1 to 9) {
      for (j <- 1 to i) {
        print(s"${j}*${i} = ${i * j} \t")
      }
      println()
    }
  }
}
