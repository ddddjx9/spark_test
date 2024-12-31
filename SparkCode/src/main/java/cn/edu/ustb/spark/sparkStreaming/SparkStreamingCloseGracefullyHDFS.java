package cn.edu.ustb.spark.sparkStreaming;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.StreamingContextState;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SparkStreamingCloseGracefullyHDFS {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("Close_Gracefully_HDFS");

        final JavaStreamingContext jsc = new JavaStreamingContext(conf, new Duration(3000));

        new Thread(() -> {
            try {
                FileSystem fs = FileSystem.get(new URI("hdfs://Hadoop130:8020"),
                        new Configuration(), "root");
                while (true) {
                    Thread.sleep(5000);
                    //创建外部变量
                    //初始的时候，该路径并不存在，如果有需要这个停下来
                    //那么就创建该路径，让流关闭
                    boolean exists = fs.exists(new Path("hdfs://Hadoop130:8020/stopSpark"));
                    if (exists) {
                        final StreamingContextState state = jsc.getState();
                        //判断当前任务是否正在运行
                        if (state == StreamingContextState.ACTIVE) {
                            //优雅关闭环境
                            jsc.stop(true, true);
                            //关闭虚拟机
                            System.exit(0);
                        }
                    }
                }
            } catch (IOException | InterruptedException | URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
