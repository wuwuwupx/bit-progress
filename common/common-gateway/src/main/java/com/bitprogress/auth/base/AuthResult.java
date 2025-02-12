package com.bitprogress.auth.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResult<T extends AuthInfo> {

    private Boolean result;

    private T userInfo;

    private AuthException authException;

}
