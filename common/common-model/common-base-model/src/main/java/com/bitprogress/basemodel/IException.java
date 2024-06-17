package com.bitprogress.basemodel;

/**
 * exception interface
 */
public interface IException {

    /**
     * get exception code
     *
     * @return exception code
     */
    Integer getCode();

    /**
     * get exception message
     *
     * @return exception message
     */
    String getMessage();

}
