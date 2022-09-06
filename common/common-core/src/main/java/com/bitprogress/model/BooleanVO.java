package com.bitprogress.model;

import java.io.Serializable;

/**
 * @author wuwuwupx
 *  用于返回查询结果
 */
public class BooleanVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean result;

    public BooleanVO(Boolean result) {
        this.result = result;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public static BooleanVO result(Boolean result) {
        return new BooleanVO(result);
    }

    @Override
    public String toString() {
        return "BooleanVO{" +
                "result=" + result +
                '}';
    }

}
