package cn.edu.ustb.spark.rdd.userDefinedOperator;

import cn.edu.ustb.spark.rdd.userDefinedOperator.impl.AsyncOperator;
import org.apache.spark.Partition;
import org.apache.spark.TaskContext;
import org.apache.spark.rdd.RDD;
import scala.collection.Iterator;
import scala.reflect.ClassTag;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.*;

/**
 * 自定义异步算子
 * @param <T> 指定的算子泛型
 */
public class AsyncRDD<T> extends RDD<T> {

    private final RDD<T> previousRDD;
    private final AsyncOperator<T> operator;
    private final List<CompletableFuture<T>> futures;
    private final ExecutorService executorService;

    public AsyncRDD(RDD<T> prev, AsyncOperator<T> operator) {
        super(prev, ClassTag.apply(AsyncRDD.class));
        this.previousRDD = prev;
        this.operator = operator;
        this.futures = new ArrayList<>();
        // 创建线程池
        this.executorService = Executors.newCachedThreadPool();
    }

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
                            // 获取I/O操作结果
                            return operator.asyncProcess(element).get();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }, executorService);
                    futures.add(futureResult);

                    // 注册回调，在结果准备好时设置当前结果并标记
                    futureResult.thenAccept(result -> {
                        if (result != null) {
                            currentResult = result;
                            hasResult = true;
                        }
                    });

                    // 如果没有结果，继续获取下一个元素
                    return false;
                }
                // 完成了所有处理，没发现有可用结果
                return false;
            }

            @Override
            public T next() {
                // 遇到没有结果的情况，抛异常
                if (!hasResult && !hasNext()) {
                    throw new NoSuchElementException();
                }
                // 重置状态
                hasResult = false;
                return currentResult;
            }
        };
    }

    public List<T> getResults() {
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        try {
            // 等待所有异步操作完成
            allOf.join();
        } catch (CompletionException e) {
            // 处理异步操作中发生的异常
            e.getCause().printStackTrace();
        }

        List<T> results = new ArrayList<>();
        for (CompletableFuture<T> future : futures) {
            try {
                // 收集结果
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public Partition[] getPartitions() {
        return previousRDD.partitions();
    }

    // 清理线程池
    public void shutdown() {
        executorService.shutdown();
    }
}