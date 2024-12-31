package cn.edu.ustb.spark.rdd.userDefinedOperator.exception;

public class AsyncOperatorException extends RuntimeException {
    public AsyncOperatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
