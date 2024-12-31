package cn.edu.ustb.spark.rdd.impl.operator;

import cn.edu.ustb.spark.rdd.impl.AsyncOperator;

import java.util.concurrent.CompletableFuture;

/**
 * 自定义的异步操作
 */
public class ApiAsyncOperator implements AsyncOperator<String> {
    @Override
    public CompletableFuture<String> asyncProcess(String element) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return "Processed: " + element;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }
}