package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

public class Spark10_Transform_KV_WordCount_GroupByKey {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        jsc.parallelizePairs(Arrays.asList(new Tuple2<>("a", 1), new Tuple2<>("b", 2), new Tuple2<>("a", 3), new Tuple2<>("b", 4))).groupByKey().mapValues(tuple -> {
            int sum = 0;
            final Iterator<Integer> iterator = (Iterator<Integer>) tuple.iterator();
            while (iterator.hasNext()) {
                final Integer num = iterator.next();
                sum += num;
            }
            return sum;
        });

        jsc.close();
    }
}
