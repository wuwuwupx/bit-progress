package com.bitprogress.securityspring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
* 用户在redis的登录信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthMsg implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 登录获取的token
     */
    private String token;

    /**
     * 用户的数据信息
     */
    private Map<String, String> params;

    /**
     * 不向下传递的权限信息
     */
    private Set<String> ignoreKeys;

    @Override
    public String toString() {
        return "AuthMsg{" +
                "token='" + token + '\'' +
                ", params=" + params +
                ", ignoreKeys=" + ignoreKeys +
                '}';
    }

}
