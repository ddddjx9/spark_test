package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class Spark04_Transform_FlatMap_1 {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final JavaRDD<String> rdd = jsc.textFile("datas/data1.txt");

        //此时发现是一行一行进行打印的，Hadoop读数据是一行一行进行读写的
        //hello world linux
        //hello spark linux
        //hello scala java
        //scala difficult
        //spark hadoop hive sql
        //mysql hive hbase flink

        //想要每一个字符串单独打印，而不是一行一行进行打印
        //map方法只负责转换数据，不能将数据拆分后使用

        final JavaRDD<String> newRDD = rdd.flatMap(line -> Arrays.asList(line.split(" ")).iterator());

        newRDD.collect().forEach(System.out::println);

        jsc.close();
    }
}
