package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
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

        final Tuple2<String, Integer> a1 = new Tuple2<>("a", 1);
        final Tuple2<String, Integer> a2 = new Tuple2<>("b", 2);
        final Tuple2<String, Integer> a3 = new Tuple2<>("c", 3);

        final List<Tuple2<String, Integer>> tuple2 = Arrays.asList(a1, a2, a3);

        /*final JavaRDD<Tuple2<String, Integer>> rdd = jsc.parallelize(tuple2);

        rdd.map(new Function<Tuple2<String, Integer>, Object>() {
            @Override
            public Object call(Tuple2<String, Integer> v1) throws Exception {
                return new Tuple2<>(v1._1, v1._2 * 2);
            }
        }).collect().forEach(System.out::println);*/

        //parallelizePairs：对一对数据做处理
        final JavaPairRDD<String, Integer> pairRDD = (JavaPairRDD<String, Integer>) jsc.parallelizePairs(tuple2);

        pairRDD.mapValues(v1 -> v1 * 2).collect().forEach(System.out::println);

        jsc.close();
    }
}
