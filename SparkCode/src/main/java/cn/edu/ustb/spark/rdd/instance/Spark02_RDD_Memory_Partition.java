package cn.edu.ustb.spark.rdd.instance;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark02_RDD_Memory_Partition {
    public static void main(String[] args) {

        final SparkConf conf = new SparkConf();
        //local环境中：分区数量和环境中的核数相关，尝试修改环境中的线程数local[*]，但是一般不推荐
        //分区数量需要手动设定
        conf.setMaster("local");
        conf.setAppName("spark");
        final JavaSparkContext jsc = new JavaSparkContext(conf);

        //TODO Kafka可以将数据进行切片，减小数据规模，也称为叫分区，这个分区操作是底层完成的
        final List<String> names = Arrays.asList("zhangsan", "lisi", "wangwu");
        //parallelize可以传两个参数
        //  第一个参数表示对接的数据源集合
        //  第二个参数numSlices表示切片数量
        final JavaRDD<String> rdd = jsc.parallelize(names, 3);

        //TODO 将数据模型分区后的数据保存到磁盘文件中
        rdd.saveAsTextFile("output");

        //TODO 释放资源
        jsc.close();
    }
}

