package com.bitprogress.rest.feign;

import com.bitprogress.model.ResultVO;
import com.bitprogress.entity.WechatAppletUser;
import com.bitprogress.model.wechatappletuser.vo.WechatAppletUserCmsVO;
import com.bitprogress.service.user.WechatAppletUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 *  wechatAppletUser
 */
@RestController
@RequestMapping("rest/baseCms/wechatAppletUser")
public class WechatAppletUserFeignController {

    @Autowired
    private WechatAppletUserService wechatAppletUserService;

    /**
     * 获取微信用户信息
     *
     * @param wechatAppletUserId  微信用户ID
     * @return ResultVO<WechatAppletUser>
     */
    @GetMapping
    ResultVO<WechatAppletUserCmsVO> getWechatAppletUser(@RequestParam Long wechatAppletUserId) {
        return ResultVO.successData(wechatAppletUserService.getWechatAppletUserById(wechatAppletUserId));
    }

    /**
     * 获取微信用户信息
     *
     * @param wechatAppletUserIds  微信用户ID集合
     * @return ResultVO<List<WechatAppletUser>>
     */
    @GetMapping("list")
    ResultVO<List<WechatAppletUserCmsVO>> listWechatAppletUser(@RequestParam Set<Long> wechatAppletUserIds) {
        return ResultVO.successData(wechatAppletUserService.listWechatAppletUserByIds(wechatAppletUserIds));
    }

    /**
     * 获取微信用户信息
     *
     * @param userIds  用户ID集合
     * @return ResultVO<List<WechatAppletUser>>
     */
    @GetMapping("list/userIds")
    ResultVO<List<WechatAppletUserCmsVO>> listWechatAppletUserByUserIds(@RequestParam Set<Long> userIds) {
        return ResultVO.successData(wechatAppletUserService.listWechatAppletUserByUserIds(userIds));
    }

}
