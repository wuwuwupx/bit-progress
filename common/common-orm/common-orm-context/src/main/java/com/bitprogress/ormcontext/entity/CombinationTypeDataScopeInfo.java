package com.bitprogress.ormcontext.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CombinationTypeDataScopeInfo extends BaseDataScopeInfo {

    /**
     * 组合类型数据范围
     */
    private Set<CombinationDataScope> dataScopes;

}
