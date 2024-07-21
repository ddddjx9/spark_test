package cn.edu.ustb.functionalProgramming

/**
 * 像Spark中的reduce那样，将对数据的操作抽象出来
 */
object CollectionOperation {
  def main(args: Array[String]): Unit = {
    //试验与Spark相关的功能
    val arr: Array[Int] = Array(1, 2, 3, 4, 5)
    println(arr.reduce(Integer.sum))
    arr.map(num => num * 2).foreach(element => println(element))

    println("*****************************")

    //对数组进行处理，将对数组的操作抽象出来
    //  将对数组的操作作为一个函数传递进去
    //  这里传递函数的时候不需要写括号，因为不是调用，传递的是参数所代表的参数名
    def arrayOperation(arr: Array[Int], op: Int => Int): Array[Int] = {
      val result: Array[Int] = for (elem <- arr) yield op(elem)
      result
    }

    arrayOperation(arr, num => num * 2).foreach(elem => println(elem))
  }
}
