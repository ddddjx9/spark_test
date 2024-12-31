package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark15_Action_OtherMethods {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Action_Count");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        final JavaRDD<Integer> rdd = jsc.parallelize(list).map(num -> num * 2);
        //collect方法用于采集数据
        final List<Integer> collected = rdd.collect();
        //count用于获取所有逻辑执行完毕后的结果数量
        final long counted = rdd.count();
        //first：获取所有逻辑执行完毕后的结果的第一个值
        final Integer firsted = rdd.first();
        //从结果中获取前n个结果，first方法实际上就是调用了take方法，也就是take(1)
        final List<Integer> taken = rdd.take(3);

        collected.forEach(System.out::println);
        System.out.println("处理完毕后记录总条数为：" + counted);
        System.out.println(firsted);
        taken.forEach(System.out::println);  //返回了2,4,6

        jsc.close();
    }
}
