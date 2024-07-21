package cn.edu.ustb.basicOperate

/**
 * 这是一个冒泡排序的算法
 */
object BubbleSort {
  def main(args: Array[String]): Unit = {
    //定义数组
    val arr: Array[Int] = Array(5, 7, 4, 8, 9, 7, 6, 1, 2, 3, 0, 8)

    bubbleSort(arr)
  }

  def bubbleSort(arr: Array[Int]): Unit = {
    //两层循环
    //第一层循环表示要执行多少轮，第二层循环表示相互表示的两个元素
    for {i <- arr.indices; j <- 0 to arr.length - 2 - i} {
      if (arr(j) > arr(j + 1)) {
        swap(arr, j, j + 1)
      }
    }

    arr.foreach(element => println(element))

    def swap(arr: Array[Int], i: Int, j: Int): Unit = {
      val temp: Int = arr(i)
      arr(i) = arr(j)
      arr(j) = temp
    }
  }
}
