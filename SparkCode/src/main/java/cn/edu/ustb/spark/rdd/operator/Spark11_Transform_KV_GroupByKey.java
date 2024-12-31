package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class Spark11_Transform_KV_GroupByKey {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);
        /*final JavaRDD<Tuple2<String, Integer>> parallelize = jsc.parallelize(
                Arrays.asList(
                        new Tuple2<>("a", 1),
                        new Tuple2<>("b", 2),
                        new Tuple2<>("a", 3),
                        new Tuple2<>("b", 4)
                )
        );*/

        //将数据的第一个值用于分组
        //(a, [new Tuple<>(""a", 1), new Tuple2<>("a", 3)]
        //(b, [new Tuple2<>("b", 2), new Tuple2<>("b", 4)]
        //(a,[(a,1), (a,3)])
        //(b,[(b,2), (b,4)])

        //final JavaPairRDD<String, Iterable<Tuple2<String, Integer>>> groupRDD = parallelize.groupBy(tuple -> tuple._1);
        //groupRDD.collect().forEach(System.out::println);

        //当前版本中泛型无法识别，所以不加泛型
        //将KV类型的数据直接按照key对value进行分组
        //(a,[1, 3])
        //(b,[2, 4])
        jsc.parallelizePairs(
                        Arrays.asList(
                                new Tuple2<>("a", 1),
                                new Tuple2<>("b", 2),
                                new Tuple2<>("a", 3),
                                new Tuple2<>("b", 4)
                        )
                ).groupByKey()
                .collect().forEach(System.out::println);

        jsc.close();
    }
}
