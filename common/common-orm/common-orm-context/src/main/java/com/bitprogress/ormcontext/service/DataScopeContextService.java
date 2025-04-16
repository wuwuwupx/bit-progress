package com.bitprogress.ormcontext.service;

import com.bitprogress.ormmodel.enums.QueryMode;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.info.parser.DataScopeParserInfo;
import com.bitprogress.ormmodel.info.user.BaseDataScopeInfo;

import java.util.Objects;
import java.util.Optional;

public interface DataScopeContextService<T extends BaseDataScopeInfo, R>
        extends ParserContextService<DataScopeParserInfo>, UserContextService<T>, SqlContextService {

    /**
     * 注解的数据范围解析信息
     */
    ThreadLocal<DataScopeParserInfo> DATA_SCOPE_PARSER_INFO = new ThreadLocal<>();

    /**
     * 上一sql的查询模式
     */
    ThreadLocal<QueryMode> PRE_SQL_QUERY_MODE = new ThreadLocal<>();

    /**
     * 当前sql的查询模式
     */
    ThreadLocal<QueryMode> CURRENT_SQL_QUERY_MODE = new ThreadLocal<>();

    /**
     * 获取数据范围信息
     */
    @Override
    T getUserInfo();

    /**
     * 获取数据范围解析信息
     */
    @Override
    default DataScopeParserInfo getParserInfo() {
        return DATA_SCOPE_PARSER_INFO.get();
    }

    /**
     * 设置数据范围解析信息
     *
     * @param dataScopeParserInfo 数据范围解析信息
     */
    @Override
    default void setParserInfo(DataScopeParserInfo dataScopeParserInfo) {
        DATA_SCOPE_PARSER_INFO.set(dataScopeParserInfo);
    }

    /**
     * 清除数据范围解析信息
     */
    @Override
    default void clearParserInfo() {
        DATA_SCOPE_PARSER_INFO.remove();
    }

    /**
     * 获取解析信息类型
     *
     * @return 解析信息类型
     */
    @Override
    default Class<DataScopeParserInfo> getParserInfoClass() {
        return DataScopeParserInfo.class;
    }

    /**
     * 获取上一sql的查询模式
     */
    default QueryMode getPreSqlQueryMode() {
        return PRE_SQL_QUERY_MODE.get();
    }

    /**
     * 设置上一sql的查询模式
     *
     * @param queryMode sql的查询模式
     */
    default void setPreSqlQueryMode(QueryMode queryMode) {
        PRE_SQL_QUERY_MODE.set(queryMode);
    }

    /**
     * 清除上一sql的查询模式
     */
    default void clearPreSqlQueryMode() {
        PRE_SQL_QUERY_MODE.remove();
    }

    /**
     * 获取当前sql的查询模式
     */
    default QueryMode getCurrentSqlQueryMode() {
        return CURRENT_SQL_QUERY_MODE.get();
    }

    /**
     * 设置当前sql的查询模式
     *
     * @param queryMode 当前sql的查询模式
     */
    default void setCurrentSqlQueryMode(QueryMode queryMode) {
        CURRENT_SQL_QUERY_MODE.set(queryMode);
    }

    /**
     * 清除当前sql的查询模式
     */
    default void clearCurrentSqlQueryMode() {
        CURRENT_SQL_QUERY_MODE.remove();
    }

    /**
     * 缓存前一sql上下文
     */
    @Override
    default void cachePreSqlContext() {
        Optional<QueryMode> currentMode = Optional.ofNullable(getCurrentSqlQueryMode());
        currentMode.ifPresent(this::setPreSqlQueryMode);
        Optional<R> currentDataScopeType = Optional.ofNullable(getCurrentSqlDataScopeType());
        currentDataScopeType.ifPresent(this::setPreSqlDataScopeType);
        clearCurrentSqlQueryMode();
        clearCurrentSqlDataScopeType();
    }

    /**
     * 恢复前一sql上下文
     */
    @Override
    default void restorePreSqlContext() {
        Optional<QueryMode> preparedMode = Optional.ofNullable(getPreSqlQueryMode());
        preparedMode.ifPresent(this::setCurrentSqlQueryMode);
        Optional<R> preDataScopeType = Optional.ofNullable(getPreSqlDataScopeType());
        preDataScopeType.ifPresent(this::setCurrentSqlDataScopeType);
        clearPreSqlQueryMode();
        clearPreSqlDataScopeType();
    }

    /**
     * 设置当前数据范围类型
     *
     * @param sqlType sql类型
     */
    default Boolean setCurrentSqlContextBySqlType(SqlType sqlType) {
        if (onParser(sqlType)) {
            if (ignoreProcess()) {
                return false;
            }
            setCurrentSqlDataScopeType(getDataScopeTypeByParserInfo());
            setCurrentSqlQueryMode(getQueryModeByParserInfo());
            return true;
        } else {
            T userInfo = getUserInfo();
            if (Objects.nonNull(userInfo)) {
                setCurrentSqlDataScopeType(getDataScopeTypeByUserInfo());
                setCurrentSqlQueryMode(QueryMode.DOWNSTREAM_CHAIN);
                return true;
            }
            return false;
        }
    }

    /**
     * 清除当前sql上下文
     */
    @Override
    default void clearCurrentSqlContext() {
        clearCurrentSqlQueryMode();
        clearCurrentSqlDataScopeType();
    }

    /**
     * 获取前一sql数据范围类型
     *
     * @return 数据范围类型
     */
    R getPreSqlDataScopeType();

    /**
     * 设置前一sql数据范围类型
     *
     * @param dataScopeType 数据范围类型
     */
    void setPreSqlDataScopeType(R dataScopeType);

    /**
     * 清除前一sql数据范围类型
     */
    void clearPreSqlDataScopeType();

    /**
     * 获取当前数据范围类型
     *
     * @return 数据范围类型
     */
    R getCurrentSqlDataScopeType();

    /**
     * 设置当前数据范围类型
     *
     * @param dataScopeType 数据范围类型
     */
    void setCurrentSqlDataScopeType(R dataScopeType);

    /**
     * 清除当前数据范围类型
     */
    void clearCurrentSqlDataScopeType();

    /**
     * 从解析信息中获取查询模式
     *
     * @return 查询模式
     */
    default QueryMode getQueryModeByParserInfo() {
        return Objects.nonNull(getParserInfo()) ? getParserInfo().getQueryMode() : QueryMode.DOWNSTREAM_CHAIN;
    }

    /**
     * 从解析信息中获取数据范围类型
     *
     * @return 当前数据范围类型
     */
    R getDataScopeTypeByParserInfo();

    /**
     * 从用户信息中获取数据范围类型
     *
     * @return 当前数据范围类型
     */
    R getDataScopeTypeByUserInfo();

}
