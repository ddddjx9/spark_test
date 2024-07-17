package cn.edu.ustb.basicOperate

object EqualsFunction {
  def main(args: Array[String]): Unit = {
    //1.比较Scala中已经定义的引用数据类型 - String
    val str1 = "hello world"
    val str2 = new String("hello world")

    //在 Scala 中，要比较对象的地址值（引用值），可以使用 eq 方法。
    // 默认情况下，Scala 的 == 方法确实被重写为比较对象的内容值而非引用值。
    // 如果需要显式地比较对象的引用值，可以使用 eq 方法
    println(str1 == str2) //true
    println(str1.eq(str2)) //false

    val s1 = new Student("zhangsan", 20)
    val s2 = new Student("zhangsan", 20)
    println(s1 == s2) //false
    println(s1.eq(s2)) //false
    //如果自定义了对象，那么默认==方法比较的就是对象的地址值
    //如果在书写对象类的过程中没有重写对象类中的hashCode方法和equals方法，那么比较的就是地址值
    //如果重写了方法，那么比较的就是地址值；eq方法比较的永远都是地址值
  }
}
