package com.bitprogress.ormcontext.service;

import com.bitprogress.ormmodel.enums.ParserType;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.info.parser.BaseParserInfo;
import com.bitprogress.util.JsonUtils;
import com.bitprogress.util.StringUtils;

import java.util.Objects;

/**
 * 解析上下文信息
 *
 * @param <T> 解析信息类型
 */
public interface ParserContextService<T extends BaseParserInfo> {

    /**
     * 获取数据范围解析信息
     */
    T getParserInfo();

    /**
     * 设置数据范围解析信息
     *
     * @param parserInfo 数据范围解析信息
     */
    void setParserInfo(T parserInfo);

    /**
     * 清除数据范围解析信息
     */
    void clearParserInfo();

    /**
     * 进入 sql 解析模式
     * {@link #getParserInfo()} != null and {@link BaseParserInfo#getEnable()} == true
     * {@link BaseParserInfo#getSqlTypes()} match sqlType
     * 进入 sql解析模式后，才会启用 {@link BaseParserInfo#getParserType()}
     *
     * @param sqlType sql 类型
     * @return true：进入 sql解析模式，false：未进入 sql 解析模式
     */
    default Boolean onParser(SqlType sqlType) {
        T parserInfo = getParserInfo();
        return Objects.nonNull(parserInfo)
                && parserInfo.getEnable()
                && matchSqlType(parserInfo.getSqlTypes(), sqlType);
    }

    /**
     * 匹配是否含有对应的 sql类型
     * 含有 {@link SqlType#NONE} 则表示包含所有的 sql类型
     *
     * @param sqlTypes sql 生效的类型
     * @param sqlType  当前 sql类型
     * @return true：当前 sql解析模式对当前 sql类型生效，false：不生效
     */
    default boolean matchSqlType(SqlType[] sqlTypes, SqlType sqlType) {
        if (Objects.isNull(sqlType)) {
            return false;
        }
        for (SqlType type : sqlTypes) {
            if (type.equals(SqlType.NONE) || type.equals(sqlType)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否忽略语句处理
     * 需要在 {@link #onParser(SqlType)} == true 的前提下使用
     * 当 {@link BaseParserInfo#getParserType()} == {@link ParserType#INCLUDE}，return false
     *
     * @return true：忽略解析
     */
    default Boolean ignoreProcess() {
        return ParserType.INCLUDE != getParserInfo().getParserType();
    }

    /**
     * 检查是否是 rpc 传递
     *
     * @return true：是 rpc 传递
     */
    default Boolean isRpcPropagate() {
        T parserInfo = getParserInfo();
        return Objects.nonNull(parserInfo)
                && parserInfo.getEnable()
                && parserInfo.getRpcPropagate();
    }

    /**
     * 获取当前 sql解析信息
     *
     * @return 当前 sql解析信息
     */
    default String getParserInfoJson() {
        return isRpcPropagate() ? JsonUtils.serializeObject(getParserInfo()) : "";
    }

    /**
     * 设置当前 sql解析信息
     *
     * @param parserInfo 当前 sql解析信息
     */
    default void setParserInfoJson(String parserInfo) {
        if (StringUtils.isNotEmpty(parserInfo)) {
            setParserInfo(JsonUtils.deserializeObject(parserInfo, getParserInfoClass()));
        }
    }

    /**
     * 获取解析信息类型
     *
     * @return 解析信息类型
     */
    Class<T> getParserInfoClass();

}
