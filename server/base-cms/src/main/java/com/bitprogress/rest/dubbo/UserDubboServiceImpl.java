package com.bitprogress.rest.dubbo;

import com.bitprogress.model.user.pojo.cms.UserCmsVO;
import com.bitprogress.user.UserDubboService;
import com.bitprogress.model.ResultVO;
import com.bitprogress.service.user.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * dubbo获取用户信息实现类
 */
@Service(version = "1.0.0")
public class UserDubboServiceImpl implements UserDubboService {

    @Autowired
    private UserService userService;

    /**
     * 返回用户信息
     *
     * @param userId
     */
    @Override
    public ResultVO<UserCmsVO> getUser(Long userId) {
        return ResultVO.successData(userService.findById(userId));
    }

    /**
     * 获取用户信息
     *
     * @param userIds 用户ID集合
     * @return ResultVO<List < User>>
     */
    @Override
    public ResultVO<List<UserCmsVO>> listUser(Set<Long> userIds) {
        return ResultVO.successData(userService.listUser(userIds));
    }

}
