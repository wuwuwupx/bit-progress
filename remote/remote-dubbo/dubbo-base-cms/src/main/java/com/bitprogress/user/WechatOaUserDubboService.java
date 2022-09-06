package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatoauser.pojo.cms.WechatOaUserCmsVO;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * wechatOaUser feign remote
 */
public interface WechatOaUserDubboService {

    /**
     * 获取微信公众号用户信息
     *
     * @param wechatOaUserId  微信公众号用户ID
     * @return ResultVO<WechatOaUser>
     */
    ResultVO<WechatOaUserCmsVO> getWechatOaUser(Long wechatOaUserId);

    /**
     * 获取微信公众号用户信息
     *
     * @param wechatOaUserIds  微信公众号用户ID集合
     * @return ResultVO<List<WechatOaUser>>
     */
    ResultVO<List<WechatOaUserCmsVO>> listWechatOaUser(Set<Long> wechatOaUserIds);

    /**
     * 获取公众号微信用户信息
     *
     * @param userIds  用户ID集合
     * @return ResultVO<List<WechatOaUser>>
     */
    ResultVO<List<WechatOaUserCmsVO>> listWechatOaUserByUserIds(Set<Long> userIds);

}
