package com.bitprogress.okhttp.handler;

import okhttp3.Response;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@FunctionalInterface
public interface ResponseHandler<T> {

    /**
     * 处理响应
     *
     * @param response 响应
     * @return 处理结果
     */
    T handle(Response response) throws IOException;

    /**
     * 默认方法支持链式操作
     *
     * @param mapper 映射函数
     * @return 映射结果
     */
    default <R> ResponseHandler<R> andThen(Function<T, R> mapper) {
        return response -> mapper.apply(this.handle(response));
    }

    /**
     * 默认方法支持链式操作
     *
     * @param predicate     映射函数
     * @param successMapper 映射函数
     * @return 映射结果
     */
    default ResponseHandler<T> andThenIf(Predicate<T> predicate, Function<T, T> successMapper) {
        return response -> {
            T result = this.handle(response);
            return predicate.test(result) ? successMapper.apply(result) : result;
        };
    }

    /**
     * 默认方法支持链式操作
     *
     * @param predicate     映射函数
     * @param successMapper 映射函数
     * @param failureMapper 映射函数
     * @return 映射结果
     */
    default <R> ResponseHandler<R> andThenIf(Predicate<T> predicate, Function<T, R> successMapper, Function<T, R> failureMapper) {
        return response -> {
            T result = this.handle(response);
            return predicate.test(result) ? successMapper.apply(result) : failureMapper.apply(result);
        };
    }

    /**
     * 异常处理
     *
     * @param errorHandler 错误处理
     * @return 错误处理结果
     */
    default ResponseHandler<T> onError(Function<Exception, T> errorHandler) {
        return response -> {
            try {
                return this.handle(response);
            } catch (Exception e) {
                return errorHandler.apply(e);
            }
        };
    }

}
