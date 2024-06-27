object Test {
  def main(args: Array[String]): Unit = {
    var num = 2
    num >>= 1
    println(num) //0
    var num1 = 2
    num1 <<= 1
    println(num1) //8
  }
}

