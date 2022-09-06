package com.bitprogress.rest.dubbo;

import com.bitprogress.model.wechatoauser.pojo.web.WechatOaUserWebVO;
import com.bitprogress.user.WechatOaUserDubboService;
import com.bitprogress.model.ResultVO;
import com.bitprogress.service.user.WechatOaUserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * wechatOaUser dubbo remote impl
 */
@Service(version = "1.0.0")
public class WechatOaUserDubboServiceImpl implements WechatOaUserDubboService {

    @Autowired
    private WechatOaUserService wechatOaUserService;

    /**
     * 获取微信公众号用户信息
     *
     * @param wechatOaUserId  微信公众号用户ID
     * @return ResultVO<WechatOaUser>
     */
    @Override
    public ResultVO<WechatOaUserWebVO> getWechatOaUser(Long wechatOaUserId) {
        return ResultVO.successData(wechatOaUserService.getWechatOaUserById(wechatOaUserId));
    }

    /**
     * 获取微信公众号用户信息
     *
     * @param wechatOaUserIds  微信公众号用户ID集合
     * @return ResultVO<List<WechatOaUser>>
     */
    @Override
    public ResultVO<List<WechatOaUserWebVO>> listWechatOaUser(Set<Long> wechatOaUserIds) {
        return ResultVO.successData(wechatOaUserService.listWechatOaUserByIds(wechatOaUserIds));
    }

    /**
     * 获取公众号微信用户信息
     *
     * @param userIds  用户ID集合
     * @return ResultVO<List<WechatOaUser>>
     */
    @Override
    public ResultVO<List<WechatOaUserWebVO>> listWechatOaUserByUserIds(Set<Long> userIds) {
        return ResultVO.successData(wechatOaUserService.listWechatOaUserByUserIds(userIds));
    }

}
