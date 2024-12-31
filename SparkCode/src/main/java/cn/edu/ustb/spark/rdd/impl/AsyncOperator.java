package cn.edu.ustb.spark.rdd.impl;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

// 定义异步处理的接口
public interface AsyncOperator<T> extends Serializable {
    CompletableFuture<T> asyncProcess(T element);
}