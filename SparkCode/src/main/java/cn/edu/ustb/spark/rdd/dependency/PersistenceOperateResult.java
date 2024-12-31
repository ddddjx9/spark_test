package cn.edu.ustb.spark.rdd.dependency;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import scala.Tuple2;

import java.util.ArrayList;

public class PersistenceOperateResult {
    public static void main(String[] args) throws InterruptedException {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final ArrayList<Tuple2<String, Integer>> datas = new ArrayList<>();

        datas.add(new Tuple2<>("a", 1));
        datas.add(new Tuple2<>("a", 2));
        datas.add(new Tuple2<>("a", 3));
        datas.add(new Tuple2<>("a", 4));

        final JavaPairRDD<String, Integer> rdd = jsc.parallelize(datas, 2).mapToPair(tuple -> {
            System.out.println("*****************************");
            return tuple;
        });

        //对具有重复逻辑的RDD算子进行持久化
        //持久化操作
        //rdd.cache();
        rdd.persist(StorageLevel.DISK_ONLY());

        rdd.reduceByKey(Integer::sum).collect();
        System.out.println("1计算完毕");
        System.out.println("#################################");

        rdd.groupByKey().collect();
        System.out.println("2计算完毕");

        jsc.close();
    }
}
