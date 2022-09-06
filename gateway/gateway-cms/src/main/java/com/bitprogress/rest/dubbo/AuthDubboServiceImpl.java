package com.bitprogress.rest.dubbo;

import com.bitprogress.auth.AuthDubboService;
import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.login.*;
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

    /**
     * 用户登录 -- 返回token
     *
     * @param loginDTO  登录信息DTO
     * @return 返回token
     */
    @Override
    public ResultVO<LoginVO> login(LoginCmsDTO loginDTO) {
        return ResultVO.successData(authorizationService.login(loginDTO.getUserId()));
    }

    /**
     * 退出登录
     *
     * @param logoutDTO  退出登录信息DTO
     * @return 返回状态码
     */
    @Override
    public ResultVO<BooleanVO> logout(LogoutDTO logoutDTO) {
        authorizationService.logout(logoutDTO.getUserId());
        return ResultVO.successData(BooleanVO.result(true));
    }

    /**
     * 解析token获取用户的userId
     *
     * @param tokenDTO  需要解析的token
     * @return 返回userId
     */
    @Override
    public ResultVO<LoginVO> getUserIdInToken(TokenDTO tokenDTO) {
        return ResultVO.successData(authorizationService.getUserIdInToken(tokenDTO.getToken()));
    }

    /**
     * 校验token是否有效
     *
     * @param tokenDTO 检查的token
     * @return ResultVO<Boolean>
     */
    @Override
    public ResultVO<BooleanVO> checkToken(TokenDTO tokenDTO) {
        return ResultVO.successData(BooleanVO.result(authorizationService.checkToken(tokenDTO.getToken()).getResult()));
    }

}
