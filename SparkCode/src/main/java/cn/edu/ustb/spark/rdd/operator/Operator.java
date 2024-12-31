package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple1;
import scala.Tuple2;

import java.util.Arrays;

public class Operator {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final JavaRDD<Integer> rdd = jsc.parallelize(
                Arrays.asList(1, 2, 3, 4)
        );

        //jdk1.8中以后也存在元组，采用特殊的类
        //元组最大数据容量为22
        final Tuple1<String> data1 = new Tuple1<>("abc");
        final Tuple2<String, String> data2 = new Tuple2<>("ac", "b");

        System.out.println(data1);
        System.out.println(data2._1);
        System.out.println(data2._2);

        System.out.println(rdd.reduce(Integer::max));

        jsc.close();
    }
}
