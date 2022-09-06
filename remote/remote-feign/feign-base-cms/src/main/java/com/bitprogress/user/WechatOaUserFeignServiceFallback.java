package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatoauser.pojo.cms.WechatOaUserCmsVO;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 *  wechatOaUser feign remote fallback
 */
public class WechatOaUserFeignServiceFallback implements FallbackFactory<WechatOaUserFeignService> {

    /**
     * Returns an instance of the fallback appropriate for the given cause.
     *
     * @param cause cause of an exception.
     * @return fallback
     */
    @Override
    public WechatOaUserFeignService create(Throwable cause) {
        return new WechatOaUserFeignService() {
            /**
             * 获取微信公众号用户信息
             *
             * @param wechatOaUserId 微信公众号用户ID
             * @return ResultVO<WechatOaUser>
             */
            @Override
            public ResultVO<WechatOaUserCmsVO> getWechatOaUser(Long wechatOaUserId) {
                return null;
            }

            /**
             * 获取微信公众号用户信息
             *
             * @param wechatOaUserIds 微信公众号用户ID集合
             * @return ResultVO<List < WechatOaUser>>
             */
            @Override
            public ResultVO<List<WechatOaUserCmsVO>> listWechatOaUser(Set<Long> wechatOaUserIds) {
                return null;
            }

            /**
             * 获取公众号微信用户信息
             *
             * @param userIds 用户ID集合
             * @return ResultVO<List < WechatOaUser>>
             */
            @Override
            public ResultVO<List<WechatOaUserCmsVO>> listWechatOaUserByUserIds(Set<Long> userIds) {
                return null;
            }
        };
    }

}
