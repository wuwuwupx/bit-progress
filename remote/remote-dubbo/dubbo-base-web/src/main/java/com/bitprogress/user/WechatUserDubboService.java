package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.wechatuser.pojo.web.WechatUserWebVO;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 *  wechatUser dubbo remote
 */
public interface WechatUserDubboService {

    /**
     * 获取微信用户信息
     *
     * @param wechatUserId  微信用户ID
     * @return ResultVO<WechatUser>
     */
    ResultVO<WechatUserWebVO> getWechatUser(Long wechatUserId);

    /**
     * 获取微信用户信息
     *
     * @param wechatUserIds  微信用户ID集合
     * @return ResultVO<List<WechatUser>>
     */
    ResultVO<List<WechatUserWebVO>> listWechatUser(Set<Long> wechatUserIds);

}
