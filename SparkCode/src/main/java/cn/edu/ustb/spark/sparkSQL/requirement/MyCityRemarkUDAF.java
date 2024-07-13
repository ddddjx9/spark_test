package cn.edu.ustb.spark.sparkSQL.requirement;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.expressions.Aggregator;

import java.util.*;

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
     * 根据传递进来的城市名，对缓冲区中的数据进行聚合处理
     *
     * @param b        要进行操作的缓冲区
     * @param cityName 传递进来的城市名
     * @return 返回计算完毕后的缓冲区
     */
    @Override
    public MyCityRemarkBuffer reduce(MyCityRemarkBuffer b, String cityName) {
        b.setCount(b.getCount() + 1);
        final Map<String, Long> cityMap = b.getCityMap();
        if (cityMap.containsKey(cityName)) {
            cityMap.put(cityName, cityMap.get(cityName) + 1);
        } else {
            cityMap.put(cityName, 1L);
        }
        b.setCityMap(cityMap);
        return b;
    }

    /**
     * 合并缓冲区：将多个节点缓冲区中的数据进行聚合
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
        for (String s : set2) {
            //判断两个map中的键 - 城市名有没有重复的
            //  如果有重复的，那么就将两个城市中的count聚合
            //  如果没有重复的，直接将没有重复的那个放在原有的集合里面
            if (set1.contains(s)) {
                map1.put(s, map1.get(s) + map2.get(s));
            } else {
                map1.put(s, map2.get(s));
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
        //尝试将map转换为list进行排序，排序后截取集合的前两个元素
        final Map<String, Long> cityMap = reduction.getCityMap();
        List<Map.Entry<String, Long>> list = new ArrayList<>(cityMap.entrySet());
        list.sort((o1, o2) -> Integer.parseInt(String.valueOf(o2.getValue() - o1.getValue())));
        //截取集合，左包右不包 - subList
        //拼接字符串
        StringBuilder sb = new StringBuilder();
        Long topTwo = list.get(0).getValue() + list.get(1).getValue();

        for (int i = 0; i < 2; i++) {
            sb.append(list.get(i).getKey())
                    .append(String.format("%.1f", (list.get(i).getValue() * 1.0 / reduction.getCount()) * 100))
                    .append("%，");
        }

        if (list.size() > 2) {
            sb.append("其他")
                    .append(String.format("%.1f", ((reduction.getCount() - topTwo) * 1.0 / reduction.getCount()) * 100))
                    .append("%");
        }

        return sb.toString();
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
