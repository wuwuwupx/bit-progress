package com.bitprogress.util;

import com.bitprogress.exception.CommonException;
import com.bitprogress.model.ResultVO;

import java.util.Objects;

/**
 * @author wuwuwupx
 * @desc: 对ResultVO进行校验
 */
public class ResultUtils {

    /**
     * 检查ResultVO
     *
     * @param resultVO
     * @param <T>
     * @return data
     */
    public static <T> T checkResultVO(ResultVO<T> resultVO) {
        Integer code = resultVO.getCode();
        if (Objects.nonNull(code) && code != NumberUtils.REQUEST_SUCCESS) {
            throw new CommonException(code, resultVO.getError(), resultVO.getMessage());
        }
        return resultVO.getData();
    }

}
