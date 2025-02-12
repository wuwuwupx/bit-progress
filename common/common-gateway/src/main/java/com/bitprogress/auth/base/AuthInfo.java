package com.bitprogress.auth.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 授权信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户的ID
     */
    private String userId;

    /**
     * 用户的token
     */
    private String token;

    /**
     * 用户信息原封不动传递到具体服务上，进行用户登录时应该封装好具体的结构再序列号
     */
    private String userInfo;

}
