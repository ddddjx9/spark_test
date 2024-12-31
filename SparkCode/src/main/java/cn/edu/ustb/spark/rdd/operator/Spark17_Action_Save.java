package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

public class Spark17_Action_Save {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Action_OtherMethod");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        final JavaRDD<Integer> rdd = jsc.parallelize(list, 2);
        final JavaPairRDD<Integer, Integer> pairRDD = rdd.mapToPair(element -> new Tuple2<>(element, element));

        //save：保存
        pairRDD.saveAsTextFile("output1");
        pairRDD.saveAsObjectFile("output2");

        jsc.close();
    }
}
