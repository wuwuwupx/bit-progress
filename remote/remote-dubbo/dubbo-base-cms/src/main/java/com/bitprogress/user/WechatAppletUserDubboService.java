package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatappletuser.vo.WechatAppletUserCmsVO;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * wechatAppletUser feign remote
 */
public interface WechatAppletUserDubboService {

    /**
     * 获取微信用户信息
     *
     * @param wechatAppletUserId  微信用户ID
     * @return ResultVO<WechatAppletUser>
     */
    ResultVO<WechatAppletUserCmsVO> getWechatAppletUser(Long wechatAppletUserId);

    /**
     * 获取微信用户信息
     *
     * @param wechatAppletUserIds  微信用户ID集合
     * @return ResultVO<List<WechatAppletUser>>
     */
    ResultVO<List<WechatAppletUserCmsVO>> listWechatAppletUser(Set<Long> wechatAppletUserIds);

    /**
     * 获取微信用户信息
     *
     * @param userIds  用户ID集合
     * @return ResultVO<List<WechatAppletUser>>
     */
    ResultVO<List<WechatAppletUserCmsVO>> listWechatAppletUserByUserIds(Set<Long> userIds);

}
