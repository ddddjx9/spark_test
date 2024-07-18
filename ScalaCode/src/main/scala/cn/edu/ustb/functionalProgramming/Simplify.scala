package cn.edu.ustb.functionalProgramming

import scala.io.StdIn

/**
 * 演示函数至简原则
 */
object Simplify {
  def main(args: Array[String]): Unit = {
    //TODO return可以省略，Scala会使用函数体的最后一行代码作为返回值
    //    如果有return，则不能省略返回值类型，必须指定
    def f0(name: String): String = {
      name
    }

    println(f0("Dong Jiaxin"))

    //TODO 如果函数体只有一行代码，可以省略花括号
    def f1(x: Int, y: Int): Int = x + y

    println(f1(1, 2))

    //TODO 返回值类型如果能够推断出来，那么可以省略
    def f2(a: Double, b: Double) = a - b

    println(f2(1.2, 3.0))

    def f3(): Unit = println("hello scala")

    f3()

    //TODO 如果指定Unit作为返回值，那么就算return xxx，这个return也是不起作用的
    print("请输入您的年龄：")
    val age: Int = StdIn.readInt()
    val result: Unit = if (age < 18) {
      println("未成年")
      "未成年"
    } else if (age < 30) {
      println("你还是一个不成熟的成年人呢（狗头jpg.）")
      age
    } else {
      println("买房了吗买车了吗可以躺平了吗")
      "可怜的中年人"
    }
    println(result) //打印结果：()

    //TODO Scala如果期望是无返回值类型，可以省略等号
    //    如果函数无参，但是声明了参数列表，那么调用时，小括号可加可不加
    def f4() {
      println("hello world")
    }

    f4()
    f4

    //TODO 如果函数没有参数列表，那么小括号可以省略，调用时小括号必须省略
    def f5 {
      println("www")
    }

    f5

    //匿名函数：lambda表达式
    () => {
      println("???这样也行捏？")
    }
  }
}
