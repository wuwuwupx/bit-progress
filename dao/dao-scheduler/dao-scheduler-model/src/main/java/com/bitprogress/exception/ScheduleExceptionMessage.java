package com.bitprogress.exception;

import static com.bitprogress.exception.ScheduleErrorCodes.*;
import static com.bitprogress.exception.ScheduleMessageCodes.*;

/**
 * @author wuwuwupx
 *  定时任务异常
 */
public enum ScheduleExceptionMessage implements IExceptionMessage {

    /**
     * 定时任务创建异常
     */
    QUARTZJOB_CREATE_EXCEPTION(4001, QUARTZJOB_CREATE_EXCEPTION_CODE, QUARTZJOB_CREATE_EXCEPTION_MESSAGE),
    /**
     * 定时任务查询异常
     */
    QUARTZJOB_QUERY_EXCEPTION(4002, QUARTZJOB_QUERY_EXCEPTION_CODE, QUARTZJOB_QUERY_EXCEPTION_MESSAGE),
    /**
     * 定时任务移除异常
     */
    QUARTZJOB_REMOVE_EXCEPTION(4003, QUARTZJOB_REMOVE_EXCEPTION_CODE, QUARTZJOB_REMOVE_EXCEPTION_MESSAGE),
    /**
     * 定时任务查重异常
     */
    QUARTZJOB_CHECK_EXISTS_EXCEPTION(4004, QUARTZJOB_CHECK_EXISTS_EXCEPTION_CODE, QUARTZJOB_CHECK_EXISTS_EXCEPTION_MESSAGE),

    /**
     * jobKey查询异常
     */
    JOBKEY_QUERY_EXCEPTION(4005, JOBKEY_QUERY_EXCEPTION_CODE, JOBKEY_QUERY_EXCEPTION_MESSAGE),

    /**
     * 触发器暂停异常
     */
    TRIGGER_PAUSE_EXCEPTION(4006, TRIGGER_PAUSE_EXCEPTION_CODE, TRIGGER_PAUSE_EXCEPTION_MESSAGE),

    /**
     * 触发器恢复异常
     */
    TRIGGER_RESCHEDULE_EXCEPTION(4007, TRIGGER_RESCHEDULE_EXCEPTION_CODE, TRIGGER_RESCHEDULE_EXCEPTION_MESSAGE),

    /**
     * 查询任务分组异常
     */
    JOBGROUP_QUERY_EXCEPTION(4008, JOBGROUP_QUERY_EXCEPTION_CODE, JOBGROUP_QUERY_EXCEPTION_MESSAGE),

    /**
     * 定时任务名称不能为空
     */
    QUARTZJOB_NAME_EMPTY_EXCEPTION(4009, QUARTZJOB_NAME_EMPTY_EXCEPTION_CODE, QUARTZJOB_NAME_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 定时任务cron表达式不能为空
     */
    QUARTZJOB_CRON_EMPTY_EXCEPTION(4010, QUARTZJOB_CRON_EMPTY_EXCEPTION_CODE, QUARTZJOB_CRON_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 定时任务类型不能为空
     */
    QUARTZJOB_TYPE_EMPTY_EXCEPTION(4010, QUARTZJOB_TYPE_EMPTY_EXCEPTION_CODE, QUARTZJOB_TYPE_EMPTY_EXCEPTION_MESSAGE),

    /**
     * 定时任务分组不存在
     */
    GROUP_NOT_EXIST_EXCEPTION(4011, GROUP_NOT_EXIST_EXCEPTION_CODE, GROUP_NOT_EXIST_EXCEPTION_MESSAGE),
    /**
     * 定时任务分组保存失败
     */
    GROUP_SAVE_EXCEPTION(4012, GROUP_SAVE_EXCEPTION_CODE, GROUP_SAVE_EXCEPTION_MESSAGE),
    /**
     * 定时任务分组更新失败
     */
    GROUP_UPDATE_EXCEPTION(4013, GROUP_UPDATE_EXCEPTION_CODE, GROUP_UPDATE_EXCEPTION_MESSAGE),
    /**
     * 定时任务分组删除失败
     */
    GROUP_DELETE_EXCEPTION(4014, GROUP_DELETE_EXCEPTION_CODE, GROUP_DELETE_EXCEPTION_MESSAGE),
    /**
     * 定时任务分组已被分配
     */
    GROUP_IS_ASSIGNED_EXCEPTION(4015, GROUP_IS_ASSIGNED_EXCEPTION_CODE, GROUP_IS_ASSIGNED_EXCEPTION_MESSAGE),

    /**
     * 定时任务不存在
     */
    QUARTZJOB_NOT_EXIST_EXCEPTION(4016, QUARTZJOB_NOT_EXIST_EXCEPTION_CODE, QUARTZJOB_NOT_EXIST_EXCEPTION_MESSAGE),
    /**
     * 定时任务保存失败
     */
    QUARTZJOB_SAVE_EXCEPTION(4017, QUARTZJOB_SAVE_EXCEPTION_CODE, QUARTZJOB_SAVE_EXCEPTION_MESSAGE),
    /**
     * 定时任务更新失败
     */
    QUARTZJOB_UPDATE_EXCEPTION(4018, QUARTZJOB_UPDATE_EXCEPTION_CODE, QUARTZJOB_UPDATE_EXCEPTION_MESSAGE),
    /**
     * 定时任务删除失败
     */
    QUARTZJOB_DELETE_EXCEPTION(4019, QUARTZJOB_DELETE_EXCEPTION_CODE, QUARTZJOB_DELETE_EXCEPTION_MESSAGE),
    /**
     * 定时任务cron表达式无效
     */
    QUARTZJOB_CRON_INVALID_EXCEPTION(4020, QUARTZJOB_CRON_INVALID_EXCEPTION_CODE, QUARTZJOB_CRON_INVALID_EXCEPTION_MESSAGE),
    /**
     * 定时任务触发时间不能为空
     */
    QUARTZJOB_DURATION_IS_NULL_EXCEPTION(4021, QUARTZJOB_DURATION_IS_NULL_EXCEPTION_CODE, QUARTZJOB_DURATION_IS_NULL_EXCEPTION_MESSAGE),
    /**
     * 获取定时任务状态失败
     */
    QUARTZJOB_GET_TRIGGER_STATE_EXCEPTION(4022, QUARTZJOB_GET_TRIGGER_STATE_EXCEPTION_CODE, QUARTZJOB_GET_TRIGGER_STATE_EXCEPTION_MESSAGE),

    ;

    private Integer code;

    private String error;

    private String message;

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getError() {
        return this.error;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ScheduleExceptionMessage(Integer code, String error, String message) {
        this.code = code;
        this.error = error;
        this.message = message;
    }

}
