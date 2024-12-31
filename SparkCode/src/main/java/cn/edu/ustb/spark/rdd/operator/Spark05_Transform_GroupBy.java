package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark05_Transform_GroupBy {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);
        final List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        final JavaRDD<Integer> rdd = jsc.parallelize(nums);

        //RDD的方法 - groupBy（按照指定的规则对数据进行分组）
        //(false,[1, 3])
        //(true,[2, 4])
        rdd.groupBy(v1 -> v1 % 2 == 0).collect().forEach(System.out::println);

        jsc.close();
    }
}
