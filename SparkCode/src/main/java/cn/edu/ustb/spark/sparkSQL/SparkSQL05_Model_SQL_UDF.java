package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;

public class SparkSQL05_Model_SQL_UDF {
    public static void main(String[] args) {
        final SparkSession sparkSession = SparkSession
                .builder()
                .master("local[*]")
                .appName("spark sql")
                .getOrCreate();

        final Dataset<Row> ds = sparkSession.read().json("datas\\user.json");
        ds.createOrReplaceTempView("user");
        //request：想要在姓名前面加上Name: ，实现Name: zhangsan这样的结果
        //      但是这样的方法不具备通用性
        //      有两个解析框架 Spark on Hive => 由Spark进行解析
        //                   Hive on Spark => 由Hive进行解析
        //                   两个框架解析规则不尽相同，所以可能会出现在hive上能跑但是没法在Spark上跑的情况
        //String sql = "select concat('Name: ',name) from user";
        //final Dataset<Row> sqlDS = sparkSession.sql(sql);
        //sqlDS.show();

        // SparkSQL提供了一种特殊的方式，可以在SQL中添加自定义方法来实现复杂的逻辑
        //      如果想要自定义的方法能够在SQL文中使用，那么必须在Spark中进行声明和注册
        //          第一个参数表示SQL中使用的方法名
        //          第二个参数表示逻辑：In => Out
        //          第三个参数表述返回的数据类型：DataType类型数据，需要使用Scala语法操作 -> 特殊的使用方式
        //              Scala Object => Java
        sparkSession.udf().register("prefixName", (UDF1<String, String>) name -> "Name: " + name, DataTypes.StringType); //=> user defined function
        String sql = "select prefixName(name) from user";
        final Dataset<Row> sqlDS = sparkSession.sql(sql);
        sqlDS.show();

        sparkSession.close();
    }
}
