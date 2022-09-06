package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatappletuser.vo.WechatAppletUserWebVO;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 *  wechatAppletUser feign remote fallback
 */
@Component
public class WechatAppletUserFeignServiceFallback implements FallbackFactory<WechatAppletUserFeignService> {

    /**
     * Returns an instance of the fallback appropriate for the given cause.
     *
     * @param cause cause of an exception.
     * @return fallback
     */
    @Override
    public WechatAppletUserFeignService create(Throwable cause) {
        return new WechatAppletUserFeignService() {
            /**
             * 获取微信小程序用户信息
             *
             * @param wechatAppletUserId 微信小程序用户ID
             * @return ResultVO<WechatAppletUser>
             */
            @Override
            public ResultVO<WechatAppletUserWebVO> getWechatAppletUser(Long wechatAppletUserId) {
                return null;
            }

            /**
             * 获取微信小程序用户信息
             *
             * @param wechatAppletUserIds 微信小程序用户ID集合
             * @return ResultVO<List < WechatAppletUser>>
             */
            @Override
            public ResultVO<List<WechatAppletUserWebVO>> listWechatAppletUser(Set<Long> wechatAppletUserIds) {
                return null;
            }

            /**
             * 获取微信小程序用户信息
             *
             * @param userIds 用户ID集合
             * @return ResultVO<List < WechatAppletUser>>
             */
            @Override
            public ResultVO<List<WechatAppletUserWebVO>> listWechatAppletUserByUserIds(Set<Long> userIds) {
                return null;
            }
        };
    }

}
