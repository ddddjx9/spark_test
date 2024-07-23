package cn.edu.ustb.functionalProgramming

/**
 * 函数参数的传递 - 可变参数
 */
object FunctionParameters {
  def main(args: Array[String]): Unit = {
    //TODO 可变参数
    def f1(str: String*): Unit = {
      println(str)
    }

    f1("lemon", "打上花火", "未闻花名")
    //WrappedArray(lemon, 打上花火, 未闻花名)

    //TODO 如果参数列表中存在多个参数，那么可变参数一般放置在最后
    def f2(str1: String, str2: String*): Unit = {
      println(str1 + str2)
    }

    f2("叶修", "喻文州", "张新杰", "肖时钦")
    //叶修WrappedArray(喻文州, 张新杰, 肖时钦)
    //可变参数的话，后面也能不传参数，就是空的WrappedArray()

    //TODO 参数默认值
    def f3(name: String = "yexiu"): Unit = {
      println(name)
    }

    f3()

    //TODO 带名参数
    def f4(name: String, age: Int): Unit = {
      println(s"${age}岁的${name}马上可以回家")
    }

    f4(name = "djx", 19)
    //但是好像第一个参数带名之后，之后的参数也必须带名，否则报错
    f4(age = 19, name = "djx")
  }
}
