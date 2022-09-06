package com.bitprogress.exception;

/**
 * @author wuwuwupx
 *  定时任务异常
 */
public class ScheduleException extends CommonException {

    public ScheduleException(IExceptionMessage requestException) {
        super(requestException);
    }

}
