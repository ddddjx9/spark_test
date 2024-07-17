package cn.edu.ustb.functionalProgramming

import java.util.Scanner

object FunctionAndMethod {
  def main(args: Array[String]): Unit = {
    //这里如果用Scala自带的StdIn，只能接受回车，不能接受空格
    val sc: Scanner = new Scanner(System.in)
    val x: Int = sc.nextInt()
    val y: Int = sc.nextInt()

    //定义函数
    def sum(x: Int, y: Int): Int = {
      x + y
    }

    //如果外部的方法和内部的函数同名，那么会就近调用临近的函数
    //如果没有函数，就会调用外部的方法
    println(sum(x, y))

    //如果非要调用外部的方法，使用对象调用
    println(FunctionAndMethod.sum(x, y))
    println(FunctionAndMethod.sum('a', 'b'))
  }

  //定义对象的方法
  def sum(x: Int, y: Int): Int = {
    x - y
  }

  //方法的重载
  def sum(x: Char, y: Char): Int = {
    x.toInt - y.toInt
  }
}
