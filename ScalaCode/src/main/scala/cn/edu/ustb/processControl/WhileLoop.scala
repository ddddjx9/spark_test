package cn.edu.ustb.processControl

object WhileLoop {
  def main(args: Array[String]): Unit = {
    //while循环
    var a: Int = 10
    while (a < 20) {
      println(a)
      a += 1
    }

    //do-while循环
    var b: Int = 10
    do {
      println(s"我想回家$b")
      b += 1
    } while (b < 20)
  }
}
