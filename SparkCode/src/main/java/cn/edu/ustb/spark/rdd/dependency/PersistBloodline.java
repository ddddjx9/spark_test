package cn.edu.ustb.spark.rdd.dependency;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class PersistBloodline {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);
        jsc.setCheckpointDir("checkpoint");

        final ArrayList<Tuple2<String, Integer>> datas = new ArrayList<>();

        datas.add(new Tuple2<>("a", 1));
        datas.add(new Tuple2<>("a", 2));
        datas.add(new Tuple2<>("a", 3));
        datas.add(new Tuple2<>("a", 4));

        final JavaPairRDD<String, Integer> rdd = jsc.parallelize(datas, 2).mapToPair(tuple -> tuple);

        //rdd.cache();
        rdd.checkpoint();

        final JavaPairRDD<String, Integer> wordCountRDD = rdd.reduceByKey(Integer::sum);

        System.out.println(wordCountRDD.toDebugString());
        System.out.println("---------------------------------");
        wordCountRDD.collect();
        System.out.println(wordCountRDD.toDebugString());

        //cache方法会在血缘关系中增加依赖关系
        /*
        不要切断血缘，这样万一内存中的数据丢失，也能重新计算回来，保证数据安全
        (2) ShuffledRDD[2] at reduceByKey at Spark05_Persist.java:30 []
        +-(2) MapPartitionsRDD[1] at mapToPair at Spark05_Persist.java:25 []
            |  ParallelCollectionRDD[0] at parallelize at Spark05_Persist.java:25 []
        ---------------------------------
        (2) ShuffledRDD[2] at reduceByKey at Spark05_Persist.java:30 []
        +-(2) MapPartitionsRDD[1] at mapToPair at Spark05_Persist.java:25 []
            |      CachedPartitions: 2; MemorySize: 304.0 B; DiskSize: 0.0 B
            |  ParallelCollectionRDD[0] at parallelize at Spark05_Persist.java:25 []
         */

        //checkpoint方法会改变血缘关系：
        // 将HDFS文件系统中存储的检查点信息当成数据来源
        //(2) ShuffledRDD[2] at reduceByKey at Spark05_Persist.java:31 []
        // +-(2) MapPartitionsRDD[1] at mapToPair at Spark05_Persist.java:26 []
        //    |  ParallelCollectionRDD[0] at parallelize at Spark05_Persist.java:26 []
        //---------------------------------
        //(2) ShuffledRDD[2] at reduceByKey at Spark05_Persist.java:31 []
        // +-(2) MapPartitionsRDD[1] at mapToPair at Spark05_Persist.java:26 []
        //    |  ReliableCheckpointRDD[3] at collect at Spark05_Persist.java:35 []

        jsc.close();
    }
}
