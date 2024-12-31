package cn.edu.ustb.spark.rdd.partition;

import cn.edu.ustb.spark.rdd.partition.function.MyPartitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class SelfDefinedPartition {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Partition");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final ArrayList<Tuple2<String, Integer>> data = new ArrayList<>();

        data.add(new Tuple2<>("nba", 1));
        data.add(new Tuple2<>("cba", 2));
        data.add(new Tuple2<>("nba", 3));
        data.add(new Tuple2<>("wnba", 4));

        jsc.parallelize(data)
                .mapToPair(tuple -> tuple)
                .reduceByKey(new MyPartitioner(3), Integer::sum)
                .saveAsTextFile("output");

        jsc.close();
    }
}
