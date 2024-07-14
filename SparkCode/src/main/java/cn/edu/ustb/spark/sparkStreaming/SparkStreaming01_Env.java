package cn.edu.ustb.spark.sparkStreaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class SparkStreaming01_Env {
    public static void main(String[] args) throws InterruptedException {
        //构建环境对象
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]").setAppName("Streaming");

        //传递两个参数
        // 第一个参数是环境配置
        //  第二个参数是指批量传递的周期 - 多长时间攒一批
        //  每3秒将数据传递给我
        final JavaStreamingContext streamingContext = new JavaStreamingContext(conf, new Duration(3000));

        //启动数据采集器
        //报错：Exception in thread "main" java.lang.IllegalArgumentException:
        // requirement failed: No output operations registered, so nothing to execute
        //  原因：没有调用行动算子，必须调用行动算子，否则不执行，直接报错
        streamingContext.start();

        //数据采集器是一个长期运行的任务，所以它不能停止，也不能释放资源
        //streamingContext.close();
        //此行代码是等待采集器关闭，阻塞主线程
        streamingContext.awaitTermination();
    }
}
