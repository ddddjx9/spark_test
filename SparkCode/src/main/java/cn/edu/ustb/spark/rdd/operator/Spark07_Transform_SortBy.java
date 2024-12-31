package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark07_Transform_SortBy {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);
        final List<Integer> list = Arrays.asList(1, 3, 2, 4, 33, 18);
        final JavaRDD<Integer> rdd = jsc.parallelize(list, 2);

        //sortBy方法：按照指定的排序规则对数据进行排序
        //  排序后原有的顺序被打乱，说明有Shuffle操作
        //      Spark会为每一个数据增加一个标记，然后按照标记对数据进行排序
        rdd.sortBy(v1 -> v1, true, 2)
                //.collect().forEach(System.out::println);
                .saveAsTextFile("output");

        jsc.close();
    }
}
