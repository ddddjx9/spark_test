package cn.edu.ustb.functionalProgramming

/**
 * 演示函数闭包
 */
object FunctionClosures {
  def main(args: Array[String]): Unit = {
    def add(a: Int, b: Int): Int = {
      a + b
    }

    //1.考虑固定一个加数的场景：因为大数据处理场景下，很多数字很可能是相同的
    def addByFour(b: Int): Int = {
      4 + b
    }

    //2.扩展固定加数改变的情况
    def addByFive(b: Int): Int = {
      5 + b
    }

    def addBy(a: Int): Int => Int = {
      def addB(b: Int): Int = {
        a + b
      }

      addB
    }

    println(add(1, 2))
    println(addByFour(1))
    println(addByFive(1))
    println(addBy(1)(2))
  }
}
