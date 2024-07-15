package cn.edu.ustb.spark.sparkStreaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class SparkStreaming07_CloseGracefully {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("CloseGracefully");

        final JavaStreamingContext jsc = new JavaStreamingContext(conf, new Duration(3000));

        final JavaReceiverInputDStream<String> socketDs = jsc.socketTextStream("localhost", 9999);
        socketDs.print();

        jsc.start();

        //但是程序运行过程中无法动态改变这里flag的值
        boolean flag = false;
        //通过外部变量控制进程
        new Thread((new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //每5秒判断一次，如果flag为true，就停止进程 -> 进程交互
                    try {
                        Thread.sleep(5000);
                        if (flag) {
                            jsc.stop();
                        }
                    } catch (Exception e) {
                        throw new RuntimeException();
                    }
                }
            }
        }));
    }
}
