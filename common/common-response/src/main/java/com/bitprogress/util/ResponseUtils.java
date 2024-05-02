package com.bitprogress.util;

import com.bitprogress.exception.CommonException;
import com.bitprogress.exception.ExceptionEnum;
import com.bitprogress.model.ResultResponse;

import static com.bitprogress.constant.ResultConstants.SUCCESS_CODE;

/**
 * 响应工具类
 */
public class ResponseUtils {

    /**
     * 断言响应结果
     *
     * @param response 响应结果
     */
    public static <T> void tryAssert(ResultResponse<T> response) {
        Assert.notNull(response, ExceptionEnum.RESPONSE_MISS_WRONG_EXCEPTION);
        Integer code = response.getCode();
        Assert.notNull(code, ExceptionEnum.RESPONSE_CODE_MISS_WRONG_EXCEPTION);
        if (code.equals(SUCCESS_CODE)) {
            return;
        }
        throw new CommonException(code, response.getMessage());
    }

    /**
     * 断言响应结果
     *
     * @param response 响应结果
     */
    public static <T> T getResultData(ResultResponse<T> response) {
        tryAssert(response);
        return response.getData();
    }

}
