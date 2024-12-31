package cn.edu.ustb.spark.rdd.operator.transform;

import cn.edu.ustb.spark.rdd.operator.funtion.NumberOperate;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class TransformMap {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final JavaRDD<Integer> rdd = jsc.parallelize(Arrays.asList(1, 2, 3, 4), 2);

        final JavaRDD<Integer> newRDD = rdd.map(NumberOperate::multiple2);

        rdd.saveAsTextFile("output1");
        newRDD.saveAsTextFile("output2");

        jsc.close();
    }
}
