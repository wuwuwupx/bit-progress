package com.bitprogress.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.user.pojo.web.UserWebVO;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 */
public interface UserDubboService {

    /**
     * 获取用户信息
     *
     * @param userId  用户ID
     * @return ResultVO<User>
     */
    ResultVO<UserWebVO> getUser(Long userId);

    /**
     * 获取用户信息
     *
     * @param userIds  用户ID集合
     * @return ResultVO<List<User>>
     */
    ResultVO<List<UserWebVO>> listUser(Set<Long> userIds);

}
