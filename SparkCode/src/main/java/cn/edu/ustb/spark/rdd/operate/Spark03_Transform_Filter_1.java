package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark03_Transform_Filter_1 {
    public static void main(String[] args) {
        //过滤后出现数据倾斜
        //由于filter底层没有进行Shuffle，所以没有改变分区的能力
        //采用RDD提供的方法重新改变分区

        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        //开始进行数据过滤
        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        final JavaRDD<Integer> rdd = jsc.parallelize(list);
        final JavaRDD<Integer> filterRDD = rdd.filter(num -> num % 2 == 0);

        //减轻数据倾斜的情况
        //coalesce - 合并分区
        filterRDD.coalesce(3, true).saveAsTextFile("output");

        jsc.close();
    }
}
