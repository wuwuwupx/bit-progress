package com.bitprogress.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.bitprogress.exception.BaseExceptionMessage;
import com.bitprogress.mapper.WechatOaUserMapper;
import com.bitprogress.entity.WechatOaUser;
import com.bitprogress.model.wechatoauser.pojo.cms.WechatOaUserCmsQueryDTO;
import com.bitprogress.model.wechatoauser.pojo.cms.WechatOaUserCmsVO;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.bitprogress.util.BeanUtils;
import com.bitprogress.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bitprogress.util.PageUtils;
import org.springframework.transaction.annotation.Transactional;
import com.bitprogress.util.Assert;

/**
 * <p>
 * 微信公众号用户信息 服务类
 * </p>
 *
 * @author wuwuwupx
 */
@Service
@Slf4j
public class WechatOaUserService extends ServiceImpl<WechatOaUserMapper, WechatOaUser> {

    public WechatOaUserCmsVO findById(Long wechatOaUserId) {
        WechatOaUser wechatOaUser = getWechatOaUserById(wechatOaUserId);
        Assert.notNull(wechatOaUser, BaseExceptionMessage.WECHATOAUSER_NOT_EXIST_EXCEPTION);
        return toWechatOaUserCmsVO(wechatOaUser);
    }

    private WechatOaUserCmsVO toWechatOaUserCmsVO(WechatOaUser wechatOaUser) {
        return BeanUtils.copyNonNullProperties(wechatOaUser, WechatOaUserCmsVO.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteWechatOaUsers(Set<Long> wechatOaUserIds) {
        int count = baseMapper.deleteBatchIds(wechatOaUserIds);
        Assert.isTrue(count == wechatOaUserIds.size(), BaseExceptionMessage.WECHATOAUSER_DELETE_EXCEPTION);
        log.info("删除数据:ids{}", wechatOaUserIds);
    }

    public IPage<WechatOaUserCmsVO> findWechatOaUserPage(WechatOaUserCmsQueryDTO queryDTO, Page page) {
        QueryWrapper<WechatOaUser> queryWrapper = new QueryWrapper<>();
        return PageUtils.conversionBean(page(page, queryWrapper), this::toWechatOaUserCmsVO);
    }

    /**
     * 获取微信公众号用户信息
     *
     * @param wechatOaUserId 微信公众号用户ID
     * @return WechatOaUser
     */
    public WechatOaUser getWechatOaUserById(Long wechatOaUserId) {
        return getById(wechatOaUserId);
    }

    /**
     * 获取微信公众号用户信息集合
     *
     * @param wechatOaUserIds 微信公众号用户ID集合
     * @return List<WechatOaUser>
     */
    public List<WechatOaUserCmsVO> listWechatOaUserByIds(Set<Long> wechatOaUserIds) {
        if (CollectionUtils.isEmpty(wechatOaUserIds)) {
            return new ArrayList<>();
        }
        LambdaQueryChainWrapper<WechatOaUser> query = lambdaQuery();
        query.in(WechatOaUser::getWechatOaUserId, wechatOaUserIds);
        return CollectionUtils.conversionList(query.list(), this::toWechatOaUserCmsVO);
    }

    /**
     * 获取微信公众号用户信息集合
     *
     * @param userIds 用户ID集合
     * @return List<WechatOaUser>
     */
    public List<WechatOaUserCmsVO> listWechatOaUserByUserIds(Set<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        LambdaQueryChainWrapper<WechatOaUser> query = lambdaQuery();
        query.in(WechatOaUser::getUserId, userIds);
        return CollectionUtils.conversionList(query.list(), this::toWechatOaUserCmsVO);
    }

}
