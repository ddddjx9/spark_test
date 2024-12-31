package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark14_Action_Collect {
    public static void main(String[] args) throws InterruptedException {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Action_Collect");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<Integer> list = Arrays.asList(7, 9, 8, 2, 5, 4);
        final JavaRDD<Integer> rdd = jsc.parallelize(list).map(num -> {
            System.out.println("**********");
            return num * 2;
        });

        //collect方法是行动算子，会触发一个Job执行
        //      Collect方法就是将Executor端执行的结果按照分区的顺序拉取回到Driver端
        final List<Integer> collected = rdd.collect();
        collected.forEach(System.out::println);

        jsc.close();
    }
}
