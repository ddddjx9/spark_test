package cn.edu.ustb.spark.sparkStreaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class SparkStreamingSocket {
    public static void main(String[] args) throws InterruptedException {
        final SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("Socket");
        final JavaStreamingContext streamingContext = new JavaStreamingContext(conf, new Duration(3000));

        //通过环境对象对接Socket数据源，获取数据模型进行处理
        final JavaReceiverInputDStream<String> socketDS = streamingContext.socketTextStream("localhost", 9999);
        socketDS.print();

        streamingContext.start();
        streamingContext.awaitTermination();
    }
}
