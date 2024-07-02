package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class Spark08_Transform_KV {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);

        //目前是单值类型，想要让其变成KV类型进行处理
        //单值类型能够和KV类型进行转换 - mapToPair
        final JavaRDD<Integer> rdd = jsc.parallelize(list);

        //mapToPair方法：
        /*rdd.mapToPair(new PairFunction<Integer, Integer, Integer>() {
                    @Override
                    public Tuple2<Integer, Integer> call(Integer integer) throws Exception {
                        return new Tuple2<Integer, Integer>(integer, integer * 2);
                    }
                }).mapValues(num -> num * 2)
                .collect().forEach(System.out::println);*/

        rdd.mapToPair(num -> new Tuple2<>(num, num * 2))
                .mapValues(num -> num * 2)
                .collect()
                .forEach(System.out::println);

        //输出结果：
        //(1,4)
        //(2,8)
        //(3,12)
        //(4,16)
        //(5,20)
        //(6,24)

        jsc.close();
    }
}
