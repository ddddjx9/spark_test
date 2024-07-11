package cn.edu.ustb.spark.rdd.requirement;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.List;

public class Spark_Reduce {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("Reduce");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6);
        /*System.out.println(jsc.parallelize(list).reduce(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        }));*/
        //System.out.println(jsc.parallelize(list).reduce(Integer::sum));

        final MyValue mv = new MyValue();
        jsc.parallelize(list).foreach(num -> {
            System.out.println(num);
            mv.sum += num;
        });
        System.out.println(mv.sum);

        jsc.close();
    }
}


