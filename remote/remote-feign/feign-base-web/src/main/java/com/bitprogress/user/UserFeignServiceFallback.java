package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.user.pojo.web.UserWebVO;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * UserFeignService的服务降级
 */
@Component
public class UserFeignServiceFallback implements FallbackFactory<UserFeignService> {

    /**
     * Returns an instance of the fallback appropriate for the given cause.
     *
     * @param cause cause of an exception.
     * @return fallback
     */
    @Override
    public UserFeignService create(Throwable cause) {
        return new UserFeignService() {
            /**
             * 获取用户信息
             *
             * @param userId 用户ID
             * @return ResultVO<User>
             */
            @Override
            public ResultVO<UserWebVO> getUser(Long userId) {
                return null;
            }

            /**
             * 获取用户信息
             *
             * @param userIds 用户ID集合
             * @return ResultVO<List < User>>
             */
            @Override
            public ResultVO<List<UserWebVO>> listUser(Set<Long> userIds) {
                return null;
            }
        };
    }

}
