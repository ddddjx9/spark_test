package cn.edu.ustb.spark.rdd.instance;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;

public class RDDFromDisk {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("spark");
        final JavaSparkContext jsc = new JavaSparkContext(conf);

        //TODO 构建数据处理模型
        //     利用环境对象对接磁盘数据（文件），构建RDD对象
        final JavaRDD<String> rdd = jsc.textFile("datas\\data1.txt");
        final List<String> collect = rdd.collect();
        collect.forEach(System.out::println);

        jsc.close();
    }
}
