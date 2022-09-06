package com.bitprogress.rest.feign;

import com.bitprogress.model.ResultVO;
import com.bitprogress.entity.User;
import com.bitprogress.model.user.pojo.cms.UserCmsVO;
import com.bitprogress.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author wuwuwupx
 * create on 2021/6/23 0:45
 * TestRemoteController is
 */
@RestController
@RequestMapping("rest/baseCms/user")
public class UserFeignController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     *
     * @param userId  用户ID
     * @return ResultVO<User>
     */
    @GetMapping
    public ResultVO<UserCmsVO> getUser(@RequestParam Long userId) {
        return ResultVO.successData(userService.findById(userId));
    }

    /**
     * 获取用户信息
     *
     * @param userIds  用户ID集合
     * @return ResultVO<List<User>>
     */
    @GetMapping("list")
    public ResultVO<List<UserCmsVO>> listUser(@RequestParam Set<Long> userIds) {
        return ResultVO.successData(userService.listUser(userIds));
    }

}
