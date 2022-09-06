package com.bitprogress.controller.user;

import com.bitprogress.model.ResultVO;
import com.bitprogress.model.manager.pojo.cms.LoginSuccessVO;
import com.bitprogress.model.manager.pojo.cms.ManagerLoginDTO;
import com.bitprogress.service.user.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 *
 * @author wpx
 **/
@RestController
@Api(tags = "系统 -- 登录模块")
@RequestMapping("/api/base/login")
public class LoginController {

    @Resource
    private LoginService loginService;

    @ApiOperation("登录")
    @PostMapping
    public ResultVO<LoginSuccessVO> login(@RequestBody @Valid ManagerLoginDTO loginDTO) {
        return ResultVO.successData(loginService.login(loginDTO));
    }

}
