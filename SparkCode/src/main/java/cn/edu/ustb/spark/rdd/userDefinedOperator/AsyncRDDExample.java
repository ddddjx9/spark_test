package cn.edu.ustb.spark.rdd.userDefinedOperator;

import cn.edu.ustb.spark.rdd.userDefinedOperator.impl.AsyncOperator;
import cn.edu.ustb.spark.rdd.userDefinedOperator.impl.operator.ApiAsyncOperator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class AsyncRDDExample {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("AsyncRDDExample").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        // 创建一个 JavaRDD
        JavaRDD<String> inputRDD = jsc.parallelize(Arrays.asList("item1", "item2", "item3"));

        // 转换为 AsyncRDD
        AsyncOperator<String> operator = new ApiAsyncOperator();
        AsyncRDD<String> asyncRDD = new AsyncRDD<>(inputRDD.rdd(), operator);

        // 将类型声明为Object，因为collect返回类型实际是Object类型
        List<String> results = asyncRDD.getResults();
        results.forEach(System.out::println);

        jsc.close();
    }
}
