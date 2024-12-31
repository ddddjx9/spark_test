package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark20_Action_Serializable {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<String> nums = Arrays.asList("Hadoop", "Hive", "Spark", "Flink");
        final JavaRDD<String> rdd = jsc.parallelize(nums, 2);

        //org.apache.spark.SparkException: Task not serializable
        //Caused by: java.io.NotSerializableException: cn.edu.ustb.spark.rdd.operate.Search
        //为了解决此问题，对Search进行序列化
        //为什么要对Search进行序列化呢？

        //RDD算子的逻辑代码是在Executor端执行的，其它代码都是在Driver端执行的
        Search s = new Search("H");
        s.match(rdd);

        jsc.close();
    }
}
