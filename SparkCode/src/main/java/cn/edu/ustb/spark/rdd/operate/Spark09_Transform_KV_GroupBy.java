package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;
import java.util.List;

public class Spark09_Transform_KV_GroupBy {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);
        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        final JavaRDD<Integer> rdd = jsc.parallelize(list);

        //groupBy - 按照指定规则对数据进行分组，对每一个数据添加标记
        //相同标记的数据会放在同一个组中，对应的标记就是组名
        //groupBy结果：(0, [2,4]),(1, [1,3])
        //实际上groupBy的结果就是KV类型的数据
        final JavaPairRDD<Integer, Iterable<Integer>> groupRDD = rdd.groupBy(num -> num % 2);
        //groupRDD.collect().forEach(System.out::println);

        groupRDD.mapValues(new Function<Iterable<Integer>, Integer>() {
            @Override
            public Integer call(Iterable<Integer> v1) throws Exception {
                int sum = 0;
                for (Integer i : v1) {
                    sum += i;
                }
                return sum;
            }
        }).collect().forEach(System.out::println);

        jsc.close();
    }
}
