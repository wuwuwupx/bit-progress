package com.bitprogress.rest.dubbo;

import com.bitprogress.model.wechatappletuser.vo.WechatAppletUserWebVO;
import com.bitprogress.user.WechatAppletUserDubboService;
import com.bitprogress.model.ResultVO;
import com.bitprogress.service.user.WechatAppletUserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 *  wechatAppleUser dubbo remote
 */
@Service(version = "1.0.0")
public class WechatAppletUserDubboServiceImpl implements WechatAppletUserDubboService {

    @Autowired
    private WechatAppletUserService wechatAppletUserService;

    /**
     * 获取微信用户信息
     *
     * @param wechatAppletUserId 微信用户ID
     * @return ResultVO<WechatAppletUser>
     */
    @Override
    public ResultVO<WechatAppletUserWebVO> getWechatAppletUser(Long wechatAppletUserId) {
        return ResultVO.successData(wechatAppletUserService.getWechatAppletUserById(wechatAppletUserId));
    }

    /**
     * 获取微信用户信息
     *
     * @param wechatAppletUserIds 微信用户ID集合
     * @return ResultVO<List < WechatAppletUser>>
     */
    @Override
    public ResultVO<List<WechatAppletUserWebVO>> listWechatAppletUser(Set<Long> wechatAppletUserIds) {
        return ResultVO.successData(wechatAppletUserService.listWechatAppletUserByIds(wechatAppletUserIds));
    }

    /**
     * 获取微信用户信息
     *
     * @param userIds 用户ID集合
     * @return ResultVO<List < WechatAppletUser>>
     */
    @Override
    public ResultVO<List<WechatAppletUserWebVO>> listWechatAppletUserByUserIds(Set<Long> userIds) {
        return ResultVO.successData(wechatAppletUserService.listWechatAppletUserByUserIds(userIds));
    }

}
