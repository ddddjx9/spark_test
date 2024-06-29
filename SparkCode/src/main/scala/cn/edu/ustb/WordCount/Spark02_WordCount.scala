package cn.edu.ustb.WordCount

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

object Spark02_WordCount {
  def main(args: Array[String]): Unit = {
    //Application如何与Spark产生交互？
    //让Spark框架运行我们的应用程序
    //使用聚合的方式进行WordCount程序的操作
    //hello,1 hello,1 hadoop,1 spark,1 => hello,2 hadoop,1 spark,1

    //在JDBC中创建连接对象
    //建立和Spark框架的连接
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("WordCount")

    //这里的sc是spark的环境对象
    val sc = new SparkContext(sparkConf)

    //执行业务操作
    //1. 读取文件，获取一行一行的数据
    //hello world
    val lines: RDD[String] = sc.textFile("datas")

    //2. 将行数据按空格或者\t进行拆分
    //扁平化操作
    val words: RDD[String] = lines.flatMap(_.split(" "))

    val wordToOne: RDD[(String, Int)] = words.map(
      word => (word, 1)
    )

    //3. 将数据根据单词进行分组
    //hello,1
    val wordGroup: RDD[(String, Iterable[(String, Int)])] = wordToOne.groupBy(
      t => t._1
    )

    //4. 对分组后的数据进行聚合(转换)
    //(hello,hello,hello) (world,world)
    //(hello,3)  (world,2)
    val wordToCount: RDD[(String, Int)] = wordGroup.map {
      //这里list表示hello,1 hello,1等等组
      case (word, list) => {
        val tuple: (String, Int) = list.reduce(
          (t1, t2) => {
            (t1._1, t1._2 + t2._2)
          }
        )
        tuple
      }
    }

    //5. 将数据采集到控制台打印出来
    val arr: Array[(String, Int)] = wordToCount.collect()
    arr.foreach(println)

    //关闭连接
    sc.stop()
  }
}
