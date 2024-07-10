package cn.edu.ustb.spark.rdd.requirement;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark_Reduce_1 {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Reduce");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<String> list = Arrays.asList("Hadoop", "Flink", "Spark", "Hive", "Flume", "Kafka", "Spark");
        final JavaRDD<String> rdd = jsc.parallelize(list);

        List<String> filterList = Arrays.asList("Spark", "Hadoop");
        rdd.filter(filterList::contains).collect().forEach(System.out::println);

        jsc.close();
    }
}

