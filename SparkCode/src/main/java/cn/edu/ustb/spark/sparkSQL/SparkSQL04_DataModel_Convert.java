package cn.edu.ustb.spark.sparkSQL;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSQL04_DataModel_Convert {
    public static void main(String[] args) {
        final SparkSession sparkSession = SparkSession.builder()
                .appName("Convert")
                .master("local[*]")
                .getOrCreate();

        final Dataset<Row> ds = sparkSession.read().json("datas\\user.json");
        //ds.rdd();
        //早期的处理方式，使用的是DataFrame
        /*
            ds.foreach(new ForeachFunction<Row>() {
                @Override
                public void call(Row row) throws Exception {
                    //这是按列取的，第二列
                    System.out.println(row.get(2));
                }
            });
        */

        // TODO 因为之前还要记住数据时第几列，很麻烦
        //      所以将数据模型中的数据类型进行转换，将Row转换为其他对象进行处理
        //      使用工具类Encoders.bean方法将当前的Row转换成User
        //      在您的情况下，出现了 No applicable constructor/method found for zero actual parameters 这个错误
        //      其中提到了 scala.math.BigInt(java.math.BigInteger)。
        //      这表明代码生成阶段尝试调用 BigInt 类的构造函数，其参数是 java.math.BigInteger 类型。
        //      在 Java 中，BigInt 和 BigInteger 是两个不同的类。
        //      scala.math.BigInt 是 Scala 提供的整数类，而 java.math.BigInteger是Java 标准库提供的整数类。
        //      由于在代码生成期间使用了与 scala.math.BigInt 不兼容的构造函数签名，因此出现了上述错误。
        final Dataset<User> userDataset = ds.as(Encoders.bean(User.class));
        userDataset.foreach(user -> {
            System.out.println(user);
        });

        sparkSession.close();
    }
}
