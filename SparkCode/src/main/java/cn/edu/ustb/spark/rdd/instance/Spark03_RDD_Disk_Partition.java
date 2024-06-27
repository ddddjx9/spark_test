package cn.edu.ustb.spark.rdd.instance;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class Spark03_RDD_Disk_Partition {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[4]");
        conf.setAppName("spark");
        conf.set("spark.default.parallelism", "1");
        final JavaSparkContext jsc = new JavaSparkContext(conf);

        //TODO Spark读取文件，传递路径
        //TODO 文件数据源分区设定也存在多个位置
        //      1. textFile可以传递第二个参数，传递分区数量：minPartitions(最小分区数)
        //          文件分区数的最小划分数为2，参数可以不需要传递，Spark可以采用默认值
        //              默认分区数：def defaultMinPartitions: Int = math.min(defaultParallelism, 2)
        //      2. 使用配置参数：spark.default.parallelism => 4 => math.min(参数，2)
        //      3. 采用环境默认总核数 => math.min(总核数，2)
        final JavaRDD<String> rdd = jsc.textFile("datas\\data1.txt");
        rdd.saveAsTextFile("output");

        jsc.close();
    }
}
