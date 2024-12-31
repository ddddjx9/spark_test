package cn.edu.ustb.spark.rdd.instance;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

public class RDDFromDiskPartitionData {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        //TODO 分区数量例子：
        //  数据：1 2 3
        //  totalSize = 7
        //  goalSize = totalSize / minPartNum = 7 / 2 = 3
        //  partNum = totalSize / goalSize = 7 / 3 = 2...1 超过10%，开辟一个新的分区，最后应该是3个分区
        final JavaRDD<String> rdd = jsc.textFile("datas\\data3.txt", 2);

        rdd.saveAsTextFile("output");

        jsc.close();
    }
}
