package cn.edu.ustb.spark.rdd.operator.action;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Action {
    public static void main(String[] args) throws InterruptedException {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);

        // 这里的collect就是RDD的行动算子，只有调用这个方法之后，数据才会流转起来
        //      RDD的行动算子会触发Job的执行
        //      调用一次行动算子，就会触发一次作业的执行
        // 注释掉collect方法之后，发现控制台上没有任何内容输出
        final JavaRDD<Integer> rdd = jsc.parallelize(list).map(num -> {
            System.out.println("num = " + num);
            return num * 2;
        });

//        rdd.collect().forEach(System.out::println);
//        rdd.collect();

        // 行动算子和转换算子的区别？ - 产生新的Job？错！
        // 转换算子的功能：将旧的RDD转换成新的RDD，所以调用完算子之后，会有新的RDD产生
        final JavaRDD<Integer> sortRDD = rdd.sortBy(num -> num, true, 2);

        //对执行结果生成相应变量后，发现生成的不是新的RDD，而是具体的执行结果
        final List<Integer> collect = rdd.collect();

        sortRDD.collect().forEach(System.out::println);
        System.out.println(collect);
        //http://localhost:4040
        Thread.sleep(1000000L);

        jsc.close();
    }
}
