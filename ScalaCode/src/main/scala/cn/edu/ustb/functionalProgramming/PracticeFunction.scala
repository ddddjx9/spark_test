package cn.edu.ustb.functionalProgramming

/**
 * 练习实现函数的功能
 */
object PracticeFunction {
  def main(args: Array[String]): Unit = {
    //定义一个匿名函数，将它作为值赋值给变量fun，函数有三个参数：
    //  类型分别为Int，String，Char，返回值类型为Boolean
    //  要求调用函数fun(0,"",'0')得到返回值为false，其他情况均返回true
    def fun(x: Int, y: String, z: Char): Boolean = {
      !(x == 0 && y == "" && z == '0')
    }

    //定义一个函数，func，它接收一个Int类型的参数，返回一个函数（记作0）
    //  它返回的函数f1，接收一个String类型的参数，同样返回一个函数（记作f2）。
    //  函数f2接收一个Char类型的参数，返回一个Boolean的值
    //  要求调用fun(0)("")('0')得到返回值为false，其他情况均返回true
    def func1(x: Int): String => Char => Boolean = {
      def f1(y: String): Char => Boolean = {
        def f2(z: Char): Boolean = {
          !(x == 0 && y == "" && z == '0')
        }

        f2
      }

      f1
    }

    def func2(x: Int): String => Char => Boolean = {
      (y: String) => (z: Char) => !(x == 0 && y == "" && z == '0')
    } //函数闭包

    //函数柯里化
    def func3(x: Int)(y: String)(z: Char): Boolean = {
      !(x == 0 && y == "" && z == '0')
    }

    println(fun(0, "", '0'))
    println(func1(0)("")('0'))
    println(func1(0)("111")('0'))
    println(func2(0)("")('0'))
    println(func2(1)("")('0'))
    println(func3(0)("")('0'))
    println(func3(666)("")('5'))
  }
}
