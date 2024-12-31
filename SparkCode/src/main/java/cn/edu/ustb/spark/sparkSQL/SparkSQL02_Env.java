package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQL02_Env {
    public static void main(String[] args) {
        // 构建环境对象
        //     Spark在结构化数据的处理过程中对核心功能，环境进行了封装
        //      采用构建器模式构建环境对象
        //      构建器模式：构建对象，将构建的过程进行封装，提供外置的接口进行调用，传递参数
        final SparkSession sparkSession = SparkSession
                .builder()
                .master("local[*]")
                .appName("SparkSQL")
                .getOrCreate();

        // SparkSQL对数据模型也进行了封装，将之前的RDD封装成了Dataset
        //      对接文件数据源时，会将文件中的一行数据封装为Row对象
        final Dataset<Row> ds = sparkSession.read().json("datas\\user.json");

        // 将数据模型转换为表，方便SQL的使用
        ds.createOrReplaceTempView("user");

        // 使用SQL文的方式来操作数据
        String sql = "select avg(age) from user";
        final Dataset<Row> sqlDS = sparkSession.sql(sql);

        // 展示数据模型的效果
        sqlDS.show();

        sparkSession.close();
    }
}
