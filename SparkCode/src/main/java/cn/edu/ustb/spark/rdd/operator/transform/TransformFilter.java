package cn.edu.ustb.spark.rdd.operator.transform;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class TransformFilter {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");
        final JavaSparkContext jsc = new JavaSparkContext(conf);
        final List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        final JavaRDD<Integer> rdd = jsc.parallelize(nums, 2);

        //RDD的转换方法：Filter
        //      RDD可以根据指定过滤规则对数据源中的数据进行筛选过滤
        //      如果满足规则（返回true），数据保留；不满足规则（返回false），数据丢弃
        //      规则：保留奇数，丢弃偶数
        final JavaRDD<Integer> filterRDD = rdd.filter(num -> num % 2 == 1);

        filterRDD.collect().forEach(System.out::println);

        jsc.close();
    }
}
