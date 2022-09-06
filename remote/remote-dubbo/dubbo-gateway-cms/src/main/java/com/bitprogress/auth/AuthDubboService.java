package com.bitprogress.auth;

import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.login.*;

/**
 * @author wuwuwupx
 */
public interface AuthDubboService {

    /**
     * 用户登录 -- 返回token
     *
     * @param loginDTO  登录信息DTO
     * @return 返回token
     */
    ResultVO<LoginVO> login(LoginCmsDTO loginDTO);

    /**
     * 退出登录
     *
     * @param logoutDTO  退出登录信息DTO
     * @return 返回状态码
     */
    ResultVO<BooleanVO> logout(LogoutDTO logoutDTO);

    /**
     * 解析token获取用户的userId
     *
     * @param tokenDTO  需要解析的token
     * @return 返回userId
     */
    ResultVO<LoginVO> getUserIdInToken(TokenDTO tokenDTO);

    /**
     * 校验token是否有效
     *
     * @param tokenDTO 检查的token
     * @return ResultVO<Boolean>
     */
    ResultVO<BooleanVO> checkToken(TokenDTO tokenDTO);

}
