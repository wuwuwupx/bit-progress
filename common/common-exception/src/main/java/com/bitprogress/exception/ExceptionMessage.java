package com.bitprogress.exception;

import com.bitprogress.basemodel.IException;
import com.bitprogress.basemodel.enums.CodeEnum;
import com.bitprogress.basemodel.enums.MessageEnum;

/**
 * exception interface
 */
public interface ExceptionMessage extends IException, CodeEnum, MessageEnum {
}
