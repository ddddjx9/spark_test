package cn.edu.ustb.functionalProgramming

import cn.edu.ustb.basicOperate.{BubbleSort, SelectionSort}

object ImitateSparkFunction {
  def main(args: Array[String]): Unit = {
    val arr: Array[Int] = Array(9, 3, 4, 8, 7, 5, 2, 1, 6, 8, 0)

    SelectionSort.selectionSort(arr)
    BubbleSort.bubbleSort(arr)
    val result: Int = arr.map(num => num - 1).filter(elem => elem % 2 != 0).reduce(Integer.sum)
    println(result)
  }
}
