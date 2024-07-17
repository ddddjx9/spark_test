package cn.edu.ustb.processControl

object ForLoop {
  def main(args: Array[String]): Unit = {
    //to函数中进行范围遍历的时候是包含结束位置的边界条件的
    for (i <- 1 to 10) {
      println(i + ". Hello World")
    }

    //可以不用new，因为Range有一个伴生对象，new出来的是不包含结束位置的
    for (i <- Range(1, 10)) {
      println(s"这是第${i}天")
    }

    for (i <- 1 until 10) {
      println(s"我希望我有一个好结果，有一个好工作，这是第${i}遍的祈祷")
    }

    //集合遍历
    for (i <- Array(1, 3, 5)) {
      println(i)
    }

    //循环守卫
    for (i <- 1 to 10) {
      if (i != 5) {
        println(i)
      }
    }

    for (i <- 1 to 10 if i != 5) {
      println(i)
    }
  }
}
