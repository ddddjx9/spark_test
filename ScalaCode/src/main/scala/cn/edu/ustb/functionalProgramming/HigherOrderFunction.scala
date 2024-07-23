package cn.edu.ustb.functionalProgramming

/**
 * 高阶函数
 */
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

    //TODO 函数可以作为参数进行传递
    //     将数据和操作全部作为参数传进来
    //     定义一个二元运算函数
    def dualEval(op: (Int, Int) => Int, x: Int, y: Int): Int = {
      op(x, y)
    }

    //Integer.compare 是一个方法引用（或者称为函数值），它可以被当作一个函数传递给 dualEval 方法。
    //当传递 Integer.compare 给 dualEval 时，不需要在方法名后面加上圆括号，因为是在引用方法本身而不是调用它。
    println(dualEval(Integer.compare, 1, 2))

    //TODO 函数可以作为函数返回值返回
    def f2(): Int => Unit = {
      def f3(x: Int): Unit = {
        println(s"f3调用：$x")
      }

      f3
    }

    println(f2()) //返回了一个函数（对象）
    println(f2()(2))
    //f3调用：2
    //()
    //val f4 = f2()
    //println(f4(2))
    //cn.edu.ustb.functionalProgramming.HigherOrderFunction$$$Lambda$8/747464370@5a39699c
  }
}
