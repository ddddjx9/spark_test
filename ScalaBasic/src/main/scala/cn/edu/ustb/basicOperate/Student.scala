package cn.edu.ustb.basicOperate

/**
 * 之前的时候，是将school作为静态变量，利用类名进行调用，不够面向对象
 * <p>
 * 所以scala设计为伴生对象，利用这个对象调用，就足够面向对象
 * <p>
 * 将全局只有一份的属性，放在全局只有一份的伴生对象里面
 *
 * @param name 学生姓名
 * @param age  学生年龄
 */
class Student(var name: String, var age: Int) {
  def printInfo(): Unit = {
    println(name + " " + age + " " + Student.school)
  }
}

/**
 * 伴生对象
 * <p>
 * 这里声明主方法后，伴生对象类也出现执行键，但是也访问的是这个伴生对象的主方法，他们之间可以互相访问
 */
object Student {
  private val school: String = "ustb.edu.cn"

  def main(args: Array[String]): Unit = {
    val student = new Student("djx", 19)

    student.printInfo()
  }
}