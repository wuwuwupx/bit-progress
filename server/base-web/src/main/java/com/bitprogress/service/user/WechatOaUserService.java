package com.bitprogress.service.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.bitprogress.exception.BaseExceptionMessage;
import com.bitprogress.mapper.WechatOaUserMapper;
import com.bitprogress.model.DecryptResult;
import com.bitprogress.model.app.envm.AppTypeEnum;
import com.bitprogress.model.login.WechatLoginDTO;
import com.bitprogress.entity.WechatOaUser;
import com.bitprogress.model.wechatoauser.pojo.web.WechatOaUserWebVO;
import com.bitprogress.service.WechatLoginService;
import com.bitprogress.util.Assert;
import com.bitprogress.util.BeanUtils;
import com.bitprogress.util.CollectionUtils;
import com.bitprogress.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * 微信公众号用户信息 服务类
 * </p>
 *
 * @author wuwuwupx
 * create on 2021-08-14
 */
@Service
@Slf4j
public class WechatOaUserService extends ServiceImpl<WechatOaUserMapper, WechatOaUser> {

    @Autowired
    private UserService userService;

    /**
     * 获取微信公众号用户信息
     *
     * @param wechatOaUserId 微信公众号用户ID
     * @return 微信公众号用户
     */
    public WechatOaUserWebVO getWechatOaUserById(Long wechatOaUserId) {
        return toWechatOaUserWebVO(getById(wechatOaUserId));
    }

    public WechatOaUserWebVO toWechatOaUserWebVO(WechatOaUser wechatOaUser) {
        return BeanUtils.copyNonNullProperties(wechatOaUser, WechatOaUserWebVO.class);
    }

    /**
     * 获取微信公众号用户信息集合
     *
     * @param wechatOaUserIds 微信公众号用户ID列表
     * @return 微信公众号用户列表
     */
    public List<WechatOaUserWebVO> listWechatOaUserByIds(Set<Long> wechatOaUserIds) {
        if (CollectionUtils.isEmpty(wechatOaUserIds)) {
            return new ArrayList<>();
        }
        LambdaQueryChainWrapper<WechatOaUser> query = lambdaQuery();
        query.in(WechatOaUser::getWechatOaUserId, wechatOaUserIds);
        return CollectionUtils.conversionList(query.list(), this::toWechatOaUserWebVO);
    }

    /**
     * 获取微信公众号用户信息集合
     *
     * @param userIds 用户ID集合
     * @return List<WechatOaUser>
     */
    public List<WechatOaUserWebVO> listWechatOaUserByUserIds(Set<Long> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            return new ArrayList<>();
        }
        LambdaQueryChainWrapper<WechatOaUser> query = lambdaQuery();
        query.in(WechatOaUser::getUserId, userIds);
        return CollectionUtils.conversionList(query.list(), this::toWechatOaUserWebVO);
    }

    /**
     * 更新微信公众号用户
     *
     * @param wechatLoginDTO
     * @param openId
     * @param unionId
     * @param wechatUserId
     * @param appSign
     * @param appId
     * @return userId
     */
    public Long updateUser(WechatLoginDTO wechatLoginDTO, String openId, String unionId, Long wechatUserId,
                           String appSign, Long appId, String sessionKey) {

        LambdaQueryChainWrapper<WechatOaUser> query = lambdaQuery().eq(WechatOaUser::getOpenId, openId)
                .eq(WechatOaUser::getUnionId, unionId)
                .eq(WechatOaUser::getAppId, appId)
                .eq(WechatOaUser::getAppSign, appSign);
        WechatOaUser wechatOaUser = getOne(query);

        String nickname = wechatLoginDTO.getNickname();
        String avatarUrl = wechatLoginDTO.getAvatarUrl();
        Integer gender = wechatLoginDTO.getGender();
        String location = wechatLoginDTO.getCountry() + wechatLoginDTO.getProvince() + wechatLoginDTO.getCity();
        Boolean authorized = wechatLoginDTO.getAuthorized();
        // 微信公众号用户为空，则在tb_user表也为空
        if (Objects.isNull(wechatOaUser)) {
            Long userId = userService.addUser(appId, appSign, AppTypeEnum.WECHAT_OA);
            wechatOaUser = new WechatOaUser();
            wechatOaUser.setUserId(userId)
                    .setWechatUserId(wechatUserId)
                    .setAppId(appId)
                    .setAppSign(appSign)
                    .setOpenId(openId)
                    .setUnionId(unionId)
                    .setNickname(nickname)
                    .setAvatar(avatarUrl)
                    .setGender(gender)
                    .setLocation(location);
            Assert.isTrue(save(wechatOaUser), BaseExceptionMessage.WECHATOAUSER_SAVE_EXCEPTION);
        } else {
            LambdaUpdateChainWrapper<WechatOaUser> update = lambdaUpdate();
            update.set(WechatOaUser::getSessionKey, sessionKey);
            if (authorized) {
                update.set(WechatOaUser::getLocation, location)
                        .set(WechatOaUser::getNickname, nickname)
                        .set(WechatOaUser::getAvatar, avatarUrl)
                        .set(WechatOaUser::getGender, gender);
            }
            update(update);
        }
        return wechatOaUser.getUserId();
    }

    /**
     * 更新用户的手机号码
     *
     * @param encryptedData
     * @param iv
     * @param userId
     */
    public void updatePhone(String encryptedData, String iv, Long userId) {
        LambdaQueryChainWrapper<WechatOaUser> query = lambdaQuery().select(WechatOaUser::getSessionKey)
                .eq(WechatOaUser::getUserId, userId);
        WechatOaUser wechatOaUser = getOne(query);
        Assert.notNull(wechatOaUser, BaseExceptionMessage.WECHATAPPLETUSER_NOT_EXIST_EXCEPTION);
        String sessionKey = wechatOaUser.getSessionKey();
        DecryptResult decryptResult = WechatLoginService.encryptedWechatData(encryptedData, iv, sessionKey);
        if (Objects.nonNull(decryptResult) && StringUtils.nonEmpty(decryptResult.getPhoneNumber())) {
            LambdaUpdateChainWrapper<WechatOaUser> update = lambdaUpdate();
            update.set(WechatOaUser::getPhone, decryptResult.getPhoneNumber())
                    .eq(WechatOaUser::getUserId, userId);
            update(update);
        }
    }

}
