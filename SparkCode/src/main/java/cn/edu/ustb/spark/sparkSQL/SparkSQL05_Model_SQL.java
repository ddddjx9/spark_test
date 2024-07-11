package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQL05_Model_SQL {
    public static void main(String[] args) {
        final SparkSession sparkSession = SparkSession
                .builder()
                .master("local[*]")
                .appName("Model")
                .getOrCreate();
        final Dataset<Row> ds = sparkSession.read().json("datas\\user.json");

        //TODO 模型对象的使用
        //      将数据模型转换为二维的结构（行，列），可以使用SQL进行访问
        //      视图：表的查询结果集；表是可以增删改的
        //      视图不能增删改，只能查询
        ds.createOrReplaceTempView("user");

        //当前JDK版本不适合开发SQL文 SQL通常很长，而Java不支持多行字符串的原样输出，但是Scala支持
        final Dataset<Row> sql = sparkSession.sql("select sum(age) from user");
        sql.show();

        sparkSession.close();
    }
}
