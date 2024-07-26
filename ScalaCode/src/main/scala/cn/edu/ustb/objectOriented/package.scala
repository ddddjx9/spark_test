package cn.edu.ustb

import java.util.{ArrayList => _} //屏蔽类

package object objectOriented {
  //定义当前包共享的属性和方法
  val commonValue: String = "ustb.edu.cn"

  def commonMethod(): Unit = {
    println(s"调用了包对象objectOriented，在${commonValue}包下面")
  }
}
