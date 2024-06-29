package cn.edu.ustb.WordCount

class KVPair {
  def test(args: Array[String]): Unit = {
    //Scala中可以将无关数据封装在一起，称为元组；不像Java中，只能将有关系的数据封装在一起作为对象使用
    //如果想要访问元组中的数据，需要使用顺序号
    //如果元组中的数据仅有两个，称为对偶元组，也称为键值对
    val kv = (0, 1)
    val kv1 = ("zhangsan", 20, 1001)
    println(kv)
    println(kv1)
  }
}


