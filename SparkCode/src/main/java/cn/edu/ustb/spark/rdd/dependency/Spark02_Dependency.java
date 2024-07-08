package cn.edu.ustb.spark.rdd.dependency;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class Spark02_Dependency {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final ArrayList<Tuple2<String, Integer>> datas = new ArrayList<>();

        datas.add(new Tuple2<>("a", 1));
        datas.add(new Tuple2<>("a", 2));
        datas.add(new Tuple2<>("a", 3));
        datas.add(new Tuple2<>("b", 4));

        final JavaRDD<Tuple2<String, Integer>> rdd = jsc.parallelize(datas);

        //将Java版本的RDD转换为Scala版本的RDD
        System.out.println(rdd.rdd().dependencies());

        final JavaPairRDD<String, Integer> mapRDD = rdd.mapToPair(tuple -> tuple);

        //List(org.apache.spark.OneToOneDependency@4acb2510) - 窄依赖
        System.out.println("*********************************");
        System.out.println(mapRDD.rdd().dependencies());

        final JavaPairRDD<String, Integer> reduceRDD = mapRDD.reduceByKey(Integer::sum);

        //List(org.apache.spark.ShuffleDependency@10cd6753) - 宽依赖
        System.out.println("*********************************");
        System.out.println(reduceRDD.rdd().dependencies());
        reduceRDD.collect().forEach(System.out::println);

        jsc.close();
    }
}
