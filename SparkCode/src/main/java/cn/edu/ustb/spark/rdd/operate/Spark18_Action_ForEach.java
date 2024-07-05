package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark18_Action_ForEach {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Action_OtherMethod");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        final JavaRDD<Integer> rdd = jsc.parallelize(list, 2);

        //def foreach(f: VoidFunction[T]): Unit = {
        //    rdd.foreach(x => f.call(x))
        //  }

        //@FunctionalInterface
        //public interface VoidFunction<T> extends Serializable {
        //  void call(T t) throws Exception;
        //}
        rdd.collect().forEach(System.out::println);
        System.out.println("***********************");
        rdd.foreach(num -> System.out.println(num));
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
        //因为collect方法是将执行结果按照分区数据拉取回到Driver形成的数据集合，有序

        jsc.close();
    }
}
