package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

/**
 * 使用Kryo框架进行序列化操作
 * <p>
 * Spark中默认的是Java的序列化，如果想要使用Kryo框架，需要进行一定的设置
 * </p>
 * <p>
 * Spark自己的类会使用Kryo进行序列化，但是自己定义的类Spark不会用，除非明确给出配置
 * </p>
 */
public class Spark21_Serializable {
    public static void main(String[] args) throws ClassNotFoundException {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]")
                .setAppName("Serializable")
                //替换默认的序列化机制
                .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                //注册需要使用kryo序列化的自定义类
                .registerKryoClasses(new Class[]{Class.forName("cn.edu.ustb.spark.rdd.operator.User1")});

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        User1 u1 = new User1(20, "zhangsan");
        User1 u2 = new User1(19, "lisi");

        jsc.parallelize(Arrays.asList(u1, u2), 2)
                .sortBy(User1::getAge, true, 2)
                .collect().forEach(System.out::println);

        jsc.close();
    }
}