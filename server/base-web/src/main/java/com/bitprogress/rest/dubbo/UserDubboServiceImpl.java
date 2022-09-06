package com.bitprogress.rest.dubbo;

import com.bitprogress.model.user.pojo.web.UserWebVO;
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
     * @param userId 用户ID
     * @return 用户信息
     */
    @Override
    public ResultVO<UserWebVO> getUser(Long userId) {
        return ResultVO.successData(userService.findById(userId));
    }

    /**
     * 获取用户信息
     *
     * @param userIds 用户ID集合
     * @return 用户列表
     */
    @Override
    public ResultVO<List<UserWebVO>> listUser(Set<Long> userIds) {
        return ResultVO.successData(userService.listUser(userIds));
    }

}
