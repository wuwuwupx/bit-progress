package com.bitprogress.ormcontext.info;

import com.bitprogress.basemodel.Info;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 基础数据范围信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseDataScopeInfo extends Info {

    /**
     * 自身数据范围
     * 用于 insert 填充
     */
    private String dataScope;

    /**
     * 当前用户ID
     */
    private Long userId;

}
