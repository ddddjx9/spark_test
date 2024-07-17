package cn.edu.ustb.processControl

import scala.util.control.Breaks

object LoopInterrupt {
  def main(args: Array[String]): Unit = {
    //循环守卫
    //如果想要跳过某个操作，可以使用循环守卫进行过滤
    for (i <- 1 to 10 if i != 6) {
      println(i)
    }

    //想先做一些操作再continue，可以使用条件判断实现
    var sum = 0
    for (i <- 1 to 10) {
      if (i != 5 && i != 4) {
        sum = sum + i
      }
    }
    println(sum)

    //如果想要退出整体循环，那么：
    //一般来说，抛出异常的话，当前程序一定会中断
    //  =>采用抛出异常的方式中断循环
    //  =>为了避免程序向下的正常执行，对异常进行捕捉
    try {
      for (i <- 0 until 5) {
        if (i == 3) {
          throw new RuntimeException("退出循环")
        }
        println(i)
      }
    } catch {
      //模式匹配
      case e: RuntimeException => //什么都不做，只是退出循环
        e.printStackTrace()
    }
    println("成功退出循环了")

    //为了简化上述代码，可以只用Scala中的Breaks类的break方法实现异常的抛出和捕捉
    //标记一下，当前循环是可以中断的
    //import scala.util.control.Breaks._ 类似于导入这个类中的所有内容，就不写类了
    Breaks.breakable(
      for (i <- 0 until 5) {
        if (i == 3) {
          //调用被包装的类里面的break方法中断循环
          Breaks.break()
        }
        println(i)
      }
    )
  }
}
