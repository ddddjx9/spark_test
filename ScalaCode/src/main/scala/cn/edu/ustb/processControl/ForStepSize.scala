package cn.edu.ustb.processControl

/**
 * 演示循环步长的用法
 */
object ForStepSize {
  def main(args: Array[String]): Unit = {
    //循环步长
    //默认循环步长为1
    for (i <- 1 to 10) {
      println(i)
    }

    //to和by底层都是调用Range方法里面的内容
    println("********************")
    for (i: Int <- 2.to(10, 2)) {
      println(i)
    }

    println("********************")
    for (i <- 1 to 10 by 2) {
      println(i)
    }

    println("********************")
    for (i <- Range(1, 10, 3)) {
      println(i)
    }

    //演示循环步长为负的情况
    println("********************")
    for (i <- 13 to 2 by -5) {
      println(i)
    }

    println("********************")
    for (i <- 2 to 12 reverse) {
      println(i)
    }

    //循环步长不能为0！

    //演示循环步长为小数的情况，这种情况下，起始和终止都得为小数
    for (data <- 1.0 to 6.0 by 0.5) {
      println(data)
    }
  }
}
