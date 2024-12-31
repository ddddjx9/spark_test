package cn.edu.ustb.spark.sparkSQL.function;


import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.expressions.Aggregator;

/**
 * <p>
 * 创建自定义的公共类！！！
 * </p>
 * 自定义UDAF函数，实现年龄平均值
 * <p>
 * &nbsp&nbsp编写自定义类
 * </p>
 * <p>
 * &nbsp&nbsp继承Aggregator抽象类
 * </p>
 * <p>
 * &nbsp&nbsp指定泛型
 * </p>
 * <p>
 * &nbsp&nbsp abstract class Aggregator[-IN, BUF, OUT] extends Serializable {
 * </p>
 * <p>
 * &nbsp&nbsp这里的Buffer指的是缓冲区的数据类型
 * </p>
 */
public class MyAvgAgeUDAF extends Aggregator<Long, AvgAgeBuffer, Long> {

    /**
     * 初始化方法 - 初始化缓冲区
     */
    @Override
    public AvgAgeBuffer zero() {
        //声明缓冲区为空
        return new AvgAgeBuffer(0L, 0L);
    }

    /**
     * 聚合 - 将输入的值和缓冲区的值做reduce操作
     *
     * @param buffer 缓冲区中存储的值
     * @param in     输入的值
     * @return 返回经过计算逻辑后，聚合完毕之后的值
     */
    @Override
    public AvgAgeBuffer reduce(AvgAgeBuffer buffer, Long in) {
        buffer.setTotal(buffer.getTotal() + in);
        buffer.setCount(buffer.getCount() + 1);
        return buffer;
    }

    /**
     * 分布式计算框架：合并缓冲区的数据
     * <p>
     * 任务可能在多个节点上运行，所以需要合并各个节点的缓冲区数据
     * </p>
     *
     * @param b1 其中一个缓冲区
     * @param b2 另一个节点的缓冲区
     * @return 返回合并之后的缓冲区数据
     */
    @Override
    public AvgAgeBuffer merge(AvgAgeBuffer b1, AvgAgeBuffer b2) {
        b1.setTotal(b1.getTotal() + b2.getTotal());
        b1.setCount(b1.getCount() + b2.getCount());
        return b1;
    }

    /**
     * 计算最终结果
     *
     * @param reduction 将缓冲区对象传递进来
     * @return 返回最终逻辑计算后的结果
     */
    @Override
    public Long finish(AvgAgeBuffer reduction) {
        return reduction.getTotal() / reduction.getCount();
    }

    /**
     * 指定缓冲区中的编码类型
     *
     * @return 返回指定编码类型
     */
    @Override
    public Encoder<AvgAgeBuffer> bufferEncoder() {
        //固定写法：如果是自己定义的对象，就叫bean
        //          如果是Java中自带的类，直接调用相应的方法即可
        return Encoders.bean(AvgAgeBuffer.class);
    }

    /**
     * 指定输出的编码类型
     *
     * @return 返回指定的编码类型
     */
    @Override
    public Encoder<Long> outputEncoder() {
        return Encoders.LONG();
    }
}
