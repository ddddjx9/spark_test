object Test {
  def main(args: Array[String]): Unit = {
    var num = 2
    num >>= 2
    println(num) //0
    var num1 = 2
    num1 <<= 2
    println(num1) //8
  }
}

