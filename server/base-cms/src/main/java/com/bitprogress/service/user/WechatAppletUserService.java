package com.bitprogress.service.user;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.bitprogress.exception.BaseExceptionMessage;
import com.bitprogress.mapper.WechatAppletUserMapper;
import com.bitprogress.entity.WechatAppletUser;
import com.bitprogress.model.wechatappletuser.dto.WechatAppletUserCmsQueryDTO;
import com.bitprogress.model.wechatappletuser.vo.WechatAppletUserCmsVO;

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
    * 微信小程序用户信息 服务类
    * </p>
*
* @author wuwuwupx
* create on 2021-08-14
*/
@Service
@Slf4j
public class WechatAppletUserService extends ServiceImpl<WechatAppletUserMapper, WechatAppletUser> {

    public WechatAppletUserCmsVO findById(Long wechatAppletUserId) {
        return getWechatAppletUserById(wechatAppletUserId);
    }

    private WechatAppletUserCmsVO toWechatAppletUserCmsVO(WechatAppletUser wechatAppletUser) {
        return BeanUtils.copyNonNullProperties(wechatAppletUser, WechatAppletUserCmsVO.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteWechatAppletUsers(Set<Long> wechatAppletUserIds) {
        int count = baseMapper.deleteBatchIds(wechatAppletUserIds);
        Assert.isTrue(count == wechatAppletUserIds.size(), BaseExceptionMessage.WECHATAPPLETUSER_DELETE_EXCEPTION);
    }

    public IPage<WechatAppletUserCmsVO> findWechatAppletUserPage (WechatAppletUserCmsQueryDTO queryDTO, Page page) {
        QueryWrapper<WechatAppletUser> queryWrapper = new QueryWrapper<>();
        return PageUtils.conversionBean(page(page, queryWrapper), this::toWechatAppletUserCmsVO);
    }

    /**
     * 获取微信小程序用户信息
     *
     * @param wechatAppletUserId  微信小程序用户ID
     * @return WechatAppletUser
     */
    public WechatAppletUserCmsVO getWechatAppletUserById(Long wechatAppletUserId) {
        return toWechatAppletUserCmsVO(getById(wechatAppletUserId));
    }

    /**
     * 获取微信小程序用户信息集合
     *
     * @param wechatAppletUserIds  微信小程序用户ID集合
     * @return List<WechatAppletUser>
     */
    public List<WechatAppletUserCmsVO> listWechatAppletUserByIds(Set<Long> wechatAppletUserIds) {
        if (CollectionUtils.isEmpty(wechatAppletUserIds)) {
            return new ArrayList<>();
        }
        LambdaQueryChainWrapper<WechatAppletUser> query = lambdaQuery();
        query.in(WechatAppletUser::getWechatAppletUserId, wechatAppletUserIds);
        return CollectionUtils.conversionList(query.list(), this::toWechatAppletUserCmsVO);
    }

    /**
     * 根据用户ID集合获取微信小程序用户信息集合
     *
     * @param userIds  用户ID集合
     * @return List<WechatAppletUser>
     */
    public List<WechatAppletUserCmsVO> listWechatAppletUserByUserIds(Set<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        LambdaQueryChainWrapper<WechatAppletUser> query = lambdaQuery();
        query.in(WechatAppletUser::getUserId, userIds);
        return CollectionUtils.conversionList(query.list(), this::toWechatAppletUserCmsVO);
    }

}
