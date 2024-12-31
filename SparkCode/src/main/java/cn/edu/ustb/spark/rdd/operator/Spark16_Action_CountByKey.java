package cn.edu.ustb.spark.rdd.operator;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Spark16_Action_CountByKey {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Action_OtherMethod");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        final JavaRDD<Integer> rdd = jsc.parallelize(list, 2);

        final JavaPairRDD<Integer, Integer> pairRDD = rdd.mapToPair(element -> new Tuple2<>(element, element));

        //将前面的执行结果按照key来计算数量
        final Map<Integer, Long> map = pairRDD.countByKey();
        /*final Set<Map.Entry<Object, Long>> entries = map.entrySet();
        for (Map.Entry<Object, Long> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }*/

        //public final String toString() { return key + "=" + value; }
        //Map的实现类中有其具体的转为字符串的方法，如果想要进一步做操作，用上述代码即可
        System.out.println(map);
        //执行结果：
        //{5=1, 1=1, 6=1, 2=1, 3=1, 4=1}

        jsc.close();
    }
}
