package cn.edu.ustb.functionalProgramming

/**
 * 实现定义函数的功能
 */
object DefinedFunction {
  def main(args: Array[String]): Unit = {
    //无参无返回值
    def function(): Unit = {
      println("Hello World")
    }

    function()

    def returnFixedValue(): Int = {
      12
    }

    val value: Int = returnFixedValue()
    println(value)
  }
}
