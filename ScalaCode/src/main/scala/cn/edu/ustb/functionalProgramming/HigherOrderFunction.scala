package cn.edu.ustb.functionalProgramming

object HigherOrderFunction {
  def main(args: Array[String]): Unit = {
    def f1(x: Int): Int = {
      println("f调用")
      x + 1
    }

    val result: Int = f1(123)
    println(result)

    //TODO 函数可以作为值进行传递
    //     类似于重命名
    //     不想让这个值接收返回值，而是接收函数整体：`f1 _` or `f1(_)`
    //     val function = f1 _
    //     前面如果指定function的函数类型 val function: Int => Int，那么就不用写 _或者(_)
    val f = f1 _
    val function: Int => Int = f1
    println(function(1).+(9))
    println(f(111))
  }
}
