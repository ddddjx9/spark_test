package cn.edu.ustb.spark.rdd.instance;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark02_RDD_Memory {
    public static void main(String[] args) {

        final SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("spark");
        final JavaSparkContext jsc = new JavaSparkContext(conf);

        //TODO 构建数据处理模型
        //     利用环境对象对接数据源，构建RDD对象
        final List<String> names = Arrays.asList("zhangsan", "lisi", "wangwu");

        //TODO parallelize方法可以传递参数，集合
        final JavaRDD<String> rdd = jsc.parallelize(names);

        final List<String> collect = rdd.collect();

        collect.forEach(System.out::println);

        //TODO 释放资源
        jsc.close();
    }
}
