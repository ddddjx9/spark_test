package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark06_Transform_Distinct {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);
        final List<Integer> list = Arrays.asList(1, 1, 1, 1, 2, 2);

        //RDD中的distinct：去重方法
        //HashSet：单点去重，每条数据都要去重
        //distinct：分布式去重，利用多个分布式节点去重
        //底层应该会打乱数据，将相同的数据放在同一个节点，这样去重会更方便，否则在两个节点不好去重
        final JavaRDD<Integer> rdd = jsc.parallelize(list);
        rdd.distinct().collect().forEach(System.out::println);

        jsc.close();
    }
}
