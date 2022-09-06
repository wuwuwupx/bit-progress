package com.bitprogress.service.user;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bitprogress.exception.BaseExceptionMessage;
import com.bitprogress.mapper.UserMapper;
import com.bitprogress.model.app.envm.AppTypeEnum;
import com.bitprogress.entity.User;
import com.bitprogress.model.user.pojo.web.UserWebVO;
import com.bitprogress.util.BeanUtils;
import com.bitprogress.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;
import com.bitprogress.util.Assert;

/**
 * <p>
 * 用户信息 服务类
 * </p>
 *
 * @author wuwuwupx
 * created on 2021-08-13
 */
@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {

    public UserWebVO findById(Long userId) {
        User user = getUserById(userId);
        Assert.notNull(user, BaseExceptionMessage.USER_NOT_EXIST_EXCEPTION);
        return toUserWebVO(user);
    }

    public User getUserById(Long userId) {
        return getById(userId);
    }

    private UserWebVO toUserWebVO(User user) {
        return BeanUtils.copyNonNullProperties(user, UserWebVO.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public UserWebVO saveUser(User user) {
        LocalDateTime time = LocalDateTime.now();
        user.setUpdateTime(time).setCreateTime(time).setDeleted(false);
        Assert.isTrue(save(user), BaseExceptionMessage.USER_SAVE_EXCEPTION);
        return toUserWebVO(user);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteUsers(Set<Long> userIds) {
        int count = baseMapper.deleteBatchIds(userIds);
        Assert.isTrue(count == userIds.size(), BaseExceptionMessage.USER_DELETE_EXCEPTION);
    }

    /**
     * 根据ID集合查询用户信息
     *
     * @param userIds 用户ID集合
     * @return 用户列表
     */
    public List<UserWebVO> listUser(Set<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        LambdaQueryChainWrapper<User> query = lambdaQuery();
        query.in(User::getUserId, userIds);
        return CollectionUtils.conversionList(query.list(), this::toUserWebVO);
    }

    /**
     * 新增用户
     *
     * @param appId
     * @param appSign
     * @param appType
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Long addUser(Long appId, String appSign, AppTypeEnum appType) {
        User user = new User();
        user.setAppId(appId)
                .setAppSign(appSign)
                .setAppType(appType)
                .setFlag(false);
        Assert.isTrue(save(user), BaseExceptionMessage.USER_SAVE_EXCEPTION);
        return user.getUserId();
    }

    /**
     * 获取用户的应用类型
     *
     * @param userId
     */
    public AppTypeEnum getUserAppTypeById(Long userId) {
        LambdaQueryChainWrapper<User> query = lambdaQuery();
        query.select(User::getAppType).eq(User::getUserId, userId);
        User user = getOne(query);
        Assert.notNull(user, BaseExceptionMessage.USER_NOT_EXIST_EXCEPTION);
        return user.getAppType();
    }

}
