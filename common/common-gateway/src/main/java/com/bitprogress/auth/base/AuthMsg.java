package com.bitprogress.auth.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
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
public class AuthMsg implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用户的token
     */
    private String token;

    /**
     * 一些补充参数
     */
    private Map<String, String> params;

    public Map<String, String> getParams() {
        return Objects.isNull(params) ? new HashMap<>(2) : params;
    }

}
