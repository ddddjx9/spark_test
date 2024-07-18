package cn.edu.ustb.functionalProgramming

object AnonymousFunction {
  def main(args: Array[String]): Unit = {
    //TODO 匿名函数，将这个匿名函数赋值给一个变量
    //    需要用的时候调用这个变量即可
    //    在这段Scala代码中，val fun: String => Unit = 是一个变量声明语句
    //    它的意思是定义了一个名为fun的不可变（val）变量，其类型为String => Unit。
    //    String => Unit 表示这个变量fun是一个函数，接受一个String类型的参数，返回Unit类型。
    val fun: String => Unit = (name: String) => {
      println(name)
    }
    fun("djx")

    //TODO 定义一个函数，以函数作为参数输入
    //     装饰器设计模式，套娃
    def func(fun: String => Unit, str: String): Unit = {
      fun(str)
    }

    func(fun, "flink")
    func((name: String) => {
      println(name)
    }, "未闻花名")

    //TODO 匿名函数简化原则：
    //    之前定义func的时候已经规定了传入函数的形参类型，所以参数类型可以省略，会根据形参进行自动推导
    //    类型省略之后，发现只有 一个 参数，则圆括号可以省略；
    //      其他情况下，没有参数 或者 参数超过1 的永远不能省略圆括号
    func(name => {
      println(name)
    }, "fiction blue")

    //    匿名函数如果只有一行，那么大括号也可以省略
    func(name => println(name), "Scala确实很适合数据开发啊")

    //    如果参数只出现一次，则参数省略且后面参数可以用_代替
    func(println(_), "我想开心地活着")

    //    直接传入println这个操作，像之前的方法引用System.out::println
    func(println, "wwwww")

    //示例：定义一个二元运算的函数，具体运算通过参数传入
    def dualFunctionOneAndTwo(fun: (Int, Int) => Int): Int = {
      fun(1, 2)
    }

    //传入对应的计算逻辑
    //实际上 + 运算符被定义在Scala的标准库中的 Int 类型中。
    // Scala中的基本运算符（比如 +、-、* 等）都被定义为方法，可以像普通方法一样被调用。
    println(dualFunctionOneAndTwo((x, y) => x + y))
    //如果两个减数之间有顺序要求，就不能_ - _
    println(dualFunctionOneAndTwo(_ - _))
    println(dualFunctionOneAndTwo(-_ + _))
    println(dualFunctionOneAndTwo((a, b) => a - b))
    //println(6 + (4))
  }
}
