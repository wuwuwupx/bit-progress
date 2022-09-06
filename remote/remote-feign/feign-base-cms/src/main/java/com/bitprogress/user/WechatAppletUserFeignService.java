package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatappletuser.vo.WechatAppletUserCmsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * wechatAppletUser feign remote
 */
@FeignClient(name = "main-base-cms", path = "rest/baseCms/wechatAppletUser", fallbackFactory = WechatAppletUserFeignServiceFallback.class)
public interface WechatAppletUserFeignService {

    /**
     * 获取微信小程序用户信息
     *
     * @param wechatAppletUserId  微信小程序用户ID
     * @return ResultVO<WechatAppletUser>
     */
    @GetMapping
    ResultVO<WechatAppletUserCmsVO> getWechatAppletUser(@RequestParam Long wechatAppletUserId);

    /**
     * 获取微信小程序用户信息
     *
     * @param wechatAppletUserIds  微信小程序用户ID集合
     * @return ResultVO<List<WechatAppletUser>>
     */
    @GetMapping("list")
    ResultVO<List<WechatAppletUserCmsVO>> listWechatAppletUser(@RequestParam Set<Long> wechatAppletUserIds);

    /**
     * 获取微信小程序用户信息
     *
     * @param userIds  用户ID集合
     * @return ResultVO<List<WechatAppletUser>>
     */
    @GetMapping("list/userIds")
    ResultVO<List<WechatAppletUserCmsVO>> listWechatAppletUserByUserIds(@RequestParam Set<Long> userIds);

}
