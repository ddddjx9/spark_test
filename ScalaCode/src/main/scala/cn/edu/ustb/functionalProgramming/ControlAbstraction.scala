package cn.edu.ustb.functionalProgramming

/**
 * 演示控制抽象
 */
object ControlAbstraction {
  def main(args: Array[String]): Unit = {
    //TODO 常规的传值参数
    def f0(a: Int): Unit = {
      println(s"这是一个值：$a")
    }

    f0(45)

    def f1(): Int = {
      println("f1调用")
      89
    }

    f0(f1())

    //TODO 演示传名参数
    def f2(fun: => Int): Unit = {
      println(s"fun的值为：$fun")
      println(s"fun的值为：$fun")
    }

    f2(45)
    f2(f1()) //将f1这个代码块完整地传递进去
    //执行结果：- 控制抽象 - 其实在内部调用几次就输出几次
    //f1调用
    //fun的值为：89
    //f1调用
    //fun的值为：89
  }
}
