package cn.edu.ustb.functionalProgramming

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
