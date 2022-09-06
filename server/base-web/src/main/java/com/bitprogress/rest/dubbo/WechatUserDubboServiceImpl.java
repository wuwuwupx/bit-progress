package com.bitprogress.rest.dubbo;

import com.bitprogress.model.wechatuser.pojo.web.WechatUserWebVO;
import com.bitprogress.user.WechatUserDubboService;
import com.bitprogress.model.ResultVO;
import com.bitprogress.service.user.WechatUserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * wechatUser dubbo remote
 */
@Service(version = "1.0.0")
public class WechatUserDubboServiceImpl implements WechatUserDubboService {

    @Autowired
    private WechatUserService wechatUserService;

    /**
     * 获取微信用户信息
     *
     * @param wechatUserId 微信用户ID
     * @return 微信用户
     */
    @Override
    public ResultVO<WechatUserWebVO> getWechatUser(Long wechatUserId) {
        return ResultVO.successData(wechatUserService.findById(wechatUserId));
    }

    /**
     * 获取微信用户信息
     *
     * @param wechatUserIds 微信用户ID列表
     * @return 微信用户列表
     */
    @Override
    public ResultVO<List<WechatUserWebVO>> listWechatUser(Set<Long> wechatUserIds) {
        return ResultVO.successData(wechatUserService.listWechatUser(wechatUserIds));
    }

}
