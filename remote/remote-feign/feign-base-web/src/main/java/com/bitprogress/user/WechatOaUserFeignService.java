package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatoauser.pojo.web.WechatOaUserWebVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * wechatOaUser feign remote
 */
@FeignClient(name = "main-base-web", path = "rest/baseWeb/wechatOaUser", fallbackFactory = WechatOaUserFeignServiceFallback.class)
public interface WechatOaUserFeignService {

    /**
     * 获取微信公众号用户信息
     *
     * @param wechatOaUserId  微信公众号用户ID
     * @return ResultVO<WechatOaUser>
     */
    @GetMapping
    ResultVO<WechatOaUserWebVO> getWechatOaUser(@RequestParam Long wechatOaUserId);

    /**
     * 获取微信公众号用户信息
     *
     * @param wechatOaUserIds  微信公众号用户ID集合
     * @return ResultVO<List<WechatOaUser>>
     */
    @GetMapping("list")
    ResultVO<List<WechatOaUserWebVO>> listWechatOaUser(@RequestParam Set<Long> wechatOaUserIds);

    /**
     * 获取公众号微信用户信息
     *
     * @param userIds  用户ID集合
     * @return ResultVO<List<WechatOaUser>>
     */
    @GetMapping("list/userIds")
    ResultVO<List<WechatOaUserWebVO>> listWechatOaUserByUserIds(@RequestParam Set<Long> userIds);

}
