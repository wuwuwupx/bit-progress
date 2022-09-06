package com.bitprogress.service.group;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.bitprogress.entity.Group;
import com.bitprogress.mapper.GroupMapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.bitprogress.model.group.pojo.dto.GroupQueryDTO;
import com.bitprogress.model.group.pojo.dto.GroupUpdateDTO;
import com.bitprogress.model.group.pojo.vo.GroupVO;
import com.bitprogress.model.group.pojo.dto.GroupAddDTO;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bitprogress.service.quartzjob.QuartzJobService;
import com.bitprogress.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bitprogress.exception.ScheduleExceptionMessage;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

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
public class GroupService extends ServiceImpl<GroupMapper, Group> {

    @Autowired
    private QuartzJobService quartzJobService;

    /**
     * 获取任务分组信息
     *
     * @param groupId 分组ID
     * @return 分组信息
     */
    public GroupVO findById(Long groupId) {
        Group group = getById(groupId);
        Assert.notNull(group, ScheduleExceptionMessage.GROUP_NOT_EXIST_EXCEPTION);
        return toGroupVO(group);
    }

    private GroupVO toGroupVO(Group group) {
        return BeanUtils.copyNonNullProperties(group, GroupVO.class);
    }

    /**
     * 新增任务分组
     *
     * @param groupAddDTO 任务分组信息
     * @return 新增成功后的分组信息
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public GroupVO saveGroup(GroupAddDTO groupAddDTO) {
        Group group = BeanUtils.copyNonNullProperties(groupAddDTO, Group.class);
        LocalDateTime time = LocalDateTime.now();
        group.setUpdateTime(time).setCreateTime(time).setDeleted(false);
        Assert.isTrue(save(group), ScheduleExceptionMessage.GROUP_SAVE_EXCEPTION);
        return toGroupVO(group);
    }

    /**
     * 删除任务分组
     * 已分配定时任务的任务分组不能删除
     *
     * @param groupIds 任务分组ID集合
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteGroups(Set<Long> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return;
        }
        checkUnassigned(groupIds);
        baseMapper.deleteBatchIds(groupIds);
    }

    /**
     * 检查任务分组是否未分配
     *
     * @param groupIds 任务分组ID集合
     */
    private void checkUnassigned(Set<Long> groupIds) {
        Integer count = quartzJobService.countByGroupIds(groupIds);
        Assert.isTrue(count == 0, ScheduleExceptionMessage.GROUP_IS_ASSIGNED_EXCEPTION);
    }

    /**
     * 更新任务分组信息
     *
     * @param groupUpdateDTO 任务分组更新信息
     * @return 更新后的任务分组
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public GroupVO updateGroup(GroupUpdateDTO groupUpdateDTO) {
        Group group = BeanUtils.copyNonNullProperties(groupUpdateDTO, Group.class);
        group.setUpdateTime(LocalDateTime.now());
        Assert.isTrue(updateById(group), ScheduleExceptionMessage.GROUP_UPDATE_EXCEPTION);
        return findById(group.getGroupId());
    }

    /**
     * 分页查询任务分组
     *
     * @param queryDTO 查询条件
     * @param page     分页信息
     * @return 分页返回任务分组信息
     */
    public IPage<GroupVO> findGroupPage(GroupQueryDTO queryDTO, Page page) {
        String groupName = queryDTO.getGroupName();
        LambdaQueryChainWrapper<Group> query = lambdaQuery();
        if (StringUtils.nonEmpty(groupName)) {
            query.like(Group::getGroupName, groupName);
        }
        return PageUtils.conversionBean(page(page, query), this::toGroupVO);
    }

    /**
     * 获取任务分组列表
     */
    public List<GroupVO> listGroup() {
        return CollectionUtils.conversionList(list(), this::toGroupVO);
    }

    /**
     * 获取分组名称
     *
     * @param groupId 任务分组ID
     * @return 任务分组名称
     */
    public String selectNameById(Long groupId) {
        LambdaQueryChainWrapper<Group> query = lambdaQuery();
        query.select(Group::getGroupId, Group::getGroupName).eq(Group::getGroupId, groupId);
        Group group = getOne(query);
        Assert.notNull(group, ScheduleExceptionMessage.GROUP_NOT_EXIST_EXCEPTION);
        return group.getGroupName();
    }

    /**
     * 获取任务名称列表
     *
     * @param groupIds 分组ID集合
     */
    public List<Group> listGroupNameByIds(Set<Long> groupIds) {
        if (CollectionUtils.isEmpty(groupIds)) {
            return CollectionUtils.emptyList();
        }
        LambdaQueryChainWrapper<Group> query = lambdaQuery();
        query.select(Group::getGroupId, Group::getGroupName).in(Group::getGroupId, groupIds);
        return list(query);
    }

}
