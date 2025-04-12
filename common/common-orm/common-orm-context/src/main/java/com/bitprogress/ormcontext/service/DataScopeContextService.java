package com.bitprogress.ormcontext.service;

import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.ormmodel.info.parser.DataScopeParserInfo;
import com.bitprogress.ormmodel.info.user.BaseDataScopeInfo;

import java.util.Objects;

public interface DataScopeContextService<T extends BaseDataScopeInfo, R>
        extends ParserContextService<DataScopeParserInfo>, UserContextService<T>,
        CurrentConditionTypeContextService<R>{

    /**
     * 注解的数据范围解析信息
     */
    ThreadLocal<DataScopeParserInfo> DATA_SCOPE_PARSER_INFO = new ThreadLocal<>();

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
     * 设置当前数据范围类型
     *
     * @param sqlType sql类型
     */
    default Boolean setCurrentConditionTypeBySqlType(SqlType sqlType) {
        if (onParser(sqlType)) {
            if (ignoreProcess()) {
                return false;
            }
            setCurrentConditionType(getDataScopeTypeByParserInfo());
        } else {
            T userInfo = getUserInfo();
            if (Objects.nonNull(userInfo)) {
                setCurrentConditionType(getDataScopeTypeByUserInfo());
                return true;
            }
        }
        return false;
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
