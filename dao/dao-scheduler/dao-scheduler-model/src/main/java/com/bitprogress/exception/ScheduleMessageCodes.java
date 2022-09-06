package com.bitprogress.exception;

/**
 * @author wuwuwupx
 *  定时任务异常信息
 */
public class ScheduleMessageCodes {

    public static final String QUARTZJOB_CREATE_EXCEPTION_MESSAGE = "定时任务创建异常";
    public static final String QUARTZJOB_NAME_EMPTY_EXCEPTION_MESSAGE = "定时任务名称为空";
    public static final String QUARTZJOB_CRON_EMPTY_EXCEPTION_MESSAGE = "定时任务cron表达式为空";
    public static final String QUARTZJOB_TYPE_EMPTY_EXCEPTION_MESSAGE = "定时任务类型为空";
    public static final String QUARTZJOB_QUERY_EXCEPTION_MESSAGE = "定时任务查询异常";
    public static final String QUARTZJOB_REMOVE_EXCEPTION_MESSAGE = "定时任务移除异常";
    public static final String QUARTZJOB_CHECK_EXISTS_EXCEPTION_MESSAGE = "定时任务查重异常";
    public static final String JOBKEY_QUERY_EXCEPTION_MESSAGE = "jobKey查询异常";
    public static final String TRIGGER_PAUSE_EXCEPTION_MESSAGE = "触发器暂停异常";
    public static final String TRIGGER_RESCHEDULE_EXCEPTION_MESSAGE = "触发器恢复异常";
    public static final String JOBGROUP_QUERY_EXCEPTION_MESSAGE = "查询任务分组异常";

    public static final String GROUP_NOT_EXIST_EXCEPTION_MESSAGE = "定时任务分组不存在";
    public static final String GROUP_SAVE_EXCEPTION_MESSAGE = "定时任务分组保存失败";
    public static final String GROUP_UPDATE_EXCEPTION_MESSAGE = "定时任务分组更新失败";
    public static final String GROUP_DELETE_EXCEPTION_MESSAGE = "定时任务分组删除失败";
    public static final String GROUP_IS_ASSIGNED_EXCEPTION_MESSAGE = "定时任务分组已被分配";

    public static final String QUARTZJOB_NOT_EXIST_EXCEPTION_MESSAGE = "定时任务不存在";
    public static final String QUARTZJOB_SAVE_EXCEPTION_MESSAGE = "定时任务保存失败";
    public static final String QUARTZJOB_UPDATE_EXCEPTION_MESSAGE = "定时任务更新失败";
    public static final String QUARTZJOB_DELETE_EXCEPTION_MESSAGE = "定时任务删除失败";
    public static final String QUARTZJOB_CRON_INVALID_EXCEPTION_MESSAGE = "定时任务cron表达式无效";
    public static final String QUARTZJOB_DURATION_IS_NULL_EXCEPTION_MESSAGE = "定时任务触发时间不能为空";
    public static final String QUARTZJOB_GET_TRIGGER_STATE_EXCEPTION_MESSAGE = "获取定时任务状态失败";

}
