package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatuser.pojo.web.WechatUserWebVO;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 *  wechatUser feign remote fallback
 */
public class WechatUserFeignServiceFallback implements FallbackFactory<WechatUserFeignService> {

    /**
     * Returns an instance of the fallback appropriate for the given cause.
     *
     * @param cause cause of an exception.
     * @return fallback
     */
    @Override
    public WechatUserFeignService create(Throwable cause) {
        return new WechatUserFeignService() {
            /**
             * 获取微信用户信息
             *
             * @param wechatUserId 微信用户ID
             * @return ResultVO<WechatUser>
             */
            @Override
            public ResultVO<WechatUserWebVO> getWechatUser(Long wechatUserId) {
                return null;
            }

            /**
             * 获取微信用户信息
             *
             * @param wechatUserIds 微信用户ID集合
             * @return ResultVO<List < WechatUser>>
             */
            @Override
            public ResultVO<List<WechatUserWebVO>> listWechatUser(Set<Long> wechatUserIds) {
                return null;
            }
        };
    }

}
