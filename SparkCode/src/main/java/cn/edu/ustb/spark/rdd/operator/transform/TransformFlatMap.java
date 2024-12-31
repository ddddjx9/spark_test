package cn.edu.ustb.spark.rdd.operator.transform;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TransformFlatMap {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);
        final List<List<Integer>> nums = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4)
        );
        final JavaRDD<List<Integer>> rdd = jsc.parallelize(nums, 2);

        //RDD的转换方法：flatMap
        //      flat(数据扁平化，将整体数据拆分成一个一个的个体使用) + Map(映射)
        //      扁平化的同时可以做map处理，不用拆成两步
        final JavaRDD<Integer> flatMapRDD = rdd.flatMap(new FlatMapFunction<List<Integer>, Integer>() {
            @Override
            public Iterator<Integer> call(List<Integer> integers) throws Exception {
                List<Integer> nums = new ArrayList<>();
                integers.forEach(
                        num -> {
                            nums.add(num * 2);
                        }
                );
                return nums.iterator();
            }
        });

        flatMapRDD.collect().forEach(System.out::println);

        jsc.close();
    }
}
