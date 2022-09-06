package com.bitprogress.rest.dubbo;

import com.bitprogress.auth.AuthDubboService;
import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.login.LoginVO;
import com.bitprogress.model.login.LoginWebDTO;
import com.bitprogress.model.login.LogoutDTO;
import com.bitprogress.model.login.TokenDTO;
import com.bitprogress.service.AuthorizationService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wuwuwupx
 * 登录服务dubbo接口
 */
@Service(version = "1.0.0")
public class AuthDubboServiceImpl implements AuthDubboService {

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public ResultVO<LoginVO> login(LoginWebDTO loginDTO) {
        return ResultVO.successData(authorizationService.login(loginDTO.getUserId()));
    }

    @Override
    public ResultVO<BooleanVO> logout(LogoutDTO logoutDTO) {
        authorizationService.logout(logoutDTO.getUserId());
        return ResultVO.successData(BooleanVO.result(true));
    }

    @Override
    public ResultVO<LoginVO> getUserIdInToken(TokenDTO tokenDTO) {
        return ResultVO.successData(authorizationService.getUserIdInToken(tokenDTO.getToken()));
    }

    @Override
    public ResultVO<BooleanVO> checkToken(TokenDTO tokenDTO) {
        return ResultVO.successData(BooleanVO.result(authorizationService.checkToken(tokenDTO.getToken()).getResult()));
    }

}
