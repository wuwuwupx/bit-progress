package com.bitprogress.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.bitprogress.exception.BaseExceptionMessage;
import com.bitprogress.mapper.WechatUserMapper;
import com.bitprogress.model.login.WechatLoginDTO;
import com.bitprogress.entity.WechatUser;
import com.bitprogress.model.wechatuser.pojo.web.WechatUserWebVO;
import com.bitprogress.util.Assert;
import com.bitprogress.util.BeanUtils;
import com.bitprogress.util.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
public class WechatUserService extends ServiceImpl<WechatUserMapper, WechatUser> {

    public WechatUserWebVO findById(Long wechatUserId) {
        WechatUser wechatUser = getWechatUserById(wechatUserId);
        Assert.notNull(wechatUser, BaseExceptionMessage.WECHATUSER_NOT_EXIST_EXCEPTION);
        return toWechatUserWebVO(wechatUser);
    }

    public WechatUser getWechatUserById(Long wechatUserId) {
        return getById(wechatUserId);
    }

    public List<WechatUserWebVO> listWechatUser(Set<Long> wechatUserIds) {
        if (CollectionUtils.isEmpty(wechatUserIds)) {
            return new ArrayList<>();
        }
        LambdaQueryChainWrapper<WechatUser> query = lambdaQuery();
        query.in(WechatUser::getWechatUserId, wechatUserIds);
        return CollectionUtils.conversionList(query.list(), this::toWechatUserWebVO);
    }

    private WechatUserWebVO toWechatUserWebVO(WechatUser wechatUser) {
        return BeanUtils.copyNonNullProperties(wechatUser, WechatUserWebVO.class);
    }

    /**
     * 更新微信用户
     *
     * @param unionId
     * @param wechatLoginDTO
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Long updateUser(String unionId, WechatLoginDTO wechatLoginDTO) {
        LambdaQueryChainWrapper<WechatUser> query = lambdaQuery().eq(WechatUser::getUnionId, unionId);
        WechatUser wechatUser = getOne(query);
        String nickname = wechatLoginDTO.getNickname();
        String avatarUrl = wechatLoginDTO.getAvatarUrl();
        Integer gender = wechatLoginDTO.getGender();
        if (Objects.isNull(wechatUser)) {
            wechatUser = new WechatUser();
            wechatUser.setNickname(nickname)
                    .setAvatar(avatarUrl)
                    .setGender(gender);
            Assert.isTrue(save(wechatUser), BaseExceptionMessage.WECHATUSER_SAVE_EXCEPTION);
        } else {
            if (wechatLoginDTO.getAuthorized()) {
                LambdaUpdateChainWrapper<WechatUser> update = lambdaUpdate();
                update.set(WechatUser::getGender, gender)
                        .set(WechatUser::getNickname, nickname)
                        .set(WechatUser::getAvatar, avatarUrl);
                update(update);
            }
        }
        return wechatUser.getWechatUserId();
    }

}
