package com.bitprogress.service.quartzjob;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.bitprogress.exception.ScheduleException;
import com.bitprogress.entity.Group;
import com.bitprogress.entity.QuartzJob;
import com.bitprogress.mapper.QuartzJobMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobQueryDTO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobStateDTO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobUpdateDTO;
import com.bitprogress.model.quartzjob.pojo.envm.TriggerStateEnum;
import com.bitprogress.model.quartzjob.pojo.envm.TriggerType;
import com.bitprogress.model.quartzjob.pojo.vo.QuartzJobVO;
import com.bitprogress.model.quartzjob.pojo.dto.QuartzJobAddDTO;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bitprogress.quartz.TriggerStateItem;
import com.bitprogress.quartz.factory.QuartzFactory;
import com.bitprogress.service.group.GroupService;
import com.bitprogress.util.*;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bitprogress.exception.ScheduleExceptionMessage;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import static com.bitprogress.util.CollectionUtils.collectionToMap;

/**
 * <p>
 * 定时任务分组 服务类
 * </p>
 *
 * @author wpx
 * create on 2021-11-19
 */
@Service
@Slf4j
public class QuartzJobService extends ServiceImpl<QuartzJobMapper, QuartzJob> {

    @Autowired
    private QuartzFactory quartzFactory;

    @Autowired
    private GroupService groupService;

    /**
     * 查询任务信息
     * 需要补全当前的任务状态
     *
     * @param quartzJobId 任务ID
     * @return 定时任务信息
     */
    public QuartzJobVO findById(Long quartzJobId) {
        QuartzJob quartzJob = getById(quartzJobId);
        Assert.notNull(quartzJob, ScheduleExceptionMessage.QUARTZJOB_NOT_EXIST_EXCEPTION);
        Long groupId = quartzJob.getGroupId();
        QuartzJobVO quartzJobVO = toQuartzJobVO(quartzJob);
        String groupName = groupService.selectNameById(groupId);
        quartzJobVO.setGroupName(groupName);
        String name = String.valueOf(quartzJobId);
        String group = String.valueOf(groupId);
        try {
            TriggerStateItem triggerState = quartzFactory.getTriggerState(name, group);
            quartzJobVO.setTriggerState(triggerState.getTriggerState());
        } catch (SchedulerException e) {
            log.error("get [{}] [{}] jobState error [{}]", name, group, e.getMessage(), e);
            quartzJobVO.setTriggerState(TriggerStateEnum.NONE);
        }
        return quartzJobVO;
    }

    private QuartzJobVO toQuartzJobVO(QuartzJob quartzJob) {
        return BeanUtils.copyNonNullProperties(quartzJob, QuartzJobVO.class);
    }

    /**
     * 信息定时任务
     *
     * @param addDTO 新增定时任务信息
     * @return 新增后的定时任务信息
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public QuartzJobVO saveQuartzJob(QuartzJobAddDTO addDTO) {
        QuartzJob quartzJob = BeanUtils.copyNonNullProperties(addDTO, QuartzJob.class);
        checkTriggerType(quartzJob.getTriggerType(), quartzJob.getDuration(), quartzJob.getCronExpression());
        LocalDateTime time = LocalDateTime.now();
        quartzJob.setUpdateTime(time).setCreateTime(time).setDeleted(false);
        Assert.isTrue(save(quartzJob), ScheduleExceptionMessage.QUARTZJOB_SAVE_EXCEPTION);
        Long quartzJobId = quartzJob.getQuartzJobId();
        try {
            quartzFactory.createQuartzJob(quartzJob);
        } catch (SchedulerException e) {
            log.error("create job [{}] error [{}]", quartzJobId, e.getMessage(), e);
            throw new ScheduleException(ScheduleExceptionMessage.QUARTZJOB_SAVE_EXCEPTION);
        }
        return findById(quartzJobId);
    }

    /**
     * 检查任务的触发信息
     *
     * @param triggerType
     * @param duration
     * @param cronExpression
     */
    private void checkTriggerType(TriggerType triggerType, Long duration, String cronExpression) {
        if (TriggerType.CRON == triggerType) {
            Assert.isTrue(CronUtils.isValid(cronExpression), ScheduleExceptionMessage.QUARTZJOB_CRON_INVALID_EXCEPTION);
        } else {
            Assert.isTrue(Objects.nonNull(duration), ScheduleExceptionMessage.QUARTZJOB_DURATION_IS_NULL_EXCEPTION);
        }
    }

    /**
     * 删除定时任务
     *
     * @param quartzJobIds 定时任务ID集合
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteQuartzJobs(Set<Long> quartzJobIds) {
        if (CollectionUtils.isEmpty(quartzJobIds)) {
            return;
        }
        List<QuartzJob> quartzJobs = listGroupByIds(quartzJobIds);
        quartzJobs.forEach(this::removeQuartz);
        baseMapper.deleteBatchIds(quartzJobIds);
    }

    /**
     * 删除定时任务
     *
     * @param quartzJob 定时任务信息
     */
    private void removeQuartz(QuartzJob quartzJob) {
        Long quartzJobId = quartzJob.getQuartzJobId();
        String name = String.valueOf(quartzJobId);
        Long groupId = quartzJob.getGroupId();
        String group = String.valueOf(groupId);
        quartzFactory.removeJob(name, group);
    }

