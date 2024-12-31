package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

public class ReadDataSourceJDBC {
    public static void main(String[] args) {
        final SparkSession sparkSession = SparkSession.builder()
                .appName("JDBC")
                .master("local[*]")
                .getOrCreate();

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "Djx_150262549");

        final Dataset<Row> jdbc = sparkSession.read()
                .jdbc("jdbc:mysql://Hadoop130:3306/study?useSSl=false&useUnicode=true&characterEncoding=UTF-8", "user", properties);

        jdbc.write()
                .jdbc("jdbc:mysql://Hadoop130:3306/study?useSSl=false&useUnicode=true&characterEncoding=UTF-8", "user_info_test", properties);
        jdbc.show();

        sparkSession.close();
    }
}
