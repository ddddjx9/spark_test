package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;

public class Spark02_Transform_Map {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final JavaRDD<Integer> rdd = jsc.parallelize(
                Arrays.asList(1, 2, 3, 4), 2
        );

        //RDD的转换方法：Map（映射 K -> V 将K转换成V）
        //          对单值数据进行处理
        //用于将指定值转换为其他值的场合
        final JavaRDD<Object> newRDD = rdd.map(new Function<Integer, Object>() {
            @Override
            public Object call(Integer v1) throws Exception {
                return v1 * 2;
            }
        });

        newRDD.collect().forEach(System.out::println);

        jsc.close();
    }
}
