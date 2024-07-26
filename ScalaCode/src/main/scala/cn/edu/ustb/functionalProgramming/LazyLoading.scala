package cn.edu.ustb.functionalProgramming

/**
 * 演示惰性加载
 */
object LazyLoading {
  def main(args: Array[String]): Unit = {
    lazy val result: Int = sum(13, 47)

    println("1. 函数被调用了")
    println(s"2. result = $result")
    println(s"4. result = $result")
    //预料输出顺序：312
    //结果：
    //1. 函数被调用了
    //3. sum调用
    //2. result = 60
    //4. result = 60
  }

  def sum(x: Int, y: Int): Int = {
    println("3. sum调用")
    x + y
  }
}
