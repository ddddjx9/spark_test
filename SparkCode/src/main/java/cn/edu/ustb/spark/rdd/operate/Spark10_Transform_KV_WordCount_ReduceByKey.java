package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.ArrayList;

public class Spark10_Transform_KV_WordCount_ReduceByKey {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        //conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final ArrayList<Tuple2<String, Integer>> datas = new ArrayList<>();

        datas.add(new Tuple2<>("a", 1));
        datas.add(new Tuple2<>("a", 2));
        datas.add(new Tuple2<>("a", 3));
        datas.add(new Tuple2<>("b", 4));

        final JavaRDD<Tuple2<String, Integer>> rdd = jsc.parallelize(datas);
        final JavaPairRDD<String, Integer> mapRDD = rdd.mapToPair(tuple -> tuple);

        //将分组聚合功能进行简化操作
        /*mapRDD.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1+v2;
            }
        });*/

        //运行结果：
        //(a,4)
        //(b,6)
        //reduceByKey的作用：按照相同的Key来做reduce
        //      和groupByKey不同的是，拥有在Shuffle之前，分区内部进行Combiner的功能，减少落盘数据量
        //将KV类型的数据按照key对value进行reduce操作，将多个值聚合成一个值
        mapRDD.reduceByKey(Integer::sum).collect().forEach(System.out::println);

        jsc.close();
    }
}
