package cn.edu.ustb.helloWorld

object StringOperator {
  def main(args: Array[String]): Unit = {
    //1. 字符串拼接
    val name: String = "alice"
    val age: Int = 18
    println("学生的姓名为：" + name + "，年龄为：" + age)

    //* 用于将一个字符串复制多次并拼接
    println(name * 3)

    //2. printf输出字符串
    printf("学生姓名为：%s，年龄为：%d", name, age)
    println

    //3. 字符串插值
    println(s"学生姓名为$name，年龄为$age")

    val num: Double = 2.3456
    println(f"The num is $num%2.2f") //格式化模版字符串
    println(raw"The num is $num%2.2f") //不会将后面的%或者$进行解析或者做格式化处理

    //三引号表示字符串，保持多行字符串的原格式输出
    val sql: String =
      s"""
         |select * from
         | student
         |where
         | name = ${name}
         |and
         | age > ${age}
         |""".stripMargin
    println(sql)
  }
}
