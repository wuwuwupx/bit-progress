package com.bitprogress.usercontext.entity;

import com.bitprogress.basemodel.Info;
import com.bitprogress.usercontext.enums.DataScopeType;
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
public class CombinationDataScope extends Info {

    /**
     * 数据范围类型
     */
    private DataScopeType dataScopeType;

    /**
     * 可查询数据范围列表
     * 用于 select 、 update 及 delete 的 where 条件
     */
    private Set<String> dataScopes = new HashSet<>();

}
