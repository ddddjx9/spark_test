package cn.edu.ustb.spark.rdd.requirement;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;

import java.util.Arrays;
import java.util.List;

public class ReduceBroadcastVariables {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Reduce");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<String> list = Arrays.asList("Hadoop", "Flink", "Spark", "Hive", "Flume", "Kafka", "Spark");
        final JavaRDD<String> rdd = jsc.parallelize(list);

        //TODO 默认数据的传输是以Task为单位进行传输的
        //      如果想要以Executor为单位进行传输，那么需要进行包装（封装）
        List<String> filterList = Arrays.asList("Spark", "Hadoop");

        //1. 包装
        final Broadcast<List<String>> broadcastList = jsc.broadcast(filterList);
        //2. 获取
        rdd.filter(s -> broadcastList.value().contains(s)).collect().forEach(System.out::println);

        jsc.close();
    }
}
