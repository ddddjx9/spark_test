package cn.edu.ustb.WordCount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Spark01_WordCount {
  def main(args: Array[String]): Unit = {
    //Application如何与Spark产生交互？
    //让Spark框架运行我们的应用程序

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

    //3. 将数据根据单词进行分组，便于统计
    val wordGroup: RDD[(String, Iterable[String])] = words.groupBy(word => word)

    //4. 对分组后的数据进行聚合(转换)
    //(hello,hello,hello) (world,world)
    //(hello,3)  (world,2)
    val wordToCount: RDD[(String, Int)] = wordGroup.map {
      case (word, list) => {
        (word, list.size)
      }
    }

    //5. 将数据采集到控制台打印出来
    val arr: Array[(String, Int)] = wordToCount.collect()
    arr.foreach(println)

    //关闭连接
    sc.stop()
  }
}
