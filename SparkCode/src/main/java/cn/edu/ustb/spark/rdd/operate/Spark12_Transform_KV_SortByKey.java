package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class Spark12_Transform_KV_SortByKey {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final ArrayList<Tuple2<String, Integer>> datas = new ArrayList<>();

        datas.add(new Tuple2<>("a", 1));
        datas.add(new Tuple2<>("b", 3));
        datas.add(new Tuple2<>("a", 4));
        datas.add(new Tuple2<>("b", 2));

        final JavaRDD<Tuple2<String, Integer>> rdd = jsc.parallelize(datas);
        final JavaPairRDD<String, Integer> mapRDD = rdd.mapToPair(tuple -> tuple);

        //sortByKey：按照Key进行排序（可以自己传递排序规则，指定升序或者降序，指定分区数量）
        //      groupByKey：按照K对V进行分组
        //      reduceByKey：按照K对V进行聚合
        final JavaPairRDD<String, Integer> sortRDD = mapRDD.sortByKey(false);

        //执行结果：
        //(a,1)
        //(a,4)
        //(b,3)
        //(b,2)
        sortRDD.collect().forEach(System.out::println);

        jsc.close();
    }
}
