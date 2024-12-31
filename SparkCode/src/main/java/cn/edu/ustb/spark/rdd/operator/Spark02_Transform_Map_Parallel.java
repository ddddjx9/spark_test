package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class Spark02_Transform_Map_Parallel {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final JavaRDD<Integer> rdd = jsc.parallelize(Arrays.asList(1, 2, 3, 4), 2);

        final JavaRDD<Integer> newRDD1 = rdd.map(
                num -> {
                    System.out.println("@" + num);
                    return num;
                }
        );

        final JavaRDD<Integer> newRDD2 = newRDD1.map(
                num -> {
                    System.out.println("###" + num);
                    return num;
                }
        );

        newRDD2.collect();

        jsc.close();
    }
}
