package cn.edu.ustb.spark.rdd.partition;

import cn.edu.ustb.spark.rdd.partition.function.MyPartitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class SelfDefinedPartitioner {
    public static void main(String[] args) throws InterruptedException {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Partition");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final ArrayList<Tuple2<String, Integer>> data = new ArrayList<>();

        data.add(new Tuple2<>("nba", 1));
        data.add(new Tuple2<>("cba", 2));
        data.add(new Tuple2<>("nba", 3));
        data.add(new Tuple2<>("wnba", 4));

        //预测：2个阶段，2 + 3 = 5个Task
        final JavaPairRDD<String, Integer> reduceRDD = jsc.parallelize(data, 2)
                .mapToPair(tuple -> tuple)
                //.reduceByKey(Integer::sum);
                .reduceByKey(new MyPartitioner(3), Integer::sum);
        final JavaPairRDD<String, Integer> reduceRDD1 =
                //reduceRDD.reduceByKey(Integer::sum);
                reduceRDD.reduceByKey(new MyPartitioner(3), Integer::sum);
        reduceRDD1.collect();
        System.out.println("计算完毕");
        Thread.sleep(1000000L);

        jsc.close();
    }
}
