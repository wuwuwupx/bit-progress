package com.bitprogress.rest.feign;

import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.login.LoginCmsDTO;
import com.bitprogress.model.login.LoginVO;
import com.bitprogress.model.login.LogoutDTO;
import com.bitprogress.model.login.TokenDTO;
import com.bitprogress.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author wuwuwupx
 * Cms端登录调用接口
 */
@RestController
@RequestMapping("rest/gatewayCms/auth")
public class AuthFeignController {

    @Autowired
    private AuthorizationService authorizationService;

    /**
     * 用户登录获取token
     *
     * @param loginDTO  登录信息DTO
     */
    @PostMapping("login")
    public ResultVO<LoginVO> login(@RequestBody LoginCmsDTO loginDTO) {
        return ResultVO.successData(authorizationService.login(loginDTO.getUserId()));
    }

    /**
     * 退出登录
     *
     * @param logoutDTO  退出登录信息DTO
     */
    @PostMapping("logout")
    public ResultVO<BooleanVO> logout(@RequestBody LogoutDTO logoutDTO) {
        authorizationService.logout(logoutDTO.getUserId());
        return ResultVO.successData(BooleanVO.result(true));
    }

    /**
     * 从token中解析userId
     *
     * @param tokenDTO  需要解析的token
     */
    @GetMapping("userId")
    public ResultVO<LoginVO> getUserIdInToken(@ModelAttribute TokenDTO tokenDTO) {
        return ResultVO.successData(authorizationService.getUserIdInToken(tokenDTO.getToken()));
    }

    /**
     * 检验token是否有效
     *
     * @param tokenDTO  需要检查的token
     */
    @PostMapping("checkToken")
    public ResultVO<BooleanVO> checkToken(@RequestBody TokenDTO tokenDTO) {
        return ResultVO.successData(BooleanVO.result(authorizationService.checkToken(tokenDTO.getToken()).getResult()));
    }

}
