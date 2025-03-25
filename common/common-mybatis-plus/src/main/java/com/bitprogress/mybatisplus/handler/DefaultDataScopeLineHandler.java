package com.bitprogress.mybatisplus.handler;

import com.bitprogress.mybatisplus.properties.DataScopeProperties;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.ormcontext.utils.DataScopeContextUtils;
import com.bitprogress.ormmodel.enums.SqlType;
import com.bitprogress.util.CollectionUtils;
import lombok.AllArgsConstructor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Table;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
public class DefaultDataScopeLineHandler implements DataScopeLineHandler {

    private final DataScopeProperties dataScopeProperties;

    /**
     * 是否启用
     *
     * @return 是否启用
     */
    @Override
    public boolean isEnabled() {
        return dataScopeProperties.getEnabled();
    }

    /**
     * 获取数据范围
     *
     * @return 数据范围
     */
    @Override
    public Expression getDataScope() {
        return new StringValue(DataScopeContextUtils.getDataScope());
    }

    /**
     * 获取数据范围字段
     *
     * @return 数据范围字段
     */
    @Override
    public String getDataScopeColumn() {
        DataScopeType dataScopeType = DataScopeContextUtils.getDataScopeType();
        if (Objects.requireNonNull(dataScopeType) == DataScopeType.SELF) {
            return getSelfWhereColumn();
        }
        return "data_scope";
    }

    /**
     * 获取自身匹配数据条件字段
     *
     * @return 自身匹配数据条件字段
     */
    @Override
    public String getSelfWhereColumn() {
        return "user_id";
    }

    /**
     * 获取可查询数据范围列表
     *
     * @return 数据范围
     */
    @Override
    public List<Expression> getDataScopes() {
        DataScopeType dataScopeType = DataScopeContextUtils.getDataScopeType();
        switch(dataScopeType) {
            case SELF -> {
                return CollectionUtils.asList(new LongValue(DataScopeContextUtils.getUserId()));
            }
            case SCOPE -> {
                Set<String> dataScopes = DataScopeContextUtils.getDataScopes();
                return CollectionUtils.toList(dataScopes, StringValue::new);
            }
            case ALL -> {
                return List.of();
            }
        }
        return List.of();
    }

    /**
     * 根据表名判断是否忽略拼接数据范围条件
     * <p>
     * 默认都要进行解析并拼接数据范围
     * - 启用表列表不为空，则只需要判断表名是否启用
     * - 启用表列表为空，则判断是否在白名单中
     *
     * @param tableName 表名
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接数据范围
     */
    @Override
    public boolean ignoreTable(String tableName) {
        List<String> enableTables = dataScopeProperties.getEnableTables();
        List<String> ignoreTables = dataScopeProperties.getIgnoreTables();
        return CollectionUtils.isNotEmpty(enableTables)
                ? CollectionUtils.notContains(enableTables, tableName)
                : CollectionUtils.contains(ignoreTables, tableName);
    }

    /**
     * 根据表名判断是否忽略拼接数据范围条件
     * <p>
     * 默认都要进行解析并拼接数据范围
     * - 启用表列表不为空，则只需要判断表名是否启用
     * - 启用表列表为空，则判断是否在白名单中
     *
     * @return 是否忽略, true:表示忽略，false:需要解析并拼接数据范围
     */
    @Override
    public boolean ignoreTable(Table table, SqlType sqlType) {
        if (SqlType.SELECT.equals(sqlType)) {
            return false;
        }
        String tableName = table.getName();
        List<String> enableTables = dataScopeProperties.getEnableTables();
        List<String> ignoreTables = dataScopeProperties.getIgnoreTables();
        return CollectionUtils.isNotEmpty(enableTables)
                ? CollectionUtils.notContains(enableTables, tableName)
                : CollectionUtils.contains(ignoreTables, tableName);
    }

}
