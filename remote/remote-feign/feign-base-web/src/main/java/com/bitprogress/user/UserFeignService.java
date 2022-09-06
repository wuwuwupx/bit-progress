package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.user.pojo.web.UserWebVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 */
@FeignClient(name = "main-base-web", path = "rest/baseWeb/user", fallbackFactory = UserFeignServiceFallback.class)
public interface UserFeignService {

    /**
     * 获取用户信息
     *
     * @param userId  用户ID
     * @return ResultVO<User>
     */
    @GetMapping
    ResultVO<UserWebVO> getUser(@RequestParam Long userId);

    /**
     * 获取用户信息
     *
     * @param userIds  用户ID集合
     * @return ResultVO<List<User>>
     */
    @GetMapping("list")
    ResultVO<List<UserWebVO>> listUser(@RequestParam Set<Long> userIds);

}
