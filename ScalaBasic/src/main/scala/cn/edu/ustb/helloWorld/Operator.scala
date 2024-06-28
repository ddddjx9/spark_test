package cn.edu.ustb.helloWorld

/**
 * Object：声明一个单例对象，全局仅有一个的对象（伴生对象）
 * <p>
 * main：从外部可以直接调用执行的方法
 * <p>
 * def：表示声明一个方法，方法（参数名称：参数类型）：返回值
 */
object Operator {
  def main(args: Array[String]): Unit = {
    var num = 2
    num >>= 1
    println(num) //0
    var num1 = 2
    num1 <<= 1
    println(num1) //8
    val `if` = 10
    println(`if`)
  }
}

