package cn.edu.ustb.functionalProgramming

/**
 * 利用控制抽象实现自定义while循环功能
 */
object ImitateWhile {
  def main(args: Array[String]): Unit = {
    //有一个循环变量，在while循环外被定义出来的
    var n = 10

    //常规的while循环
    while (n >= 1) {
      println(n)
      n -= 1
    }

    //用闭包实现一个函数
    println("************************************")

    //op: => Unit 的意思是:
    //这是一个函数类型,没有参数,返回 Unit。
    //这是一个"by-name"参数,意味着它会在函数内部被使用时才被求值。
    //在 myWhile 函数中,这个参数表示循环体,也就是需要在每次循环时执行的代码块。
    def myWhile(condition: => Boolean): (=> Unit) => Unit = {
      //内层函数需要递归调用，参数就是循环体内的代码
      def doLoop(op: => Unit): Unit = {
        if (condition) {
          op
          myWhile(condition)(op)
        }
      }

      doLoop _
    }

    n = 10
    myWhile(n >= 1)({
      println(n)
      n -= 1
    })
  }
}
