package com.bitprogress.rest.feign;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.user.pojo.web.UserWebVO;
import com.bitprogress.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * create on 2021/6/23 0:45
 * TestRemoteController is
 */
@RestController
@RequestMapping("rest/baseWeb/user")
public class UserFeignController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping
    public ResultVO<UserWebVO> getUser(@RequestParam Long userId) {
        return ResultVO.successData(userService.findById(userId));
    }

    /**
     * 获取用户信息
     *
     * @param userIds 用户ID集合
     * @return 用户列表
     */
    @GetMapping("list")
    public ResultVO<List<UserWebVO>> listUser(@RequestParam Set<Long> userIds) {
        return ResultVO.successData(userService.listUser(userIds));
    }

}
