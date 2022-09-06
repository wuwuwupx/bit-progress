package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatappletuser.vo.WechatAppletUserWebVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * wechatAppletUser feign remote
 */
@FeignClient(name = "main-base-web", path = "rest/baseWeb/wechatAppletUser", fallbackFactory = WechatAppletUserFeignServiceFallback.class)
public interface WechatAppletUserFeignService {

    /**
     * 获取微信小程序用户信息
     *
     * @param wechatAppletUserId  微信小程序用户ID
     * @return ResultVO<WechatAppletUser>
     */
    @GetMapping
    ResultVO<WechatAppletUserWebVO> getWechatAppletUser(@RequestParam Long wechatAppletUserId);

    /**
     * 获取微信小程序用户信息
     *
     * @param wechatAppletUserIds  微信小程序用户ID集合
     * @return ResultVO<List<WechatAppletUser>>
     */
    @GetMapping("list")
    ResultVO<List<WechatAppletUserWebVO>> listWechatAppletUser(@RequestParam Set<Long> wechatAppletUserIds);

    /**
     * 获取微信小程序用户信息
     *
     * @param userIds  用户ID集合
     * @return ResultVO<List<WechatAppletUser>>
     */
    @GetMapping("list/userIds")
    ResultVO<List<WechatAppletUserWebVO>> listWechatAppletUserByUserIds(@RequestParam Set<Long> userIds);

}
