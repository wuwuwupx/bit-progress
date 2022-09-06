package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatuser.pojo.cms.WechatUserCmsVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * wechatUser feign remote
 */
@FeignClient(name = "main-base-cms", path = "rest/baseCms/wechatUser", fallbackFactory = WechatUserFeignServiceFallback.class)
public interface WechatUserFeignService {

    /**
     * 获取微信用户信息
     *
     * @param wechatUserId  微信用户ID
     * @return ResultVO<WechatUser>
     */
    @GetMapping
    ResultVO<WechatUserCmsVO> getWechatUser(@RequestParam Long wechatUserId);

    /**
     * 获取微信用户信息
     *
     * @param wechatUserIds  微信用户ID集合
     * @return ResultVO<List<WechatUser>>
     */
    @GetMapping("list")
    ResultVO<List<WechatUserCmsVO>> listWechatUser(@RequestParam Set<Long> wechatUserIds);

}
