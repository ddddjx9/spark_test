package cn.edu.ustb.spark.rdd.operator.transform;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class TransformKVSortByKey2 {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final ArrayList<Tuple2<String, Integer>> datas = new ArrayList<>();

        datas.add(new Tuple2<>("a", 1));
        datas.add(new Tuple2<>("a", 3));
        datas.add(new Tuple2<>("a", 4));
        datas.add(new Tuple2<>("a", 2));

        final JavaRDD<Tuple2<String, Integer>> rdd = jsc.parallelize(datas);
        final JavaPairRDD<String, Integer> mapRDD = rdd.mapToPair(tuple -> tuple);

        //当前数据Key值相同，想要按照value来排序，但是没有对应的方法
        //  利用map方法将key和value进行颠倒
        //  ("a", 1) [1, ("a", 1)]
        //  ("a", 3) [3, ("a", 3)]
        //  ("a", 4) [4, ("a", 4)]
        //  ("a", 2) [2, ("a", 2)]

        //输出结果：
        //(1,(a,1))
        //(2,(a,2))
        //(3,(a,3))
        //(4,(a,4))

        //这时候我们只想要value，不想要key，所以再次进行转换
        mapRDD.mapToPair(tuple -> new Tuple2<>(tuple._2, tuple))
                .sortByKey()
                .map(tuple -> tuple._2)
                .collect().forEach(System.out::println);

        jsc.close();
    }
}
