package cn.edu.ustb.spark.rdd.dependency;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class PersistShuffleOperator {
    public static void main(String[] args) throws InterruptedException {
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

        final JavaPairRDD<String, Integer> rdd = jsc.parallelize(datas, 2)
                .mapToPair(tuple -> {
                    System.out.println("*********************");
                    return tuple;
                });

        // 所有的Shuffle性能都非常低
        // 所以Spark为了提升Shuffle算子的性能，每一个Shuffle算子都自动含有缓存
        final JavaPairRDD<String, Integer> wordCountRDD = rdd.reduceByKey(Integer::sum);
        //final JavaPairRDD<String, Integer> wordCountRDD1 = wordCountRDD.reduceByKey(Integer::sum);
        wordCountRDD.sortByKey().collect();
        System.out.println("##########################");
        wordCountRDD.groupByKey().collect();
//        wordCountRDD1.collect();
//        Thread.sleep(10000000L);

        //执行结果：
        //*********************
        //*********************
        //*********************
        //*********************
        //##########################

        jsc.close();
    }
}
