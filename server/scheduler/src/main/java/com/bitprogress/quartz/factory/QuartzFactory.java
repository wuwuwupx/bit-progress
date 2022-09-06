package com.bitprogress.quartz.factory;

import com.bitprogress.exception.ScheduleException;
import com.bitprogress.exception.ScheduleExceptionMessage;
import com.bitprogress.entity.QuartzJob;
import com.bitprogress.model.quartzjob.pojo.envm.TriggerStateEnum;
import com.bitprogress.model.quartzjob.pojo.envm.TriggerType;
import com.bitprogress.quartz.TriggerStateItem;
import com.bitprogress.quartz.job.CustomJob;
import com.bitprogress.util.Assert;
import com.bitprogress.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author wuwuwupx
 * Quartz
 */
@Component
@Slf4j
public class QuartzFactory {

    @Autowired
    private Scheduler scheduler;

    public JobKey createQuartzJob(QuartzJob job) throws SchedulerException {
        Long quartzJobId = job.getQuartzJobId();
        String name = String.valueOf(quartzJobId);
        Long groupId = job.getGroupId();
        String groupName = String.valueOf(groupId);
        JobKey jobKey = new JobKey(name, groupName);
        TriggerKey triggerKey = new TriggerKey(name, groupName);
        if (scheduler.checkExists(jobKey)) {
            log.info("quartzJob exists, jobName [{}]", name);
            return jobKey;
        } else {
            log.info("create quartz trigger，name {}", name);
            TriggerType type = job.getTriggerType();
            long millis = System.currentTimeMillis();
            switch (type) {
                case SIMPLE: {
                    Long duration = job.getDuration();
                    long startAtTime = Objects.nonNull(duration) ? millis + duration : millis;
                    JobDetail jobDetail = JobBuilder
                            .newJob(CustomJob.class)
                            .withIdentity(jobKey)
                            .build();
                    jobDetail.getJobDataMap().put("quartzJob", job);
                    Trigger trigger = TriggerBuilder
                            .newTrigger()
                            .withIdentity(triggerKey)
                            .startAt(new Date(startAtTime))
                            .build();
                    scheduler.scheduleJob(jobDetail, trigger);
                    return jobKey;
                }
                case CRON: {
                    String cronExpression = job.getCronExpression();
                    Assert.isNotEmpty(cronExpression, ScheduleExceptionMessage.QUARTZJOB_CRON_EMPTY_EXCEPTION);
                    JobDetail jobDetail = JobBuilder.newJob(CustomJob.class).withIdentity(jobKey).build();
                    jobDetail.getJobDataMap().put("quartzJob", job);
                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression)
                            .withMisfireHandlingInstructionDoNothing();
                    Trigger scheduleTrigger = TriggerBuilder
                            .newTrigger()
                            .withIdentity(triggerKey)
                            .withSchedule(scheduleBuilder)
                            .startAt(new Date(millis))
                            .build();
                    scheduler.scheduleJob(jobDetail, scheduleTrigger);
                    return jobKey;
                }
                default: {
                    throw new ScheduleException(ScheduleExceptionMessage.JOBGROUP_QUERY_EXCEPTION);
                }
            }
        }
    }

    /**
     * 获取任务信息
     *
     * @param jobKey
     * @throws SchedulerException
     */
    public JobDetail getJobDetail(JobKey jobKey) throws SchedulerException {
        return scheduler.getJobDetail(jobKey);
    }

    /**
     * 获取触发器信息
     *
     * @param jobKey
     * @throws SchedulerException
     */
    public List<? extends Trigger> getTriggersOfJob(JobKey jobKey) throws SchedulerException {
        return scheduler.getTriggersOfJob(jobKey);
    }

    /**
     * 获取定时任务状态 -- 即获取触发器状态
     *
     * @param triggerName
     * @param triggerGroup
     * @return 任务的触发器状态
     * @throws SchedulerException
     */
    public TriggerStateItem getTriggerState(String triggerName, String triggerGroup) throws SchedulerException {
        TriggerKey triggerKey = generateTriggerKey(triggerName, triggerGroup);
        return getTriggerState(triggerKey);
    }

    /**
     * 获取触发器状态
     *
     * @param triggerKey
     * @throws SchedulerException
     */
    public TriggerStateItem getTriggerState(TriggerKey triggerKey) throws SchedulerException {
        Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);
        TriggerStateItem stateItem = new TriggerStateItem();
        stateItem.setName(triggerKey.getName());
        stateItem.setTriggerState(TriggerStateEnum.valueOf(triggerState.name()));
        return stateItem;
    }

    /**
     * 重试job
     *
     * @param job
     */
    public void reTrySimpleJob(QuartzJob job) {
        try {
            job.setJobName(job.getJobName() + "10");
            String name = job.getJobName();

            log.info("重试任务，名称 {}", name);

            TriggerKey triggerKey = new TriggerKey(name, job.getClass().getName());
            Trigger tri = scheduler.getTrigger(triggerKey);
            if (Objects.isNull(tri)) {
                TriggerType type = job.getTriggerType();
                if (type.equals(TriggerType.SIMPLE)) {
                    String groupName = job.getClass().getName();
                    //递增重试，每 5 * retryTime ^ 2
                    Long startAtTime = System.currentTimeMillis() + new Double(5000 * Math.pow(job.getRetry(), 2)).longValue();
                    JobDetail jobDetail = JobBuilder.newJob(CustomJob.class).withIdentity(name, groupName).build();
                    jobDetail.getJobDataMap().put("quartzJob", job);
                    Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, groupName)
                            .startAt(new Date(startAtTime)).build();
                    scheduler.scheduleJob(jobDetail, trigger);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("retry error: ", e);
        }
    }

    /**
     * 移除任务
     *
     * @param name
     * @param group
     */
    public void removeJob(String name, String group) {
        try {
            JobKey jobKey = JobKey.jobKey(name, group);
            TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
            removeJob(triggerKey, jobKey);
        } catch (Exception e) {
            log.error("removeJob [{}] error: ", name, e);
            throw new ScheduleException(ScheduleExceptionMessage.QUARTZJOB_DELETE_EXCEPTION);
        }
    }

    /**
     * 移除任务
     *
     * @param triggerKey
     * @param jobKey
     * @throws SchedulerException
     */
    public void removeJob(TriggerKey triggerKey, JobKey jobKey) throws SchedulerException {
        //停止触发器
        scheduler.pauseTrigger(triggerKey);
        //移除触发器
        scheduler.unscheduleJob(triggerKey);
        //删除任务
        scheduler.deleteJob(jobKey);
    }

    /**
     * 是否有相同名称的job
     *
     * @param jobName
     */
    public Boolean hasSameNameJob(String jobName) {
        try {
            String jobGroupName = QuartzJob.class.getName();
            JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
            return null != jobDetail;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 暂停定时任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void pauseQuartzJob(String name, String group) throws SchedulerException {
        JobKey jobKey = generateJobKey(name, group);
        scheduler.pauseJob(jobKey);
    }

    /**
     * 暂停定时任务
     *
     * @param jobKey
     * @throws SchedulerException
     */
    public void pauseQuartzJob(JobKey jobKey) throws SchedulerException {
        scheduler.pauseJob(jobKey);
    }

    /**
     * 停止触发器
     *
     * @param triggerKey
     * @throws SchedulerException
     */
    public void pauseTrigger(TriggerKey triggerKey) throws SchedulerException {
        scheduler.pauseTrigger(triggerKey);
    }

    /**
     * 恢复定时任务
     *
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void rescheduleQuartzJob(String name, String group) throws SchedulerException {
        TriggerKey triggerKey = generateTriggerKey(name, group);
        Trigger trigger = scheduler.getTrigger(triggerKey);
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 恢复触发器
     *
     * @param triggerKey
     * @throws SchedulerException
     */
    public void rescheduleTrigger(TriggerKey triggerKey) throws SchedulerException {
        Trigger trigger = scheduler.getTrigger(triggerKey);
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    /**
     * 查询所有jobKey
     *
     * @param anyGroup
     */
    public Set<JobKey> getJobKeys(GroupMatcher<JobKey> anyGroup) throws SchedulerException {
        return scheduler.getJobKeys(anyGroup);
    }

    /**
     * 检查任务是否已存在
     *
     * @param jobKey
     * @throws SchedulerException
     */
    public Boolean checkExists(JobKey jobKey) throws SchedulerException {
        return scheduler.checkExists(jobKey);
    }

    /**
     * 查询触发器
     *
     * @param triggerKey
     * @throws SchedulerException
     */
    public Trigger getTrigger(TriggerKey triggerKey) throws SchedulerException {
        return scheduler.getTrigger(triggerKey);
    }

    /**
     * 查询任务分组
     */
    public List<String> getJobGroupNames() throws SchedulerException {
        return scheduler.getJobGroupNames();
    }

    /**
     * 获取定时任务状态
     *
     * @param triggerKeys 定时任务触发器key列表
     * @return 定时任务状态列表
     */
    public List<TriggerStateItem> listTriggerState(List<TriggerKey> triggerKeys) {
        if (CollectionUtils.isEmpty(triggerKeys)) {
            return CollectionUtils.emptyList();
        }
        return CollectionUtils.conversionList(triggerKeys, triggerKey -> {
            try {
                return getTriggerState(triggerKey);
            } catch (SchedulerException e) {
                log.error("get [{}] [{}] jobState error", triggerKey.getName(), triggerKey.getGroup(), e);
                throw new ScheduleException(ScheduleExceptionMessage.QUARTZJOB_GET_TRIGGER_STATE_EXCEPTION);
            }
        });
    }

    /**
     * 生成JobKey
     */
    public JobKey generateJobKey(String jobName, String groupName) {
        return JobKey.jobKey(jobName, groupName);
    }

    /**
     * 生成TriggerKey
     */
    public TriggerKey generateTriggerKey(String triggerName, String triggerGroup) {
        return TriggerKey.triggerKey(triggerName, triggerGroup);
    }

}
