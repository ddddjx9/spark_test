package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

public class Spark20_Action_Serializable_1 {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Action");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        final JavaRDD<Integer> rdd = jsc.parallelize(nums, 2);

        //org.apache.spark.SparkException: Task not serializable
        //Caused by: java.io.NotSerializableException: java.io.PrintStream
        //JDK1.8函数式编程其实采用的是对象模拟出来的

        //这个类是在Driver端创建的，而这个类又无法具体序列化，所以导致这里不能用函数式编程
        final PrintStream out = System.out;
        rdd.foreach(out::println);

        jsc.close();
    }
}
