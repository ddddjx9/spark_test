package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class Spark18_Action_ForEach {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Action_OtherMethod");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        final JavaRDD<Integer> rdd = jsc.parallelize(nums, 2);

        //def foreach(f: VoidFunction[T]): Unit = {
        //    rdd.foreach(x => f.call(x))
        //  }

        //@FunctionalInterface
        //public interface VoidFunction<T> extends Serializable {
        //  void call(T t) throws Exception;
        //}

        //因为collect方法是将执行结果按照分区数据拉取回到Driver形成的数据集合，有序
        rdd.collect().forEach(System.out::println);
        System.out.println("***********************");
        //forEach：分布式循环
        //这里的两个方法都不可以书写lambda表达式，否则会报错：
        //Task not serializable
        //Caused by: java.io.NotSerializableException: java.io.PrintStream
        //Serialization stack:
        rdd.foreach(element -> System.out.println(element));
        //执行结果：
        //1
        //2
        //3
        //4
        //5
        //6
        //***********************
        //4
        //5
        //6
        //1
        //2
        //3

        rdd.foreachPartition(list -> System.out.println(list));
        //IteratorWrapper(<iterator>)  两个分区的整体数据传过来处理
        //IteratorWrapper(<iterator>)

        jsc.close();
    }
}
