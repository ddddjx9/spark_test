package cn.edu.ustb.spark.rdd.partition;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class Spark01_Partition {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final ArrayList<Tuple2<String, Integer>> datas = new ArrayList<>();

        datas.add(new Tuple2<>("a", 1));
        datas.add(new Tuple2<>("a", 2));
        datas.add(new Tuple2<>("a", 3));
        datas.add(new Tuple2<>("a", 4));

        //先在分区内求和之后，在分区间求和
        final JavaPairRDD<String, Integer> rdd = jsc.parallelize(datas, 3).mapToPair(tuple -> tuple)
                //TODO reduceByKey需要传递两个参数
                //      第一个参数表示数据分区的规则，参数可以不用传递，使用时会用默认分区规则：HashPartitioner
                //          HashPartitioner中有一个方法，这个方法的名字叫getPartition
                //              getPartition需要传递一个参数，也就是key，然后方法需要返回一个值，表示分区编号，从0开始
                //                  逻辑：分区编号 <= Key.hashCode % partNum（哈希取余）
                //      第二个参数表示数据聚合的逻辑
                .reduceByKey(Integer::sum);

        //TODO 数据分区的规则
        //      计算后数据所在的分区是通过Spark的内部计算完成的，也就是分区规则
        //      尽可能让数据均衡（散列算法），但不是平均分
        rdd.sortByKey();

        rdd.saveAsTextFile("output");
        jsc.close();
    }
}
