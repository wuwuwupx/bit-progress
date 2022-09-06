package com.bitprogress.util;

import com.bitprogress.constant.NumberConstants;
import com.bitprogress.exception.CommonException;
import com.bitprogress.model.WechatResult;

import java.util.Objects;

/**
 * @author wuwuwupx
 *  微信接口调用结果处理
 */
public class WechatResultUtils {

    /**
     * 检查微信接口调用结果
     *
     * @param result
     */
    public static WechatResult wechatResultCheck(String result) {
        return wechatResultCheck(result, WechatResult.class);
    }

    /**
     * 检查微信接口调用结果
     *
     * @param result
     */
    public static <T extends WechatResult> T wechatResultCheck(String result, Class<T> target) {
        T data = JsonUtils.deserializeObject(result, target);
        String errCode = data.getErrCode();
        if (Objects.nonNull(errCode) && !StringUtils.equals(NumberConstants.STRING_ZERO, errCode)) {
            throw new CommonException(Integer.parseInt(errCode), data.getErrMsg(), "微信接口返回异常");
        }
        return data;
    }

}
