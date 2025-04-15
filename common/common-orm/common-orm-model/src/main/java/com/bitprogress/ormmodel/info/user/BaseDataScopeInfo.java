package com.bitprogress.ormmodel.info.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * 基础数据范围信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDataScopeInfo extends BaseUserInfo {

    /**
     * 自身数据范围
     * 用于 insert 填充
     */
    private String dataScope;

    /**
     * 用户自身数据，userId 之类的
     */
    private Long selfData;

    /**
     * 用户拥有数据，salesId 之类的
     */
    private Long ownedData;

    /**
     * 所属的数据范围列表
     * 用于 select 、 update 及 delete 的 where 条件
     */
    private Set<String> belongDataScopes = new HashSet<>();

}
