package com.bitprogress.securityspring.entity;

import com.bitprogress.securityspring.exception.AuthException;
import lombok.Data;

@Data
public class AuthResult<T extends AuthMsg> {

    private Boolean result;

    private String userId;

    private T authMsg;

    private AuthException authException;

}
