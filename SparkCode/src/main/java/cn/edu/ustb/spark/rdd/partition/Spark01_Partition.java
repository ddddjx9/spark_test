package cn.edu.ustb.spark.rdd.partition;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class Spark01_Partition {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final ArrayList<Tuple2<String, Integer>> datas = new ArrayList<>();

        datas.add(new Tuple2<>("a", 1));
        datas.add(new Tuple2<>("a", 2));
        datas.add(new Tuple2<>("a", 3));
        datas.add(new Tuple2<>("a", 4));

        //先在分区内求和之后，在分区间求和
        final JavaPairRDD<String, Integer> rdd = jsc.parallelize(datas, 3).mapToPair(tuple -> tuple)
                .reduceByKey(Integer::sum);

        rdd.saveAsTextFile("output");
        jsc.close();
    }
}
