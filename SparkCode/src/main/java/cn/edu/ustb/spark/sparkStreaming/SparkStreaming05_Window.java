package cn.edu.ustb.spark.sparkStreaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;

public class SparkStreaming05_Window {
    public static void main(String[] args) throws InterruptedException {
        final SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("Window");

        final JavaStreamingContext jsc = new JavaStreamingContext(conf, new Duration(3000));

        final JavaReceiverInputDStream<String> socketDS = jsc.socketTextStream("localhost", 9999);
        final JavaDStream<String> flatDS = socketDS.flatMap(line -> Arrays.asList(line.split(" ")).iterator());

        final JavaPairDStream<String, Integer> wordDS = flatDS.mapToPair(word -> new Tuple2<>(word, 1));
        //窗口方法 - 窗口函数
        //  窗口实际上指的就是数据的范围（时间范围）
        //  window方法可以改变窗口的数据范围（默认的数据范围为采集周期）
        //  window方法可以传递两个参数
        //      第一个参数表示窗口的数据范围（时间）
        //      第二个参数表示窗口的移动幅度
        //      spark streaming是在窗口移动的时候计算的，以窗口移动幅度作为计算标准
        final JavaPairDStream<String, Integer> windowDS = wordDS.window(new Duration(3000), new Duration(6000));

        final JavaPairDStream<String, Integer> wordCountDS = windowDS.reduceByKey(Integer::sum);

        wordCountDS.print();
        jsc.start();
        jsc.awaitTermination();
    }
}
