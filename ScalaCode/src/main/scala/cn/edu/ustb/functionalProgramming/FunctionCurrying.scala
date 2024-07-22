package cn.edu.ustb.functionalProgramming

/**
 * 演示函数柯里化
 * <p>
 * 柯里化的底层是闭包
 * </p>
 */
object FunctionCurrying {
  def main(args: Array[String]): Unit = {
    //将一个参数列表的多个参数，转换为多个参数列表的操作
    def addBy(a: Int)(b: Int): Int = {
      a + b
    }

    println(addBy(1)(9))
  }
}
