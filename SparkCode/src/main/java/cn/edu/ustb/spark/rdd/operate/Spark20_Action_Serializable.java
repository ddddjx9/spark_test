package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark20_Action_Serializable {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<String> nums = Arrays.asList("Hadoop", "Hive", "Spark", "Flink");
        final JavaRDD<String> rdd = jsc.parallelize(nums, 2);

        //org.apache.spark.SparkException: Task not serializable
        //Caused by: java.io.NotSerializableException: cn.edu.ustb.spark.rdd.operate.Search
        //为了解决此问题，对Search进行序列化
        //为什么要对Search进行序列化呢？

        //RDD算子的逻辑代码是在Executor端执行的，其它代码都是在Driver端执行的
        Search s = new Search("H");
        s.match(rdd);

        jsc.close();
    }
}

/**
 * <p>
 * 感觉过滤逻辑每次都改很不方便，所以想做一个封装
 * </p>
 * <p>
 * 将整体逻辑封装好，只需要传递一个参数，用于查询即可
 * </p>
 * <p>
 * private final String query
 * </p>
 * <p>
 * public Search(String query) {
 * *         this.query = query;
 * *     }
 * </p>
 * <p>
 * 这两段代码都是在Driver端执行的
 * </p>
 * <p>
 * 这两段下面的代码都是在Executor端执行的
 * </p>
 * <p>
 * 执行Executor端的代码时，用到了query，这是在Driver执行的，所以需要从Driver拉取到Executor
 * </p>
 * <p>
 * 因为query是成员属性，所以需要将类进行加载，才能获取成员属性
 * </p>
 */
class Search {
    private final String query;

    public Search(String query) {
        this.query = query;
    }

    public void match(JavaRDD<String> rdd) {
        String q = this.query;
        rdd.filter(s -> !s.startsWith(q)).collect().forEach(System.out::println);
    }
}