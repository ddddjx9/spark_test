package cn.edu.ustb.basicOperate

/**
 * 选择排序
 */
object SelectionSort {
  def main(args: Array[String]): Unit = {
    //这是一个选择排序的算法
    val arr: Array[Int] = Array(9, 3, 7, 6, 4, 8, 0, 5, 2, 1, 7)
    //创建一个数组后给数组赋值
    for (i <- 0 to arr.length - 2) {
      for (j <- i + 1 until arr.length) {
        if (arr(i) > arr(j)) {
          val temp: Int = arr(i)
          arr(i) = arr(j)
          arr(j) = temp
        }
      }
    }
    for (elem <- arr) {
      println(elem)
    }
  }
}
