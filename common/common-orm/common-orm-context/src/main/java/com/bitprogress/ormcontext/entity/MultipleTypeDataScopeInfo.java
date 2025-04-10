package com.bitprogress.ormcontext.entity;

import com.bitprogress.ormmodel.enums.DataScopeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * 多重类型的数据范围信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultipleTypeDataScopeInfo extends BaseDataScopeInfo {

    /**
     * 数据范围类型
     */
    private Set<DataScopeType> dataScopeType;

    /**
     * 可查询数据范围列表
     * 用于 select 、 update 及 delete 的 where 条件
     */
    private Set<String> dataScopes = new HashSet<>();

}
