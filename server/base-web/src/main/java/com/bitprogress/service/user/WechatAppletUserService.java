package com.bitprogress.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bitprogress.exception.BaseExceptionMessage;
import com.bitprogress.mapper.WechatAppletUserMapper;
import com.bitprogress.model.DecryptResult;
import com.bitprogress.model.app.envm.AppTypeEnum;
import com.bitprogress.model.login.WechatLoginDTO;
import com.bitprogress.entity.WechatAppletUser;
import com.bitprogress.model.wechatappletuser.vo.WechatAppletUserWebVO;
import com.bitprogress.service.WechatLoginService;
import com.bitprogress.util.Assert;
import com.bitprogress.util.BeanUtils;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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

    @Autowired
    private UserService userService;

    /**
     * 获取微信小程序用户信息
     *
     * @param wechatAppletUserId 微信小程序用户ID
     * @return WechatAppletUser
     */
    public WechatAppletUserWebVO getWechatAppletUserById(Long wechatAppletUserId) {
        return toWechatAppletUserWebVO(getById(wechatAppletUserId));
    }

    public WechatAppletUserWebVO toWechatAppletUserWebVO(WechatAppletUser wechatAppletUser) {
        return BeanUtils.copyNonNullProperties(wechatAppletUser, WechatAppletUserWebVO.class);
    }

    /**
     * 获取微信小程序用户信息集合
     *
     * @param wechatAppletUserIds 微信小程序用户ID集合
     * @return List<WechatAppletUser>
     */
    public List<WechatAppletUserWebVO> listWechatAppletUserByIds(Set<Long> wechatAppletUserIds) {
        if (CollectionUtils.isEmpty(wechatAppletUserIds)) {
            return new ArrayList<>();
        }
        LambdaQueryChainWrapper<WechatAppletUser> query = lambdaQuery();
        query.in(WechatAppletUser::getWechatAppletUserId, wechatAppletUserIds);
        return CollectionUtils.conversionList(query.list(), this::toWechatAppletUserWebVO);
    }

    /**
     * 根据用户ID集合获取微信小程序用户信息集合
     *
     * @param userIds 用户ID集合
     * @return List<WechatAppletUser>
     */
    public List<WechatAppletUserWebVO> listWechatAppletUserByUserIds(Set<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        LambdaQueryChainWrapper<WechatAppletUser> query = lambdaQuery();
        query.in(WechatAppletUser::getUserId, userIds);
        return CollectionUtils.conversionList(query.list(), this::toWechatAppletUserWebVO);
    }

    /**
     * 更新小程序用户
     *
     * @param wechatLoginDTO
     * @param openId
     * @param unionId
     * @param wechatUserId
     * @param appId
     * @param appSign
     * @param sessionKey
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Long updateUser(WechatLoginDTO wechatLoginDTO, String openId, String unionId, Long wechatUserId,
                           Long appId, String appSign, String sessionKey) {

        LambdaQueryChainWrapper<WechatAppletUser> query = lambdaQuery().eq(WechatAppletUser::getOpenId, openId)
                .eq(WechatAppletUser::getUnionId, unionId)
                .eq(WechatAppletUser::getAppId, appId)
                .eq(WechatAppletUser::getAppSign, appSign);
        WechatAppletUser wechatAppletUser = getOne(query);

        Boolean authorized = wechatLoginDTO.getAuthorized();
        String nickname = wechatLoginDTO.getNickname();
        String avatarUrl = wechatLoginDTO.getAvatarUrl();
        Integer gender = wechatLoginDTO.getGender();
        String location = wechatLoginDTO.getCountry() + wechatLoginDTO.getProvince() + wechatLoginDTO.getCity();
        // 微信小程序用户为空，则在tb_user表也为空
        if (Objects.isNull(wechatAppletUser)) {
            Long userId = userService.addUser(appId, appSign, AppTypeEnum.WECHAT_APPLET);
            wechatAppletUser = new WechatAppletUser();
            wechatAppletUser.setUserId(userId)
                    .setWechatUserId(wechatUserId)
                    .setAppId(appId)
                    .setAppSign(appSign)
                    .setOpenId(openId)
                    .setUnionId(unionId)
                    .setNickname(nickname)
                    .setAvatar(avatarUrl)
                    .setGender(gender)
                    .setLocation(location)
                    .setSessionKey(sessionKey);
            Assert.isTrue(save(wechatAppletUser), BaseExceptionMessage.WECHATAPPLETUSER_SAVE_EXCEPTION);
        } else {
            LambdaUpdateChainWrapper<WechatAppletUser> update = lambdaUpdate();
            update.set(WechatAppletUser::getSessionKey, sessionKey);
            if (authorized) {
                update.set(WechatAppletUser::getLocation, location)
                        .set(WechatAppletUser::getNickname, nickname)
                        .set(WechatAppletUser::getAvatar, avatarUrl)
                        .set(WechatAppletUser::getGender, gender);
            }
            update(update);
        }
        return wechatAppletUser.getUserId();
    }

    /**
     * 更新用户的手机号码
     *
     * @param encryptedData
     * @param iv
     * @param userId
     */
    public void updatePhone(String encryptedData, String iv, Long userId) {
        LambdaQueryChainWrapper<WechatAppletUser> query = lambdaQuery().select(WechatAppletUser::getSessionKey)
                .eq(WechatAppletUser::getUserId, userId);
        WechatAppletUser wechatAppletUser = getOne(query);
        Assert.notNull(wechatAppletUser, BaseExceptionMessage.WECHATAPPLETUSER_NOT_EXIST_EXCEPTION);
        String sessionKey = wechatAppletUser.getSessionKey();
        DecryptResult decryptResult = WechatLoginService.encryptedWechatData(encryptedData, iv, sessionKey);
        if (Objects.nonNull(decryptResult) && StringUtils.nonEmpty(decryptResult.getPhoneNumber())) {
            LambdaUpdateChainWrapper<WechatAppletUser> update = lambdaUpdate();
            update.set(WechatAppletUser::getPhone, decryptResult.getPhoneNumber())
                    .eq(WechatAppletUser::getUserId, userId);
            update(update);
        }
    }

}
