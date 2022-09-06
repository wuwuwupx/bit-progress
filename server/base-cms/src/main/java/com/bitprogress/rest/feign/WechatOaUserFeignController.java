package com.bitprogress.rest.feign;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatoauser.pojo.cms.WechatOaUserCmsVO;
import com.bitprogress.service.user.WechatOaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * wechatOaUser feign remote controller
 */
@RestController
@RequestMapping("rest/baseCms/wechatOaUser")
public class WechatOaUserFeignController {

    @Autowired
    private WechatOaUserService wechatOaUserService;

    /**
     * 获取微信公众号用户信息
     *
     * @param wechatOaUserId  微信公众号用户ID
     * @return ResultVO<WechatOaUser>
     */
    @GetMapping
    ResultVO<WechatOaUserCmsVO> getWechatOaUser(@RequestParam Long wechatOaUserId) {
        return ResultVO.successData(wechatOaUserService.findById(wechatOaUserId));
    }

    /**
     * 获取微信公众号用户信息
     *
     * @param wechatOaUserIds  微信公众号用户ID集合
     * @return ResultVO<List<WechatOaUser>>
     */
    @GetMapping("list")
    ResultVO<List<WechatOaUserCmsVO>> listWechatOaUser(@RequestParam Set<Long> wechatOaUserIds) {
        return ResultVO.successData(wechatOaUserService.listWechatOaUserByIds(wechatOaUserIds));
    }

    /**
     * 获取公众号微信用户信息
     *
     * @param userIds  用户ID集合
     * @return ResultVO<List<WechatOaUser>>
     */
    @GetMapping("list/userIds")
    ResultVO<List<WechatOaUserCmsVO>> listWechatOaUserByUserIds(@RequestParam Set<Long> userIds) {
        return ResultVO.successData(wechatOaUserService.listWechatOaUserByUserIds(userIds));
    }

}
