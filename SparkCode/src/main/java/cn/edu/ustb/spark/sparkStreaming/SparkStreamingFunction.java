package cn.edu.ustb.spark.sparkStreaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;

public class SparkStreamingFunction {
    public static void main(String[] args) throws InterruptedException {
        //使用Socket方法传递数据
        //实现word count功能
        final SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("Function");
        final JavaStreamingContext streamingContext = new JavaStreamingContext(conf, new Duration(3000));

        final JavaReceiverInputDStream<String> socketDS = streamingContext.socketTextStream("localhost", 9999);

        //万一一行中有多个单词
        final JavaDStream<String> flatDS = socketDS.flatMap(
                line -> Arrays.asList(line.split(" ")).iterator()
        );

        final JavaPairDStream<String, Integer> wordDS = flatDS.mapToPair(
                word -> new Tuple2<>(word, 1)
        );

        final JavaPairDStream<String, Integer> wordCountDS = wordDS.reduceByKey(Integer::sum);

        //循环遍历每一个RDD
        /*wordCountDS.foreachRDD(new VoidFunction<JavaPairRDD<String, Integer>>() {
            @Override
            public void call(JavaPairRDD<String, Integer> rdd) throws Exception {
                rdd.map(tuple -> tuple)
                        .sortBy(new Function<Tuple2<String, Integer>, Object>() {
                            @Override
                            public Object call(Tuple2<String, Integer> v1) throws Exception {
                                return v1._2;
                            }
                        }, false, 2)
                        .collect().forEach(System.out::println);
            }
        });*/

        wordCountDS.foreachRDD(rdd -> rdd.map(tuple -> tuple)
                .sortBy(tuple -> tuple._2, false, 2)
                .collect().forEach(System.out::println));

        streamingContext.start();
        streamingContext.awaitTermination();
    }
}
