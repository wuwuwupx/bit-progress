package com.bitprogress.auth;

import com.bitprogress.model.BooleanVO;
import com.bitprogress.model.ResultVO;
import com.bitprogress.model.login.LoginVO;
import com.bitprogress.model.login.LoginWebDTO;
import com.bitprogress.model.login.LogoutDTO;
import com.bitprogress.model.login.TokenDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wuwuwupx
 * create on 2021/6/14 19:53
 * Gateway模块的对外提供的授权和鉴权接口
 */
@FeignClient(name = "gateway-web", path = "rest/gatewayWeb/auth", fallbackFactory = AuthFeignServiceFallback.class)
public interface AuthFeignService {

    /**
     * 用户登录 -- 返回token
     *
     * @param loginDTO  登录信息DTO
     * @return token
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    ResultVO<LoginVO> login(@RequestBody LoginWebDTO loginDTO);

    /**
     * 退出登录
     *
     * @param logoutDTO  退出登录信息DTO
     * @return 状态码
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    ResultVO<BooleanVO> logout(@RequestBody LogoutDTO logoutDTO);

    /**
     * 解析token获取用户的userId
     *
     * @param token  需要解析的token
     * @return userId
     */
    @RequestMapping(value = "userId", method = RequestMethod.GET)
    ResultVO<LoginVO> getUserIdInToken(@RequestParam String token);

    /**
     * 校验token是否有效
     *
     * @param tokenDTO  需要检查的token
     * @return 是否通过
     */
    @RequestMapping(value = "checkToken", method = RequestMethod.POST)
    ResultVO<BooleanVO> checkToken(@RequestBody TokenDTO tokenDTO);

}
