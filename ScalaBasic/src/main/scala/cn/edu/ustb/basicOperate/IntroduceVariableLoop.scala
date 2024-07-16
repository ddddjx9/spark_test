package cn.edu.ustb.basicOperate

object IntroduceVariableLoop {
  def main(args: Array[String]): Unit = {
    //循环引入变量
    //根据i的值决定j的值，这样使两层循环之间有关联
    //只有单一的决定关系，没有控制范围，这样的话就相当于在循环外部定义了一个变量
    for (i <- 1 to 10; j = 4 - i) {
      println(s"i= $i, j= $j")
    }

    //如果在Java中定义的话：
    for (i <- 1 to 10) {
      val j: Int = 10 - i
      println(s"i= $i, j= $j")
    }

    //如果有多行代码，可以这样书写
    for {
      i <- 1 to 10
      j = 10 - i} {
      println(s"i= $i, j= $j")
    }
  }
}
