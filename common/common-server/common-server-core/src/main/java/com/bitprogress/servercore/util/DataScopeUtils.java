package com.bitprogress.servercore.util;

import com.bitprogress.ormcontext.info.SingleTypeDataScopeInfo;
import com.bitprogress.ormmodel.enums.DataScopeType;
import com.bitprogress.usercontext.entity.UserInfo;

import java.util.Objects;

/**
 * 数据范围工具类
 */
public class DataScopeUtils {

    /**
     * 从用户信息中获取数据范围信息
     * 不依赖前端传递
     *
     * @param userInfo 用户信息
     * @return 数据范围信息
     */
    public static SingleTypeDataScopeInfo getDataScopeInfo(UserInfo userInfo) {
        SingleTypeDataScopeInfo singleTypeDataScopeInfo = new SingleTypeDataScopeInfo();
        singleTypeDataScopeInfo.setUserId(userInfo.getUserId());
        singleTypeDataScopeInfo.setDataScope(userInfo.getDataScope());
        singleTypeDataScopeInfo.setManagedDataScopes(userInfo.getDataScopes());
        if (Objects.nonNull(userInfo.getDataScopeType())) {
            singleTypeDataScopeInfo.setDataScopeType(DataScopeType.getByValue(userInfo.getDataScopeType().getValue()));
        } else {
            // 为避免越权操作，默认为自身范围
            singleTypeDataScopeInfo.setDataScopeType(DataScopeType.SELF);
        }
        return singleTypeDataScopeInfo;
    }

}
