package cn.edu.ustb.spark.rdd.operator.transform;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class TransformGroupBy1 {
    public static void main(String[] args) throws InterruptedException {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        final JavaRDD<Integer> rdd = jsc.parallelize(nums, 3);

        rdd.groupBy(v1 -> v1 % 2 == 0, 2)
                .collect()
                .forEach(System.out::println);

        System.out.println("计算完毕");
        //http://localhost:4040，本地模式，访问该端口就能够访问到监控界面
        //这时候我们为了让监控界面中依然能够显示资源使用情况
        //计算完毕后进入休眠，这样进程就不会立即释放
        Thread.sleep(10000000L);

        jsc.close();
    }
}
