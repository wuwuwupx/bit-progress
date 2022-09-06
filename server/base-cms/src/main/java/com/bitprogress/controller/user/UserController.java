package com.bitprogress.controller.user;

import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.user.pojo.cms.UserCmsAddDTO;
import com.bitprogress.model.user.pojo.cms.UserCmsQueryDTO;
import com.bitprogress.model.user.pojo.cms.UserCmsUpdateDTO;
import com.bitprogress.model.user.pojo.cms.UserCmsVO;
import com.bitprogress.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bitprogress.model.ResultVO;

import javax.validation.Valid;
import java.util.Set;
import org.springframework.web.bind.annotation.RestController;

/**
* @author wuwuwupx
* created on 2021-08-13
*/
@Api(tags = {"用户信息"})
@RestController
@RequestMapping("/api/base/user/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation("查询详情")
    public ResultVO<UserCmsVO> findById(@RequestParam @ApiParam("userId") Long userId) {
        return ResultVO.successData(userService.findById(userId));
    }

    @PostMapping
    @ApiOperation("添加")
    public ResultVO<UserCmsVO> save(@RequestBody @Valid UserCmsAddDTO userAddDTO) {
        return ResultVO.successData(userService.saveUser(userAddDTO));
    }

    @DeleteMapping
    @ApiOperation("删除")
    public ResultVO<BooleanVO> delete(@RequestParam @ApiParam("userId列表") Set<Long> userIds) {
        userService.deleteUsers(userIds);
        return ResultVO.successData(BooleanVO.result(true));
    }

    @PutMapping
    @ApiOperation("修改")
    public ResultVO<UserCmsVO> update(@RequestBody @Valid UserCmsUpdateDTO userUpdateDTO) {
         return ResultVO.successData(userService.updateUser(userUpdateDTO));
    }

    @GetMapping("page")
    @ApiOperation("分页")
    public ResultVO<IPage<UserCmsVO>> page(@ModelAttribute UserCmsQueryDTO userQueryDTO, Page page) {
        return ResultVO.successData(userService.findUserPage(userQueryDTO,page));
    }

}

