package com.bitprogress.coreflux.holder;

import org.springframework.web.server.ServerWebExchange;

public final class ReactiveRequestContextHolder {

    /**
     * 线程本地变量，用于存储当前请求上下文
     */
    private static final ThreadLocal<ServerWebExchange> EXCHANGE_HOLDER = new ThreadLocal<>();

    private ReactiveRequestContextHolder() {
    }

    /**
     * 设置当前请求上下文
     *
     * @param exchange 当前请求上下文
     */
    public static void setExchange(ServerWebExchange exchange) {
        EXCHANGE_HOLDER.set(exchange);
    }

    /**
     * 获取当前请求上下文
     *
     * @return 当前请求上下文
     */
    public static ServerWebExchange getExchange() {
        return EXCHANGE_HOLDER.get();
    }

    /**
     * 清除当前请求上下文
     */
    public static void reset() {
        EXCHANGE_HOLDER.remove();
    }

}