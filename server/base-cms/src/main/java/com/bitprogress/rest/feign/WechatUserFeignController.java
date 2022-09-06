package com.bitprogress.rest.feign;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatuser.pojo.cms.WechatUserCmsVO;
import com.bitprogress.service.user.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * wechatUser feign remote
 */
@RestController
@RequestMapping("rest/baseCms/wechatUser")
public class WechatUserFeignController {

    @Autowired
    private WechatUserService wechatUserService;

    /**
     * 获取微信用户信息
     *
     * @param wechatUserId  微信用户ID
     * @return ResultVO<WechatUser>
     */
    @GetMapping
    public ResultVO<WechatUserCmsVO> getWechatUser(@RequestParam Long wechatUserId) {
        return ResultVO.successData(wechatUserService.findById(wechatUserId));
    }

    /**
     * 获取微信用户信息
     *
     * @param wechatUserIds  微信用户ID集合
     * @return ResultVO<List<WechatUser>>
     */
    @GetMapping("list")
    public ResultVO<List<WechatUserCmsVO>> listWechatUser(@RequestParam Set<Long> wechatUserIds) {
        return ResultVO.successData(wechatUserService.listWechatUser(wechatUserIds));
    }

}
