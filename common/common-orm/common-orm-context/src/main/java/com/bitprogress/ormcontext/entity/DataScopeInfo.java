package com.bitprogress.ormcontext.entity;

import com.bitprogress.basemodel.Info;
import com.bitprogress.ormmodel.enums.DataScopeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataScopeInfo extends Info {

    /**
     * 数据范围类型
     */
    private DataScopeType dataScopeType;

    /**
     * 自身数据范围
     * 用于 insert 填充
     */
    private String dataScope;

    /**
     * 可查询数据范围列表
     * 用于 select 、 update 及 delete 的 where 条件
     */
    private Set<String> dataScopes = new HashSet<>();

    /**
     * 当前用户ID
     */
    private Long userId;

}
