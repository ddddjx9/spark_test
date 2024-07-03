package cn.edu.ustb.spark.rdd.operate;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.io.Serializable;
import java.util.ArrayList;

public class Spark12_Transform_KV_SortByKey_1 {
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf();
        conf.setMaster("local[*]");
        conf.setAppName("spark");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        final ArrayList<Tuple2<User, Integer>> list = new ArrayList<>();

        User u1 = new User(30, 2000);
        User u2 = new User(40, 3000);
        User u3 = new User(30, 3000);
        User u4 = new User(40, 2500);

        list.add(new Tuple2<>(u1, 1));
        list.add(new Tuple2<>(u2, 3));
        list.add(new Tuple2<>(u3, 4));
        list.add(new Tuple2<>(u4, 2));

        //java.lang.ClassCastException User cannot be cast to java.lang.Comparable
        //sortByKey方法要求数据中的K必须可以进行比较，实现Comparable接口
        final JavaRDD<Tuple2<User, Integer>> rdd = jsc.parallelize(list);
        rdd.mapToPair(tuple -> tuple).sortByKey().collect().forEach(System.out::println);

        jsc.close();
    }
}

/**
 * 注意在执行前需要进行序列化，否则报错：
 * java.io.NotSerializableException
 */
class User implements Serializable, Comparable<User> {
    private final int age;
    private final int salary;

    public User(int age, int salary) {
        this.age = age;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", salary=" + salary +
                '}';
    }

    @Override
    public int compareTo(User o) {
        //this - o：升序，o - this：降序
        return this.age - o.age != 0 ? this.age - o.age : this.salary - o.salary;
    }
}
