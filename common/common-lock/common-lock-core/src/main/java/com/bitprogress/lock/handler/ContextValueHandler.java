package com.bitprogress.lock.handler;

public interface ContextValueHandler {

    /**
     * 获取上下文值
     *
     * @param expression 表达式
     * @return 上下文值
     */
    String getContextValue(String expression);

}
