package cn.edu.ustb.spark.rdd.instance;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark02_RDD_Memory_Partition_Data {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);
        final List<Integer> names = Arrays.asList(1, 2, 3, 4, 5, 6);

        final JavaRDD<Integer> rdd = jsc.parallelize(names, 4);
        rdd.saveAsTextFile("output");

        jsc.close();
    }
}
