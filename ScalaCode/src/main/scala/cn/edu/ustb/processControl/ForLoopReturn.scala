package cn.edu.ustb.processControl

import scala.collection.immutable

object ForLoopReturn {
  def main(args: Array[String]): Unit = {
    //循环的返回值
    //for训话要经过多次循环，所以就算要确定返回值，那也很难确定它的返回值到底是多少
    //默认情况下，循环的返回值就是空
    val unit: Unit = for (i <- 1 to 10) {
      println(i)
    }
    println(unit)

    //特殊情况下，使用关键字，可以将循环中j的每一个值保存为一个集合
    //yield关键字表示在当前循环中生成一个集合类型作为返回值返回
    val nums: immutable.IndexedSeq[Int] = for (i <- 1 to 10) yield i * 2
    println(nums)
  }
}
