package com.bitprogress.servercore.util;

import com.bitprogress.ormcontext.entity.DataScopeInfo;
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
    public static DataScopeInfo getDataScopeInfo(UserInfo userInfo) {
        DataScopeInfo dataScopeInfo = new DataScopeInfo();
        dataScopeInfo.setUserId(userInfo.getUserId());
        dataScopeInfo.setDataScope(userInfo.getDataScope());
        dataScopeInfo.setDataScopes(userInfo.getDataScopes());
        if (Objects.nonNull(userInfo.getDataScopeType())) {
            dataScopeInfo.setDataScopeType(DataScopeType.getByValue(userInfo.getDataScopeType().getValue()));
        } else {
            // 为避免越权操作，默认为自身范围
            dataScopeInfo.setDataScopeType(DataScopeType.SELF);
        }
        return dataScopeInfo;
    }

}
