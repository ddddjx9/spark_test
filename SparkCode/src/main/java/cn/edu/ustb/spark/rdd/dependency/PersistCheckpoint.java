package cn.edu.ustb.spark.rdd.dependency;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class PersistCheckpoint {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);
        //TODO 设定检查点路径：推荐使用HDFS的共享文件系统
        //      也可以使用本地文件路径进行练习
        jsc.setCheckpointDir("checkpoint");

        final ArrayList<Tuple2<String, Integer>> datas = new ArrayList<>();

        datas.add(new Tuple2<>("a", 1));
        datas.add(new Tuple2<>("a", 2));
        datas.add(new Tuple2<>("a", 3));
        datas.add(new Tuple2<>("a", 4));

        final JavaPairRDD<String, Integer> rdd = jsc.parallelize(datas, 2).mapToPair(tuple -> {
            System.out.println("*****************************");
            return tuple;
        });

        //利用中间件进行持久化操作
        //报错：Exception in thread "main" org.apache.spark.SparkException:
        // Checkpoint directory has not been set in the SparkContext
        rdd.cache();
        rdd.checkpoint();

        rdd.reduceByKey(Integer::sum).collect();
        System.out.println("1计算完毕");
        System.out.println("#################################");

        rdd.groupByKey().collect();
        System.out.println("2计算完毕");

        jsc.close();
    }
}
