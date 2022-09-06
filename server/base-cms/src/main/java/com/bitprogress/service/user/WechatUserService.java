package com.bitprogress.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.bitprogress.exception.BaseExceptionMessage;
import com.bitprogress.mapper.WechatUserMapper;
import com.bitprogress.entity.WechatUser;
import com.bitprogress.model.wechatuser.pojo.cms.WechatUserCmsAddDTO;
import com.bitprogress.model.wechatuser.pojo.cms.WechatUserCmsQueryDTO;
import com.bitprogress.model.wechatuser.pojo.cms.WechatUserCmsUpdateDTO;
import com.bitprogress.model.wechatuser.pojo.cms.WechatUserCmsVO;

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
import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;
import com.bitprogress.util.Assert;
/**
* <p>
    * 用户信息 服务类
    * </p>
*
* @author wuwuwupx
*/
@Service
@Slf4j
public class WechatUserService extends ServiceImpl<WechatUserMapper, WechatUser> {

    public WechatUserCmsVO findById(Long wechatUserId) {
        WechatUser wechatUser = getWechatUserById(wechatUserId);
        Assert.notNull(wechatUser, BaseExceptionMessage.WECHATUSER_NOT_EXIST_EXCEPTION);
        return toWechatUserCmsVO(wechatUser);
    }

    public WechatUser getWechatUserById(Long wechatUserId) {
        return getById(wechatUserId);
    }

    public List<WechatUserCmsVO> listWechatUser(Set<Long> wechatUserIds) {
        if (CollectionUtils.isEmpty(wechatUserIds)) {
            return new ArrayList<>();
        }
        LambdaQueryChainWrapper<WechatUser> query = lambdaQuery();
        query.in(WechatUser::getWechatUserId, wechatUserIds);
        return CollectionUtils.conversionList(query.list(), this::toWechatUserCmsVO);
    }

    private WechatUserCmsVO toWechatUserCmsVO(WechatUser wechatUser) {
        return BeanUtils.copyNonNullProperties(wechatUser, WechatUserCmsVO.class);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public WechatUserCmsVO saveWechatUser(WechatUserCmsAddDTO wechatUserAddDTO) {
        WechatUser wechatUser = BeanUtils.copyNonNullProperties(wechatUserAddDTO, WechatUser.class);
        LocalDateTime time=LocalDateTime.now();
        wechatUser.setUpdateTime(time).setCreateTime(time).setDeleted(false);
        Assert.isTrue(save(wechatUser), BaseExceptionMessage.WECHATUSER_SAVE_EXCEPTION);
        log.info("保存数据---:{}", wechatUser);
        return toWechatUserCmsVO(wechatUser);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void deleteWechatUsers(Set<Long> wechatUserIds) {
        int count = baseMapper.deleteBatchIds(wechatUserIds);
        Assert.isTrue(count == wechatUserIds.size(), BaseExceptionMessage.WECHATUSER_DELETE_EXCEPTION);
        log.info("删除数据:ids{}", wechatUserIds);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public WechatUserCmsVO updateWechatUser(WechatUserCmsUpdateDTO wechatUserUpdateDTO) {
        WechatUser wechatUser = BeanUtils.copyNonNullProperties(wechatUserUpdateDTO, WechatUser.class);
        wechatUser.setUpdateTime(LocalDateTime.now());
        Assert.isTrue(updateById(wechatUser), BaseExceptionMessage.WECHATUSER_UPDATE_EXCEPTION);
        log.info("修改数据：bean:{}", wechatUserUpdateDTO);
        return findById(wechatUser.getWechatUserId());
    }

    public IPage<WechatUserCmsVO> findWechatUserPage (WechatUserCmsQueryDTO wechatUserQueryDTO, Page page) {
        QueryWrapper<WechatUser> queryWrapper = new QueryWrapper<>();
        return PageUtils.conversionBean(page(page, queryWrapper), this::toWechatUserCmsVO);
    }
}
