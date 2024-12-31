package cn.edu.ustb.spark.rdd.userDefinedOperator.rdd;

import cn.edu.ustb.spark.rdd.userDefinedOperator.exception.AsyncOperatorException;
import cn.edu.ustb.spark.rdd.userDefinedOperator.impl.AsyncOperator;
import org.apache.spark.Partition;
import org.apache.spark.TaskContext;
import org.apache.spark.rdd.RDD;
import scala.collection.Iterator;
import scala.reflect.ClassTag;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * 自定义异步算子类，用于在基于RDD的数据处理中实现异步操作
 * @param <T> 指定的算子泛型，表示RDD中元素的类型
 */
public class AsyncRDD<T> extends RDD<T> {
    /**
     * 依赖的前置RDD
     */
    private final RDD<T> previousRDD;
    /**
     * 自定义的异步算子，定义了具体的异步处理逻辑，针对每个RDD元素进行操作
     */
    private final AsyncOperator<T> operator;
    /**
     * 用于存放各个异步操作对应的CompletableFuture对象
     */
    private final List<CompletableFuture<T>> futures;
    /**
     * 线程池对象，用于执行异步任务
     */
    private final ExecutorService executorService;

    /**
     * 构造函数，初始化AsyncRDD对象
     *
     * @param prev    前置的RDD
     * @param operator 自定义的异步算子
     */
    public AsyncRDD(RDD<T> prev, AsyncOperator<T> operator) {
        super(prev, ClassTag.apply(AsyncRDD.class));
        this.previousRDD = prev;
        this.operator = operator;
        this.futures = new ArrayList<>();
        // 创建线程池
        this.executorService = Executors.newCachedThreadPool();
    }

    /**
     * 重写compute方法，实现异步计算逻辑
     * 针对每个分区的数据元素，通过异步方式应用算子操作，并处理结果获取和状态管理
     *
     * @param split   数据分区
     * @param context 任务上下文
     * @return 迭代器，用于遍历经过异步处理后的分区数据元素
     */
    @Override
    public Iterator<T> compute(Partition split, TaskContext context) {
        final Iterator<T> data = previousRDD.iterator(split, context);
        return new Iterator<T>() {
            private T currentResult;
            private boolean hasResult = false;

            @Override
            public boolean hasNext() {
                // 判断是否已有可用结果
                if (hasResult) {
                    return true;
                }
                // 循环直到获取到结果
                while (data.hasNext()) {
                    T element = data.next();
                    CompletableFuture<T> futureResult = CompletableFuture.supplyAsync(() -> {
                        try {
                            // 获取I/O操作结果，这里通过异步算子执行异步处理，并返回结果
                            return operator.asyncProcess(element).get();
                        } catch (Exception e) {
                            // 抛出自定义异常，便于更合理地处理异步操作中的错误
                            throw new AsyncOperatorException("异步算子处理元素时出错", e);
                        }
                    }, executorService);
                    futures.add(futureResult);

                    // 注册回调，在结果准备好时设置当前结果并标记
                    futureResult.thenAccept(result -> {
                        if (result!= null) {
                            currentResult = result;
                            hasResult = true;
                        }
                    });

                    // 如果当前还没有获取到结果，继续获取下一个元素
                    if (!hasResult) {
                        return false;
                    }
                }
                // 完成了所有处理，没发现有可用结果
                return false;
            }

            @Override
            public T next() {
                // 遇到没有结果的情况，抛异常
                if (!hasResult &&!hasNext()) {
                    throw new NoSuchElementException();
                }
                // 重置状态
                hasResult = false;
                return currentResult;
            }
        };
    }

    /**
     * 获取所有异步操作的结果列表
     * 首先等待所有异步操作完成，然后收集每个异步操作对应的结果
     *
     * @return 包含所有异步操作结果的列表
     */
    public List<T> getResults() {
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            // 等待所有异步操作完成
            allOf.join();
        } catch (CompletionException e) {
            // 处理异步操作中发生的异常
            e.getCause().printStackTrace();
        }

        return futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (Exception e) {
                        // 异常处理，例如记录日志等，这里简单打印堆栈信息
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    /**
     * 重写getPartitions方法，返回依赖的前置RDD的分区信息
     *
     * @return 分区数组，与前置RDD的分区一致
     */
    @Override
    public Partition[] getPartitions() {
        return previousRDD.partitions();
    }

    /**
     * 清理线程池资源，在使用完AsyncRDD后应该调用此方法来释放线程相关资源
     */
    public void shutdown() {
        executorService.shutdown();
    }

    /**
     * 将给定的RDD转换为AsyncRDD，通过指定的异步算子进行异步处理
     *
     * @param rdd      原始的RDD
     * @param operator 自定义的异步算子
     * @return 转换后的AsyncRDD对象
     */
    public static <T> AsyncRDD<T> fromRDD(RDD<T> rdd, AsyncOperator<T> operator) {
        return new AsyncRDD<>(rdd, operator);
    }
}