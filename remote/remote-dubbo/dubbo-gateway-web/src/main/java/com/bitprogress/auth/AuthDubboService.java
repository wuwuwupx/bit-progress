package com.bitprogress.auth;

import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.login.LoginVO;
import com.bitprogress.model.login.LoginWebDTO;
import com.bitprogress.model.login.LogoutDTO;
import com.bitprogress.model.login.TokenDTO;

/**
 * @author wuwuwupx
 * gateway-web dubbo远程服务
 */
public interface AuthDubboService {

    /**
     * 用户登录 -- 返回token
     *
     * @param dto
     * @return token
     */
    ResultVO<LoginVO> login(LoginWebDTO dto);

    /**
     * 退出登录
     *
     * @param dto
     * @return 状态码
     */
    ResultVO<BooleanVO> logout(LogoutDTO dto);

    /**
     * 解析token获取用户的userId
     *
     * @param tokenDTO
     * @return userId
     */
    ResultVO<LoginVO> getUserIdInToken(TokenDTO tokenDTO);

    /**
     * 校验token是否有效
     *
     * @param checkDTO
     * @return 是否通过
     */
    ResultVO<BooleanVO> checkToken(TokenDTO checkDTO);

}
