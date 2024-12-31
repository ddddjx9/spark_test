package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark19_Action {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Action");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6);
        final JavaRDD<Integer> rdd = jsc.parallelize(nums, 2);

        //TODO 这里的对象是在Driver端创建的
        Student s = new Student();
        //TODO 执行过程中报错：
        // Exception in thread "main" org.apache.spark.SparkException: Task not serializable
        // Caused by: java.io.NotSerializableException: cn.edu.ustb.spark.rdd.operate.Student
        //   我们在Executor端循环遍历的时候使用到了Driver端的对象
        //      需要将Driver端的对象通过网络传递到Executor端，否则无法使用
        //          网络中不能够传递对象，只能传递字节码
        //              所以需要对这个对象进行序列化，拉取到Executor端
        //                  将对象变成字节码的过程叫做序列化，反之叫做反序列化
        rdd.foreach(num -> System.out.println(s.age + num));

        jsc.close();
    }
}