package cn.edu.ustb.spark.sparkSQL.requirement;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.expressions.Aggregator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义UDAF类，输入城市名，输出拼接好的字符串
 */
public class MyCityRemarkUDAF extends Aggregator<String, MyCityRemarkBuffer, String> {
    /**
     * 初始化缓冲区内容
     *
     * @return 返回初始化后的缓冲区
     */
    @Override
    public MyCityRemarkBuffer zero() {
        return new MyCityRemarkBuffer(0L, new HashMap<>());
    }

    /**
     * 根据传递进来的城市名，对缓冲区中的数据进行操作
     *
     * @param b 要进行操作的缓冲区
     * @param a 传递进来的城市名
     * @return 返回计算完毕后的缓冲区
     */
    @Override
    public MyCityRemarkBuffer reduce(MyCityRemarkBuffer b, String a) {
        b.setCount(b.getCount() + 1);
        final Map<String, Long> cityMap = b.getCityMap();
        if (cityMap.containsKey(a)) {
            cityMap.put(a, cityMap.get(a) + 1);
        } else {
            cityMap.put(a, 1L);
        }
        b.setCityMap(cityMap);
        return b;
    }

    /**
     * 将多个节点缓冲区中的数据进行聚合
     *
     * @param b1 节点1的缓冲区
     * @param b2 节点2的缓冲区
     * @return 返回聚合之后的缓冲区
     */
    @Override
    public MyCityRemarkBuffer merge(MyCityRemarkBuffer b1, MyCityRemarkBuffer b2) {
        b1.setCount(b1.getCount() + b2.getCount());
        final Map<String, Long> map1 = b1.getCityMap();
        final Map<String, Long> map2 = b2.getCityMap();
        final Set<String> set1 = map1.keySet();
        final Set<String> set2 = map2.keySet();
        for (String s1 : set1) {
            for (String s2 : set2) {
                if (s1.equals(s2)) {
                    map1.put(s1, map1.get(s1) + map2.get(s1));
                }
            }
        }
        b1.setCityMap(map1);
        return b1;
    }

    /**
     * 根据缓冲区中的内容计算最后的结果，并拼接位客户要求的字符串
     *
     * @param reduction 给出最后计算完毕之后的缓冲区
     * @return 返回拼接好之后的字符串
     */
    @Override
    public String finish(MyCityRemarkBuffer reduction) {
        return "";
    }

    /**
     * 指定缓冲区中的数据类型，如果包含两个及两个以上的数据，那么可以采用自定义缓冲区类
     *
     * @return 返回定义好的缓冲区类型
     */
    @Override
    public Encoder<MyCityRemarkBuffer> bufferEncoder() {
        return Encoders.bean(MyCityRemarkBuffer.class);
    }

    /**
     * 指定最终输出的编码类型
     *
     * @return 返回最终输出的编码类型
     */
    @Override
    public Encoder<String> outputEncoder() {
        return Encoders.STRING();
    }
}
