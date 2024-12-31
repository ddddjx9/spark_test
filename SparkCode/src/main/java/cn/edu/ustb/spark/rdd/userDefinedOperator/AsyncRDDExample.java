package cn.edu.ustb.spark.rdd.userDefinedOperator;

import cn.edu.ustb.spark.rdd.userDefinedOperator.impl.AsyncOperator;
import cn.edu.ustb.spark.rdd.userDefinedOperator.rdd.AsyncRDD;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AsyncRDDExample {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("AsyncRDDExample").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);

        // 创建一个 JavaRDD
        JavaRDD<String> inputRDD = jsc.parallelize(Arrays.asList("item1", "item2", "item3"));

        // 转换为 AsyncRDD
        AsyncRDD<String> asyncRDD = new AsyncRDD<>(inputRDD.rdd(), new AsyncOperator<String>() {
            @Override
            public CompletableFuture<String> asyncProcess(String element) {
                return CompletableFuture.supplyAsync(() -> {
                    try {
                        Thread.sleep(1000);
                        return "Processed: " + element;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });

        // AsyncRDD.fromRDD(inputRDD, asyncRDD);

        List<String> results = asyncRDD.getResults();
        results.forEach(System.out::println);

        jsc.close();
    }
}
