package cn.edu.ustb.helloWorld

object Variable {
  def main(args: Array[String]): Unit = {
    //非引用数据类型，数据里面的内容不可以进行更改
    //这里对象是一个常量，引用数据类型中常量对象里面的属性是可以更改的
    //引用类型常量表示指针地址不能够更改，即不能指向其他的堆空间，但是空间里面的内容是可变的
    val bob: Student = new Student("bob", 20)
    bob.age = 21
  }
}