    /**
     * 更新定时任务信息
     *
     * @param updateDTO 定时任务信息
     * @return 修改后的定时任务信息
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public QuartzJobVO updateQuartzJob(QuartzJobUpdateDTO updateDTO) {
        QuartzJob quartzJob = BeanUtils.copyNonNullProperties(updateDTO, QuartzJob.class);
        Long quartzJobId = quartzJob.getQuartzJobId();
        Long dbGroupId = getGroupById(quartzJobId);
        quartzJob.setUpdateTime(LocalDateTime.now());
        Assert.isTrue(updateById(quartzJob), ScheduleExceptionMessage.QUARTZJOB_UPDATE_EXCEPTION);
        quartzFactory.removeJob(String.valueOf(quartzJobId), String.valueOf(dbGroupId));
        try {
            quartzFactory.createQuartzJob(quartzJob);
        } catch (SchedulerException e) {
            log.error("update job [{}] error [{}]", quartzJobId, e.getMessage(), e);
        }
        return findById(quartzJob.getQuartzJobId());
    }

    /**
     * 获取定时任务的分组
     *
     * @param quartzJobId 定时任务ID
     * @return 定时任务分组ID
     */
    private Long getGroupById(Long quartzJobId) {
        LambdaQueryChainWrapper<QuartzJob> query = lambdaQuery();
        query.select(QuartzJob::getQuartzJobId, QuartzJob::getGroupId).eq(QuartzJob::getQuartzJobId, quartzJobId);
        QuartzJob quartzJob = getOne(query);
        Assert.notNull(quartzJob, ScheduleExceptionMessage.QUARTZJOB_NOT_EXIST_EXCEPTION);
        return quartzJob.getGroupId();
    }

    /**
     * 分页获取定时任务
     *
     * @param queryDTO 查询信息
     * @param page     分页信息
     * @return 分页返回定时任务信息
     */
    public IPage<QuartzJobVO> findQuartzJobPage(QuartzJobQueryDTO queryDTO, Page page) {
        LambdaQueryChainWrapper<QuartzJob> query = lambdaQuery();
        Long groupId = queryDTO.getGroupId();
        String jobName = queryDTO.getJobName();
        TriggerType triggerType = queryDTO.getTriggerType();
        if (Objects.nonNull(groupId)) {
            query.eq(QuartzJob::getGroupId, groupId);
        }
        if (Objects.nonNull(triggerType)) {
            query.eq(QuartzJob::getTriggerType, triggerType);
        }
        if (StringUtils.nonEmpty(jobName)) {
            query.like(QuartzJob::getJobName, jobName);
        }
        IPage<QuartzJobVO> iPage = PageUtils.conversionBean(page(page, query), this::toQuartzJobVO);
        List<QuartzJobVO> records = iPage.getRecords();
        if (CollectionUtils.nonEmpty(records)) {
            Set<Long> groupIds = CollectionUtils.conversionSet(records, QuartzJobVO::getGroupId);
            List<Group> groups = groupService.listGroupNameByIds(groupIds);
            Map<Long, String> groupMap = collectionToMap(groups, Group::getGroupId, Group::getGroupName);
            List<TriggerKey> triggerKeys = CollectionUtils.conversionList(records, quartzJob -> {
                String name = String.valueOf(quartzJob.getQuartzJobId());
                String group = String.valueOf(quartzJob.getGroupId());
                return quartzFactory.generateTriggerKey(name, group);
            });
            List<TriggerStateItem> items = quartzFactory.listTriggerState(triggerKeys);
            Map<String, TriggerStateEnum> triggerStateMap = collectionToMap(items, TriggerStateItem::getName,
                    TriggerStateItem::getTriggerState);
            records.forEach(quartzJob -> {
                Long jobGroupId = quartzJob.getGroupId();
                quartzJob.setGroupName(groupMap.get(jobGroupId));
                quartzJob.setTriggerState(triggerStateMap.get(String.valueOf(quartzJob.getQuartzJobId())));
            });
        }
        return iPage;
    }

    /**
     * 获取定时任务列表
     *
     * @param quartzJobIds 定时任务ID集合
     * @return 定时任务列表
     */
    private List<QuartzJob> listGroupByIds(Set<Long> quartzJobIds) {
        if (CollectionUtils.isEmpty(quartzJobIds)) {
            return CollectionUtils.emptyList();
        }
        LambdaQueryChainWrapper<QuartzJob> query = lambdaQuery();
        query.select(QuartzJob::getQuartzJobId, QuartzJob::getGroupId).in(QuartzJob::getQuartzJobId, quartzJobIds);
        return list(query);
    }

    /**
     * 统计分组分配的任务数量
     * 有一条直接返回
     *
     * @param groupIds 任务分组ID集合
     * @return 任务分配数量
     */
    public Integer countByGroupIds(Set<Long> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return 0;
        }
        LambdaQueryChainWrapper<QuartzJob> query = lambdaQuery();
        query.in(QuartzJob::getGroupId, groupIds).last("limit 1");
        return count(query);
    }

    /**
     * 暂停定时任务
     *
     * @param stateDTO 暂停信息
     */
    public void pauseQuartzJob(QuartzJobStateDTO stateDTO) {
        Long quartzJobId = stateDTO.getQuartzJobId();
        Long groupId = getGroupById(quartzJobId);
        try {
            quartzFactory.pauseQuartzJob(String.valueOf(quartzJobId), String.valueOf(groupId));
        } catch (SchedulerException e) {
            log.error("pauseQuartzJob [{}] error [{}] ", quartzJobId, e.getMessage(), e);
        }
    }

    /**
     * 恢复定时任务
     *
     * @param stateDTO 恢复信息
     */
    public void rescheduleQuartzJob(QuartzJobStateDTO stateDTO) {
        Long quartzJobId = stateDTO.getQuartzJobId();
        Long groupId = getGroupById(quartzJobId);
        try {
            quartzFactory.rescheduleQuartzJob(String.valueOf(quartzJobId), String.valueOf(groupId));
        } catch (SchedulerException e) {
            log.error("rescheduleQuartzJob [{}] error [{}] ", quartzJobId, e.getMessage(), e);
        }
    }

}
